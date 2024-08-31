package com.example.fibonacci_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Fibonacci {

    @Id
    private int id;
    private Long fibonacciValue;
    private int queryCount;

    public Fibonacci() {
    }

    public Fibonacci(int id, Long fibonacciValue) {
        this.id = id;
        this.fibonacciValue = fibonacciValue;
        this.queryCount = 0;
    }

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
