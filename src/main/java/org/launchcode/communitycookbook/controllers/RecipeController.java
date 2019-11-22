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

    @RequestMapping(value = "search")
    public String search(@RequestAttribute @Valid Recipe byName, Model model, Errors errors) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Find Recipe");
            model.addAttribute("recipes", recipeDao.findAll());
            return "recipe/search";
        }

        List<Recipe> searchByName = new ArrayList<>();
        for (Recipe recipe : recipeDao.findAll()) {
            if (contains(recipe.getName(), byName.getName())){
                searchByName.add(recipe);
            }
        }
        model.addAttribute("results", searchByName);
        model.addAttribute("title", "Search Results");


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
                                       HttpServletRequest request, ArrayList<String> ingredients, Errors errors){

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Recipe");
            model.addAttribute("recipeTypes", RecipeType.values());
            model.addAttribute("categories", Category.values());
            model.addAttribute("user", userDao.findAll());
            return "recipe/add";
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

        return "recipe/indiv";
    }

    @RequestMapping(value = "user/home")
    public String userIndex(Model model, HttpServletRequest request){
        /* need to verify active session for user and pass user info into model*/

        User loggedInUser = userService.findUserByEmail(request.getRemoteUser());
        List<Recipe> userRecipes = loggedInUser.getUserRecipes();
        model.addAttribute("title", loggedInUser.getName() + "'s Recipes");
        model.addAttribute("user", loggedInUser);
        model.addAttribute("recipes", loggedInUser.getUserRecipes());
        model.addAttribute("recipeTypes", RecipeType.values());
        return "user/index";
    }

}
