package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;

/**
 * @program: sky-take-out
 * @author: Qiaolezi
 * @create: 2024-07-12 15:07
 * @description: 套餐
 **/
public interface SetmealService {
	/**
	 * 新增套餐
	 * @param setmealDTO
	 */
	void saveWithDish(SetmealDTO setmealDTO);

	/**
	 * 分页查询套餐
	 * @param setmealPageQueryDTO
	 * @return
	 */
	PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);
}
