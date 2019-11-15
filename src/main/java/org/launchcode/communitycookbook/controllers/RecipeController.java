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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("recipe")
public class RecipeController {

    @Autowired
    private RecipeDao recipeDao;

    @Autowired
    private UserService userService;

    private UserDao userDao;

    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("recipes", recipeDao.findAll());
        model.addAttribute("title", "All Recipes");

        return "recipe/index";
    }

    @RequestMapping(value = "search")
    public String search(Model model) {
        model.addAttribute("title", "Search All Recipes");

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
        return "recipe/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddRecipeForm(Model model, @ModelAttribute @Valid Recipe newRecipe, Errors errors){

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Recipe");
            model.addAttribute("recipes", recipeDao.findAll());
            return "recipe/add";
        }

        recipeDao.save(newRecipe);
        return "recipe/indiv";
    }

    /*public String displayIndivRecipe(Model model, Errors errors){

    }*/

    @RequestMapping(value = "user/home")
    public String userIndex(Model model, HttpServletRequest request){
        /* need to verify active session for user and pass user info into model*/

        User loggedInUser = userService.findUserByEmail(request.getRemoteUser());
        model.addAttribute("title", loggedInUser.getName() + "'s Recipes");
        model.addAttribute("user", loggedInUser);
        return "user/index";
    }

}
