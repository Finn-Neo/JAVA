package com.hmdp.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.dto.Result;
import com.hmdp.entity.Shop;
import com.hmdp.mapper.ShopMapper;
import com.hmdp.service.IShopService;
import com.hmdp.utils.CacheClient;
import com.hmdp.utils.RedisData;
import com.hmdp.utils.SystemConstants;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.domain.geo.GeoReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.hmdp.utils.RedisConstants.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements IShopService {
	@Resource
	private StringRedisTemplate stringRedisTemplate;

	@Resource
	private CacheClient cacheClient;

	@Override
	public Result queryById(Long id) {
		//缓存穿透
		//Shop shop = cacheClient.
		//		queryWithPassThrough(CACHE_SHOP_KEY, id, Shop.class, this::getById, CACHE_SHOP_TTL, TimeUnit.MINUTES);

		//互斥锁解决缓存击穿 + 缓存穿透
		//Shop shop = queryWithMutex(id);

		//逻辑过期
		Shop shop = cacheClient.
				queryWithLogicalExpire(CACHE_SHOP_KEY, id, Shop.class, this::getById, CACHE_SHOP_TTL, TimeUnit.SECONDS);

		if (shop == null) {
			return Result.fail("店铺不存在");
		}
		//返回
		return Result.ok(shop);
	}

	//互斥锁
	//public Shop queryWithMutex(Long id) {
	//	String key = CACHE_SHOP_KEY + id;
	//	// 1.从redis查询商铺缓存
	//	String shopJson = stringRedisTemplate.opsForValue().get(key);
	//	//2.判断是否存在
	//	if (StrUtil.isNotBlank(shopJson)) {
	//		//3.存在，直接返回
	//		return JSONUtil.toBean(shopJson, Shop.class);
	//	}
	//	//命中的是否是空值
	//	if (shopJson != null) {
	//		//返回错误信息
	//		return null;
	//	}
	//	//4.不存在，实现缓存重建
	//	//4.1尝试获取互斥锁
	//	String lockKey = LOCK_SHOP_KEY + id;
	//	Shop shop = null;
	//	try {
	//		boolean isLock = tryLock(lockKey);
	//		//4.2判断是否获取成功
	//		if (!isLock) {
	//			try {
	//				//4.3失败：休眠重试
	//				Thread.sleep(50);
	//				return queryWithMutex(id);//递归，重来一次 TODO 使用while
	//			} catch (InterruptedException e) {
	//				throw new RuntimeException(e);
	//			}
	//		}
	//		//4.4成功：double check 如果存在无需重建缓存
	//		//4.4.1.从redis查询商铺缓存
	//		shopJson = stringRedisTemplate.opsForValue().get(key);
	//		//4.4.2.判断是否存在
	//		if (StrUtil.isNotBlank(shopJson)) {
	//			//4.4.3.存在，直接返回
	//			return JSONUtil.toBean(shopJson, Shop.class);
	//		}
	//		//4.4.4：根据id查询数据库
	//		shop = getById(id);
	//		//休眠
	//		Thread.sleep(200);
	//		//5.不存在，返回错误
	//		if (shop == null) {
	//			//将null写入redis
	//			stringRedisTemplate.opsForValue().set(key, "", CACHE_NULL_TTL, TimeUnit.MINUTES);
	//			return null;
	//		}
	//		//6.存在，写入redis，设置有效期（timeout）30分钟
	//		stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(shop), CACHE_SHOP_TTL, TimeUnit.MINUTES);
	//	} catch (RuntimeException e) {
	//		throw new RuntimeException(e);
	//	} catch (InterruptedException e) {
	//		throw new RuntimeException(e);
	//	} finally {
	//		//7.释放锁
	//		unLock(lockKey);
	//	}
	//
	//	//8.返回
	//	return shop;
	//}

	//逻辑过期 数据预热
	public void saveShop2Redis(Long id, Long expireSeconds) {
		//1.查询数据
		Shop shop = getById(id);
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		//2.封装逻辑过期时间
		RedisData redisData = new RedisData();
		redisData.setData(shop);
		redisData.setExpireTime(LocalDateTime.now().plusSeconds(expireSeconds));
		//3.写入redis
		stringRedisTemplate.opsForValue().set(CACHE_SHOP_KEY + id, JSONUtil.toJsonStr(redisData));
	}

	@Override
	@Transactional//更新数据库和删除缓存做成一个事务，如果删除缓存不成功，会导致数据不一致
	public Result update(Shop shop) {
		Long id = shop.getId();
		if(id == null) {
			return Result.fail("商铺id不能为空");
		}
		//更新数据库
		updateById(shop);
		//删除缓存
		stringRedisTemplate.delete(CACHE_SHOP_KEY + id);

		return Result.ok();
	}

	@Override
	public Result queryShopByType(Integer typeId, Integer current, Double x, Double y) {
		//判断是否需要根据坐标查询 x y
		if(x == null || y == null) {
			//不需要根据坐标查询
			Page<Shop> page = query()
					.eq("type_id", typeId)
					.page(new Page<>(current, SystemConstants.DEFAULT_PAGE_SIZE));
			return Result.ok(page.getRecords());
		}
		//计算分页参数
		int from = (current - 1) * SystemConstants.DEFAULT_PAGE_SIZE;
		int end = current * SystemConstants.DEFAULT_PAGE_SIZE;
		//查询redis，按照距离排序、分页。结果：shopId, distance
		String key = SHOP_GEO_KEY + typeId;
		GeoResults<RedisGeoCommands.GeoLocation<String>> results = stringRedisTemplate.opsForGeo()
				.search(key,
						GeoReference.fromCoordinate(x, y),
						new Distance(5000),
						RedisGeoCommands.GeoSearchCommandArgs.newGeoSearchArgs().includeDistance().limit(end));
		//解析id
		if(results == null) {
			return Result.ok(Collections.emptyList());
		}
		List<GeoResult<RedisGeoCommands.GeoLocation<String>>> list = results.getContent();
		//截取from - end的部分
		List<Long> ids = new ArrayList<>(list.size());
		Map<String, Distance> distanceMap = new HashMap<>(list.size());
		list.stream()
				.skip(from)
				.forEach(result -> {
					String shopIdStr = result.getContent().getName();
					ids.add(Long.valueOf(shopIdStr));
					Distance distance = result.getDistance();
					distanceMap.put(shopIdStr, distance);
				});
		//根据id查询shop
		String idStr = StrUtil.join(",", ids);
		List<Shop> shops = query().in("id", ids)
				.last("order by field(id," +  idStr + ")")
				.list();
		for(Shop shop : shops) {
			shop.setDistance(distanceMap.get(shop.getId().toString()).getValue());
		}
		//返回
		return Result.ok(shops);
	}
}
