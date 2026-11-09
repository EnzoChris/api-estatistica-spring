package com.api.estatistica;

import org.junit.jupiter.api.Test;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.api.estatistica.controller.MetricasController;

class EstatisticaApplicationTests {

	@Test
	void contextLoads() {
		try (ConfigurableApplicationContext context = new SpringApplicationBuilder(EstatisticaApplication.class)
				.web(WebApplicationType.NONE)
				.run()) {
			org.junit.jupiter.api.Assertions.assertNotNull(context.getBean(MetricasController.class));
		}
	}

}
