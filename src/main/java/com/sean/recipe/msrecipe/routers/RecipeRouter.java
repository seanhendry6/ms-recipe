package com.sean.recipe.msrecipe.routers;

import com.sean.recipe.msrecipe.handlers.RecipeHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RecipeRouter {

    private static final String RECIPES = "/recipes";

    @Bean
    public RouterFunction<ServerResponse> routes (RecipeHandler recipeHandler){

        return route()
                .nest(path(RECIPES).and(accept(MediaType.APPLICATION_JSON)), builder -> builder
                        .GET("", recipeHandler::findAll)
                        .GET("/{id}", recipeHandler::findById)
                        .POST("", recipeHandler::create)
                        .PUT("/{id}", recipeHandler::updateById)
                        .DELETE("/{id}", recipeHandler::deleteById)
                ).build();

    }

}
