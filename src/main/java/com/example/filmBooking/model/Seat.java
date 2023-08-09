package com.example.filmBooking.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "seat")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(name = "code")
    private String code;

    @Column(name = "number")
    private Integer number;

    @ManyToOne
    @JoinColumn(name = "seat_type_id")
    private SeatType seatType;

    @Column(name = "line")
    private String line;

    @Column(name = "status")
    private Integer status;

    @Column(name = "description")
    private String description;


}
