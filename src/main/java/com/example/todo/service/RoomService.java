package com.example.todo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.todo.entity.Room;
import com.example.todo.exception.ResourceNotFoundException;
import com.example.todo.repository.RoomRepository;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;
    
    public List<Room> getAllRooms(){
    	return roomRepository.findAll();
    }


    public Room getRoomById(Long roomId){
        return roomRepository.findById(roomId).orElseThrow(()->new ResourceNotFoundException("Room not found with id " + roomId));
    }

    public Room createRoom(Room room){
        return roomRepository.save(room);
    } 

    public Room updateRoom(Long roomId, Room room){
        Room targetRoom = roomRepository.findById(roomId).orElseThrow(()->new ResourceNotFoundException("Room not found with id " + roomId));
        targetRoom.setName(room.getName());
        targetRoom.setDescription(room.getDescription());
        return roomRepository.save(targetRoom);
    }
    
    public void deleteRoom(Long roomId){
        if(roomRepository.existsById(roomId)){
            roomRepository.deleteById(roomId);
        }else{
            throw new ResourceNotFoundException("Room not found with id " + roomId);
        }
    }

    public boolean roomExists(Long roomId){
        return roomRepository.existsById(roomId);
    }
}
