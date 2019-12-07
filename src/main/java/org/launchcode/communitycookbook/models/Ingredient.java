package org.launchcode.communitycookbook.models;

import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Ingredient {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=3, max=30)
    private String name;

    private String measurement;

    @ManyToOne
    private Recipe recipe_ingredients;

    public Ingredient() {}

    /*public Ingredient(String name, Recipe recipe){
        this.name = name;
        this.recipe = recipe;
    } */

    public Ingredient(String name, String measurement) {
        this.name = name;
        this.measurement = measurement;
    }

    public int getId() { return id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getMeasurement() { return measurement; }

    public void setMeasurement(String measurement) { this.measurement = measurement; }

    public Recipe getRecipe() { return recipe_ingredients; }

    public void setRecipe(Recipe recipe_ingredients) { this.recipe_ingredients = recipe_ingredients; }
}
