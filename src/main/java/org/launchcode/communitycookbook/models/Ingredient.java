package org.launchcode.communitycookbook.models;

import java.util.ArrayList;
import java.util.List;

public class Ingredient {

    private int id;

    private String name;

    private List<Recipe> recipes = new ArrayList<>();

    public Ingredient() {}

    public Ingredient(String name){ this.name = name; }

    public int getId() { return id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
