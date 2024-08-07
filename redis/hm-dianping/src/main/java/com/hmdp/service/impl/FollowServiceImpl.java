package com.hmdp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.dto.Result;
import com.hmdp.dto.UserDTO;
import com.hmdp.entity.Follow;
import com.hmdp.mapper.FollowMapper;
import com.hmdp.service.IFollowService;
import com.hmdp.service.IUserService;
import com.hmdp.utils.UserHolder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements IFollowService {
	@Resource
	private StringRedisTemplate stringRedisTemplate;

	@Resource
	private IUserService userService;

	@Override
	public Result follow(Long followUserId, Boolean isFollow) {
		//获取当前登录的用户信息
		Long userId = UserHolder.getUser().getId();
		String key = "follows:" + userId;
		//判断是关注/取关
		if (isFollow) {
			//关注，添加
			Follow follow = new Follow();
			follow.setFollowUserId(followUserId);
			follow.setUserId(userId);
			boolean isSuccess = save(follow);
			if (isSuccess) {
				//将关注用户的id，放入到redis的set集合 sadd userId followUserId
				stringRedisTemplate.opsForSet().add(key, followUserId.toString());
			}
		} else {
			//取关，删除 delete from tb_follow where userId=? and follow_user_id=?
			boolean isSuccess = remove(new QueryWrapper<Follow>()
					.eq("user_id", userId)
					.eq("follow_user_id", followUserId));
			if (isSuccess) {
				//从redis集合中移除关注用户的id
				stringRedisTemplate.opsForZSet().remove(key, followUserId.toString());
			}
		}
		return Result.ok(followUserId);
	}

	@Override
	public Result isFollow(Long followUserId) {
		//获取当前登录的用户信息
		Long userId = UserHolder.getUser().getId();
		//查询是否关注
		Integer count = query().eq("user_id", userId).eq("follow_user_id", followUserId).count();
		//判断是否关注
		return Result.ok(count > 0);
	}

	@Override
	public Result followCommons(Long id) {
		//获取当前登录用户
		Long userId = UserHolder.getUser().getId();
		String key = "follows:" + userId;
		String key2 = "follows:" + id;
		//求交集
		Set<String> intersect = stringRedisTemplate.opsForSet().intersect(key, key2);
		if (intersect == null || intersect.isEmpty()) {
			return Result.ok(Collections.emptyList());
		}
		//解析用户id
		List<Long> ids = intersect.stream().map(Long::valueOf).collect(Collectors.toList());
		//查询用户
		List<UserDTO> userDTOS = userService.listByIds(ids)
				.stream()
				.map(user -> BeanUtil.copyProperties(user, UserDTO.class))
				.collect(Collectors.toList());

		return Result.ok(userDTOS);
	}
}
