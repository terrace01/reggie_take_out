package cn.luxun.reggie.controller;

import cn.luxun.reggie.model.Result;
import cn.luxun.reggie.model.dto.DishDto;

import cn.luxun.reggie.model.entity.Dish;
import cn.luxun.reggie.service.DishService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

	@Autowired
	private DishService dishService;


	/**
	 * 新增菜品
	 *
	 * @param dishDto
	 * @return
	 */
	@PostMapping()
	public Result<String> saveDishByParamsWithFlavor(@RequestBody DishDto dishDto) {
		return dishService.saveDishByParamsWithFlavor(dishDto);
	}

	/**
	 * 菜品信息分页查询
	 *
	 * @param page
	 * @param pageSize
	 * @param name
	 * @return
	 */
	@GetMapping("/page")
	public Result<Page> getDishByPage(int page, int pageSize, String name) {
		return dishService.getDishByPage(page, pageSize, name);
	}

	/**
	 * 根据id查询菜品信息和对应的口味信息
	 *
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public Result<DishDto> getOneByIdWithFlavor(@PathVariable Long id) {
		return dishService.getOneByIdWithFlavor(id);
	}

	/**
	 * 修改菜品
	 *
	 * @param dishDto
	 * @return
	 */
	@PutMapping
	public Result<String> updateDishByParamsWithFlavor(@RequestBody DishDto dishDto) {
		return dishService.updateDishByParamsWithFlavor(dishDto);
	}

	/**
	 * 根据条件查询对应的菜品数据
	 *
	 * @param dish
	 * @return
	 */
	// @GetMapping("/list")
	public Result<List<Dish>> getAllDish(Dish dish) {
		return dishService.getAllDish(dish);
	}

	/**
	 * 根据条件查询对应的菜品数据
	 *
	 * @param dish
	 * @return
	 */
	@GetMapping("/list")
	public Result<List<DishDto>> getAllDishByDto(Dish dish) {
		return dishService.getAllDishByDto(dish);
	}
}
