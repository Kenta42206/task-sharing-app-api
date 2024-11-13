package com.example.todo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.todo.entity.Room;
import com.example.todo.entity.User;
import com.example.todo.entity.UserRoom;
import com.example.todo.repository.UserRoomRepository;

@Service
public class UserRoomService {
    @Autowired
    private UserRoomRepository userRoomRepository;

    public UserRoom addUserToroom(User user, Room room){
        UserRoom userRoom = new UserRoom();
        userRoom.setUser(user);
        userRoom.setRoom(room);
        userRoom.setRoomId(room.getId());
        userRoom.setUserId(user.getId());
        return userRoomRepository.save(userRoom);
    }

    public List<Room> getRoomsByUserId(Long userId){
        List<Room> rooms = userRoomRepository.findRoomsByUserId(userId);
        return rooms;
    }
    public boolean isUserInRoom(Long userId, Long roomId){
        return userRoomRepository.existsByUserIdAndRoomId(userId, roomId);
    }
}
