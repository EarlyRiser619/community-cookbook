package org.launchcode.communitycookbook.controllers;


import org.launchcode.communitycookbook.models.User;
import org.launchcode.communitycookbook.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "{id}")
    public String index(Model model, @PathVariable int id) {
        model.addAttribute("title", "Welcome!");
        model.addAttribute("user", userDao.findById(id));
        return "user/index";
    }

    @RequestMapping(value="add", method= RequestMethod.GET)
    public String add(Model model){
        model.addAttribute("title", "Create Account");
        model.addAttribute("user", new User());
        return "user/add";
    }

    @RequestMapping(value="add", method=RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid User user, String verify, Errors errors) {

        if (user.getPassword() == verify) {
            model.addAttribute("user", user);
            if (errors.hasErrors()) {
                model.addAttribute("Title", "Create Account");
                model.addAttribute("user", new User());
                return "user/add";
            }
        }
        userDao.save(user);
        return "redirect:";
    }

    @RequestMapping(value="login", method= RequestMethod.GET)
    public String login(Model model){
        model.addAttribute("title", "Login");
        return "user/login";
    }

    @RequestMapping(value="login", method= RequestMethod.POST)
    public String login(Model model, User user){
        return "user/index";
    }

}