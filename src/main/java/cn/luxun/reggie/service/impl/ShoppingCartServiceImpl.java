package cn.luxun.reggie.service.impl;

import cn.luxun.reggie.utils.EmployeeThreadLocal;
import cn.luxun.reggie.model.Result;
import cn.luxun.reggie.model.entity.ShoppingCart;
import cn.luxun.reggie.mapper.ShoppingCartMapper;
import cn.luxun.reggie.service.ShoppingCartService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

	@Override
	public Result<ShoppingCart> DealSetmealAddShoppingCard(ShoppingCart shoppingCart) {
		// 设置用户id，指定当前  是哪个用户的购物车数据
		Long currentId = EmployeeThreadLocal.getCurrentId();
		shoppingCart.setUserId(currentId);

		// 查询当前彩票或者套餐是否在购物车中
		Long dishId = shoppingCart.getDishId();
		LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(ShoppingCart::getUserId, currentId);

		if (dishId != null) {
			// 添加到购物车的是菜品
			queryWrapper.eq(ShoppingCart::getDishId, dishId);
		} else {
			// 添加到购物车的是套餐
			queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
		}

		// SQL:select * from shopping_cart where user_id = ? and dish_id/setmeal_id = ?
		ShoppingCart cart = this.getOne(queryWrapper);

		if (cart != null) {
			// 如果已经存在 就在原来数量基础上加一
			Integer number = cart.getNumber();
			cart.setNumber(number + 1);
			this.updateById(cart);

		} else {
			// 如果不存在，则添加到购物车，数量默认为1
			shoppingCart.setNumber(1);
			shoppingCart.setCreateTime(LocalDateTime.now());
			this.save(shoppingCart);
			cart = shoppingCart;
		}

		return Result.success(cart);
	}

	@Override
	public Result<String> DealSetmealRemoveShoppingCard(ShoppingCart shoppingCart) {
		// 设置用户id，指定当前  是哪个用户的购物车数据
		Long currentId = EmployeeThreadLocal.getCurrentId();
		shoppingCart.setUserId(currentId);

		// 查询当前彩票或者套餐是否在购物车中
		Long dishId = shoppingCart.getDishId();
		LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(ShoppingCart::getUserId, currentId);

		if (dishId != null) {
			// 添加到购物车的是菜品
			queryWrapper.eq(ShoppingCart::getDishId, dishId);
		} else {
			// 添加到购物车的是套餐
			queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
		}

		// SQL:select * from shopping_cart where user_id = ? and dish_id/setmeal_id = ?
		ShoppingCart cart = this.getOne(queryWrapper);

		if (cart != null) {
			// 如果已经存在 就在原来数量基础上加一
			Integer number = cart.getNumber();
			cart.setNumber(number - 1);
			this.updateById(cart);

		}

		return Result.success("移除成功");
	}

	@Override
	public Result<List<ShoppingCart>> getAllShoppingCartByUserId() {

		// 构建条件构造器
		LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper();

		// 添加查询条件
		queryWrapper.eq(ShoppingCart::getUserId, EmployeeThreadLocal.getCurrentId());
		queryWrapper.orderByAsc(ShoppingCart::getCreateTime);

		List<ShoppingCart> list = this.list(queryWrapper);
		return Result.success(list);
	}

	@Override
	public Result<String> deleteAllShoppingCardByUserId() {

		// 构建条件构造器
		LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();

		// 添加查询条件
		queryWrapper.eq(ShoppingCart::getUserId, EmployeeThreadLocal.getCurrentId());
		this.remove(queryWrapper);
		return Result.success("清口数据库成功");
	}
}
