package cn.luxun.reggie.service;

import cn.luxun.reggie.model.Result;
import cn.luxun.reggie.model.dto.SetmealDto;
import cn.luxun.reggie.model.entity.Setmeal;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface SetmealService extends IService<Setmeal> {

	/**
	 * 新增套餐 同时保存保存套餐和菜品的关联关系
	 *
	 * @param setmealDto
	 * @return
	 */
	Result<String> saveSetmeal(SetmealDto setmealDto);

	/**
	 * 分页获取套餐
	 *
	 * @param page
	 * @param pageSize
	 * @param name
	 * @return
	 */
	Result<Page> getSetmealByPage(int page, int pageSize, String name);

	/**
	 * 删除套餐 同时需要删除套餐和菜品的关联数据
	 *
	 * @param ids
	 * @return
	 */
	Result<String> deleteSetmealById(List<Long> ids);

	/**
	 * 根据条件查询套餐
	 *
	 * @param setmeal
	 * @return
	 */
	Result<List<Setmeal>> getAllSetmealByParams(Setmeal setmeal);
}
