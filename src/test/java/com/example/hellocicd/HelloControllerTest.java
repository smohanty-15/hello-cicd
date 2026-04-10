package com.example.hellocicd;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHomeEndpoint() throws Exception {
        mockMvc.perform(get("/"))
               .andExpect(status().isOk())
               .andExpect(content().string(
                   "Hello! CI/CD Pipeline - Version 2 is live!"));
    }

    @Test
    public void testGreetEndpoint() throws Exception {
        mockMvc.perform(get("/hello/Jenkins"))
               .andExpect(status().isOk())
               .andExpect(content().string(
                   "Hello Jenkins! Jenkins built and tested this automatically!"));
    }

    @Test
    public void testHealthEndpoint() throws Exception {
        mockMvc.perform(get("/health"))
               .andExpect(status().isOk());
    }
}
