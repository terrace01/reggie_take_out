package cn.luxun.reggie.service.impl;


import cn.luxun.reggie.common.CustomException;
import cn.luxun.reggie.common.Result;
import cn.luxun.reggie.entity.Category;
import cn.luxun.reggie.entity.Dish;
import cn.luxun.reggie.entity.Setmeal;
import cn.luxun.reggie.mapper.CategoryMapper;
import cn.luxun.reggie.service.CategoryService;
import cn.luxun.reggie.service.DishService;
import cn.luxun.reggie.service.SetmealService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {


	private final DishService dishService;
	public CategoryServiceImpl(DishService dishService) {
		this.dishService = dishService;
	}

	@Autowired
	private SetmealService setmealService;


	@Override
	public Result<String> saveCategoryByParams(Category category) {
		this.save(category);
		return Result.success("新增分类成功");
	}

	@Override
	public Result<Page> getCategoryByPage(int page, int pageSize) {
		// 分页构造器
		Page<Category> pageInfo = new Page<>(page, pageSize);

		// 条件构造器
		LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();

		// 添加排序 根据sort进行排序
		queryWrapper.orderByAsc(Category::getSort);

		//分页查询
		this.page(pageInfo, queryWrapper);
		return Result.success(pageInfo);
	}

	@Override
	public Result<String> deleteCategoryById(Long id) {

		// 添加查询条件 根据分类id进行查询
		LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
		dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
		Long count1 = dishService.count(dishLambdaQueryWrapper);

		LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
		setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
		long count2 = setmealService.count(setmealLambdaQueryWrapper);

		// 查询当前分类是否关联了菜品 如果已经关联则抛出一个业务异常
		if (count1 > 0) {
			// 已经关联菜品则抛出一个业务异常
			throw new CustomException("当前分类下关联了菜品，不能删除");
		}

		// 查询当前分类是否关联了套餐 如果已经关联则抛出一个业务异常

		if (count2 > 0) {
			// 已经关联套餐则抛出一个业务异常
			throw new CustomException("当前分类下关联了套餐，不能删除");

		}
		// 正常删除分类
		this.removeById(id);
		return Result.success("分类信息删除成功");
	}

	@Override
	public Result<String> updateCategoryById(Category category) {
		this.updateById(category);
		return Result.success("修改分类信息成功");
	}

	@Override
	public Result<List<Category>> getAllCategory(Category category) {

		// 条件构造器
		LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();

		// 添加条件
		queryWrapper.eq(category.getType() != null, Category::getType, category.getType());

		// 添加排序条件
		queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

		List<Category> list = this.list(queryWrapper);

		return Result.success(list);
	}
}
