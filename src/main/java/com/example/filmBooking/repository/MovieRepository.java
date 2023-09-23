package com.example.filmBooking.repository;

import com.example.filmBooking.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;


public interface MovieRepository extends JpaRepository<Movie, String> {

    String showPhimDangChieu = "select * from projectLinh.movie where status like 'Đang chiếu'; " ;
    @Query(value = showPhimDangChieu, nativeQuery = true)
    List<Movie> showPhimDangChieu();


    String showPhimSapChieu = "select * from projectLinh.movie where status like 'Sắp chiếu'; " ;
    @Query(value = showPhimSapChieu, nativeQuery = true)
    List<Movie> showPhimSapChieu();
}
