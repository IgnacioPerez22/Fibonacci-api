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
        Optional<Fibonacci> cachedFibonacci = fibonacciRepository.findById(n);
        if (cachedFibonacci.isPresent()) {
            Fibonacci fib = cachedFibonacci.get();
            fib.incrementQueryCount();
            fibonacciRepository.save(fib);
            return fib.getFibonacciValue();
        }

        Long result = computeFibonacciWithMemoization(n);

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
