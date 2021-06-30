package com.sean.recipe.msrecipe.services;

import com.sean.recipe.msrecipe.entities.Recipe;
import com.sean.recipe.msrecipe.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;
import java.util.function.Predicate;

@Slf4j
@DataMongoTest
@Import(RecipeService.class)
public class RecipeServiceTest {

    private final RecipeService recipeService;
    private final RecipeRepository recipeRepository;

    public RecipeServiceTest(@Autowired RecipeService recipeService,
                             @Autowired RecipeRepository recipeRepository){
        this.recipeService = recipeService;
        this.recipeRepository = recipeRepository;
    }

    @Test
    public void getAll(){

        Flux<Recipe> savedRecipes = recipeRepository.saveAll(Flux.just(
                new Recipe(UUID.randomUUID().toString(),
                        "Recipe A",
                        LocalDateTime.now(),
                        false,
                        6,
                        Arrays.asList(
                                "1 Step 1",
                                "2 Step 2"),
                        "Some instructions"),
                new Recipe(UUID.randomUUID().toString(),
                        "Recipe B",
                        LocalDateTime.now(),
                        false,
                        6,
                        Arrays.asList(
                                "1 Step 1",
                                "2 Step 2"),
                        "Some other instructions")));

        Flux<Recipe> composite  = recipeService.all().thenMany(savedRecipes);

        Predicate<Recipe> match = recipe -> savedRecipes.any(savedRecipe -> savedRecipe.equals(recipe)).block();

        StepVerifier
                .create(composite)
                .expectNextMatches(match)
                .expectNextMatches(match)
                .expectComplete();

    }

    @Test
    public void save(){

        Mono<Recipe> recipeMono = this.recipeService.create("Recipe Test Save",
                        false,
                        6,
                        Arrays.asList(
                                "1 Step 1",
                                "2 Step 2"),
                        "Some other instructions");

        StepVerifier
                .create(recipeMono)
                .expectNextMatches(savedRecipe -> StringUtils.hasText(savedRecipe.getId().toString()))
                .expectComplete();

    }

    @Test
    public void getById(){

        String title = "Recipe Test GetById";

        String uuid = UUID.randomUUID().toString();

        Mono<Recipe> entry = this.recipeService
                .create(title,
                        false,
                        6,
                        Arrays.asList(
                                "1 Step 1",
                                "2 Step 2"),
                        "Some other instructions")
                .flatMap(savedRecipe -> this.recipeService.get(savedRecipe.getId()));

        StepVerifier
                .create(entry)
                .expectNextMatches(recipe -> StringUtils.hasText(recipe.getId()) && recipe.getTitle().equals(title))
                .expectComplete();

    }

}
