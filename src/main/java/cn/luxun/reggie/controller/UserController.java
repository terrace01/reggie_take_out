package cn.luxun.reggie.controller;

import cn.luxun.reggie.model.Result;
import cn.luxun.reggie.model.entity.User;
import cn.luxun.reggie.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * 发送手机短信验证码
	 *
	 * @param user
	 * @return
	 */
	@PostMapping("/sendMsg")
	public Result<String> sendMsg(@RequestBody User user, HttpSession session) {
		return userService.sendMsg(user, session);
	}


	/**
	 * 移动端用户登录
	 *
	 * @param user
	 * @return
	 */
	@PostMapping("/login")
	public Result<User> login(@RequestBody Map map, HttpSession session) {
		return userService.login(map, session);
	}

}
