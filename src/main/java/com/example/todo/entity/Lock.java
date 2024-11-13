package com.example.todo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "locks")
@IdClass(LockId.class) // 複合プライマリキーを指定
public class Lock {

    @Id
    @Column(name = "task_id", nullable = false)
    private Long taskId;

    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "lock_time", nullable = false)
    private LocalDateTime lockTime;

    public Lock(){};

    public Lock(Long taskId, Long userId) {
        this.taskId = taskId;
        this.userId = userId;
        this.lockTime = LocalDateTime.now();
    }
}
