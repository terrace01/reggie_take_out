package cn.luxun.reggie.service;

import cn.luxun.reggie.model.Result;
import cn.luxun.reggie.model.dto.DishDto;
import cn.luxun.reggie.model.entity.Dish;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface DishService extends IService<Dish> {

	/**
	 * 新增分类
	 *
	 * @param dishDto
	 * @return
	 */
	Result<String> saveDishByParamsWithFlavor(DishDto dishDto);

	/**
	 * 菜品信息分页查询
	 * @param page
	 * @param pageSize
	 * @param name
	 * @return
	 */
	Result<Page> getDishByPage(int page, int pageSize, String name);

	/**
	 * 根据id查询菜品信息和对应的口味信息
	 *
	 * @param id
	 * @return
	 */
	Result<DishDto> getOneByIdWithFlavor(Long id);

	/**
	 * 修改菜品
	 *
	 * @param dishDto
	 * @return
	 */
	Result<String> updateDishByParamsWithFlavor(DishDto dishDto);

	/**
	 * 根据条件查询对应的菜品数据
	 *
	 * @param dish
	 * @return
	 */
	Result<List<Dish>> getAllDish(Dish dish);

	/**
	 * 根据条件查询对应的菜品数据
	 *
	 * @param dish
	 * @return
	 */
	Result<List<DishDto>> getAllDishByDto(Dish dish);

}
