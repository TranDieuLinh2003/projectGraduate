package com.example.filmBooking.repository;

import com.example.filmBooking.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ScheduleRepository extends JpaRepository<Schedule, String> {
    String Start_at = ("SELECT DISTINCT  DATE(s.start_at)\n" +
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
            " and c.id =:cinemaId and  DATE(s.start_at)=:start_at");

    @Query(value = time, nativeQuery = true)
    List<String> getTime(@Param("movieId") String movieId,
                         @Param("cinemaId") String cinemaId,
                         @Param("start_at") String start_at);

    //   lấy ra schedule
    String schedule = ("SELECT DISTINCT   s.id, s.code, s.name, s.price, s.start_at, s.finish_at, s.status, s.room_id, s.movie_id\n" +
            "            FROM projectLinh.cinema c\n" +
            "            JOIN projectLinh.room r ON c.id = r.cinema_id\n" +
            "            JOIN projectLinh.schedule s ON r.id = s.room_id\n" +
            "            JOIN projectLinh.movie m ON s.movie_id = m.id\n" +
            "            join projectLinh.ticket t on t.schedule_id = s.id\n" +
            "            join projectLinh.seat se on se.id= t.seat_id\n" +
            "            WHERE c.id = :cinemaId AND m.id = :movieId\n" +
            "            AND DATE(s.start_at ) = :startAt \n" +
            "            AND DATE_FORMAT(s.start_at, '%H:%i') = :startTime");
    @Query(value = schedule, nativeQuery = true)
    List<Schedule> getSchedule(@Param("cinemaId") String cinemaId,
                               @Param("movieId") String movieId,
                               @Param("startAt") String startAt,
                               @Param("startTime") String startTime);



    @Query(value = "select*from schedule ORDER BY start_at ASC", nativeQuery = true)
    List<Schedule> findAll();

    @Query(value = "select *from schedule where room_id= ?1", nativeQuery = true)
    List<Schedule> findByRoom(String id);

    List<Schedule> findByNameContains(String name);

}
