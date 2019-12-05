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

    //@ManyToOne
    //private Recipe recipe;

    public Ingredient() {}

    /*public Ingredient(String name, Recipe recipe){
        this.name = name;
        this.recipe = recipe;
    } */

    public Ingredient(String name) {
        this.name = name;
    }

    public int getId() { return id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    //public Recipe getRecipe() { return recipe; }

    //public void setRecipe(Recipe recipe) { this.recipe = recipe; }
}
