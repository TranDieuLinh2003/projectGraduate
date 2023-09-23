package com.example.filmBooking.apis;

import com.example.filmBooking.model.Cinema;
import com.example.filmBooking.repository.CinemaRepository;
import com.example.filmBooking.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping(value="/api/cinema")
public class CinemaApi {
    @Autowired
    private CinemaRepository cinemaRepository;


    @GetMapping
    private ResponseEntity<List<Cinema>> getCinemaThatShowTheMovie(@RequestParam String movieId){
        return new ResponseEntity<>(cinemaRepository.getCinemaThatShowTheMovie(movieId), HttpStatus.OK);
    }
}
