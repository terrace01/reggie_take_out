package cn.luxun.reggie.config;


import cn.luxun.reggie.common.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

	/**
	 * 设置静态资源的映射
	 * url 映射到 resouces 里的 文件
	 *
	 * @param registry
	 */
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		log.info("开始进行静态资源映射...");
		registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
		registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
	}


	@Override
	protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

		// 创建消息转换器对象
		MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();

		// 设置对象转换器  底层使用Jackcon将对象转为json
		messageConverter.setObjectMapper(new JacksonObjectMapper());

		// 将上面的消息转换器对象追加到springMVC框架的转换器集合中
		converters.add(0, messageConverter);


	}
}
