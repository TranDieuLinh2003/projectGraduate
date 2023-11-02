package com.example.filmBooking.service;

import com.example.filmBooking.model.Seat;
import com.example.filmBooking.model.dto.DtoSeat;
import com.example.filmBooking.model.dto.SeatDTO;

import java.util.List;


public interface SeatService {
    List<Seat> getAll();

    List<Seat> findAllByRoom(String roomId);

    Seat save(Integer lineNumber, Integer number, String idRoom);

    Seat update(String id, Seat seat);

    void delete(String id);

    Seat findById(String id);

    List<SeatDTO> getSeatsByScheduleId(String scheduleId);

    List<Object[]> getSeatsByCustomerId(String customerId);

    List<DtoSeat> getSeats(String cinemaId,String movieId,String startAt, String startTime);

    List<DtoSeat> getSeats1(String cinemaName,String movieName,String startAt);

}
