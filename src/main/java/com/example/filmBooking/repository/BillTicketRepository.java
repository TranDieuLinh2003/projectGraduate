package com.example.filmBooking.repository;

import com.example.filmBooking.model.BillTicket;
import com.example.filmBooking.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BillTicketRepository extends JpaRepository<BillTicket, String> {


}
