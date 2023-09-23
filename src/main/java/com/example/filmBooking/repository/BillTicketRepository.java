package com.example.filmBooking.repository;

import com.example.filmBooking.model.BillTicket;
import org.springframework.data.jpa.repository.JpaRepository;



public interface BillTicketRepository extends JpaRepository<BillTicket, String> {
}
