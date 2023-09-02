package com.example.filmBooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
public class FilmBookingApplication {

    public static void main(String[] args) {
        SpringApplication.run(FilmBookingApplication.class, args);
    }

}
