package com.example.filmBooking.service;

import com.example.filmBooking.model.Room;

import java.util.List;
import java.util.UUID;

public interface RoomService {
    List<Room> fillAll();

    Room save(Room room);

    Room update(UUID id, Room room);

    void delete(UUID id);

    Room findById(UUID id);
}
