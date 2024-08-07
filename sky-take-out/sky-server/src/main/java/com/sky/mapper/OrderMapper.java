package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.GoodsSalesDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @program: sky-take-out
 * @author: Qiaolezi
 * @create: 2024-07-17 20:43
 * @description:
 **/
@Mapper
public interface OrderMapper {

	/**
	 * 插入数据
	 *
	 * @param orders
	 */
	void insert(Orders orders);

	/**
	 * 根据订单号查询订单
	 *
	 * @param orderNumber
	 */
	@Select("select * from orders where number = #{orderNumber}")
	Orders getByNumber(String orderNumber);

	/**
	 * 修改订单信息
	 *
	 * @param orders
	 */
	void update(Orders orders);

	/**
	 * 历史订单分页查询
	 *
	 * @param ordersPageQueryDTO
	 * @return
	 */
	Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

	/**
	 * 根据id查询订单
	 * @param id
	 * @return
	 */
	@Select("select * from orders where id = #{id}")
	Orders getById(Long id);

	/**
	 * 各个状态的订单数量统计
	 * @param toBeConfirmed
	 * @return
	 */
	@Select("select count(id) from orders where status = #{status}")
	Integer countStatus(Integer toBeConfirmed);

	/**
	 * 根据状态和下单时间查询订单
	 * @return
	 */
	@Select("select * from orders where status = #{status} and order_time < #{orderTime}")
	List<Orders> getByStatusAndOrderTimeLT(Integer status, LocalDateTime orderTime);

	/**
	 * 动态条件统计每天的营业额
	 * @param map
	 * @return
	 */
	Double sumByMap(Map map);

	/**
	 * 动态条件统计订单数量
	 * @param map
	 * @return
	 */
	Integer countByMap(Map map);

	/**
	 * 销量排名top10
	 * @param begin
	 * @param end
	 * @return
	 */
	List<GoodsSalesDTO> getSalesTop(LocalDateTime begin, LocalDateTime end);
}
