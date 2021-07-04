package com.sean.recipe.msrecipe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication(exclude={MongoAutoConfiguration.class})
@EnableReactiveMongoRepositories
public class MsRecipeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsRecipeApplication.class, args);
	}

}
