package cn.luxun.reggie.service.impl;

import cn.luxun.reggie.common.Result;
import cn.luxun.reggie.common.ValidateCodeUtils;
import cn.luxun.reggie.entity.User;
import cn.luxun.reggie.mapper.UserMapper;
import cn.luxun.reggie.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

	@Override
	public Result<String> sendMsg(User user, HttpSession session) {

		// 获取手机号
		String phone = user.getPhone();
		System.out.println("手机号" + phone);
		if (StringUtils.isNotEmpty(phone)) {
			// 生辰随机的4位验证码
			// String code = ValidateCodeUtils.generateValidateCode(4).toString();
			String code = "2001";
			//调用阿里云提供的短信服务接口完成发送短信

			//需要将生辰的验证码保存到Session中
			session.setAttribute(phone, code);
			System.out.println(phone + "@" + code);
			return Result.success("手机验证码短信发送成功");
		}

		return Result.success("手机验证码短信发送失败");
	}

	@Override
	public Result<User> login(Map map, HttpSession session) {

		// 获取手机号
		String phone = map.get("phone").toString();

		// 获取验证码
		String code = map.get("code").toString();

		//从Session中获取保存的验证码
		Object codeInSession = session.getAttribute(phone);
		System.out.println(codeInSession);
		// 进行验证码的比对  页面提交的验证码和Session中保存的验证码比对
		if (codeInSession != null && codeInSession.equals(code)) {

			// 如果能够比对成功，说明登录成功
			LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(User::getPhone, phone);

			User user = this.getOne(queryWrapper);
			if (user == null) {
				//判断当前手机号对应用户是否为新用户 如果是新用户就自动完成注册
				user = new User();
				user.setPhone(phone);
				user.setStatus(1);
				user.setName(phone);
				this.save(user);
			}
			session.setAttribute("user", user.getId());
			return Result.success(user);


		}


		return Result.error("登陆失败");
	}
}
