package com.example.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.todo.entity.Status;

public interface StatusRepository extends JpaRepository<Status, Long> {
}
