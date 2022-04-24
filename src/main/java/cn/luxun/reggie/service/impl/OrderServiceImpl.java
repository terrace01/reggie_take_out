package cn.luxun.reggie.service.impl;

import cn.luxun.reggie.common.Exception.CustomException;
import cn.luxun.reggie.utils.EmployeeThreadLocal;
import cn.luxun.reggie.model.Result;
import cn.luxun.reggie.model.entity.*;
import cn.luxun.reggie.mapper.OrderMapper;
import cn.luxun.reggie.service.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderService {

	private final ShoppingCartService shoppingCartService;
	private final UserService userService;
	private final AddressBookService addressBookService;
	private final OrderDetailService orderDetailService;

	public OrderServiceImpl(ShoppingCartService shoppingCartService, UserService userService, AddressBookService addressBookService, OrderDetailService orderDetailService) {
		this.shoppingCartService = shoppingCartService;
		this.userService = userService;
		this.addressBookService = addressBookService;
		this.orderDetailService = orderDetailService;
	}

	@Override
	@Transactional
	public Result<String> submit(Orders orders) {

		// 获取当前用户Id
		Long currentId = EmployeeThreadLocal.getCurrentId();

		// 查询当前用户的购物车数据
		LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(ShoppingCart::getUserId, currentId);
		List<ShoppingCart> list = shoppingCartService.list(queryWrapper);

		if (list == null || list.size() == 0) {
			throw new CustomException("购物车为空，不能下单呢");
		}

		// 查询用户数据
		User user = userService.getById(currentId);

		// 查询地址数据
		Long addressBookId = orders.getAddressBookId();
		AddressBook addressBook = addressBookService.getById(addressBookId);
		if (addressBook == null) {
			throw new CustomException("用户地址信息有误，不能下单呢");

		}

		// 订单号
		long orderId = IdWorker.getId();

		AtomicInteger amount = new AtomicInteger(0);


		List<OrderDetail> orderDetails = list.stream().map((item) -> {
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOrderId(orderId);
			orderDetail.setNumber(item.getNumber());
			orderDetail.setDishFlavor(item.getDishFlavor());
			orderDetail.setDishId(item.getDishId());
			orderDetail.setSetmealId(item.getSetmealId());
			orderDetail.setName(item.getName());
			orderDetail.setImage(item.getImage());
			orderDetail.setAmount(item.getAmount());
			amount.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
			return orderDetail;
		}).collect(Collectors.toList());


		// 向订单表插入一条数据
		orders.setId(orderId);
		orders.setOrderTime(LocalDateTime.now());
		orders.setCheckoutTime(LocalDateTime.now());
		orders.setStatus(2);
		orders.setAmount(new BigDecimal(amount.get()));//总金额
		orders.setUserId(currentId);
		orders.setNumber(String.valueOf(orderId));
		orders.setUserName(user.getName());
		orders.setConsignee(addressBook.getConsignee());
		orders.setPhone(addressBook.getPhone());
		orders.setAddress((addressBook.getProvinceName() == null ? "" : addressBook.getProvinceName())
				+ (addressBook.getCityName() == null ? "" : addressBook.getCityName())
				+ (addressBook.getDistrictName() == null ? "" : addressBook.getDistrictName())
				+ (addressBook.getDetail() == null ? "" : addressBook.getDetail()));

		this.save(orders);
		// 向订单明细表插入多条数据
		orderDetailService.saveBatch(orderDetails);

		// 清空购物车数据
		shoppingCartService.remove(queryWrapper);
		return Result.success("下单成功");
	}
}
