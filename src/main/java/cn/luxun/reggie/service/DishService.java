package cn.luxun.reggie.service;

import cn.luxun.reggie.common.Result;
import cn.luxun.reggie.dto.DishDto;
import cn.luxun.reggie.entity.Dish;
import com.baomidou.mybatisplus.extension.service.IService;

public interface DishService extends IService<Dish> {

	/**
	 * 新增分类
	 *
	 * @param dishDto
	 * @return
	 */
	Result<String> saveDishByParams(DishDto dishDto);
}
