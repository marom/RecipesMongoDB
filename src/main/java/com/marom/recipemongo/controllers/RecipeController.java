package com.marom.recipemongo.controllers;

import com.marom.recipemongo.domain.Recipe;
import com.marom.recipemongo.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RecipeController {

    private RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @GetMapping("/recipe/{recipeId}/show")
    public String showRecipe(@PathVariable String recipeId, Model model) {

        Recipe recipe = recipeService.findById(recipeId);
        model.addAttribute("recipe", recipe);
        return "recipe/viewRecipe";
    }
}
