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
import java.util.List;

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

    @RequestMapping(value = "search", method = RequestMethod.POST)
    public String findByName(@Valid @ModelAttribute ("byName") String byName, Model model,Errors errors) {
        if(errors.hasErrors()) {
            model.addAttribute("title", "Search for a Favorite Recipe");
            model.addAttribute("authors", userDao.findAll());
            return "recipe/index";
        }
        List<Recipe> results = new ArrayList<>();
        for (Recipe recipe : recipeDao.findByName(byName)) {
            results.add(recipe);
        }
        model.addAttribute("results", results);
        model.addAttribute("title", "Search for a Favorite Recipe");
        model.addAttribute("recipeTypes", RecipeType.values());
        model.addAttribute("authors", userDao.findAll());
        return "recipe/results";

    }

    @RequestMapping(value = "search/{byName}", method = RequestMethod.GET)
    public String searchRecipes(@Valid @ModelAttribute ("byName") String byName, Model model, Errors errors) {
        if(errors.hasErrors()) {
            model.addAttribute("title", "Recipe Search Results");
            model.addAttribute("authors", userDao.findAll());
            return "recipe/index";
        }
        List<Recipe> results = new ArrayList<>();
        for (Recipe recipe : recipeDao.findAll()) {
            if (contains(recipe.getType(), byName)) {
                results.add(recipe);
            }
        }
        model.addAttribute("results", results);
        model.addAttribute("title", "Recipe Search Results");
        model.addAttribute("recipeTypes", RecipeType.values());
        model.addAttribute("authors", userDao.findAll());
        return "recipe/results";
        }

    @RequestMapping(value = "findAuthor", method = RequestMethod.POST)
    public String searchUserRecipes(@Valid @ModelAttribute ("byUser") String byUser, Model model, Errors errors) {
        List<User> results = new ArrayList<>();
        if(errors.hasErrors()) {
            model.addAttribute("title", "Author Search Results");
            model.addAttribute("recipes", recipeDao.findAll());
            return "recipe/index";
        }
        for (User user : userDao.findByNameOrLastName(byUser, byUser)) {
            results.add(user);
        }
        model.addAttribute("results", results);
        model.addAttribute("recipes", recipeDao.findAll());
        model.addAttribute("recipeTypes", RecipeType.values());
        model.addAttribute("title", "Search Results");
        return "user/results";
    }

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

        User author = userDao.findByEmail(request.getRemoteUser());
        newRecipe.setUser(author);
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
        model.addAttribute("recipeTypes", RecipeType.values());
        model.addAttribute("categories", Category.values());
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
        model.addAttribute("recipeTypes", RecipeType.values());
        model.addAttribute("categories", Category.values());
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

    @RequestMapping(value = "user/indiv/{userId}", method = RequestMethod.GET)
    public String processUserSearch(@Valid @ModelAttribute("userId") int userId, Model model, Errors errors){
        User user = userDao.findOne(userId);
        List<Recipe> recipes = new ArrayList<>();
        for (Recipe recipe : recipeDao.findByUser(user)) {
            recipes.add(recipe);
        }
        model.addAttribute("title", "Author Results");
        model.addAttribute("recipes", recipes);
        model.addAttribute("recipeTypes", RecipeType.values());
        model.addAttribute("user", user);
        model.addAttribute("allRecipes", recipeDao.findAll());

        return "user/indiv";
    }

    @RequestMapping(value = "user/home")
    public String userIndex(Model model, HttpServletRequest request){
        User loggedInUser = userService.findUserByEmail(request.getRemoteUser());
        //List<Recipe> userRecipes = loggedInUser.getRecipes();
        List<Recipe> userRecipes = new ArrayList<>();
        for (Recipe recipe : recipeDao.findByUser(loggedInUser)) {
            userRecipes.add(recipe);
        }
        model.addAttribute("title", loggedInUser.getName() + "'s Recipes");
        model.addAttribute("user", loggedInUser);
        model.addAttribute("recipes", userRecipes);
        model.addAttribute("recipeTypes", RecipeType.values());
        return "user/index";
    }

    @RequestMapping(value = "edit/{recipeId}", method = RequestMethod.GET)
    public String displayEditRecipeForm(Model model, @PathVariable int recipeId){
        Recipe recipe = recipeDao.findOne(recipeId);

        model.addAttribute("recipe", recipe);
        model.addAttribute("recipeTypes", RecipeType.values());
        model.addAttribute("categories", Category.values());
        model.addAttribute("ingredients", ingredientDao.findAll());
        model.addAttribute("title", "Edit Recipe " + recipe.getName());
        return "recipe/edit";
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public String processEditRecipeForm(int recipeId, @ModelAttribute @Valid Recipe recipe, Errors errors, Model model){
        if (errors.hasErrors()) {
            model.addAttribute("title", "Edit Recipe");
            model.addAttribute("recipeTypes", RecipeType.values());
            model.addAttribute("categories", Category.values());
            model.addAttribute("ingredients", ingredientDao.findAll());
            model.addAttribute("user", userDao.findAll());
            return "recipe/edit";
        }


        recipeDao.save(recipe);
        return "redirect:/recipe/indiv/" + recipe.getId();
    }

    @RequestMapping(value = "edit", params={"addIngredient"})
    public String addEditIngredient(Model model, Recipe recipe, BindingResult bindingResult) {
        model.addAttribute("recipeTypes", RecipeType.values());
        model.addAttribute("categories", Category.values());
        model.addAttribute("ingredients", ingredientDao.findAll());
        model.addAttribute("user", userDao.findAll());
        recipe.getIngredients().add(new Ingredient());
        return "recipe/edit";
    }

    @RequestMapping(value = "edit", params={"removeIngredient"})
    public String removeEditIngredient(Model model, Recipe recipe, BindingResult bindingResult, HttpServletRequest req) {
        model.addAttribute("ingredients", ingredientDao.findAll());
        Integer ingredientId = Integer.valueOf(req.getParameter("removeIngredient"));
        recipe.getIngredients().remove(ingredientId.intValue());
        model.addAttribute("recipeTypes", RecipeType.values());
        model.addAttribute("categories", Category.values());
        return "recipe/edit";
    }

    @RequestMapping(value = "edit", params={"addStep"})
    public String addEditInstruction(Model model, Recipe recipe, BindingResult bindingResult) {
        model.addAttribute("recipeTypes", RecipeType.values());
        model.addAttribute("categories", Category.values());
        model.addAttribute("ingredients", ingredientDao.findAll());
        model.addAttribute("user", userDao.findAll());
        recipe.getInstructions().add("addStep");
        return "recipe/edit";
    }

    @RequestMapping(value = "edit", params={"removeStep"})
    public String removeEditStep(Model model, Recipe recipe, BindingResult bindingResult, HttpServletRequest req) {
        model.addAttribute("instructions", recipe.getInstructions());
        Integer stepId = Integer.valueOf(req.getParameter("removeIngredient"));
        recipe.getInstructions().remove(stepId.intValue());
        model.addAttribute("recipeTypes", RecipeType.values());
        model.addAttribute("categories", Category.values());
        return "recipe/edit";
    }

}
