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
