package com.vamsi.newsletter.subscriptionapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @author Vamsi Krihna
 * 
 *         Swagger 2 Configuration
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket swaggerDocketApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.vamsi.newsletter.subscriptionapi"))
				.paths(PathSelectors.regex("/.*")).build().apiInfo(apiEndpointsInfo());
	}

	private ApiInfo apiEndpointsInfo() {
		return new ApiInfoBuilder().title("Newsletter Subscription REST API")
				.description("Its Newsletter subscription REST API built using Spring Boot")
				.contact(new Contact("Vamsi Krishna", "https://www.linkedin.com/in/vamsi-krishna-v",
						"vamsi020@gmail.com"))
				.version("1.0.0").build();
	}

}
