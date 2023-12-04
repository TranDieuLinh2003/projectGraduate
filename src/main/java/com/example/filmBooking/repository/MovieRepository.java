package com.example.filmBooking.repository;

import com.example.filmBooking.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    String showPhimSapChieuAndDangChieu = "select * from projectLinh.movie where status IN ('Sắp chiếu', 'Đang chiếu');" ;
    @Query(value = showPhimSapChieuAndDangChieu, nativeQuery = true)
    List<Movie> showPhimSapChieuAndDangChieu();

    String movie = ("SELECT DISTINCT  m.id, m.code, m.name, m.movie_duration, m.trailer, m.premiere_date, m.end_date,m.status, m.rated_id, m.director, m.language, m.image,m.movie_type, m.description, m.performers\n" +
            "\tfrom projectLinh.cinema c\n" +
            "\tjoin projectLinh.room r on r.cinema_id = c.id\n" +
            "\tjoin projectLinh.schedule s on s.room_id = r.id\n" +
            "\tjoin projectLinh.movie m on m.id = s.movie_id\n" +
            "where m.id=:movieId\n" +
            " and c.id =:cinemaId");
    @Query(value = movie, nativeQuery = true)
    List<Movie> getMovie(@Param("movieId") String movieId, @Param("cinemaId") String cinemaId);
    
    Page<Movie> findByNameContains(String keyword, Pageable pageable);

    Movie findByNameLike(String name);

    @Query(value = "SELECT m FROM Movie m WHERE (:status IS NULL OR m.status IS NULL OR m.status = :status) " +
            "AND (:name IS NULL OR m.name IS NULL OR m.name LIKE %:name%)")
    Page<Movie> findAllByStatusAndName(@Param("status") String status,@Param("name") String keyword, Pageable pageable);
}
