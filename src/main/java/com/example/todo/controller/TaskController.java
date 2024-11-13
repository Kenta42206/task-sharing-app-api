package com.example.todo.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.entity.Task;
import com.example.todo.security.CustomUserDetails;
import com.example.todo.service.RoomService;
import com.example.todo.service.TaskService;
import com.example.todo.service.UserRoomService;
import com.example.todo.service.UserService;

import jakarta.persistence.OptimisticLockException;


@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private  TaskService taskService;

    @Autowired
    private  RoomService roomService;

    @Autowired
    private  UserService userService;

    @Autowired
    private  UserRoomService userRoomService;

    @GetMapping
    public ResponseEntity<?> getAllTasksByRoomId(@RequestParam Long roomId,@RequestParam Long userId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long loginUserId = userDetails.getId();
        
        if(!roomService.roomExists(roomId)){
            return ResponseEntity.badRequest().body("Invalid Room Id");
        }

        if(!userService.userExists(userId)){
            return ResponseEntity.badRequest().body("Invalid User Id");
        }

        if(!Objects.equals(loginUserId, userId)){
            return ResponseEntity.status(403).body("Access denied: Request user is not Login user.");
        }
        
        
        if(!userRoomService.isUserInRoom(userId, roomId)){
            return ResponseEntity.status(403).body("Access denied: You are not a member of this room.");
        }

        List<Task> tasks = taskService.getTasksByRoomId(roomId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Optional<Task> task = taskService.getTaskById(id);
        if (task.isPresent()) {
            return ResponseEntity.ok(task.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        try {
            Task updatedTask = taskService.updateTask(id, taskDetails);
            return ResponseEntity.ok(updatedTask);
        } catch (OptimisticLockException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        taskService.deleteTaskById(id);
        return ResponseEntity.noContent().build();
    }
}