package com.marom.recipemongo.controllers;

import com.marom.recipemongo.converters.IngredientDtoToIngredient;
import com.marom.recipemongo.converters.IngredientToIngredientDto;
import com.marom.recipemongo.converters.RecipeToRecipeDto;
import com.marom.recipemongo.converters.UnitOfMeasureToUnitOfMeasureDto;
import com.marom.recipemongo.domain.Ingredient;
import com.marom.recipemongo.domain.Recipe;
import com.marom.recipemongo.dto.IngredientDto;
import com.marom.recipemongo.dto.UnitOfMeasureDto;
import com.marom.recipemongo.services.IngredientService;
import com.marom.recipemongo.services.RecipeService;
import com.marom.recipemongo.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
public class IngredientController {

    private RecipeService recipeService;
    private IngredientService ingredientService;
    private RecipeToRecipeDto toRecipeDto;
    private IngredientToIngredientDto toIngredientDto;
    private UnitOfMeasureToUnitOfMeasureDto toUnitOfMeasureDto;
    private UnitOfMeasureService unitOfMeasureService;
    private IngredientDtoToIngredient toIngredient;

    private WebDataBinder webDataBinder;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, RecipeToRecipeDto toRecipeDto, IngredientToIngredientDto toIngredientDto, UnitOfMeasureToUnitOfMeasureDto toUnitOfMeasureDto, UnitOfMeasureService unitOfMeasureService, IngredientDtoToIngredient toIngredient) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.toRecipeDto = toRecipeDto;
        this.toIngredientDto = toIngredientDto;
        this.toUnitOfMeasureDto = toUnitOfMeasureDto;
        this.unitOfMeasureService = unitOfMeasureService;
        this.toIngredient = toIngredient;
    }

    @InitBinder("ingredient")
    public void initBinder(WebDataBinder webDataBinder){
        this.webDataBinder = webDataBinder;
    }


    @GetMapping("/recipe/{recipeId}/ingredients")
    public String listIngredients(@PathVariable String recipeId, Model model){
        log.debug("Getting ingredient list for recipe id: " + recipeId);

        model.addAttribute("recipe", recipeService.findById(recipeId).map(toRecipeDto::convert));
        return "recipe/ingredient/list";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String showIngredientDetails(@PathVariable String recipeId,
                                        @PathVariable String ingredientId,
                                        Model model) {
        Mono<IngredientDto> ingredientDtoMono = ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId)
                .map(toIngredientDto::convert);

        model.addAttribute("ingredient", ingredientDtoMono);
        return "recipe/ingredient/show";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId,
                                         @PathVariable String ingredientId, Model model){

        IngredientDto ingredientDto = toIngredientDto.convert(ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId).block());

        model.addAttribute("ingredient", ingredientDto);
        return "recipe/ingredient/updateIngredient";
    }

    @PostMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute("ingredient") IngredientDto ingredientDto, Model model){

        webDataBinder.validate();
        BindingResult bindingResult = webDataBinder.getBindingResult();

        if(bindingResult.hasErrors()) {

            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });
            return "recipe/ingredient/updateIngredient";
        }

        Ingredient savedIngredient = ingredientService.saveIngredient(toIngredient.convert(ingredientDto)).block();

        log.debug("saved recipe id:" + savedIngredient.getRecipeId());
        log.debug("saved ingredient id:" + savedIngredient.getId());

        return "redirect:/recipe/" + savedIngredient.getRecipeId() + "/ingredient/" + savedIngredient.getId() + "/show";
    }

    @RequestMapping("recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteIngredient(@PathVariable String recipeId,
                                   @PathVariable String ingredientId){

        ingredientService.deleteById(recipeId, ingredientId).block();

        return "redirect:/recipe/" + recipeId + "/ingredients";
    }

    @GetMapping("recipe/{recipeId}/ingredient/new")
    public String newRecipe(@PathVariable String recipeId, Model model){

        //make sure we have a good id value
        Recipe recipe = recipeService.findById(recipeId).block();
        //todo raise exception if null

        //need to return back parent id for hidden form property
        IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setRecipeId(recipe.getId());
        model.addAttribute("ingredient", ingredientDto);

        //init uom
        ingredientDto.setUom(new UnitOfMeasureDto());
        return "recipe/ingredient/updateIngredient";
    }

    @ModelAttribute("uomList")
    public Flux<UnitOfMeasureDto> getAllUoms() {
        return unitOfMeasureService.listAllUoms().map(toUnitOfMeasureDto::convert);
    }
}
