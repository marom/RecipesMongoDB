package com.marom.recipemongo.controllers;

import com.marom.recipemongo.domain.Recipe;
import com.marom.recipemongo.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


public class HomeControllerTest {

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    HomeController homeController;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(homeController)
                .build();
    }

    @Test
    public void displayAllRecipes() throws Exception {

        Flux<Recipe> recipes = Flux.empty();
        when(recipeService.getAllRecipes()).thenReturn(recipes);


        mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("allRecipes"));
    }
}
