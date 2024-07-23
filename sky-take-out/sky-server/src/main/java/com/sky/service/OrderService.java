package com.sky.service;

import com.github.pagehelper.Page;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import com.sky.result.PageResult;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

import java.util.List;

/**
 * @program: sky-take-out
 * @author: Qiaolezi
 * @create: 2024-07-17 20:39
 * @description:
 **/
public interface OrderService {

	/**
	 * 用户下单
	 *
	 * @param ordersSubmitDTO
	 * @return
	 */
	OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);

	/**
	 * 订单支付
	 *
	 * @param ordersPaymentDTO
	 * @return
	 */
	OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

	/**
	 * 支付成功，修改订单状态
	 *
	 * @param outTradeNo
	 */
	void paySuccess(String outTradeNo);

	/**
	 * 历史订单分页查询
	 * @param page
	 * @param pageSize
	 * @param status
	 * @return
	 */
	PageResult pageQuery4User(int page, int pageSize, Integer status);

	/**
	 * 根据订单id查询订单详情
	 * @param id
	 * @return
	 */
	OrderVO getOrderDetailByOrderId(Long id);

	/**
	 * 取消订单
	 * @param id
	 */
	void cancel(Long id) throws Exception;

	/**
	 * 再来一单
	 * @param id
	 */
	void repetition(Long id);

	/**
	 * 订单搜索
	 * @param ordersPageQueryDTO
	 * @return
	 */
	PageResult conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO);
}
