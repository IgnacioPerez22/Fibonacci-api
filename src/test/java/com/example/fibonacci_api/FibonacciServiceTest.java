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
        // Limpiar mocks antes de cada prueba para evitar interferencias
        reset(fibonacciRepository);
    }

    @Test
    void testComputeFibonacciWithMemoization_BaseCases() {
        // Casos base para memoización
        Long result1 = fibonacciService.calculateFibonacci(1);
        Long result2 = fibonacciService.calculateFibonacci(2);

        assertEquals(1L, result1);
        assertEquals(1L, result2);
    }

    @Test
    void testComputeFibonacciWithMemoization_MemoizedCase() {
        // Configura el repositorio para que devuelva un resultado ya calculado
        Fibonacci cachedResult = new Fibonacci(5, 5L);
        cachedResult.incrementQueryCount(); // Simula que el contador ya está en 1

        // Simula que la búsqueda en el repositorio devuelve el resultado ya calculado
        when(fibonacciRepository.findById(5)).thenReturn(Optional.of(cachedResult));

        // Primera llamada, incrementa el contador a 2
        Long resultMemoized1 = fibonacciService.calculateFibonacci(5);
        assertEquals(5L, resultMemoized1); // Verifica que el resultado es el esperado
        assertEquals(2, cachedResult.getQueryCount()); // Verifica que el contador de consultas se incrementó a 2

        // Segunda llamada, incrementa el contador a 3
        Long resultMemoized2 = fibonacciService.calculateFibonacci(5);
        assertEquals(5L, resultMemoized2); // Verifica que el resultado es el esperado
        assertEquals(3, cachedResult.getQueryCount()); // Verifica que el contador de consultas se incrementó a 3

        // Verifica que findById fue llamado dos veces
        verify(fibonacciRepository, times(2)).findById(5);
        // Verifica que save fue llamado dos veces
        verify(fibonacciRepository, times(2)).save(cachedResult);
    }

    @Test
    void testComputeFibonacciWithMemoization_CalculateAndCache() {
        // Configura el repositorio para devolver un resultado vacío
        when(fibonacciRepository.findById(6)).thenReturn(Optional.empty());

        Long resultCalculated = fibonacciService.calculateFibonacci(6);

        assertEquals(8L, resultCalculated); // Supongamos que 8 es el valor esperado para fibonacci(6)
        verify(fibonacciRepository, times(1)).findById(6); // Verifica que se buscó en el repositorio
        verify(fibonacciRepository, times(1)).save(any(Fibonacci.class)); // Verifica que se guardó el nuevo cálculo
    }

    @Test
    void testGetFibonacciStatistics() {
        Fibonacci fib1 = new Fibonacci(1, 1L);
        fib1.incrementQueryCount(); // 2 consultas
        Fibonacci fib2 = new Fibonacci(2, 1L);
        fib2.incrementQueryCount(); // 2 consultas
        fib2.incrementQueryCount(); // 3 consultas

        when(fibonacciRepository.findAllByOrderByQueryCountDesc()).thenReturn(List.of(fib2, fib1));

        List<Fibonacci> statistics = fibonacciService.getFibonacciStatistics();

        assertEquals(2, statistics.size());
        assertEquals(fib2, statistics.get(0)); // El elemento con más consultas debería estar primero
        assertEquals(fib1, statistics.get(1)); // El elemento con menos consultas debería estar segundo
        verify(fibonacciRepository, times(1)).findAllByOrderByQueryCountDesc();
    }
}
