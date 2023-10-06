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
    List<String> getstartAtAndFinishAt(@Param("movieId") String movieId
            , @Param("cinemaId") String cinemaId);

    String time = ("\n" +
            "\tselect distinct   DATE_FORMAT(s.start_at , '%H:%i')\n" +
            "\tfrom projectLinh.cinema c\n" +
            "\tjoin projectLinh.room r on r.cinema_id = c.id\n" +
            "\tjoin projectLinh.schedule s on s.room_id = r.id\n" +
            "\tjoin projectLinh.movie m on m.id = s.movie_id\n" +
            "where m.id=:movieId\n" +
            " and c.id =:cinemaId and CONCAT(dayofweek(s.start_at), ' - ', DATE_FORMAT(s.start_at,  '%d/%m'))=:start_at");
    @Query(value = time, nativeQuery = true)
    List<String> getTime(@Param("movieId") String movieId,
                         @Param("cinemaId") String cinemaId,
                         @Param("start_at") String start_at);

    @Query(value = "select*from schedule ORDER BY start_at ASC", nativeQuery = true)
    List<Schedule> findAll();

    @Query(value = "select *from schedule where room_id= ?1", nativeQuery = true)
    List<Schedule> findByRoom(String id);

    List<Schedule> findByNameContains(String name);

}
