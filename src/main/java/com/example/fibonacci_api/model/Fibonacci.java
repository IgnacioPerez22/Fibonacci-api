package com.example.fibonacci_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Fibonacci {

    @Id
    private int id; // El índice n del número de Fibonacci
    private Long fibonacciValue; // El n-ésimo número de Fibonacci
    private int queryCount; // Contador de consultas

    // Constructor por defecto
    public Fibonacci() {}

    public Fibonacci(int id, Long fibonacciValue) {
        this.id = id;
        this.fibonacciValue = fibonacciValue;
        this.queryCount = 0; // Inicia el contador de consultas en 1
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getFibonacciValue() {
        return fibonacciValue;
    }

    public void setFibonacciValue(Long fibonacciValue) {
        this.fibonacciValue = fibonacciValue;
    }

    public int getQueryCount() {
        return queryCount;
    }

    public void incrementQueryCount() {
        this.queryCount++;
    }
}
