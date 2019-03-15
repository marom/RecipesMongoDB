package com.marom.recipemongo.controllers;

import com.marom.recipemongo.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private RecipeService recipeService;


    public HomeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @GetMapping("/")
    public String displayAllRecipes(Model model) {

        model.addAttribute("recipes", recipeService.getAllRecipes());
        return "allRecipes";
    }
}
