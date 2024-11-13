package com.example.todo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.todo.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByName(String name);
}
