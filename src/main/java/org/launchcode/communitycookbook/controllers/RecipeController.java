package org.launchcode.communitycookbook.controllers;

import org.launchcode.communitycookbook.models.Category;
import org.launchcode.communitycookbook.models.Recipe;
import org.launchcode.communitycookbook.models.RecipeType;
import org.launchcode.communitycookbook.models.User;
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
import java.util.Optional;

import static org.thymeleaf.util.StringUtils.contains;

@Controller
@RequestMapping("recipe")
public class RecipeController {

    @Autowired
    private RecipeDao recipeDao;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("recipes", recipeDao.findAll());
        model.addAttribute("title", "Community Cookbook");
        model.addAttribute("recipeTypes", RecipeType.values());
        model.addAttribute("users", userDao.findAll());

        return "recipe/index";
    }

    @RequestMapping(value = "search/{byName}", method = RequestMethod.GET)
    public String searchRecipes(@Valid @ModelAttribute ("byName") String byName, Model model, Errors errors) {
        List<Recipe> results = new ArrayList<>();
        for (Recipe recipe : recipeDao.findAll()) {
            if (contains(recipe.getName(), byName)) {
                return "redirect:/recipe/indiv/" + recipe.getId();
            } else {
                if (contains(recipe.getType(), byName)) {
                    results.add(recipe);
                }
            }
        }
        model.addAttribute("results", results);
        model.addAttribute("title", "Search Results");
        return "recipe/results";
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
            model.addAttribute("user", userDao.findAll());
            return "recipe/add";
        }

        ArrayList<String> ingredients = new ArrayList<>();
        for (String ingredient : newRecipe.getIngredients()) {
            newRecipe.addIngredient(ingredient);
        }

        User author = userDao.findByEmail(request.getRemoteUser());
        newRecipe.setUser(author);
        newRecipe.setIngredients(ingredients);
        recipeDao.save(newRecipe);
        return "redirect:/recipe/indiv/" + newRecipe.getId();
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
