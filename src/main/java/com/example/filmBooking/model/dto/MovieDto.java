package com.example.filmBooking.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.UUID;

@Data
@Getter
@Setter
public class MovieDto {
    private UUID id;
    private String code;
    private String name;
    private Integer movieDuration;
    private String trailer;
    private Date premiereDate;
    private Date endDate;
    private String director;
    private String performers;
    private String languages;
    private String image;
    private String typeIds;
    private String description;
    private String status = status(premiereDate, endDate);

    private String status(Date premiereDate, Date endDate) {
        java.util.Date date = new java.util.Date();
        if (date.after(endDate)) {
            return "Đã chiếu";
        } else if (date.before(premiereDate)) {
            return "Sắp chiếu";
        } else {
            return "Đang chiếu";
        }
    }
}
