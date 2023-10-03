package com.example.filmBooking.repository;

import com.example.filmBooking.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;


public interface MovieRepository extends JpaRepository<Movie, String> {

    String showPhimDangChieu = "select * from projectLinh.movie where status like 'Đang chiếu'; " ;
    @Query(value = showPhimDangChieu, nativeQuery = true)
    List<Movie> showPhimDangChieu();


    String showPhimSapChieu = "select * from projectLinh.movie where status like 'Sắp chiếu'; " ;
    @Query(value = showPhimSapChieu, nativeQuery = true)
    List<Movie> showPhimSapChieu();

    String movie = ("select distinct m.name, m.code, m.description,m.director,m.end_date,m.id,m.image,m.language,m.movie_duration,m.movie_type,m.performers,m.premiere_date,m.status,m.trailer\n" +
            "from projectLinh.cinema c\n" +
            "join projectLinh.room r on r.cinema_id = c.id\n" +
            "join projectLinh.schedule s on s.room_id = r.id\n" +
            "join projectLinh.movie m on m.id = s.movie_id\n" +
            "where c.id=:cinemaId and m.id=:movieId");
    @Query(value = movie, nativeQuery = true)
    List<Movie> getMovie(@Param("movieId") String movieId, @Param("cinemaId") String cinemaId);
}
