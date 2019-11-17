package org.launchcode.communitycookbook.models;


import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Recipe {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=3, max=40)
    private String name;

    private RecipeType type;

    @ManyToOne
    private User user;

    private String source;

    private int servings;

    private int time;

    @ManyToOne
    private Ingredient ingredient;

    private String instructions;

    @ElementCollection(targetClass = Category.class)
    @CollectionTable(name = "recipe_category",
            joinColumns = @JoinColumn(name = "recipe_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "category_id")
    private Set<Category> categorySet;

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

    public Ingredient getIngredient() { return ingredient; }

    public void setIngredient(Ingredient ingredient) { this.ingredient = ingredient; }

    public String getInstructions() { return instructions; }

    public void setInstructions(String instructions) { this.instructions = instructions; }

    public RecipeType getType() { return type; }

    public void setType(RecipeType type) { this.type = type; }

    public Set<Category> getCategories () { return categorySet; }
}
