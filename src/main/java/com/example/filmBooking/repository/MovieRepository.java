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

    String showPhimDangChieu = "select * from projectLinh.movie where status like 'Đang chiếu'; ";

    @Query(value = showPhimDangChieu, nativeQuery = true)
    List<Movie> showPhimDangChieu();


    String showPhimSapChieu = "select * from projectLinh.movie where status like 'Sắp chiếu'; ";

    @Query(value = showPhimSapChieu, nativeQuery = true)
    List<Movie> showPhimSapChieu();

    String showPhimSapChieuAndDangChieu = "select * from projectLinh.movie where status IN ('Sắp chiếu', 'Đang chiếu');";

    @Query(value = showPhimSapChieuAndDangChieu, nativeQuery = true)
    List<Movie> showPhimSapChieuAndDangChieu();

    String movie = ("SELECT DISTINCT  m.*\n" +
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

    @Query(value = "SELECT m FROM Movie m WHERE (:status IS NULL OR m.status IS NULL OR m.status like %:status%) " +
            "AND (:name IS NULL OR m.name IS NULL OR m.name LIKE %:name%)")
    Page<Movie> findAllByStatusAndName(@Param("status") String status, @Param("name") String keyword, Pageable pageable);

    @Query(value = "SELECT DISTINCT m FROM Movie m " +
            "LEFT JOIN m.directors d " +
            "LEFT JOIN m.languages lang " +
            "LEFT JOIN m.movieTypes type " +
            "LEFT JOIN m.performers performer " +
            "WHERE (:status IS NULL OR m.status IS NULL OR m.status LIKE %:status%) " +
            "AND (:keyword IS NULL OR m.name LIKE %:keyword% " +
            "OR d.name LIKE %:keyword% " +
            "OR lang.name LIKE %:keyword% " +
            "OR type.name LIKE %:keyword% " +
            "OR performer.name LIKE %:keyword%)")
    Page<Movie> findAllByStatusAndNameAndKeyWord(@Param("status") String status, @Param("keyword") String keyword, Pageable pageable);

    @Query(value = "SELECT DISTINCT m\n" +
            "FROM Movie m\n" +
            "LEFT JOIN m.directors d " +
            "LEFT JOIN m.languages lang " +
            "LEFT JOIN m.movieTypes type " +
            "LEFT JOIN m.performers performer " +
            "WHERE (?1 IS NULL OR d.id IN ?1) \n" +
            "AND (?2 IS NULL OR lang.id IN ?2) \n" +
            "AND (?3 IS NULL OR type.id IN ?3) \n" +
            "AND (?4 IS NULL OR performer.id IN ?4)")
    Page<Movie> filterMovies(Pageable pageable,
                             @Param("directors") String directors,
                             @Param("languages") String languages,
                             @Param("movieTypes") String movieTypes,
                             @Param("performers") String performers);


}
