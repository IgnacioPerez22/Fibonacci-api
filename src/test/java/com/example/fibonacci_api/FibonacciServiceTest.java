package com.example.fibonacci_api;

import com.example.fibonacci_api.model.Fibonacci;
import com.example.fibonacci_api.repository.FibonacciRepository;
import com.example.fibonacci_api.service.FibonacciService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FibonacciServiceTest {

    @Mock
    private FibonacciRepository fibonacciRepository;

    @InjectMocks
    private FibonacciService fibonacciService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        reset(fibonacciRepository);
    }

    @Test
    void testComputeFibonacciWithMemoization_BaseCases() {
        Long result1 = fibonacciService.calculateFibonacci(1);
        Long result2 = fibonacciService.calculateFibonacci(2);

        assertEquals(1L, result1);
        assertEquals(1L, result2);
    }

    @Test
    void testComputeFibonacciWithMemoization_MemoizedCase() {
        Fibonacci cachedResult = new Fibonacci(5, 5L);
        cachedResult.incrementQueryCount();

        when(fibonacciRepository.findById(5)).thenReturn(Optional.of(cachedResult));

        Long resultMemoized1 = fibonacciService.calculateFibonacci(5);
        assertEquals(5L, resultMemoized1);
        assertEquals(2, cachedResult.getQueryCount());

        Long resultMemoized2 = fibonacciService.calculateFibonacci(5);
        assertEquals(5L, resultMemoized2);
        assertEquals(3, cachedResult.getQueryCount());

        verify(fibonacciRepository, times(2)).findById(5);
        verify(fibonacciRepository, times(2)).save(cachedResult);
    }

    @Test
    void testComputeFibonacciWithMemoization_CalculateAndCache() {
        when(fibonacciRepository.findById(6)).thenReturn(Optional.empty());

        Long resultCalculated = fibonacciService.calculateFibonacci(6);

        assertEquals(8L, resultCalculated);
        verify(fibonacciRepository, times(1)).findById(6);
        verify(fibonacciRepository, times(1)).save(any(Fibonacci.class));
    }

    @Test
    void testGetFibonacciStatistics() {
        Fibonacci fib1 = new Fibonacci(1, 1L);
        fib1.incrementQueryCount();
        Fibonacci fib2 = new Fibonacci(2, 1L);
        fib2.incrementQueryCount();
        fib2.incrementQueryCount();

        when(fibonacciRepository.findAllByOrderByQueryCountDesc()).thenReturn(List.of(fib2, fib1));

        List<Fibonacci> statistics = fibonacciService.getFibonacciStatistics();

        assertEquals(2, statistics.size());
        assertEquals(fib2, statistics.get(0));
        assertEquals(fib1, statistics.get(1));
        verify(fibonacciRepository, times(1)).findAllByOrderByQueryCountDesc();
    }
}
