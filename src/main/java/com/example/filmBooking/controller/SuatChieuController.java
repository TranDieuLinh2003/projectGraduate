package com.example.filmBooking.controller;

import com.example.filmBooking.model.Schedule;
import com.example.filmBooking.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.*;

@Controller
public class SuatChieuController {
    @Autowired
    private ScheduleRepository repository;

    @GetMapping("/suatchieu")
    public String getAllSchedule(Model model){
        List<Schedule> allSuatChieu = repository.findAll(); // Lấy tất cả suất chiếu từ cơ sở dữ liệu
        Map<String, Map<String, List<LocalDateTime>>> suatChieuMap = new HashMap<>();
        for (Schedule suatChieu : allSuatChieu) {
            String tenPhim = suatChieu.getMovie().getName();
            String phongChieu = suatChieu.getRoom().getName();
            LocalDateTime gioChieu = suatChieu.getStartAt();

            if (!suatChieuMap.containsKey(tenPhim)) {
                suatChieuMap.put(tenPhim, new HashMap<>());
            }

            Map<String, List<LocalDateTime>> phongChieuMap = suatChieuMap.get(tenPhim);

            if (!phongChieuMap.containsKey(phongChieu)) {
                phongChieuMap.put(phongChieu, new ArrayList<>());
            }

            List<LocalDateTime> gioChieuList = phongChieuMap.get(phongChieu);
            gioChieuList.add(gioChieu);
        }
        for (Map<String, List<LocalDateTime>> phongChieuMap : suatChieuMap.values()) {
            for (List<LocalDateTime> gioChieuList : phongChieuMap.values()) {
                Collections.sort(gioChieuList);
            }
        }
        model.addAttribute("suatChieuMap", suatChieuMap);
        return "users/hienthi";
    }
}
