package com.example.fibonacci_api;

import org.junit.jupiter.api.Test;

import com.example.fibonacci_api.model.Fibonacci;

import static org.junit.jupiter.api.Assertions.*;

class FibonacciTest {

    @Test
    void testDefaultConstructor() {
        Fibonacci fibonacci = new Fibonacci();
        assertNotNull(fibonacci);
        assertEquals(0, fibonacci.getId());
        assertNull(fibonacci.getFibonacciValue());
        assertEquals(0, fibonacci.getQueryCount());
    }

    @Test
    void testParameterizedConstructor() {
        Fibonacci fibonacci = new Fibonacci(5, 8L);
        assertNotNull(fibonacci);
        assertEquals(5, fibonacci.getId());
        assertEquals(8L, fibonacci.getFibonacciValue());
        assertEquals(0, fibonacci.getQueryCount());
    }

    @Test
    void testSettersAndGetters() {
        Fibonacci fibonacci = new Fibonacci();

        fibonacci.setId(10);
        assertEquals(10, fibonacci.getId());

        fibonacci.setFibonacciValue(55L);
        assertEquals(55L, fibonacci.getFibonacciValue());

        fibonacci.setId(15);
        assertEquals(15, fibonacci.getId());

        fibonacci.incrementQueryCount();
        assertEquals(1, fibonacci.getQueryCount());

        fibonacci.incrementQueryCount();
        assertEquals(2, fibonacci.getQueryCount());
    }

    @Test
    void testIncrementQueryCount() {
        Fibonacci fibonacci = new Fibonacci(5, 8L);
        fibonacci.incrementQueryCount();
        assertEquals(1, fibonacci.getQueryCount());
    }
}
