package com.marom.recipemongo.controllers;

import com.marom.recipemongo.converters.RecipeToRecipeDto;
import com.marom.recipemongo.dto.RecipeDto;
import com.marom.recipemongo.services.IngredientService;
import com.marom.recipemongo.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
public class IngredientController {

    private RecipeService recipeService;
    private IngredientService ingredientService;
    private RecipeToRecipeDto toRecipeDto;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, RecipeToRecipeDto toRecipeDto) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.toRecipeDto = toRecipeDto;
    }

    @GetMapping("/recipe/{recipeId}/ingredients")
    public String listIngredients(@PathVariable String recipeId, Model model){
        log.debug("Getting ingredient list for recipe id: " + recipeId);

        final RecipeDto recipeDto = toRecipeDto.convert(recipeService.findById(recipeId));
        model.addAttribute("recipe", recipeDto);

        return "recipe/ingredient/list";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String showIngredientDetails(@PathVariable String recipeId,
                                        @PathVariable String ingredientId,
                                        Model model) {
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId));
        return "recipe/ingredient/show";
    }


}
