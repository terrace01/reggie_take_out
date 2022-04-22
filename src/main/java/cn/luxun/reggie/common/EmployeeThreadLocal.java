package cn.luxun.reggie.common;


/**
 * 基于ThreadLocal封装工具类  用户保存和获取当前登录用户id
 */
public class EmployeeThreadLocal {

	//线程变量隔离
	private static final ThreadLocal<Long> LOCAL = new ThreadLocal<>();

	/**
	 * 设置值
	 * @param id
	 */
	public static void setCurrentId(Long id) {
		LOCAL.set(id);
	}

	/**
	 * 获取值
	 * @return
	 */
	public static Long getCurrentId() {
		return LOCAL.get();
	}

}
