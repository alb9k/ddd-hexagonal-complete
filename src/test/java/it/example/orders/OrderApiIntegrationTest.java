package it.example.orders;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderApiIntegrationTest {
    @Autowired
    MockMvc mvc;

    @Test
    void createOrder() throws Exception {
        mvc.perform(post("/api/v1/orders").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"product":"Book","quantity":2,"unitPrice":10.50,"currency":"EUR"}
                                """))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.total").value(21.0))
                .andExpect(jsonPath("$.status").value("CREATED"));
    }

    @Test
    void invalidOrder() throws Exception {
        mvc.perform(post("/api/v1/orders").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"product":"","quantity":0,"unitPrice":0,"currency":"EUR"}
                                """))
                .andExpect(status().isBadRequest());
    }
}