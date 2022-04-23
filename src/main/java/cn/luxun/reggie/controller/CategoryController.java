package cn.luxun.reggie.controller;


import cn.luxun.reggie.common.Result;
import cn.luxun.reggie.entity.Category;
import cn.luxun.reggie.service.CategoryService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	/**
	 * 新增分类
	 *
	 * @param category
	 * @return
	 */
	@PostMapping()
	public Result<String> saveCategoryByParams(@RequestBody Category category) {
		log.info("category: {}", category);
		return categoryService.saveCategoryByParams(category);
	}

	/**
	 * 获取分类分页查询
	 *
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@GetMapping("/page")
	public Result<Page> getCategoryByPage(int page, int pageSize) {
		return categoryService.getCategoryByPage(page, pageSize);
	}

	/**
	 * 根据id删除分类
	 *
	 * @param ids
	 * @return
	 */
	@DeleteMapping()
	public Result<String> deleteCategoryById(Long ids) {
		log.info("删除分类，id为: {}", ids);
		return categoryService.deleteCategoryById(ids);
	}

	/**
	 * 根据id修改分类
	 *
	 * @param category
	 * @return
	 */
	@PutMapping
	public Result<String> updateCategoryById(@RequestBody Category category) {
		log.info("修改分类信息:{}", category);
		return categoryService.updateCategoryById(category);
	}

	/**
	 * 获取所有分类
	 *
	 * @param category
	 * @return
	 */
	@GetMapping("/list")
	public Result<List<Category>> getAllCategory(Category category) {
		return categoryService.getAllCategory(category);
	}

}
