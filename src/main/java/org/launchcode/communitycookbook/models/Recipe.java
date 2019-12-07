package org.launchcode.communitycookbook.models;


import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.*;

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

    //@ElementCollection
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name = "recipe_ingredients")
    private List<Ingredient> ingredients = new ArrayList<Ingredient>();

    private String instructions;

    //@ElementCollection(targetClass = Category.class)
    //@CollectionTable(name = "recipe_category", joinColumns= {@JoinColumn(name="recipe_id")})
    //private Set<Category> categories;

    //@Enumerated(EnumType.STRING)
    private Category categories;

    //@ManyToMany
    //private List<Category> categories = new ArrayList<>();

    //@ElementCollection(targetClass = Category.class)
    //@CollectionTable(name = "recipe_category", joinColumns = {@JoinColumn(name = "recipe_id")})
    //@Column(name = "categories", nullable = false)
    //@Enumerated(EnumType.STRING)
    //private Collection<Category> categories;

    public Recipe() {}

    public Recipe(String name, User user, String source, RecipeType type, int servings, int time, List<Ingredient> ingredients,
                  String instructions, Category categories) {
        this.name = name;
        this.user = user;
        this.source = source;
        this.type = type;
        this.servings = servings;
        this.time = time;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.categories = categories;
    }

    public int getId() { return id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public String getSource() { return source; }

    public void setSource(String source) { this.source = source; }

    public int getServings() { return servings; }

    public void setServings(int servings) { this.servings = servings; }

    public int getTime() { return time; }

    public void setTime(int time) { this.time = time; }

    public String getInstructions() { return instructions; }

    public void setInstructions(String instructions) { this.instructions = instructions; }

    public RecipeType getType() { return type; }

    public void setType(RecipeType type) { this.type = type; }

    public Category getCategories () { return categories; }

    //public void addCategory(Category category) { this.categories.add(category); }

    public void setCategories(Category categories) { this.categories = categories; }

    public List<Ingredient> getIngredients() { return ingredients; }

    public void addIngredient(Ingredient ingredient) { this.ingredients.add(ingredient); }

    public void setIngredients(ArrayList<Ingredient> ingredients) { this.ingredients = ingredients; }


}
