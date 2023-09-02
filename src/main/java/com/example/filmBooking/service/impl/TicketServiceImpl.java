package com.example.filmBooking.service.impl;

import com.example.filmBooking.model.Ticket;
import com.example.filmBooking.repository.TicketRepository;
import com.example.filmBooking.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository repository;

    @Override
    public List<Ticket> fillAll() {
        return repository.findAll();
    }

    @Override
    public Ticket save(Ticket ticket) {
        Random generator = new Random();
        int value = generator.nextInt((100000 - 1) + 1) + 1;
        ticket.setCode("TK" + value);
        return repository.save(ticket);
    }

    @Override
    public Ticket update(UUID id, Ticket ticket) {
        Ticket ticketNew = findById(id);
        ticketNew.setSchedule(ticket.getSchedule());
        ticketNew.setSeat(ticket.getSeat());
        return repository.save(ticketNew);
    }

    @Override
    public Ticket findById(UUID id) {
        return repository.findById(id).get();
    }

    @Override
    public void delete(UUID id) {
        repository.delete(findById(id));
    }
}
