package com.sky.controller.user;

import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: sky-take-out
 * @author: Qiaolezi
 * @create: 2024-07-17 20:22
 * @description:
 **/
@RestController("userOrderController")
@RequestMapping("/user/order")
@Api("用户订单相关接口")
@Slf4j
public class OrderController {
	@Autowired
	private OrderService orderService;

	/**
	 * 用户下单
	 *
	 * @param ordersSubmitDTO
	 * @return
	 */
	@PostMapping("/submit")
	@ApiOperation("用户下单")
	public Result<OrderSubmitVO> submit(@RequestBody OrdersSubmitDTO ordersSubmitDTO) {
		log.info("用户下单：{}", ordersSubmitDTO);
		OrderSubmitVO orderSubmitVO = orderService.submitOrder(ordersSubmitDTO);
		return Result.success(orderSubmitVO);
	}

	/**
	 * 订单支付
	 *
	 * @param ordersPaymentDTO
	 * @return
	 */
	@PutMapping("/payment")
	@ApiOperation("订单支付")
	public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws Exception {
		log.info("订单支付：{}", ordersPaymentDTO);
		OrderPaymentVO orderPaymentVO = orderService.payment(ordersPaymentDTO);
		log.info("生成预支付交易单：{}", orderPaymentVO);
		return Result.success(orderPaymentVO);
	}

	/**
	 * 历史订单查询
	 *
	 * @param page
	 * @param pageSize
	 * @param status
	 * @return
	 */
	@GetMapping("/historyOrders")
	@ApiOperation("历史订单查询")
	public Result<PageResult> page(int page, int pageSize, Integer status) {
		log.info("历史订单查询：page:{},pageSize:{},status:{}", page, pageSize, status);
		PageResult pageResult = orderService.pageQuery4User(page, pageSize, status);
		return Result.success(pageResult);
	}

	/**
	 * 订单详细信息
	 * @param id
	 * @return
	 */
	@GetMapping("/orderDetail/{id}")
	@ApiOperation("订单详细信息")
	public Result<OrderVO> orderDetail(@PathVariable Long id) {
		OrderVO orderVO = orderService.getOrderDetailByOrderId(id);
		return Result.success(orderVO);
	}

	/**
	 * 取消订单
	 * @param id
	 * @return
	 */
	@PutMapping("/cancel/{id}")
	public Result cancel(@PathVariable Long id) throws Exception {
		orderService.cancel(id);
		return Result.success();
	}

	/**
	 * 再来一单
	 * @param id
	 * @return
	 */
	@PostMapping("/repetition/{id}")
	@ApiOperation("再来一单")
	public Result repetition(@PathVariable("id") Long id) {
		orderService.repetition(id);
		return Result.success();
	}

	@GetMapping("/reminder/{id}")
	@ApiOperation("客户催单")
	public Result reminder(@PathVariable("id") Long id) {
		orderService.reminder(id);

		return Result.success();
	}
}
