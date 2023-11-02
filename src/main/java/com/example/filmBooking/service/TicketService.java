package com.example.filmBooking.service;

import com.example.filmBooking.model.Seat;
import com.example.filmBooking.model.Ticket;

import java.util.List;


public interface TicketService {
    List<Ticket> fillAll();

    Ticket autoSave(String idSchedule);

    Ticket update(String id, Ticket ticket);

    void delete(String id);

    Ticket findById(String id);

    List<Ticket> getTicket(String cinemaId, String movieId, String startAt, String startTime);
}
