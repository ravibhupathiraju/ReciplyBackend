package com.lmig.reciply;
//import com.zaxxer.hikari.HikariConfig;

//import com.zaxxer.hikari.HikariDataSource;

import com.lmig.reciply.AppUserRepository;

import com.lmig.reciply.ReciplyApplication;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

//import static javax.measure.unit.SI.KILOGRAM;

import java.sql.SQLException;
import java.util.Map;

//import javax.measure.quantity.Mass;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.jscience.physics.amount.Amount;
//import org.jscience.physics.model.RelativisticModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableSwagger2
@Controller
public class ReciplyApplication {

	@Autowired
	private AppUserRepository appUserRepository;

	@Autowired
	private MealPlanRepository mealPlanRepository;

	@Autowired
	private RecipeRepository recipeRepository;

	@Autowired
	private IngredientRepository ingredientrepository;

	private static final Logger log = LoggerFactory
			.getLogger(ReciplyApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ReciplyApplication.class, args);
		Utilities utils = new Utilities();
		String password = "Glen";
		System.out.println("***New Password = " + utils.hashPassword(password));
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public Docket swaggerSettings() {
		// ApiInfo api_updated = new ApiInfo("Recip-ly API", "Recip-ly API
		// Documentation", "Version 1.0", null, null, null, null);
		return new Docket(DocumentationType.SWAGGER_2).groupName("Recip-ly API")
				.select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build()
				// .apiInfo(api_updated)
				.pathMapping("/");
	}

}
