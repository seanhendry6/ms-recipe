package com.sean.recipe.msrecipe.controllers;

import com.sean.recipe.msrecipe.entities.Recipe;
import com.sean.recipe.msrecipe.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class RecipesController {

    @Autowired
    private RecipeService recipeService;

    public ResponseEntity<Flux<Recipe>> getAllRecipes(){
       return null;
    }

}
