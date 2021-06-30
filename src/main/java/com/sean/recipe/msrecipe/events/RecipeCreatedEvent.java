package com.sean.recipe.msrecipe.events;

import com.sean.recipe.msrecipe.entities.Recipe;
import org.springframework.context.ApplicationEvent;

public class RecipeCreatedEvent extends ApplicationEvent {

    public RecipeCreatedEvent(Recipe recipe) {
        super(recipe);
    }
}
