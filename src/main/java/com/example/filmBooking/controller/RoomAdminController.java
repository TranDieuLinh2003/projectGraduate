package com.example.filmBooking.controller;

import com.example.filmBooking.model.Cinema;
import com.example.filmBooking.model.Promotion;
import com.example.filmBooking.model.Room;
import com.example.filmBooking.model.Seat;
import com.example.filmBooking.model.dto.DtoSeat;
import com.example.filmBooking.service.CinemaService;
import com.example.filmBooking.service.RoomService;
import com.example.filmBooking.service.impl.RoomServiceImpl;
import com.example.filmBooking.service.impl.SeatServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/room")
@SessionAttributes("soldTicketsCount")

public class RoomAdminController {
    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomServiceImpl roomServiceI;

    @Autowired
    private SeatServiceImpl seatService;

    @Autowired
    private CinemaService cinemaService;

    @PostMapping("/save")
    @Operation(summary = "[Thêm dữ liệu room]")
    public String addRoom(Model model, @RequestParam(name = "cinema") Cinema idCinema, @RequestParam(name = "quantity") int quantity,
                          @RequestParam(name = "description1") String description, RedirectAttributes ra) {
        try {
            boolean saveRoom = roomService.saveAll(idCinema, quantity, description);
            if (saveRoom == true) {
                ra.addFlashAttribute("successMessage", "Thêm thành công");
            } else {
                ra.addFlashAttribute("errorMessage", "Thêm thất bại");
            }
            model.addAttribute("room", new Room());
            return "redirect:/room/find-all";
        } catch (Exception e) {
            e.printStackTrace();
            return "admin/room";
        }

    }

    @GetMapping("/find-all")
    public String viewRoom(Model model) {
        return findAll(model, 1, null);
    }

    @GetMapping("/find-all/page/{pageNumber}")
    public String findAll(Model model, @PathVariable("pageNumber") Integer currentPage, @Param("keyword") String keyword) {
        Page<Room> page = roomService.getAll(currentPage);
        if (keyword != null) {
            page = roomService.serachRoom(keyword, currentPage);
        }
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listRoom", page.getContent());
        model.addAttribute("cinemaId", cinemaService.fillAll());
        model.addAttribute("room", new Room()); // bắt buộc. không có là lỗi
        return "admin/room";
    }

    @GetMapping("/delete/{id}")
    @Operation(summary = "[Xóa dữ liệu room]")
    public String delete(@PathVariable(name = "id") String id, RedirectAttributes ra) {
        try {
            roomService.delete(id);
            ra.addFlashAttribute("successMessage", "Xóa thành công!!!");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Xóa thất bại!!!");
        }
        return "redirect:/room/find-all";
    }

    @GetMapping("/update/{pageNumber}/{id}")
    @Operation(summary = "[Sửa theo id]")
    public String findById(Model model, @PathVariable("id") String id, @PathVariable("pageNumber") Integer currentPage) {
        Page<Room> page = roomService.getAll(currentPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("room", roomService.findById(id));
        model.addAttribute("listRoom", page.getContent());
        List<Cinema> cinemaId = cinemaService.fillAll();
        model.addAttribute("cinemaId", cinemaId);
        return "admin/room";
    }


    @PostMapping("/update/{id}")
    public String updatePromotion(@PathVariable(name = "id") String id, Room updatedRoom, RedirectAttributes ra) {
        roomServiceI.update(id, updatedRoom);
        ra.addFlashAttribute("successMessage", "Sửa thành công!!!");

        return "redirect:/room/find-all";   // Redirect to the promotion list page after update
    }

    @GetMapping("/search-room/{pageNumber}")
    public String searchRoom(@RequestParam(name = "keyword") String keyword, @PathVariable("pageNumber") Integer currentPage, Model model) {
        Page<Room> page = roomService.serachRoom(keyword, currentPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listRoom", page.getContent());
        List<Cinema> cinemaId = cinemaService.fillAll();
        model.addAttribute("cinemaId", cinemaId);
        model.addAttribute("room", new Room());
        return "admin/room";
    }


    @GetMapping("/search/seat")
    public String SearchSeat(Model model, @RequestParam(value = "roomName", required = false) String roomName) {
        List<Room> getAll = roomService.fillAll();
        model.addAttribute("getAll", getAll);
//        List<Seat> listSeat = seatService.getAll();
        List<Seat> seatList = seatService.listSeat(roomName);
        Map<Character, List<Seat>> groupedSeats = new HashMap<>();
        for (Seat seat : seatList) {
            char initialLetter = seat.getCode().charAt(0);
            if (groupedSeats.containsKey(initialLetter)) {
                groupedSeats.get(initialLetter).add(seat);
            } else {
                List<Seat> seats = new ArrayList<>();
                seats.add(seat);
                groupedSeats.put(initialLetter, seats);
            }
        }
        model.addAttribute("groupedSeats", groupedSeats);
        model.addAttribute("seatList", seatList);
//        System.out.println("Tôi là :" + seatList);

        return "admin/seat-manager";
    }
}
