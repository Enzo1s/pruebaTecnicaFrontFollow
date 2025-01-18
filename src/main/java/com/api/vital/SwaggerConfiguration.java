package com.api.vital;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
	
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	
	
	private ApiKey apiKey() { 
	    return new ApiKey("JWT", "Authorization", "header"); 
	}

	private SecurityContext securityContext() { 
	    return SecurityContext.builder().securityReferences(defaultAuth()).build(); 
	} 
	
	private List<SecurityReference> defaultAuth() { 
	    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything"); 
	    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1]; 
	    authorizationScopes[0] = authorizationScope; 
	    return Arrays.asList(new SecurityReference("JWT", authorizationScopes)); 
	}

	
	@Bean
    public Docket api() {
            return new Docket(DocumentationType.SWAGGER_2)
                		     .securityContexts(Arrays.asList(securityContext()))
                	         .securitySchemes(Arrays.asList(apiKey()))
                             .select()
                             .apis(
                                     RequestHandlerSelectors
                                     .basePackage("com.api.vital.controller"))
                             .paths(PathSelectors.any())
                             .build()
                             .apiInfo(getApiInfo());
         }
	
	private ApiInfo getApiInfo() {
		return new ApiInfo(
				"Follow Hub",
				"Digital Logístico",
				"1.0",
				"",
				new Contact("Follow Hub", "http://followlsn.com", "info@followlsn.com"),
				"Api License",
				"LICENSE URL",
				Collections.emptyList()
				);
	}
}
