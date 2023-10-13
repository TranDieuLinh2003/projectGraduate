package com.example.filmBooking.repository;

import com.example.filmBooking.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface SeatRepository extends JpaRepository<Seat, String> {

    String str_findByPrimary =
            "select * from seat where  (room_id = (:roomId) )";
    @Query(nativeQuery = true, value = str_findByPrimary)
    List<Seat> findAllByRoom(@Param("roomId") String roomId);
    @Query(value = "SELECT s, b.dateCreate FROM Seat s \n" +
            "               JOIN projectLinh.ticket t ON t.seat.id = s.id\n" +
            "               JOIN projectLinh.bill_ticket bt ON bt.ticket.id = t.id \n" +
            "               JOIN projectLinh.bill b ON bt.bill.id = b.id \n" +
            "               JOIN projectLinh.customer c ON b.customer.id = c.id\n" +
            "               WHERE c.id = :customerId", nativeQuery = true)
    List<Object[]> findSeatsByCustomerId(@Param("customerId") String customerId);

    List<Seat> getSeatByRoomId(String roomId);
}
