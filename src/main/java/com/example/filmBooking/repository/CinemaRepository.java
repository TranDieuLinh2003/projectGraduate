package com.example.filmBooking.repository;

import com.example.filmBooking.model.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CinemaRepository extends JpaRepository<Cinema, String> {
//        @Query("SELECT c FROM Room r JOIN r.cinema c where c.id in " +
//            "(SELECT s.room.id FROM Schedule s JOIN s.movie m WHERE s.movie.id = :movieId  )")
//    List<Cinema> getCinemaThatShowTheMovie(@Param("movieId") UUID movieId);
    @Query("SELECT c FROM Cinema c  where c.id in " +
            "(SELECT s.room.cinema.id FROM Schedule s JOIN s.movie m JOIN s.room r WHERE s.movie.id = :movieId  )")
    List<Cinema> getCinemaThatShowTheMovie(@Param("movieId") String movieId);
}
