package org.launchcode.communitycookbook.models;

import java.util.List;

public class IngredientsList {
    private List<Ingredient> ingredients;

    private Ingredient ingredient;

    public IngredientsList() {}

    public IngredientsList(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public void addIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
