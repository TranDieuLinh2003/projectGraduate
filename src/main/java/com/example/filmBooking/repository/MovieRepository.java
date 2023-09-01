package com.example.filmBooking.repository;

import com.example.filmBooking.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.UUID;

public interface MovieRepository extends JpaRepository<Movie, UUID> {
    String view = "select * from projectLinh.movie where status like 'Đang chiếu'" ;
    @Query(value = view, nativeQuery = true)
    Collection<Movie> findAllBy();

    String view2 = "select * from projectLinh.movie where status like 'Sắp chiếu'" ;
    @Query(value = view2, nativeQuery = true)
    Collection<Movie> findAllBy1();
}
