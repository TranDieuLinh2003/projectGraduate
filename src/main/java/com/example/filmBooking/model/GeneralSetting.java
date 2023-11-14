package com.example.filmBooking.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Timer;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "general_setting")
public class GeneralSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "fixed_ticket_price")
    private BigDecimal fixedTicketPrice;

    // Giờ bắt đầu thay đổi
    @Column(name = "time_begins_to_change")
//    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING, timezone = "Asia/Bangkok")
    private LocalTime timeBeginsToChange;

    //Giờ mở cửa
    @Column(name = "business_hours")
    private LocalTime businessHours;

    //Giờ đóng cửa
    @Column(name = "close_time")
    private LocalTime closeTime;

    // phần trăm
    @Column(name = "percent_day")
    private Integer percentDay;

    //ngày thay đổi
//    @Column(name = "weekend")
//    private Integer weekend;

    //phần trăm thay đổi ngày
    @Column(name = "percent_weekend")
    private Integer percentWeekend;

    //thời gian giữa các suất chiếu
    @Column(name = "break_time")
    private Integer breakTime;

    @Column(name = "waiting_time")
    private Integer waitingTime;
    
    //thời gian chờ xác nhận
    @Column(name = "confirmation_waiting_time")
    private Integer confirmationWaitingTime;
}
