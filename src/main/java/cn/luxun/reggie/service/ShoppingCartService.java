package cn.luxun.reggie.service;

import cn.luxun.reggie.model.Result;
import cn.luxun.reggie.model.entity.ShoppingCart;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ShoppingCartService extends IService<ShoppingCart> {

	/**
	 * 将菜品或套餐添加到购物车
	 *
	 * @param shoppingCart
	 * @return
	 */
	Result<ShoppingCart> DealSetmealAddShoppingCard(ShoppingCart shoppingCart);


	/**
	 * 将菜品或套餐移除购物车
	 *
	 * @param shoppingCart
	 * @return
	 */
	Result<String> DealSetmealRemoveShoppingCard(ShoppingCart shoppingCart);

	/**
	 * 查看购物车
	 *
	 * @return
	 */
	Result<List<ShoppingCart>> getAllShoppingCartByUserId();

	/**
	 * 根据用户id清空购物车
	 *
	 * @return
	 */
	Result<String> deleteAllShoppingCardByUserId();

}
