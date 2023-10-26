package com.example.filmBooking.repository;

import com.example.filmBooking.model.Schedule;
import com.example.filmBooking.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface SeatRepository extends JpaRepository<Seat, String> {

    String findAllByRoomValue =
            "select * from seat where  (room_id = (:roomId) )";
    @Query(nativeQuery = true, value = findAllByRoomValue)
    List<Seat> findAllByRoom(@Param("roomId") String roomId);
    @Query(value = "SELECT s, b.dateCreate FROM Seat s \n" +
            "               JOIN projectLinh.ticket t ON t.seat.id = s.id\n" +
            "               JOIN projectLinh.bill_ticket bt ON bt.ticket.id = t.id \n" +
            "               JOIN projectLinh.bill b ON bt.bill.id = b.id \n" +
            "               JOIN projectLinh.customer c ON b.customer.id = c.id\n" +
            "               WHERE c.id = :customerId", nativeQuery = true)
    List<Object[]> findSeatsByCustomerId(@Param("customerId") String customerId);

    List<Seat> getSeatByRoomId(String roomId);


    String seat = ("SELECT DISTINCT    se.id, se.code, se.room_id, se.status, se.description, se.line, se.number\n" +
            "            FROM projectLinh.cinema c\n" +
            "            JOIN projectLinh.room r ON c.id = r.cinema_id\n" +
            "            JOIN projectLinh.schedule s ON r.id = s.room_id\n" +
            "            JOIN projectLinh.movie m ON s.movie_id = m.id\n" +
            "            join projectLinh.ticket t on t.schedule_id = s.id\n" +
            "            join projectLinh.seat se on se.id= t.seat_id\n" +
            "            WHERE c.id = :cinemaId AND m.id = :movieId\n" +
            "            AND DATE(s.start_at ) = :startAt \n" +
            "            AND DATE_FORMAT(s.start_at, '%H:%i') = :startTime ORDER BY se.code ASC");
    @Query(value = seat, nativeQuery = true)
    List<Seat> getSeat(@Param("cinemaId") String cinemaId,
                               @Param("movieId") String movieId,
                               @Param("startAt") String startAt,
                               @Param("startTime") String startTime);
}
