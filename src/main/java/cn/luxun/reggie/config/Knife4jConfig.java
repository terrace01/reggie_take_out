package cn.luxun.reggie.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
@Import(BeanValidatorPluginsConfiguration.class)
public class Knife4jConfig {

	@Bean(value = "defaultApi2")
	public Docket defaultApi2() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(new ApiInfoBuilder()
						.title("瑞吉外卖")
						.description("# SpringBootRESTful APIs - 20220418-20220424")
						.termsOfServiceUrl("https://www.zhaozhenyu.cn/")
						.version("0.0.1")
						.build())
				//分组名称
				.groupName("reggie")
				.select()
				//这里指定Controller扫描包路径
				.apis(RequestHandlerSelectors.basePackage("cn.luxun.reggie.Controller"))
				.paths(PathSelectors.any())
				.build();
	}
}