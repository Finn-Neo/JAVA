package com.qlz.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @program: mscloud
 * @author: Qiaolezi
 * @create: 2024-06-12 15:41
 * @description:
 **/
@Configuration
public class RestTemplateConfig {
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
