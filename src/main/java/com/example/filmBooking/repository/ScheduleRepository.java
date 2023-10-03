package com.example.filmBooking.repository;

import com.example.filmBooking.model.Movie;
import com.example.filmBooking.model.Schedule;
import com.example.filmBooking.model.dto.ScheduleDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Time;
import java.time.LocalDateTime;
import java.sql.Date;
import java.util.List;


public interface ScheduleRepository extends JpaRepository<Schedule, String> {
//    lấy ra ngày tháng và thứ
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




    //linh làm
    @Query(value = "SELECT TIMEDIFF(?1, ?2)", nativeQuery = true)
    List<Time> layKhoangTrong(Time start, Time end);

    @Query(value = "select*from schedule ORDER BY start_at ASC", nativeQuery = true)
    List<Schedule> findAll();

    @Query(value = "select *from schedule where room_id= ?1", nativeQuery = true)
    List<Schedule> findByRoom(String id);

    String str_CheckConstraint_count = "select count(1) from `schedule` s where s.room_id= ?1" +
            "AND (?2 is null or start_at >= ?3 ) " +
            "AND  (?4 is null or start_at <= ?5 ) " +
            "AND (?6 is null or finish_at >= ?7 ) " +
            "AND  (?8 is null or finish_at <= ?9)  ";


    @Query(value = str_CheckConstraint_count, nativeQuery = true)
    long CheckConstraint(String id,
                         LocalDateTime from_startAt,
                         LocalDateTime startAt_from,
                         LocalDateTime to_startAt,
                         LocalDateTime startAt_to,
                         LocalDateTime from_finishAt,
                         LocalDateTime finishAt_from,
                         LocalDateTime to_finishAt,
                         LocalDateTime finishAt_to);


}
