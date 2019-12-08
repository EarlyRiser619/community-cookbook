package org.launchcode.communitycookbook.controllers;

import org.launchcode.communitycookbook.models.*;
import org.launchcode.communitycookbook.models.data.IngredientDao;
import org.launchcode.communitycookbook.models.data.RecipeDao;
import org.launchcode.communitycookbook.models.data.UserDao;
import org.launchcode.communitycookbook.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.thymeleaf.util.StringUtils.contains;

@Controller
@RequestMapping("recipe")
public class RecipeController {

    @Autowired
    private RecipeDao recipeDao;

    @Autowired
    private IngredientDao ingredientDao;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("recipes", recipeDao.findAll());
        model.addAttribute("title", "Community Cookbook");
        model.addAttribute("recipeTypes", RecipeType.values());
        model.addAttribute("ingredients", ingredientDao.findAll());
        model.addAttribute("users", userDao.findAll());

        return "recipe/index";
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public String findByName(@Valid @ModelAttribute String byName, Model model,Errors errors) {
        List<Recipe> results = new ArrayList<>();
        for (Recipe recipe : recipeDao.findAll()) {
            if (contains(recipe.getName(), byName)) {
                results.add(recipe);
            }
        }
        model.addAttribute("results", results);
        model.addAttribute("title", "Search for a Favorite Recipe");
        return "recipe/results";

    }

    @RequestMapping(value = "findAuthor", method = RequestMethod.GET)
    public String findByUser(@Valid @ModelAttribute String byUser, Model model,Errors errors) {
        List<User> results = new ArrayList<>();
        for (User user : userDao.findAll()) {
            if (contains(user.getName(), byUser)) {
                results.add(user);
            }
        }
        model.addAttribute("results", results);
        model.addAttribute("title", "Search for a Favorite Author");
        return "user/results";

    }


    @RequestMapping(value = "search/{byName}", method = RequestMethod.GET)
    public String searchRecipes(@Valid @ModelAttribute ("byName") String byName, Model model, Errors errors) {
        List<Recipe> results = new ArrayList<>();
        for (Recipe recipe : recipeDao.findAll()) {
            if (contains(recipe.getType(), byName)) {
                results.add(recipe);
            }
        }
        model.addAttribute("results", results);
        model.addAttribute("title", "Recipe Search Results");
        model.addAttribute("allUsers", userDao.findAll());
        return "recipe/results";
        }

    @RequestMapping(value = "search/{byUser}", method = RequestMethod.GET)
    public String searchUserRecipes(@Valid @ModelAttribute ("byUser") String byUser, Model model, Errors errors) {
        List<User> results = new ArrayList<>();
        for (User user : userDao.findAll()) {
            if (contains(user.getName(), byUser)) {
                results.add(user);
            } else if (contains(user.getLastName(), byUser)) {
                results.add(user);
            }
        }
        model.addAttribute("results", results);
        model.addAttribute("allRecipes", recipeDao.findAll());
        model.addAttribute("title", "Search Results");
        return "user/results";
    }

    /* @RequestMapping(value = "results", method = RequestMethod.POST)
    public String results(Model model, @Valid @ModelAttribute List<Recipe> results, Errors errors) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Find Recipe");
            model.addAttribute("recipes", recipeDao.findAll());
            model.addAttribute("errors", errors);
            return "recipe/search";
        }

        model.addAttribute("results", results);
        model.addAttribute("title", "Search Results");
        return "redirect:recipe/searchResults";
    } */


    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddRecipeForm(Model model) {


        model.addAttribute("title", "Add Recipe");
        model.addAttribute(new Recipe());
        model.addAttribute("recipeTypes", RecipeType.values());
        model.addAttribute("categories", Category.values());
        model.addAttribute("ingredients", ingredientDao.findAll());
        model.addAttribute("user", userDao.findAll());
        return "recipe/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddRecipeForm(Model model, @ModelAttribute @Valid Recipe newRecipe,
                                       HttpServletRequest request, Errors errors){

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Recipe");
            model.addAttribute("recipeTypes", RecipeType.values());
            model.addAttribute("categories", Category.values());
            model.addAttribute("ingredients", ingredientDao.findAll());
            model.addAttribute("user", userDao.findAll());
            return "recipe/add";
        }

        /*ArrayList<String> ingredients = new ArrayList<>();
        for (String ingredient : newRecipe.getIngredients()) {
            ingredients.add(ingredient);
        }*/

        //Category cat = Category.getById(int id : categoryId);
        //newRecipe.setCategories(cat);
        User author = userDao.findByEmail(request.getRemoteUser());
        newRecipe.setUser(author);
        //newRecipe.setIngredients(ingredients);
        recipeDao.save(newRecipe);
        return "redirect:/recipe/indiv/" + newRecipe.getId();
    }

    @RequestMapping(value = "add", params={"addIngredient"})
    public String addIngredient(Model model, Recipe newRecipe, BindingResult bindingResult) {
        model.addAttribute("recipeTypes", RecipeType.values());
        model.addAttribute("categories", Category.values());
        model.addAttribute("ingredients", ingredientDao.findAll());
        model.addAttribute("user", userDao.findAll());
        newRecipe.getIngredients().add(new Ingredient());
        return "recipe/add";
    }

    @RequestMapping(value = "add", params={"removeIngredient"})
    public String removeIngredient(Model model, Recipe newRecipe, BindingResult bindingResult, HttpServletRequest req) {
        model.addAttribute("ingredients", ingredientDao.findAll());
        Integer ingredientId = Integer.valueOf(req.getParameter("removeIngredient"));
        newRecipe.getIngredients().remove(ingredientId.intValue());
        return "recipe/add";
    }

    @RequestMapping(value = "add", params={"addStep"})
    public String addInstruction(Model model, Recipe newRecipe, BindingResult bindingResult) {
        model.addAttribute("recipeTypes", RecipeType.values());
        model.addAttribute("categories", Category.values());
        model.addAttribute("ingredients", ingredientDao.findAll());
        model.addAttribute("user", userDao.findAll());
        newRecipe.getInstructions().add("addStep");
        return "recipe/add";
    }

    @RequestMapping(value = "add", params={"removeStep"})
    public String removeStep(Model model, Recipe newRecipe, BindingResult bindingResult, HttpServletRequest req) {
        model.addAttribute("instructions", newRecipe.getInstructions());
        Integer stepId = Integer.valueOf(req.getParameter("removeIngredient"));
        newRecipe.getInstructions().remove(stepId.intValue());
        return "recipe/add";
    }


    @RequestMapping(value = "indiv/{recipeId}")
    public String displayIndivRecipe(@Valid @ModelAttribute("recipeId") int recipeId, Model model, Errors errors){
        Recipe recipe = recipeDao.findOne(recipeId);
        model.addAttribute("title", recipe.getName());
        model.addAttribute("recipe", recipe);
        model.addAttribute("recipeTypes", RecipeType.values());
        model.addAttribute("categories", recipe.getCategories());
        model.addAttribute("ingredients", recipe.getIngredients());

        return "recipe/indiv";
    }

    @RequestMapping(value = "user/indiv/{userId}")
    public String displayUserSearch(@Valid @ModelAttribute("userId") int userId, Model model, Errors errors){
        User user = userDao.findOne(userId);
        model.addAttribute("title", user.getName() + user.getLastName());
        model.addAttribute("recipes", user.getUserRecipes());
        model.addAttribute("recipeTypes", RecipeType.values());
        model.addAttribute("user", user);

        return "user/indiv";
    }

    @RequestMapping(value = "user/home")
    public String userIndex(Model model, HttpServletRequest request){
        /* need to verify active session for user and pass user info into model*/

        User loggedInUser = userService.findUserByEmail(request.getRemoteUser());
        List<Recipe> userRecipes = loggedInUser.getUserRecipes();
        model.addAttribute("title", loggedInUser.getName() + "'s Recipes");
        model.addAttribute("user", loggedInUser);
        model.addAttribute("recipes", userRecipes);
        model.addAttribute("recipeTypes", RecipeType.values());
        return "user/index";
    }

}
