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
 String Start_at = ("SELECT DISTINCT CONCAT(dayofweek(s.start_at), ' - ', DATE_FORMAT(s.start_at,  '%d/%m'))\n" +
            "FROM projectLinh.cinema c\n" +
            "JOIN projectLinh.room r ON c.id = r.cinema_id\n" +
            "JOIN projectLinh.schedule s ON r.id = s.room_id\n" +
            "JOIN projectLinh.movie m ON s.movie_id = m.id\n" +
            "where c.id=:cinemaId and m.id=:movieId");
    @Query(value = Start_at, nativeQuery = true)
    List<String> getstartAtAndFinishAt(@Param("movieId") String movieId
            , @Param("cinemaId") String cinemaId);

//   lấy ra thời gian
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
