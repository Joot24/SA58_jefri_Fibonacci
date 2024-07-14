package com.project.moneycounting;

import com.project.moneycounting.controller.MoneyCountController;
import com.project.moneycounting.model.MoneyCount;
import com.project.moneycounting.validator.MoneyCountValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// https://medium.com/javarevisited/restful-api-testing-in-java-with-mockito-controller-layer-f4605f8ffaf3

@WebMvcTest(controllers = MoneyCountController.class)
@ExtendWith(MockitoExtension.class)
public class MoneyCountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MoneyCountValidator moneyCountValidator;

    private MoneyCount moneyCount;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        moneyCount = new MoneyCount();
        when(moneyCountValidator.supports(any())).thenReturn(true);
    }

    @Test
    public void testCountForm() throws Exception {
        // Perform HTTP Get request
        ResultActions response =mockMvc.perform(get("/api/count"));

        // Asserting the response expectations
        response.andExpect(status().isOk())
                .andExpect(content().json(MoneyCount.DENOMINATION_AMOUNTS.toString()));
    }

    @Test
    public void testCountResult() throws Exception {
        // Perform HTTP Post request with assumed target and selected denominations
        ResultActions response =mockMvc.perform(post("/api/count")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"targetAmount\":\"100.00\",\"denominationSelected\":[true,false,true,false,true,false,true,false,true,false,true,false]}"));

        // Asserting the response expectations
        response.andExpect(status().isOk())
                .andExpect(content().json("[0, 0, 0, 0, 0, 1]")); // Expected answer
    }

}
