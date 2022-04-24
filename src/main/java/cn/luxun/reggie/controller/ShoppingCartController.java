package cn.luxun.reggie.controller;

import cn.luxun.reggie.common.Result;
import cn.luxun.reggie.entity.ShoppingCart;
import cn.luxun.reggie.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

	@Autowired
	private ShoppingCartService shoppingCartService;

	/**
	 * 将菜品或套餐添加到购物车
	 *
	 * @param shoppingCart
	 * @return
	 */
	@PostMapping("/add")
	public Result<ShoppingCart> DealSetmealAddShoppingCard(@RequestBody ShoppingCart shoppingCart) {
		log.info("购物车数据，{}", shoppingCart);
		return shoppingCartService.DealSetmealAddShoppingCard(shoppingCart);
	}


	/**
	 * 将菜品或套餐移除购物车
	 *
	 * @param shoppingCart
	 * @return
	 */
	@PostMapping("/sub")
	public Result<String> DealSetmealRemoveShoppingCard(@RequestBody ShoppingCart shoppingCart) {
		log.info("购物车数据，{}", shoppingCart);
		return shoppingCartService.DealSetmealRemoveShoppingCard(shoppingCart);
	}


	/**
	 * 查看购物车
	 *
	 * @return
	 */
	@GetMapping("/list")
	public Result<List<ShoppingCart>> getAllShoppingCartByUserId() {
		return shoppingCartService.getAllShoppingCartByUserId();
	}

	/**
	 * 根据用户id清空购物车
	 *
	 * @return
	 */
	@DeleteMapping("/clean")
	public Result<String> deleteAllShoppingCardByUserId() {
		return shoppingCartService.deleteAllShoppingCardByUserId();
	}
}
