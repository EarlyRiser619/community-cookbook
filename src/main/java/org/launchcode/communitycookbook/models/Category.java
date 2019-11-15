package org.launchcode.communitycookbook.models;

import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


public enum Category {

    GLUTENFREE ("Gluten-Free"),
    VEGITARIAN ("Vegitarian"),
    VEGAN ("Vegan"),
    LOWSODIUM ("Low Sodium");

    private final String name;

    Category(String name) { this.name = name; }

    public String getName() { return name; }



    /* If Category ever changes back into a class...

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=3, max=30)
    private String name;

    @ManyToMany(mappedBy= "categories")
    private List<Recipe> recipes = new ArrayList<>();

    public void addRecipe(Recipe recipe) { recipes.add(recipe); }

    public Category() {}

    public Category(String name){ this.name = name; }

    public int getId() { return id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public List<Recipe> getRecipes() { return recipes; } */
}
