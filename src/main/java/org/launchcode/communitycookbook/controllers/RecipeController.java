package org.launchcode.communitycookbook.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("recipe")
public class RecipeController {

    @RequestMapping(value = "")
    public String index(Model model) {
        model.addAttribute("title", "My Recipes");

        return "recipes/index";
    }

}
