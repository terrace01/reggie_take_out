package cn.luxun.reggie.controller;


import cn.luxun.reggie.model.Result;
import cn.luxun.reggie.model.entity.Employee;
import cn.luxun.reggie.service.EmployeeService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

		return employeeService.addEmployeeByInfo(request, employee);
	}

	/**
	 * 员工分页查询
	 *
	 * @param page
	 * @param pageSize
	 * @param name
	 * @return
	 */
	@GetMapping("/page")
	public Result<Page> getEmployeeByPage(int page, int pageSize, String name) {
		log.info("page={},pageSize={},name={}", page, pageSize, name);
		return employeeService.getEmployeeByPage(page, pageSize, name);
	}

	/**
	 * 根据员工id修改员工信息
	 *
	 * @param employee
	 * @return
	 */
	@PutMapping
	public Result<String> updateEmployeeById(HttpServletRequest request, @RequestBody Employee employee) {
		log.info(employee.toString());
		return employeeService.updateEmployeeById(request, employee);
	}

	/**
	 * 根据员工id查询员工信息
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public Result<Employee> getEmployeeById(@PathVariable Long id) {

		log.info("根据id查询员工信息");
		return employeeService.getEmployeeById(id);
	}
}
