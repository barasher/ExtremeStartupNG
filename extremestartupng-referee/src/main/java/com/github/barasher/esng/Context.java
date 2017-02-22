package com.github.barasher.esng;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import com.github.barasher.esng.model.Game;
import com.github.barasher.esng.model.Player;
import com.github.barasher.esng.question.QuestionFactory;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableAsync
@EnableScheduling
public class Context {

	@Bean
	public Game getReferee() {
		return new Game();
	}

	@Bean
	public QuestionFactory getQuestionFactory() {
		return new QuestionFactory();
	}

	@Bean
	@Scope("prototype")
	public Player getPlayer() {
		return new Player();
	}

	@Bean
	public MetricManager getMetricManager() {
		return new MetricManager();
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.github.barasher.esng.controller"))
				.paths(PathSelectors.any()).build();
	}

}
