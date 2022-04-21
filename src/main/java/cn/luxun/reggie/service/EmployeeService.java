package cn.luxun.reggie.service;

import cn.luxun.reggie.common.Result;
import cn.luxun.reggie.entity.Employee;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

public interface EmployeeService extends IService<Employee> {

	/**
	 * 用户登录功能
	 *
	 * @param request
	 * @param employee
	 * @return
	 */
	Result<Employee> loginByUserNameAndPassword(HttpServletRequest request, Employee employee);


	/**
	 * 用户退出登录功能
	 *
	 * @param request
	 * @return
	 */
	Result<String> logout(HttpServletRequest request);

	/**
	 * 新增员工
	 *
	 * @param employee
	 * @return
	 */
	Result<String> addEmployeeByInfo(HttpServletRequest request, Employee employee);

	/**
	 * 员工分页查询
	 *
	 * @param page
	 * @param pageSize
	 * @param name
	 * @return
	 */
	Result<Page> getEmployeeByPage(int page, int pageSize, String name);

	/**
	 * 根据员工id修改员工信息
	 *
	 * @param employee
	 * @return
	 */
	Result<String> updateEmployeeById(HttpServletRequest request, Employee employee);

	/**
	 * 根据员工id查询员工信息
	 * @param id
	 * @return
	 */
	Result<Employee> getEmployeeById(Long id);
}
