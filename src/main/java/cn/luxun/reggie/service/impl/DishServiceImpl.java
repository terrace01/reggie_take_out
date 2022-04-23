package cn.luxun.reggie.service.impl;


import cn.luxun.reggie.common.Result;
import cn.luxun.reggie.dto.DishDto;
import cn.luxun.reggie.entity.Dish;
import cn.luxun.reggie.entity.DishFlavor;
import cn.luxun.reggie.mapper.DishMapper;
import cn.luxun.reggie.service.DishFlavorService;
import cn.luxun.reggie.service.DishService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

	@Autowired
	private DishFlavorService dishFlavorService;

	@Override
	@Transactional
	public Result<String> saveDishByParams(DishDto dishDto) {

		// 保存菜品的基本信息到菜品表dish中
		this.save(dishDto);

		// 菜品id
		Long id = dishDto.getId();

		// 菜品口味
		List<DishFlavor> flavors = dishDto.getFlavors();
		flavors.stream().map((item) -> {
			item.setDishId(id);
			return item;
		}).collect(Collectors.toList());

		// 保存菜品口味数据到菜品口味表dish_Flavor中
		dishFlavorService.saveBatch(dishDto.getFlavors());
		return Result.success("新增菜品成功");
	}
}
