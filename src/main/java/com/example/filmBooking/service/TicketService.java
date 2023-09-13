package com.example.filmBooking.service;

import com.example.filmBooking.model.Ticket;

import java.util.List;
import java.util.UUID;

public interface TicketService {
    List<Ticket> fillAll();

    Ticket autoSave(UUID idSchedule);

    Ticket update(UUID id, Ticket ticket);

    void delete(UUID id);

    Ticket findById(UUID id);

}
