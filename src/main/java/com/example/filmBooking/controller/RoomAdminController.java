package com.example.filmBooking.controller;

import com.example.filmBooking.model.*;
import com.example.filmBooking.model.dto.DtoSeat;
import com.example.filmBooking.service.CinemaService;
import com.example.filmBooking.service.RoomService;
import com.example.filmBooking.service.impl.RoomServiceImpl;
import com.example.filmBooking.service.impl.SeatServiceImpl;
import com.example.filmBooking.service.impl.SeatTypeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

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

    @Autowired
    private SeatTypeServiceImpl seatTypeService;


    @PostMapping("/save")
    @Operation(summary = "[Thêm dữ liệu room]")
    public String addRoom(Model model, @RequestParam(name = "cinema") Cinema cinema,
                          @RequestParam(name = "capacity") int capacity,
                          @RequestParam(name = "acreage") int acreage,
                          @RequestParam(name = "projector") String projector,
                          @RequestParam(name = "other_equipment") String other_equipment,
                          @RequestParam(name = "status") int status,
                          @RequestParam(name = "description") String description,
                          @RequestParam(name = "id") String id,
                          RedirectAttributes ra) {

        try {
            Room room = Room.builder()
                    .id(id)
                    .cinema(cinema)
                    .capacity(capacity)
                    .acreage(acreage)
                    .projector(projector)
                    .other_equipment(other_equipment)
                    .status(status)
                    .description(description)
                    .build();
            if (roomService.save(room) instanceof Room) {
                ra.addFlashAttribute("successMessage", "Thêm thành công!!!");
            } else {
                ra.addFlashAttribute("errorMessage", "Thêm thất bại");
            }
            model.addAttribute("cinema", new Cinema());
            return "redirect:/cinema/find-all";
        } catch (Exception e) {
            e.printStackTrace();
            return "admin/room";
        }

    }

    @GetMapping("/find-all")
    public String viewRoom(Model model) {
        List<Room> roomList = roomService.fillAll();
        model.addAttribute("roomList", roomList);

        List<SeatType> seatTypeList = seatTypeService.findAll();
        Collections.sort(seatTypeList, Comparator.comparing(SeatType::getSurcharge));
        List<Cinema> cinemaList = cinemaService.fillAll();
        model.addAttribute("seatTypeList", seatTypeList);
        model.addAttribute("cinemaList", cinemaList);
        model.addAttribute("room", new Room());

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
