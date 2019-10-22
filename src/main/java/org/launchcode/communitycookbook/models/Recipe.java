package org.launchcode.communitycookbook.models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Recipe {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    private String source;

    private int servings;

    private int time;

    private String ingredient;

    /*private List<Ingredient> ingredients = new ArrayList<>();*/

    private String instructions;

    private String category;

    /*private List<Category> categories = new ArrayList<>();*/

    /*public void addIngredient(Ingredient item){
        ingredients.add(item);
    }*/

    /*public void addCategory(Category category){
        categories.add(category);
    }*/

    public Recipe() {}

    public Recipe(String name, String source, int servings, int time, String ingredient, String instructions) {
        this.name = name;
        this.source = source;
        this.servings = servings;
        this.time = time;
        this.ingredient = ingredient;
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

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

    /*public List<Ingredient> getIngredients () { return ingredients; }*/

    /*public List<Category> getCategories () { return categories; }*/
}
