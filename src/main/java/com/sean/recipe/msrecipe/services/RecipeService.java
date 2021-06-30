package com.sean.recipe.msrecipe.services;

import com.sean.recipe.msrecipe.entities.Recipe;
import com.sean.recipe.msrecipe.events.RecipeCreatedEvent;
import com.sean.recipe.msrecipe.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class RecipeService {

    private final ApplicationEventPublisher publisher;
    private final RecipeRepository recipeRepository;

    public RecipeService(ApplicationEventPublisher publisher, RecipeRepository recipeRepository) {
        this.publisher = publisher;
        this.recipeRepository = recipeRepository;
    }

    public Flux<Recipe> all(){
        return this.recipeRepository.findAll();
    }

    public Mono<Recipe> create(String title, boolean vegetarian, Integer serves, List<String> ingredients, String cookingInsructions){

        return this.recipeRepository
                .save(new Recipe(UUID.randomUUID().toString(), title, LocalDateTime.now(), vegetarian, serves, ingredients, cookingInsructions))
                .doOnSuccess(recipe -> this.publisher.publishEvent(new RecipeCreatedEvent(recipe)));

    }

    public Mono<Recipe> update(String id, boolean vegetarian, Integer serves, List<String> ingredients, String cookingInsructions){

        return this.recipeRepository
                .findById(id)
                .map(recipe -> new Recipe(
                        recipe.getId(),
                        recipe.getTitle(),
                        recipe.getCreated(),
                        recipe.isVegetarian(),
                        recipe.getServes(),
                        recipe.getIngredients(),
                        recipe.getCookingInsructions()))
                .flatMap(this.recipeRepository::save);

    }

    public Mono<Recipe> delete(String id){

        return this.recipeRepository
                .findById(id)
                .flatMap(recipe -> this.recipeRepository.deleteById(recipe.getId()).thenReturn(recipe));

    }


    public Mono<Recipe> get(String id){
        return this.recipeRepository.findById(id);
    }

}
