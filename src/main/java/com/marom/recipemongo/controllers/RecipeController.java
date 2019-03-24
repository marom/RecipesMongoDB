package com.marom.recipemongo.controllers;

import com.marom.recipemongo.converters.CategoryToCategoryDto;
import com.marom.recipemongo.converters.RecipeDtoToRecipe;
import com.marom.recipemongo.converters.RecipeToRecipeDto;
import com.marom.recipemongo.domain.Category;
import com.marom.recipemongo.domain.Recipe;
import com.marom.recipemongo.dto.CategoryDto;
import com.marom.recipemongo.dto.RecipeDto;
import com.marom.recipemongo.exceptions.NotFoundException;
import com.marom.recipemongo.services.CategoryService;
import com.marom.recipemongo.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class RecipeController {

    private RecipeService recipeService;
    private CategoryService categoryService;
    private RecipeToRecipeDto recipeToRecipeDto;
    private RecipeDtoToRecipe recipeDtoToRecipe;
    private CategoryToCategoryDto categoryToCategoryDto;

    public RecipeController(RecipeService recipeService, RecipeToRecipeDto recipeToRecipeDto, CategoryService categoryService, RecipeDtoToRecipe recipeDtoToRecipe, CategoryToCategoryDto categoryToCategoryDto) {
        this.recipeService = recipeService;
        this.recipeToRecipeDto = recipeToRecipeDto;
        this.categoryService = categoryService;
        this.recipeDtoToRecipe = recipeDtoToRecipe;
        this.categoryToCategoryDto = categoryToCategoryDto;
    }


    @GetMapping("/recipe/{recipeId}/show")
    public String showRecipe(@PathVariable String recipeId, Model model) {

        Recipe recipe = recipeService.findById(recipeId).block();
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

        List<CategoryDto> categoriesDto = new ArrayList<>();
        categoryService.getAllCategories().forEach((Category category) -> categoriesDto.add(categoryToCategoryDto.convert(category)));

        model.addAttribute("recipe", recipeToRecipeDto.convert(recipeService.findById(recipeId).block()));
        model.addAttribute("allCategories", categoriesDto);

        return "recipe/editRecipe";
    }

    @PostMapping("recipe")
    public String updateRecipe(@Valid @ModelAttribute("recipe") RecipeDto recipeDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> log.error(objectError.toString()));
            return "recipe/editRecipe";
        }
        Recipe savedRecipe = recipeService.saveRecipe(recipeDtoToRecipe.convert(recipeDto)).block();

        return "redirect:/recipe/" + savedRecipe.getId() + "/show" ;
    }

    @GetMapping("/recipe/{id}/delete")
    public String deleteRecipe(@PathVariable String id) {
        recipeService.deleteById(id);
        return "redirect:/";
    }
}
