package com.example.filmBooking.controller;

import com.example.filmBooking.model.Schedule;
import com.example.filmBooking.model.Ticket;
import com.example.filmBooking.service.ScheduleService;
import com.example.filmBooking.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/ticket")
public class TicketAdminController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/find-all")
    public String viewTicket(Model model) {
        return findAll(model, null, 1, null);
    }

    @GetMapping("/find-all/page/{pageNumber}")
    public String findAll(Model model, @Param("id") String id, @PathVariable("pageNumber") Integer currentPage,
                          @Param("status") String status) {
        Page<Ticket> page = ticketService.findByScheduleId(id, currentPage);
        if(status != null){
            page = ticketService.findAllByStatus(status, currentPage);
        }
        model.addAttribute("status", status);
        model.addAttribute("idSchedule", id);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listTicket", page.getContent());
        List<Schedule> scheduleId = scheduleService.findAll();
        model.addAttribute("scheduleId", scheduleId);
        return "admin/ticket";
    }

}
