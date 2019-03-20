package com.marom.recipemongo.controllers;

import com.marom.recipemongo.converters.RecipeToRecipeDto;
import com.marom.recipemongo.domain.Recipe;
import com.marom.recipemongo.exceptions.NotFoundException;
import com.marom.recipemongo.services.CategoryService;
import com.marom.recipemongo.services.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RecipeController {

    private RecipeService recipeService;
    private RecipeToRecipeDto recipeToRecipeDto;
    private CategoryService categoryService;

    public RecipeController(RecipeService recipeService, RecipeToRecipeDto recipeToRecipeDto, CategoryService categoryService) {
        this.recipeService = recipeService;
        this.recipeToRecipeDto = recipeToRecipeDto;
        this.categoryService = categoryService;
    }


    @GetMapping("/recipe/{recipeId}/show")
    public String showRecipe(@PathVariable String recipeId, Model model) {

        Recipe recipe = recipeService.findById(recipeId);
        model.addAttribute("recipe", recipe);
        return "recipe/viewRecipe";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception){

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("404");
        modelAndView.addObject("exception", exception);
        return modelAndView;
    }

    @GetMapping("/recipe/{recipeId}/update")
    public String showRecipeForUpdate(@PathVariable String recipeId, Model model) {

        model.addAttribute("recipe", recipeToRecipeDto.convert(recipeService.findById(recipeId)));
        model.addAttribute("categories", categoryService.getAllCategories());
        return "recipe/editRecipe";
    }
}
