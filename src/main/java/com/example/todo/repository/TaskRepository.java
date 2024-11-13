package com.example.todo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.todo.entity.Task;

public interface  TaskRepository extends JpaRepository<Task, Long>{
    List<Task> findByUserId(Long userId);
    List<Task> findByStatus_Id(Long statusId);
    List<Task> findByCategory_Id(Long categoryId);
    List<Task> findByRoomId(Long roomId);
    List<Task> findByPriority(Integer priority); 
}
