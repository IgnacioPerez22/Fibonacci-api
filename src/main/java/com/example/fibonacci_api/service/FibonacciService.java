package com.example.fibonacci_api.service;

import com.example.fibonacci_api.model.Fibonacci;
import com.example.fibonacci_api.repository.FibonacciRepository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class FibonacciService {

    @Autowired
    private FibonacciRepository fibonacciRepository;

    private Map<Integer, Long> memoizationCache = new HashMap<>();

    public Long calculateFibonacci(int n) {
        // Verifica si el resultado ya existe en la base de datos
        Optional<Fibonacci> cachedFibonacci = fibonacciRepository.findById(n);
        if (cachedFibonacci.isPresent()) {
            // Incrementar el contador de consultas
            Fibonacci fib = cachedFibonacci.get();
            fib.incrementQueryCount(); // Incrementar el contador de consultas
            fibonacciRepository.save(fib); // Guardar en la base de datos
            return fib.getFibonacciValue();
        }

        // Calcular el Fibonacci usando memoización
        Long result = computeFibonacciWithMemoization(n);

        // Guardar el resultado en la base de datos con contador inicial 1
        fibonacciRepository.save(new Fibonacci(n, result));

        return result;
    }

    private Long computeFibonacciWithMemoization(int n) {
        if (n == 1 || n == 2) {
            return 1L;
        }

        if (memoizationCache.containsKey(n)) {
            return memoizationCache.get(n);
        }

        Long result = computeFibonacciWithMemoization(n - 1) + computeFibonacciWithMemoization(n - 2);
        memoizationCache.put(n, result);
        return result;
    }

    public List<Fibonacci> getFibonacciStatistics() {
        return fibonacciRepository.findAllByOrderByQueryCountDesc();
    }
}
