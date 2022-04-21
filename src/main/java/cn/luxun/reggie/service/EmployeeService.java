package cn.luxun.reggie.service;

import cn.luxun.reggie.common.Result;
import cn.luxun.reggie.entity.Employee;
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
}
