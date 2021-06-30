package com.sean.recipe.msrecipe.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {

    @Id
    private String id;
    private String title;
    private LocalDateTime created;
    private boolean vegetarian;
    private Integer serves;
    private List<String> ingredients;
    private String cookingInsructions;

}
