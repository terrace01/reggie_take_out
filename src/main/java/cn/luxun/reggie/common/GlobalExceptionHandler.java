package cn.luxun.reggie.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理
 */
@Slf4j
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
public class GlobalExceptionHandler {

	/**
	 * 异常处理方法
	 *
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public Result doException(SQLIntegrityConstraintViolationException ex) {

		log.error(ex.getMessage());
		if (ex.getMessage().contains("Duplicate entry")) {
			String[] split = ex.getMessage().split(" ");
			return Result.error(split[2] + "已存在");
		}
		return Result.error("未知错误");
	}


}
