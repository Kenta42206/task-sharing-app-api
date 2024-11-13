package com.example.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.entity.Lock;
import com.example.todo.service.LockService;


@RestController
@RequestMapping("/api/locks")
public class LockController {
    @Autowired
    private final LockService lockService;

    public LockController(LockService lockService) {
        this.lockService = lockService;
    }

    @GetMapping
    public List<Lock> getAllLocks() {
        return lockService.getAllLocks();
    }

    @GetMapping("/status/{taskId}")
    public boolean isLocked(@PathVariable Long taskId, @RequestParam Long userId) {
        return lockService.isLocked(taskId,userId);
    }
    
    

    @PostMapping("/lock/{taskId}")
    public ResponseEntity<String> lockTask(@PathVariable(name = "taskId") Long taskId, @RequestParam(name = "userId") Long userId) {
        boolean locked = lockService.lockTask(taskId, userId);
        return locked ? ResponseEntity.ok("Task locked") : ResponseEntity.status(409).body("Task is already locked");
    }

    @DeleteMapping("/unlock/{taskId}")
    public ResponseEntity<String> unlockTask(@PathVariable(name = "taskId") Long taskId){
        lockService.unlockedTask(taskId);
        return ResponseEntity.ok("Task unlocked");
    }


    
}
