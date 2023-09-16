package com.example.filmBooking.controller;

import com.example.filmBooking.model.Schedule;
import com.example.filmBooking.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/filmbooking")
public class LichChieuController {
    @Autowired
    private ScheduleService scheduleService;
    @GetMapping("/schudule")
    public String showLichChieu(Model model) {
        List<Schedule> scheduleList = scheduleService.findAll(); // Lấy danh sách lịch chiếu từ Service hoặc Repository
        model.addAttribute("scheduleList", scheduleList);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM - EEEE", new Locale("vi"));
        List<String> scheduleDates = scheduleList.stream()
                .map(schedule -> schedule.getStartAt().format(formatter))
                .distinct()
                .collect(Collectors.toList());
        model.addAttribute("scheduleDates", scheduleDates);
        return "users/LichChieu";
    }
}
