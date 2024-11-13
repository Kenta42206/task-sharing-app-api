package com.example.todo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user_rooms")
@IdClass(UserRoomId.class) // 複合プライマリーキーのためのクラスを使用
public class UserRoom {

    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId; // Long型に変更

    @Id
    @Column(name = "room_id", nullable = false)
    private Long roomId;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false) // 上記の変更に合わせて
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id", insertable = false, updatable = false) // 上記の変更に合わせて
    private Room room;

    public UserRoom(){};

}
