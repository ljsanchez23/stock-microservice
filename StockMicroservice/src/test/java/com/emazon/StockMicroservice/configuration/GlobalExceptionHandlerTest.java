package com.emazon.StockMicroservice.configuration;

import com.emazon.StockMicroservice.configuration.util.TestController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(TestController.class)
class GlobalExceptionHandlerTest {

    private final MockMvc mockMvc;

    public GlobalExceptionHandlerTest(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void whenInvalidNameException_thenReturnsConflict() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/test/invalid-name")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid name exception triggered."));
    }

    @Test
    void whenInvalidDescriptionException_thenReturnsBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/test/invalid-description")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid description exception triggered."));
    }

    @Test
    void whenInvalidCategoryException_thenReturnsBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/test/invalid-category")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid category exception triggered."));
    }

    @Test
    void whenInvalidPriceException_thenReturnsBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/test/invalid-price")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid price exception triggered."));
    }

    @Test
    void whenInvalidQuantityException_thenReturnsBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/test/invalid-quantity")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid quantity exception triggered."));
    }

    @Test
    void whenGeneralException_thenReturnsInternalServerError() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/test/general")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("An unexpected error occurred."));
    }
}

