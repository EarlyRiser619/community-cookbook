package org.launchcode.communitycookbook.models;

public enum RecipeType {

    APPETIZER ("Appetizer"),
    SOUP ("Soup"),
    SALAD ("Salad"),
    BREAD ("Bread"),
    SANDWICH ("Sandwich"),
    SIDE ("Side Dish"),
    MAIN ("Main Course"),
    DESSERT ("Dessert"),
    BEVERAGE ("Beverage"),
    OTHER ("Other");

    private final String name;

    RecipeType(String name) { this.name = name; }

    public String getName() { return name; }


}
