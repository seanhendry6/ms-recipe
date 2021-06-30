package com.sean.recipe.msrecipe.repository;

import com.sean.recipe.msrecipe.entities.Recipe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface RecipeRepository extends ReactiveMongoRepository<Recipe, String> {
}
