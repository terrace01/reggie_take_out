package cn.luxun.reggie.service;

import cn.luxun.reggie.model.Result;
import cn.luxun.reggie.model.entity.Category;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface CategoryService extends IService<Category> {

	/**
	 * 新增分类
	 *
	 * @param category
	 * @return
	 */
	Result<String> saveCategoryByParams(Category category);

	/**
	 * 获取分类分页查询
	 * @param page
	 * @param pageSize
	 * @return
	 */
	Result<Page> getCategoryByPage(int page, int pageSize);

	/**
	 * 根据id删除分类
	 *
	 * @param id
	 * @return
	 */
	Result<String> deleteCategoryById(Long id);

	/**
	 * 根据id修改分类
	 * @param category
	 * @return
	 */
	Result<String> updateCategoryById(Category category);

	/**
	 * 获取所有分类
	 *
	 * @param category
	 * @return
	 */
	Result<List<Category>> getAllCategory(Category category);
}
