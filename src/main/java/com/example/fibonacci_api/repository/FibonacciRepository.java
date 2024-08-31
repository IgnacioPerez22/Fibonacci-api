package com.example.fibonacci_api.repository;

import com.example.fibonacci_api.model.Fibonacci;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FibonacciRepository extends JpaRepository<Fibonacci, Integer> {
    List<Fibonacci> findAllByOrderByQueryCountDesc();
}