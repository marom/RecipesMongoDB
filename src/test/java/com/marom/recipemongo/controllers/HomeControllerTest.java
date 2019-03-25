package com.marom.recipemongo.controllers;

import com.marom.recipemongo.domain.Recipe;
import com.marom.recipemongo.services.RecipeService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.mockito.Mockito.when;

@Ignore("servlet is not loaded when using webflux")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HomeControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    HomeController homeController;

   // MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        webTestClient = WebTestClient.bindToController(homeController).build();
//        mockMvc = MockMvcBuilders.standaloneSetup(homeController)
//                .build();
    }

    @Test
    public void displayAllRecipes() throws Exception {

        Flux<Recipe> recipes = Flux.empty();
        when(recipeService.getAllRecipes()).thenReturn(recipes);


       // mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("allRecipes"));

        webTestClient.get()
                .uri("/")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response ->
                        Assertions.assertThat(response.getResponseBody()).isNotNull());

    }
}
