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
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/ticket")
@SessionAttributes("soldTicketsCount")
public class TicketAdminController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/find-all")
    public String viewTicket(Model model) {
        return findAll(model, null, null, null, 1, null);
    }

    @GetMapping("/find-all/page/{pageNumber}")
    public String findAll(Model model, @RequestParam(value = "roomId", required = false) String roomId, @RequestParam(value = "movieId", required = false) String movieId, @RequestParam(value = "dateSearch", required = false) Date dateSearch, @PathVariable("pageNumber") Integer currentPage,
                          @RequestParam(value = "status", required = false) String status) {
        Page<Ticket> page = ticketService.findAllByStatus(roomId, movieId, dateSearch, status, currentPage);
//        Page<Ticket> page = ticketService.findByScheduleId(id, currentPage);
        if (status != null) {
            page = ticketService.findAllByStatus(status, currentPage);
        }
        model.addAttribute("status", status);
//        model.addAttribute("idSchedule", id);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listTicket", page.getContent());
        List<Schedule> scheduleId = scheduleService.findAll();
        model.addAttribute("scheduleId", scheduleId);
        return "admin/ticket";
    }

}
