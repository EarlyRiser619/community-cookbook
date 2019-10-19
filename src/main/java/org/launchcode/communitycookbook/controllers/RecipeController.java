package org.launchcode.communitycookbook.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("recipe")
public class RecipeController {

    @RequestMapping(value = "")
    @ResponseBody
    public String index() {
        return "My Recipes";
    }

    @RequestMapping(value = "goodbye")
    @ResponseBody
    public String goodbye() {
        return "Goodbye";
    }

}
