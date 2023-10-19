package com.example.filmBooking.model.dto;

import com.example.filmBooking.model.Room;
import lombok.Data;

@Data
public class DtoSeat {
    private String id;
    private Room room;
    private String code;
    private String line;
    private String description;
    private Integer status;
    private Integer number;
    private int isOccupied;
}
