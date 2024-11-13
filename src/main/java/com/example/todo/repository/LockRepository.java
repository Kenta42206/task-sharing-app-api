package com.example.todo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.todo.entity.Lock;
import com.example.todo.entity.LockId;


public interface LockRepository extends JpaRepository<Lock, LockId> {
    Optional<Lock> findByTaskId(Long taskId);
    void deleteByTaskId(Long taskId);
    
}
