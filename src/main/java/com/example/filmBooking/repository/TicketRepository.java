package com.example.filmBooking.repository;

import com.example.filmBooking.model.Seat;
import com.example.filmBooking.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, String> {
    String str_findBySchedule =
            "select * from ticket where  (schedule_id = (:scheduleId) )";

    @Query(nativeQuery = true, value = str_findBySchedule)
    List<Ticket> findBySchedule(@Param("scheduleId") String scheduleId);
    List<Ticket> findTicketByScheduleId(String scheduleId);
    List<Ticket> findTicketByScheduleIdAndSeatId(String scheduleId, String seatId);


    String ticket = ("SELECT DISTINCT  t.id, t.code, t.schedule_id, t.seat_id, t.status \n" +
            "            FROM projectLinh.cinema c\n" +
            "            JOIN projectLinh.room r ON c.id = r.cinema_id\n" +
            "            JOIN projectLinh.schedule s ON r.id = s.room_id\n" +
            "            JOIN projectLinh.movie m ON s.movie_id = m.id\n" +
            "            join projectLinh.ticket t on t.schedule_id = s.id\n" +
            "            join projectLinh.seat se on se.id= t.seat_id\n" +
            "            WHERE c.id = :cinemaId AND m.id = :movieId\n" +
            "            AND DATE(s.start_at ) = :startAt \n" +
            "            AND DATE_FORMAT(s.start_at, '%H:%i') = :startTime  and t.status like 'đã hết hạn'");
    @Query(value = ticket, nativeQuery = true)
    List<Ticket> findTicketsBySchedule_Id(@Param("cinemaId") String cinemaId,
                       @Param("movieId") String movieId,
                       @Param("startAt") String startAt,
                       @Param("startTime") String startTime);

    String ticket1 = ("SELECT DISTINCT  t.id, t.code, t.schedule_id, t.seat_id, t.status \n" +
            "            FROM projectLinh.cinema c\n" +
            "            JOIN projectLinh.room r ON c.id = r.cinema_id\n" +
            "            JOIN projectLinh.schedule s ON r.id = s.room_id\n" +
            "            JOIN projectLinh.movie m ON s.movie_id = m.id\n" +
            "            join projectLinh.ticket t on t.schedule_id = s.id\n" +
            "            join projectLinh.seat se on se.id= t.seat_id\n" +
            "             WHERE c.name = :cinemaName AND m.name = :movieName\n" +
            "            AND s.start_at  = :startAt \n" +
            "            AND t.status like 'đã hết hạn'");
    @Query(value = ticket1, nativeQuery = true)
    List<Ticket> findTicketsBySchedule_Id1(@Param("cinemaName") String cinemaName,
                                          @Param("movieName") String movieName,
                                          @Param("startAt") String startAt);

}
