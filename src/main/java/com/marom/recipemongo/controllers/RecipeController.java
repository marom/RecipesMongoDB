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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.exceptions.TemplateInputException;
import reactor.core.publisher.Mono;

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

    private WebDataBinder webDataBinder;

    public RecipeController(RecipeService recipeService, RecipeToRecipeDto recipeToRecipeDto, CategoryService categoryService, RecipeDtoToRecipe recipeDtoToRecipe, CategoryToCategoryDto categoryToCategoryDto) {
        this.recipeService = recipeService;
        this.recipeToRecipeDto = recipeToRecipeDto;
        this.categoryService = categoryService;
        this.recipeDtoToRecipe = recipeDtoToRecipe;
        this.categoryToCategoryDto = categoryToCategoryDto;
    }

    @InitBinder("recipe")
    public void initBinder(WebDataBinder webDataBinder){
        this.webDataBinder = webDataBinder;
    }


    @GetMapping("/recipe/{recipeId}/show")
    public String showRecipe(@PathVariable String recipeId, Model model) {

        Mono<Recipe> recipe = recipeService.findById(recipeId);
        model.addAttribute("recipe", recipe);
        return "recipe/viewRecipe";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotFoundException.class, TemplateInputException.class})
    public String handleNotFound(Exception exception, Model model){

        log.error("Handling not found exception");
        log.error(exception.getMessage());

        model.addAttribute("exception", exception);

        return "404";
    }

    @GetMapping("/recipe/{recipeId}/update")
    public String showRecipeForUpdate(@PathVariable String recipeId, Model model) {

        List<CategoryDto> categoriesDto = new ArrayList<>();
        categoryService.getAllCategories().forEach((Category category) -> categoriesDto.add(categoryToCategoryDto.convert(category)));

        model.addAttribute("recipe",recipeToRecipeDto.convert(recipeService.findById(recipeId).block()));
        model.addAttribute("allCategories", categoriesDto);

        return "recipe/editRecipe";
    }

    @PostMapping("recipe")
    public String updateRecipe(@ModelAttribute("recipe") RecipeDto recipeDto, Model model) {

        webDataBinder.validate();
        BindingResult bindingResult = webDataBinder.getBindingResult();

        if(bindingResult.hasErrors()){

            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });
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
