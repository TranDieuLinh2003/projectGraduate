package com.example.filmBooking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/statistical")
@SessionAttributes("soldTicketsCount")
public class StatisticalAdminController {
    @GetMapping("/find-all")
    public String viewThongKe(){
        return "admin/thong-ke";
    }
}
