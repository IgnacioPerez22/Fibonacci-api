package com.example.fibonacci_api;
import com.example.fibonacci_api.repository.FibonacciRepository;
import com.example.fibonacci_api.service.FibonacciService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = FibonacciApiApplication.class)
@AutoConfigureMockMvc
class FibonacciControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FibonacciService fibonacciService;

    @MockBean
    private FibonacciRepository fibonacciRepository;

    @Test
    void testGetFibonacci_ValidInput() throws Exception {
        when(fibonacciService.calculateFibonacci(5)).thenReturn(5L);

        mockMvc.perform(get("/api/fibonacci/5")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    @Test
    void testGetFibonacci_InvalidInput() throws Exception {
        mockMvc.perform(get("/api/fibonacci/0")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetFibonacciStatistics() throws Exception {
        mockMvc.perform(get("/api/fibonacci/statistics")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
