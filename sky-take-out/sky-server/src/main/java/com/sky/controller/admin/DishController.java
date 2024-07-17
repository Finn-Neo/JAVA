package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * @program: sky-take-out
 * @author: Qiaolezi
 * @create: 2024-07-11 16:18
 * @description: 菜品管理
 **/
@RestController("adminDishController")
@RequestMapping("/admin/dish")
@Api(tags = "菜品相关接口")
@Slf4j
public class DishController {
	@Autowired
	private DishService dishService;
	@Autowired
	private RedisTemplate redisTemplate;

	/**
	 * 新增菜品
	 * @param dishDTO
	 * @return
	 */
	@PostMapping
	@ApiOperation("新增菜品")
	public Result save(@RequestBody DishDTO dishDTO) {
		log.info("新增菜品：{}", dishDTO);
		dishService.saveWithFlavor(dishDTO);
		//清理缓存数据
		String key = "dish_" + dishDTO.getCategoryId();
		cleanCache(key);
		return Result.success();
	}

	/**
	 * 菜品分页查询
	 * @param dishPageQueryDTO
	 * @return
	 */
	@GetMapping("/page")
	@ApiOperation("菜品分页查询")
	public Result pageQuery(DishPageQueryDTO dishPageQueryDTO) {
		log.info("菜品分页查询：{}", dishPageQueryDTO);
		PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
		return Result.success(pageResult);
	}

	/**
	 * 批量删除菜品
	 * <p>{@RequestParam} 可以将String解析为List&lt;Long&gt;
	 * @param ids String "1,2,3"
	 * @return
	 */
	@DeleteMapping()
	@ApiOperation("菜品批量删除")
	public Result delete(@RequestParam List<Long> ids) {
		log.info("菜品批量删除：{}", ids);
		dishService.deleteBatch(ids);
		//可能会影响到多个菜品/套餐
		//将所有的菜品缓存数据清理 dish_xxx
		cleanCache("dish_*");
		return Result.success();
	}

	/**
	 * 根据id查询菜品
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	@ApiOperation("根据id查询菜品")
	public Result<DishVO> getById(@PathVariable Long id) {
		log.info("根据id查询菜品：{}", id);
		DishVO dishVO = dishService.getByIdWithFlavor(id);
		return Result.success(dishVO);
	}

	/**
	 * 修改菜品
	 * @param dishDTO
	 * @return
	 */
	@PutMapping()
	@ApiOperation("修改菜品")
	public Result update(@RequestBody DishDTO dishDTO) {
		log.info("修改菜品：{}", dishDTO);
		dishService.updateWithFlavor(dishDTO);
		//可能会影响到多个菜品/套餐
		//将所有的菜品缓存数据清理 dish_xxx
		cleanCache("dish_*");
		return Result.success();
	}

	/**
	 * 根据分类id查询菜品
	 * @param categoryId
	 * @return
	 */
	@GetMapping("/list")
	public Result<List<Dish>> list(Long categoryId) {
		List<Dish> list = dishService.list(categoryId);
		return Result.success(list);
	}

	/**
	 *菜品起售停售
	 * @param status
	 * @param id
	 * @return
	 */
	@PostMapping("/status/{status}")
	@ApiOperation("菜品起售停售")
	public Result startOrStop(@PathVariable Integer status, Long id) {
		dishService.startOrStop(status, id);
		//将所有的菜品缓存数据清理 dish_xxx
		cleanCache("dish_*");
		return Result.success();
	}

	/**
	 * 清理缓存数据
	 * @param pattern
	 */
	private void cleanCache(String pattern) {
		Set keys = redisTemplate.keys(pattern);
		redisTemplate.delete(keys);
	}
}
