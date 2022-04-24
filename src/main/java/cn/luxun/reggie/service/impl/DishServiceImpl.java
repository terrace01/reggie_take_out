package cn.luxun.reggie.service.impl;


import cn.luxun.reggie.common.Result;
import cn.luxun.reggie.dto.DishDto;
import cn.luxun.reggie.entity.Category;
import cn.luxun.reggie.entity.Dish;
import cn.luxun.reggie.entity.DishFlavor;
import cn.luxun.reggie.mapper.DishMapper;
import cn.luxun.reggie.service.CategoryService;
import cn.luxun.reggie.service.DishFlavorService;
import cn.luxun.reggie.service.DishService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

	@Autowired
	private DishFlavorService dishFlavorService;

	@Lazy
	public DishServiceImpl(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	private final CategoryService categoryService;

	@Override
	@Transactional
	public Result<String> saveDishByParamsWithFlavor(DishDto dishDto) {

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

	@Override
	public Result<Page> getDishByPage(int page, int pageSize, String name) {

		// 构造分页构造器
		Page<Dish> pageInfo = new Page<>(page, pageSize);
		Page<DishDto> dishDtoPage = new Page<>();

		// 条件构造器
		LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();

		// 添加过滤条件
		queryWrapper.like(name != null, Dish::getName, name);

		// 添加排序条件
		queryWrapper.orderByDesc(Dish::getUpdateTime);

		// 执行分页查询
		this.page(pageInfo, queryWrapper);

		// 对象拷贝
		BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");
		List<Dish> records = pageInfo.getRecords();

		List<DishDto> list = records.stream().map((item) -> {

			DishDto dishDto = new DishDto();
			BeanUtils.copyProperties(item, dishDto);
			// 分类id
			Long categoryId = item.getCategoryId();

			// 根据id查询分类对象
			Category category = categoryService.getById(categoryId);
			if (category != null) {
				String categoryName = category.getName();
				dishDto.setCategoryName(categoryName);
			}

			return dishDto;
		}).collect(Collectors.toList());
		dishDtoPage.setRecords(list);

		return Result.success(dishDtoPage);
	}

	@Override
	public Result<DishDto> getOneByIdWithFlavor(Long id) {

		// 查询菜品基本信息 从dish表查询
		Dish dish = this.getById(id);

		DishDto dishDto = new DishDto();
		BeanUtils.copyProperties(dish, dishDto);

		// 查询当前菜品相应的口味信息 从dish_falovr表查询
		LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(DishFlavor::getDishId, dish.getId());
		List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);
		dishDto.setFlavors(flavors);

		return Result.success(dishDto);
	}

	@Override
	@Transactional
	public Result<String> updateDishByParamsWithFlavor(DishDto dishDto) {

		// 更新dish表中基本信息
		this.updateById(dishDto);

		// 清理当前菜品对应口味数据 -- dish_flaovr表中的delete操作
		LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(DishFlavor::getDishId, dishDto.getId());

		dishFlavorService.remove(queryWrapper);

		// 添加当前提交过的口味数据 -- dish_flavor表的insert操作
		List<DishFlavor> flavors = dishDto.getFlavors();

		flavors.stream().map((item) -> {
			item.setDishId(dishDto.getId());
			return item;
		}).collect(Collectors.toList());
		dishFlavorService.saveBatch(flavors);
		return Result.success("修改菜品成功");
	}

	@Override
	public Result<List<Dish>> getAllDish(Dish dish) {

		// 构造查询条件
		LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
		// 添加条件 查询状态为1（起售状态）的菜品
		queryWrapper.eq(Dish::getStatus, 1);

		// 添加排序条件
		queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

		List<Dish> list = this.list(queryWrapper);
		return Result.success(list);
	}

	@Override
	public Result<List<DishDto>> getAllDishByDto(Dish dish) {

		// 构造查询条件
		LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
		// 添加条件 查询状态为1（起售状态）的菜品
		queryWrapper.eq(Dish::getStatus, 1);

		// 添加排序条件
		queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

		List<Dish> list = this.list(queryWrapper);

		List<DishDto> dishDtoList = list.stream().map((item) -> {

			DishDto dishDto = new DishDto();
			BeanUtils.copyProperties(item, dishDto);
			// 分类id
			Long categoryId = item.getCategoryId();

			// 根据id查询分类对象
			Category category = categoryService.getById(categoryId);
			if (category != null) {
				String categoryName = category.getName();
				dishDto.setCategoryName(categoryName);
			}

			Long dishId = item.getId();
			LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
			dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId, dishId);
			List<DishFlavor> dishFlavors = dishFlavorService.list(dishFlavorLambdaQueryWrapper);

			dishDto.setFlavors(dishFlavors);
			return dishDto;
		}).collect(Collectors.toList());

		// return Result.success(list);
		return Result.success(dishDtoList);
	}

}
