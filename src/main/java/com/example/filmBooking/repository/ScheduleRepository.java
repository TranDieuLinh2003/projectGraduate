package com.example.filmBooking.repository;

import com.example.filmBooking.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


public interface ScheduleRepository extends JpaRepository<Schedule, String> {
//            @Query("SELECT s.startAt, s.finishAt \n" +
//                "FROM Cinema c\n" +
//                "JOIN Room r ON c.id = r.cinema.id\n" +
//                "JOIN Schedule s ON r.id = s.room.id\n" +
//                "JOIN Movie m ON s.movie.id = m.id\n" +
//                "where m.id= ?1 and c.id=?2")
    @Query("SELECT DISTINCT s.startAt FROM Schedule s WHERE s.movie.id=:movieId AND s.room.cinema.id" +
            "= :cinemaId ")
    List<LocalDateTime> getstartAtAndFinishAt(@Param("movieId") String movieId
            , @Param("cinemaId") String cinemaId);

    @Query("SELECT DISTINCT s.finishAt FROM Schedule s WHERE s.movie.id=:movieId AND s.room.cinema.id" +
            "= :cinemaId ")
    List<LocalDateTime> getstartAtAndFinishAt1(@Param("movieId") String movieId
            , @Param("cinemaId") String cinemaId);

//    List<Schedule> getALL(String movieId,String roomId, LocalDateTime startAt,String cinemaId);

}
