package org.launchcode.communitycookbook.models;

import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Category {

   /* GLUTENFREE (1,"Gluten-Free"),
    VEGETARIAN (2,"Vegetarian"),
    VEGAN (3,"Vegan"),
    LOWSODIUM (4,"Low Sodium");

    private final String name;

    @Column(name = "category_id")
    private final int id;

    private static final Map<Integer, Category> byId = new HashMap<Integer, Category>();

    //@ManyToMany(mappedBy = "categories")
    //private List<Recipe> recipes;

    Category(int id, String name) { this.name = name; this.id = id; }

    public String getName() { return name; }

    public int getId() { return id; }

    public static Category getById(int id){
        return byId.get(id);
    }
*/



    /* If Category ever changes back into a class... */

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=3, max=30)
    private String name;

    @ManyToMany(mappedBy= "categories")
    private List<Recipe> recipes;

    public void addRecipe(Recipe recipe) { recipes.add(recipe); }

    public Category() {}

    public Category(String name){ this.name = name; }

    public int getId() { return id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public List<Recipe> getRecipes() { return recipes; }
}
