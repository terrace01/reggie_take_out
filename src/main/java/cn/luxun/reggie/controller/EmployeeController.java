package cn.luxun.reggie.controller;


import cn.luxun.reggie.common.Result;
import cn.luxun.reggie.entity.Employee;
import cn.luxun.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;


	/**
	 * 用户登录功能
	 *
	 * @param request
	 * @param employee
	 * @return
	 */
	@PostMapping("/login")
	public Result<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
		return employeeService.loginByUserNameAndPassword(request, employee);
	}

	/**
	 * 用户退出登录功能
	 *
	 * @param request
	 * @return
	 */
	@PostMapping("/logout")
	public Result<String> logout(HttpServletRequest request) {
		return employeeService.logout(request);
	}


	/**
	 * 新增员工
	 *
	 * @param employee
	 * @return
	 */
	@PostMapping()
	public Result<String> addEmployeeByInfo(HttpServletRequest request, @RequestBody Employee employee) {

		return employeeService.addEmployeeByInfo(request,employee);
	}
}
