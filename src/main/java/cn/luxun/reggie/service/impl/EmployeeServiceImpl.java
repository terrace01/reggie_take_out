package cn.luxun.reggie.service.impl;

import cn.luxun.reggie.common.Result;
import cn.luxun.reggie.entity.Employee;
import cn.luxun.reggie.mapper.EmployeeMapper;
import cn.luxun.reggie.service.EmployeeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;


@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {


	@Override
	public Result<Employee> loginByUserNameAndPassword(HttpServletRequest request, Employee employee) {

		// 获取页面传入的密码 并进行md5加密
		String password = employee.getPassword();
		password = DigestUtils.md5DigestAsHex(password.getBytes());

		// 将页面传入的用户名查询数据库
		LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(Employee::getUsername, employee.getUsername());
		Employee emp = this.getOne(queryWrapper);

		// 判断用户是否存在 如果不存在返回登陆失败结果
		if (emp == null) {
			return Result.error("登陆失败");
		}

		// 判断密码是否符合 如果不一致则返回登陆失败结果
		if (!emp.getPassword().equals(password)) {
			return Result.error("登陆失败");
		}

		// 判断员工状态  如果为禁用状态则返回员工已禁用结果
		if (emp.getStatus() == 0) {
			return Result.error("账号已禁用");
		}

		// 登陆成功 将员工id存入Session并返回登录成功结果
		request.getSession().setAttribute("employee", emp.getId());
		return Result.success(emp);

	}

	@Override
	public Result<String> logout(HttpServletRequest request) {
		request.getSession().removeAttribute("employee");
		return Result.success("退出成功");
	}

	@Override
	public Result<String> addEmployeeByInfo(HttpServletRequest request, Employee employee) {

		// 设置初始密码为123456 需要进行md5加密
		employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

		employee.setCreateTime(LocalDateTime.now());
		employee.setUpdateTime(LocalDateTime.now());

		// 从 session 当中获取当前登录用户的id
		Long empId = (Long) request.getSession().getAttribute("employee");
		employee.setCreateUser(empId);
		employee.setUpdateUser(empId);
		this.save(employee);
		return Result.success("新增员工成功");
	}
}
