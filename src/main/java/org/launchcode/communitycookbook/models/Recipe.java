package org.launchcode.communitycookbook.models;


import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Recipe {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=3, max=40)
    private String name;

    private RecipeType type;

    private String source;

    private int servings;

    private int time;

    private String ingredient;

    @ManyToMany(mappedBy = "recipes")
    private List<Ingredient> ingredients;

    private String instructions;

    @ManyToMany
    private List<Category> categories;

    public void addIngredient(Ingredient item){ ingredients.add(item); }

    public Recipe() {}

    public Recipe(String name, String source, int servings, int time, String instructions) {
        this.name = name;
        this.source = source;
        this.servings = servings;
        this.time = time;
        this.instructions = instructions;
    }

    public int getId() { return id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getSource() { return source; }

    public void setSource(String source) { this.source = source; }

    public int getServings() { return servings; }

    public void setServings(int servings) { this.servings = servings; }

    public int getTime() { return time; }

    public void setTime(int time) { this.time = time; }

    public String getIngredient() { return ingredient; }

    public void setIngredient(String ingredient) { this.ingredient = ingredient; }

    public String getInstructions() { return instructions; }

    public void setInstructions(String instructions) { this.instructions = instructions; }

    public RecipeType getType() { return type; }

    public void setType(RecipeType type) { this.type = type; }

    public List<Ingredient> getIngredients () { return ingredients; }

    public List<Category> getCategories () { return categories; }
}
