package com.example.todo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.todo.entity.Room;
import com.example.todo.entity.UserRoom;
import com.example.todo.entity.UserRoomId;

public interface UserRoomRepository extends  JpaRepository<UserRoom, UserRoomId> {
    @Query("SELECT ur.room FROM UserRoom ur WHERE ur.user.id = :userId")
    List<Room> findRoomsByUserId(@Param("userId") Long userId);
    boolean existsByUserIdAndRoomId(Long userId, Long roomId);
}
