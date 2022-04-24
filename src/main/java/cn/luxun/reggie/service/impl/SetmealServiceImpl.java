package cn.luxun.reggie.service.impl;


import cn.luxun.reggie.common.Exception.CustomException;
import cn.luxun.reggie.model.Result;
import cn.luxun.reggie.model.dto.SetmealDto;
import cn.luxun.reggie.model.entity.Category;
import cn.luxun.reggie.model.entity.Setmeal;
import cn.luxun.reggie.model.entity.SetmealDish;
import cn.luxun.reggie.mapper.SetmealMapper;
import cn.luxun.reggie.service.CategoryService;
import cn.luxun.reggie.service.SetmealDishService;
import cn.luxun.reggie.service.SetmealService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

	private final SetmealDishService setmealDishService;
	private final CategoryService categoryService;

	@Lazy
	public SetmealServiceImpl(SetmealDishService setmealDishService, CategoryService categoryService) {
		this.setmealDishService = setmealDishService;
		this.categoryService = categoryService;
	}

	@Override
	@Transactional
	public Result<String> saveSetmeal(SetmealDto setmealDto) {

		// 保存套餐的基本信息 操作setmeal 执行insert操作
		this.save(setmealDto);

		List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
		setmealDishes.stream().map((item) -> {
			item.setSetmealId(setmealDto.getId());
			return item;
		}).collect(Collectors.toList());

		// 保存套餐和菜品的关联信息，操作setmeal_dish 执行insert操作
		setmealDishService.saveBatch(setmealDishes);
		return Result.success("添加套餐成功");
	}

	@Override
	public Result<Page> getSetmealByPage(int page, int pageSize, String name) {

		// 分页构造器对象
		Page<Setmeal> pageInfo = new Page<>(page, pageSize);
		Page<SetmealDto> dtoPage = new Page<>();

		LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
		// 添加查询条件 根据name进行like模糊查询
		queryWrapper.like(name != null, Setmeal::getName, name);

		// 添加排序条件 根据更新时间降序排序
		queryWrapper.orderByDesc(Setmeal::getUpdateTime);

		this.page(pageInfo, queryWrapper);

		// 对象拷贝
		BeanUtils.copyProperties(pageInfo, dtoPage, "records");
		List<Setmeal> record = pageInfo.getRecords();

		List<SetmealDto> list = record.stream().map((item) -> {

			SetmealDto setmealDto = new SetmealDto();
			BeanUtils.copyProperties(item, setmealDto);
			// 获取分类Id
			Long categoryId = item.getCategoryId();

			// 根据分类id查询分类对象
			Category category = categoryService.getById(categoryId);

			if (category != null) {

				// 分类名称
				String categoryName = category.getName();
				setmealDto.setCategoryName(categoryName);
			}
			return setmealDto;
		}).collect(Collectors.toList());

		dtoPage.setRecords(list);
		return Result.success(dtoPage);
	}

	@Override
	public Result<String> deleteSetmealById(List<Long> ids) {

		// 查询套餐状态 确认是否可用删除
		LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.in(Setmeal::getId, ids);
		queryWrapper.eq(Setmeal::getStatus, 1);

		Long count = this.count(queryWrapper);
		if (count > 0) {
			// 如果不能删除 抛出一个业务异常
			throw new CustomException("套餐正在售卖中 不能删除");
		}

		// 如果可以删除 先删除套餐表中的数据 -- setmeal
		this.removeByIds(ids);

		// delete from setmeal dish where setmeal id in(1,2,3)
		LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.in(SetmealDish::getSetmealId, ids);

		// 删除关系表中的数据 -- setmeal dish
		setmealDishService.remove(lambdaQueryWrapper);
		return Result.success("套餐数据删除成功");
	}

	@Override
	public Result<List<Setmeal>> getAllSetmealByParams(Setmeal setmeal) {
		// 构造条件构造器
		LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper();

		// 添加过滤条件
		queryWrapper.eq(setmeal.getCategoryId() != null, Setmeal::getCategoryId, setmeal.getCategoryId());
		queryWrapper.eq(setmeal.getStatus() != null, Setmeal::getStatus, setmeal.getStatus());
		queryWrapper.orderByDesc(Setmeal::getUpdateTime);

		List<Setmeal> list = this.list(queryWrapper);

		return Result.success(list);
	}
}
