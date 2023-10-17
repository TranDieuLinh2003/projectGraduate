package com.example.filmBooking.model.dto;

import com.example.filmBooking.model.Rated;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;


@Data

public class DtoMovie {
    private String id;
    private String name;
    private Integer movieDuration;
    private String trailer;
    private Date premiereDate;
    private String image;
    private String movieType;
    private String director;
    private String performers;
    private String languages;
    private Rated rated;

}
