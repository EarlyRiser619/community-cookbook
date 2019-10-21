package org.launchcode.communitycookbook.models;

import java.util.ArrayList;
import java.util.List;

public class Recipe {

    private int id;

    private String name;

    private String source;

    private int servings;

    private float prepTime;

    private List<Ingredient> ingredients = new ArrayList<>();

    private String instructions;

    private List<Category> categories = new ArrayList<>();

    public void addIngredient(Ingredient item){
        ingredients.add(item);
    }

    public void addCategory(Category category){
        categories.add(category);
    }

    public Recipe() {}

    public Recipe(String name, String source, int servings, float prepTime, String instructions) {
        this.name = name;
        this.source = source;
        this.servings = servings;
        this.prepTime = prepTime;
        this.instructions = instructions;
    }

    public int getId() { return id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getSource() { return source; }

    public void setSource(String source) { this.source = source; }

    public int getServings() { return servings; }

    public void setServings(int servings) { this.servings = servings; }

    public float getPrepTime() { return prepTime; }

    public void setPrepTime(float prepTime) { this.prepTime = prepTime; }

    public String getInstructions() { return instructions; }

    public void setInstructions(String instructions) { this.instructions = instructions; }

    public List<Ingredient> getIngredients () { return ingredients; }

    public List<Category> getCategories () { return categories; }
}
