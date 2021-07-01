package com.sean.recipe.msrecipe.handlers;

import com.sean.recipe.msrecipe.entities.Recipe;
import com.sean.recipe.msrecipe.services.RecipeService;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.net.URI;
import java.time.LocalDateTime;

@Component
public class RecipeHandler {

    private final RecipeService recipeService;

    public RecipeHandler(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    public Mono<ServerResponse> findAll(ServerRequest request){
        return defaultReadResponse(recipeService.findAll());
    }

    public Mono<ServerResponse> findById(ServerRequest request){

        String id = request.pathVariable("id");
        Mono<Recipe> recipe = recipeService.findById(id);

        return defaultReadResponse(recipe);

    }

    public Mono<ServerResponse> create(ServerRequest request){

        Flux<Recipe> recipe = request
                .bodyToFlux(Recipe.class)
                .flatMap(r -> this.recipeService.create(
                        r.getTitle(),
                        r.isVegetarian(),
                        r.getServes(),
                        r.getIngredients(),
                        r.getCookingInsructions()));

        return defaultWriteResponse(recipe);

    }

    public Mono<ServerResponse> updateById(ServerRequest request){

        String id = request.pathVariable("id");

        Flux<Recipe> recipeFlux = request.bodyToFlux(Recipe.class)
                .flatMap(p -> this.recipeService.update(
                        id,
                        p.getTitle(),
                        p.isVegetarian(),
                        p.getServes(),
                        p.getIngredients(),
                        p.getCookingInsructions()));

        return defaultWriteResponse(recipeFlux);

    }

    public Mono<ServerResponse> deleteById(ServerRequest request){

        String id = request.pathVariable("id");
        return defaultReadResponse(this.recipeService.delete(id));

    }

    //SH - TODO = update responses (NB for update method)

    private static Mono<ServerResponse> defaultReadResponse(Publisher<Recipe> recipes){

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(recipes,Recipe.class);

    }

    private static Mono<ServerResponse> defaultWriteResponse(Publisher<Recipe> recipes){

        return Mono
                .from(recipes)
                .flatMap(r -> ServerResponse
                        .created(URI.create("/recipes/" + r.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .build());

    }
}
