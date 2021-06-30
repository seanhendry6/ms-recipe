package com.sean.recipe.msrecipe.repository;

import com.sean.recipe.msrecipe.entities.Recipe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@Component
@Slf4j
@org.springframework.context.annotation.Profile("demo")  //only initialize sample data when demo profile is selected
public class SampleDataInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final RecipeRepository recipeRepository;

    public SampleDataInitializer(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

        String instructions =
                "Step 1\n" +
                        "Preheat oven to 350 degrees F (175 degrees C).\n\n"
                        + "Step 2\n"
                        + "Place chicken in a roasting pan, and season generously\n"
                        + "inside and out with salt and pepper. Sprinkle inside and out\n"
                        + "with onion powder. Place 3 tablespoons margarine in the chicken\n"
                        + "cavity. Arrange dollops of the remaining margarine around the \n"
                        + "chicken's exterior. Cut the celery into 3 or 4 pieces, and place \n"
                        + "in the chicken cavity.\n\n"
                        + "Step 3\n"
                        + "Bake uncovered 1 hour and 15 minutes in the preheated oven, to a\n"
                        + "minimum internal temperature of 180 degrees F (82 degrees C). Remove\n"
                        + "from heat, and baste with melted margarine and drippings. Cover with\n"
                        + "aluminum foil, and allow to rest about 30 minutes before serving.";

        recipeRepository
                .deleteAll()
                .thenMany(
                        Flux
                            .just("EntryA") //Reactor factory method to create in-memory item for conversion to Entity thereafter
                            .map(recipeName -> new Recipe(UUID.randomUUID().toString(),
                                    "Juicy Roast Chicken",
                                    LocalDateTime.now(),
                                    false,
                                    6,
                                    Arrays.asList(
                                            "1 (3 pound) whole chicken, giblets removed",
                                            "salt and black pepper to taste",
                                            "1 tablespoon onion powder, or to taste",
                                            "1/2 cup margarine, divided",
                                            "1 stalk celery, leaves removed"),
                                    instructions))
                            .flatMap(recipeRepository::save)
                )
                .map(Recipe::toString)
                .subscribe(log::info);

    }


}
