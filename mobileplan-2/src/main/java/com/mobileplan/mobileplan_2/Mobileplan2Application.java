package com.mobileplan.mobileplan_2;

import com.mobileplan.mobileplan_2.filter.JWTFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Mobileplan2Application {

	@Bean
	FilterRegistrationBean<JWTFilter> jwtFilter() {
		FilterRegistrationBean<JWTFilter> filterBean = new FilterRegistrationBean<JWTFilter>();
		filterBean.setFilter(new JWTFilter());
		filterBean.addUrlPatterns("/mobileplan/*");
		return filterBean;
	}

	public static void main(String[] args) {
		SpringApplication.run(Mobileplan2Application.class, args);
		System.out.println("Mobile Plan 2 Application Started!....");
	}

}
