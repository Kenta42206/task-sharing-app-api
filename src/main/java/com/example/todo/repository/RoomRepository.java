package com.example.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.todo.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {

}
