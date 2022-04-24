package cn.luxun.reggie.controller;

import cn.luxun.reggie.model.Result;
import cn.luxun.reggie.model.dto.SetmealDto;
import cn.luxun.reggie.model.entity.Setmeal;
import cn.luxun.reggie.service.SetmealDishService;
import cn.luxun.reggie.service.SetmealService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 套餐管理
 */

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

	@Autowired
	private SetmealService setmealService;

	/**
	 * 新增套餐 同时保存保存套餐和菜品的关联关系
	 *
	 * @param setmealDto
	 * @return
	 */
	@PostMapping
	@CacheEvict(value = "setmealCache", allEntries = true)
	public Result<String> saveSetmeal(@RequestBody SetmealDto setmealDto) {
		return setmealService.saveSetmeal(setmealDto);
	}


	/**
	 * 分页获取套餐
	 *
	 * @param page
	 * @param pageSize
	 * @param name
	 * @return
	 */
	@GetMapping("/page")
	public Result<Page> getSetmealByPage(int page, int pageSize, String name) {
		return setmealService.getSetmealByPage(page, pageSize, name);
	}

	/**
	 * 删除套餐 同时需要删除套餐和菜品的关联数据
	 *
	 * @param ids
	 * @return
	 */
	@DeleteMapping()
	@CacheEvict(value = "setmealCache", allEntries = true)
	public Result<String> deleteSetmealById(@RequestParam List<Long> ids) {
		return setmealService.deleteSetmealById(ids);
	}

	/**
	 * 根据条件查询套餐
	 *
	 * @param setmeal
	 * @return
	 */
	@GetMapping("/list")
	@Cacheable(value = "setmealCache", key = "#setmeal.categoryId + '_' + #setmeal.status")
	public Result<List<Setmeal>> getAllSetmealByParams(Setmeal setmeal) {
		return setmealService.getAllSetmealByParams(setmeal);

	}
}
