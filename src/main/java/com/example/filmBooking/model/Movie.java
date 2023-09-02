package com.example.filmBooking.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "movie_duration")
    private Integer movieDuration;

    @Column(name = "trailer")
    private String trailer;

    @Column(name = "premiere_date")
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING, timezone = "Asia/Bangkok")
    private Date premiereDate;

    @Column(name = "end_date")
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING, timezone = "Asia/Bangkok")
    private Date endDate;

    @Column(name = "status")
    private String status;

    @Column(name = "director")
    private String director;

    @Column(name = "performers")
    private String performers;

    @Column(name = "language")
    private String languages;

    @Column(name = "image")
    private String image;

    @Column(name = "movie_type")
    private String movieType;

    @Column(name = "movie_type")
    private String movieType;

    @Column(name = "description", length = 1000)
    private String description;


}
