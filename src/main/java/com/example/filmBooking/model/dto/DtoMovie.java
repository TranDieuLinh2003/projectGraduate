package com.example.filmBooking.model.dto;

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


}
