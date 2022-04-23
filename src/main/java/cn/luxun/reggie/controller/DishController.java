package cn.luxun.reggie.controller;

import cn.luxun.reggie.common.Result;
import cn.luxun.reggie.dto.DishDto;

import cn.luxun.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

	@Autowired
	private DishService dishService;

	/**
	 * 新增分类
	 *
	 * @param dishDto
	 * @return
	 */
	@PostMapping()
	public Result<String> saveDishByParams(@RequestBody DishDto dishDto) {
		return dishService.saveDishByParams(dishDto);
	}

}
