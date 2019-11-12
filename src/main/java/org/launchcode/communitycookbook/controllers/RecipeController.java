package org.launchcode.communitycookbook.controllers;

import org.launchcode.communitycookbook.models.Recipe;
import org.launchcode.communitycookbook.models.RecipeType;
import org.launchcode.communitycookbook.models.data.RecipeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;

@Controller
@RequestMapping("recipe")
public class RecipeController {

    @Autowired
    private RecipeDao recipeDao;

    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("recipes", recipeDao.findAll());
        model.addAttribute("title", "My Recipes");

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
        return "redirect:";
    }

}
