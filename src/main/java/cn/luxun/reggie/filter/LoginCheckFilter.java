package cn.luxun.reggie.filter;


import cn.luxun.reggie.common.EmployeeThreadLocal;
import cn.luxun.reggie.common.Result;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 检查 用户 是否已经完成登录
 */
@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

	// 路径匹配器 支持通配符
	public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse reponse = (HttpServletResponse) servletResponse;

		// 获取本次请求的uri
		String requestURI = request.getRequestURI();
		log.info("拦截到请求:{}", requestURI);


		// 定义不需要处理的请求路径
		String[] urls = new String[]{
				"/employee/login",
				"/employee/logout",
				"/backend/**",
				"/front/**",
				"/common/**",
		};

		// 判断本次请求是否需要处理
		boolean check = check(urls, requestURI);

		// 如果不需要处理 则直接放行
		if (check) {
			log.info("本次请求{}不需要处理", requestURI);
			filterChain.doFilter(request, reponse);
			return;
		}

		// 判读登陆状态 如果已登录 则直接放行
		if (request.getSession().getAttribute("employee") != null) {
			log.info("用户已登录，用户id为{}", request.getSession().getAttribute("employee"));

			// 将员工id传入封装到的 ThreadLocal中 进行保存
			Long empId = (Long) request.getSession().getAttribute("employee");
			EmployeeThreadLocal.setCurrentId(empId);
			// System.out.println("@" + EmployeeThreadLocal.getCurrentId());


			filterChain.doFilter(request, reponse);
			return;
		}
		log.info("用户未登录");

		// 如果未登录则返回未登录结果 通过输出流方式向客户端页面响应数据
		reponse.getWriter().write(JSON.toJSONString(Result.error("NOTLOGIN")));
		return;

	}

	/**
	 * 路径匹配 检查本次请求是否需要放行
	 *
	 * @param urls
	 * @param requestURI
	 * @return
	 */
	public boolean check(String[] urls, String requestURI) {

		for (String url : urls) {
			boolean match = PATH_MATCHER.match(url, requestURI);
			if (match) {
				return true;
			}
		}
		return false;
	}

}
