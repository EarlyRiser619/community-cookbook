package org.launchcode.communitycookbook.models;

import com.sun.istack.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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

    /*private List<Recipe> recipes = new ArrayList<>();*/

    public Ingredient() {}

    public Ingredient(String name){ this.name = name; }

    public int getId() { return id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
