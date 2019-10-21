package org.launchcode.communitycookbook.models;

import java.util.ArrayList;
import java.util.List;

public class Category {

    private int id;

    private String name;

    private List<Recipe> recipes = new ArrayList<>();

    public Category() {}

    public Category(String name){ this.name = name; }

    public int getId() { return id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
