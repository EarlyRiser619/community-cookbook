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
import java.util.List;

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
        model.addAttribute("title", "All Recipes");
        model.addAttribute("user", userDao.findAll());

        return "recipe/index";
    }

    @RequestMapping(value = "search")
    public String search(Model model) {
        model.addAttribute("title", "Search All Recipes");
        model.addAttribute("recipes", recipeDao.findAll());

        return "recipe/search";
    }

    /*@RequestMapping(value = "search/results")
    public String listSearchResults(Model model) {
        model.addAttribute("title", "Search Results");
    }  */

    /*@RequestMapping(value = "search/{recipeId}")
    public String indivSearchResults(Model model) {
        model.addAttribute("title", "{Recipe.name}");
    }  */


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
                                       HttpServletRequest request, String ingredients, Errors errors){

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Recipe");
            model.addAttribute("recipeTypes", RecipeType.values());
            model.addAttribute("categories", Category.values());
            model.addAttribute("user", userDao.findAll());
            return "recipe/add";
        }

        User author = userDao.findByEmail(request.getRemoteUser());
        newRecipe.setUser(author);
        newRecipe.addIngredient(ingredients);
        recipeDao.save(newRecipe);
        return "recipe/indiv";
    }

    /*public String displayIndivRecipe(Model model, Errors errors){

    }*/

    @RequestMapping(value = "user/home")
    public String userIndex(Model model, HttpServletRequest request){
        /* need to verify active session for user and pass user info into model*/

        User loggedInUser = userService.findUserByEmail(request.getRemoteUser());
        List<Recipe> userRecipes = loggedInUser.getUserRecipes();
        model.addAttribute("title", loggedInUser.getName() + "'s Recipes");
        model.addAttribute("user", loggedInUser);
        model.addAttribute("recipes", userRecipes);
        return "user/index";
    }

}
