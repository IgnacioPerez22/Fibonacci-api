package com.example.fibonacci_api.controller;

import com.example.fibonacci_api.model.Fibonacci;
import com.example.fibonacci_api.repository.FibonacciRepository;
import com.example.fibonacci_api.service.FibonacciService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fibonacci")
public class FibonacciController {

    @Autowired
    private FibonacciService fibonacciService;

    @Autowired
    private FibonacciRepository fibonacciRepository;

    @GetMapping("/{n}")
    public ResponseEntity<Long> getFibonacci(@PathVariable int n) {
        if (n < 1) {
            return ResponseEntity.badRequest().body(null);
        }
        Long result = fibonacciService.calculateFibonacci(n);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/statistics")
    public List<Fibonacci> getFibonacciStatistics() {
        // Obtener los números de Fibonacci más consultados
        return fibonacciRepository.findAllByOrderByQueryCountDesc();
    }
}
