package com.example.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.dto.JoinRoomRequest;
import com.example.todo.entity.Room;
import com.example.todo.entity.User;
import com.example.todo.exception.AlreadyMemberException;
import com.example.todo.exception.ResourceNotFoundException;
import com.example.todo.service.RoomService;
import com.example.todo.service.UserRoomService;
import com.example.todo.service.UserService;




@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    @Autowired
    private UserRoomService userRoomService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    
    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms(){
    	List<Room> rooms = roomService.getAllRooms();
    	return ResponseEntity.ok(rooms);
    }

    /**
     * @param userId
     * @return 
     */
    @GetMapping("/{userId}")
    public ResponseEntity<?> getRoomsByUserId(@PathVariable Long userId) {
        if(!userService.userExists(userId)){
        	throw new ResourceNotFoundException("User not found with userId" + userId);
        }
        List<Room> rooms = userRoomService.getRoomsByUserId(userId);
        return ResponseEntity.ok(rooms);
    }

    /**
     * @param room
     * @return
     */
    @PostMapping
    public Room createRoom(@RequestBody Room room) {
        return roomService.createRoom(room);
    }
    

    /**
     * @param roomId
     * @param room
     * @return
     */
    @PutMapping("/{roomId}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long roomId, @RequestBody Room room) {
        return ResponseEntity.ok(roomService.updateRoom(roomId, room)); 
    }

    /**
     * @param roomId
     * @return
     */
    @DeleteMapping("/{roomId}")
    public ResponseEntity<?> deletRoom(@PathVariable Long roomId){
        roomService.deleteRoom(roomId);
        return ResponseEntity.noContent().build();
    }

    /**
     * UserがRoomに参加するメソッド
     * userIdとroomIdをパラメータとしてリクエストが送信される
     * 
     * @param joinRoomRequest
     * @param userDetails
     * @return
     */
    @PostMapping("/join")
    public ResponseEntity<?> joinRoom(@RequestBody JoinRoomRequest joinRoomRequest) {
        Long roomId = joinRoomRequest.getRoomId();
        Long userId = joinRoomRequest.getUserId();
        Room room = roomService.getRoomById(roomId);
        User user = userService.getUserById(userId);


        if(!roomService.roomExists(roomId)){
        	throw new ResourceNotFoundException("Room not found with userId" + roomId);
        }

        if(!userService.userExists(userId)){
        	throw new ResourceNotFoundException("User not found with userId" + userId);
        }

        if (userRoomService.isUserInRoom(userId, roomId)) {
            throw new AlreadyMemberException("User is already a member of this room.");
        }
        
        try {
            userRoomService.addUserToroom(user, room);
            return ResponseEntity.ok(room);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to join room: " + e.getMessage());
        }
    }
    
    
    
}
