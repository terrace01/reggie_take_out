package cn.luxun.reggie.service;

import cn.luxun.reggie.common.Result;
import cn.luxun.reggie.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface UserService extends IService<User> {

	/**
	 * 发送手机短信验证码
	 *
	 * @param user
	 * @return
	 */
	Result<String> sendMsg(User user, HttpSession session);

	/**
	 * 移动端用户登录
	 *
	 * @param user
	 * @return
	 */
	Result<User> login(Map map, HttpSession session);
}
