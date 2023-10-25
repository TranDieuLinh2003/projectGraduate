package com.example.filmBooking.service;

import com.example.filmBooking.model.Cinema;
import com.example.filmBooking.model.Room;

import java.util.List;


public interface RoomService {
    List<Room> fillAll();

    void saveAll(Cinema idCinema, int quantity);

    Room save(Room room);

    Room update(String id, Room room);

    void delete(String id);

    Room findById(String id);
}
