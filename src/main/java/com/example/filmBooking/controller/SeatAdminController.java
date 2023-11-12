package com.example.filmBooking.controller;

import com.example.filmBooking.common.ResponseBean;
import com.example.filmBooking.model.Room;
import com.example.filmBooking.model.Seat;
import com.example.filmBooking.model.Ticket;
import com.example.filmBooking.service.RoomService;
import com.example.filmBooking.service.SeatService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/seat")
public class SeatAdminController {

    @Autowired
    private SeatService seatService;

    @Autowired
    private RoomService roomService;

    @GetMapping("/find-all")
    public String viewSeat(Model model) {
        return findAll(model, 1, null);
    }

    @GetMapping("/find-all/page/{pageNumber}")
    public String findAll(Model model, @PathVariable("pageNumber") Integer currentPage, @Param("id") String id) {
        Page<Seat> page = seatService.findAll(currentPage);

        if (id != null) {
            page = seatService.searchByRoom(id, currentPage);
        }
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listSeat", page.getContent());
        List<Room> roomId = roomService.finByRoom();
        List<Room> getAll = roomService.fillAll();
        model.addAttribute("listRoomId", getAll);
        model.addAttribute("roomId", roomId);
        model.addAttribute("idByRoom", id);
        model.addAttribute("room", new Room());
        model.addAttribute("seat", new Seat());

        return "admin/seat";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") String id) {
        seatService.delete(id);
        return "redirect:/seat/find-all";
    }

    @PostMapping("/save")
    @Operation(summary = "[Thêm mới]")
    public String save( Model model,@RequestParam("lineNumber") Integer lineNumber, @RequestParam("number") Integer number, @RequestParam("room") Room room) {
        try {
            if ( seatService.save(lineNumber, number, room) instanceof Seat){
                model.addAttribute("thanhCong", "Thêm ghế thành công");
            }else {
                model.addAttribute("thatBai", "Thêm ghế thất bại");
            }
            model.addAttribute("seat", new Seat());
            return "redirect:/seat/find-all";
        }catch (Exception e){
            e.printStackTrace();
            return "admin/seat";
        }
    }

    @GetMapping("/seat-manager")
    public String viewSeatCustomer() {
        return "admin/seat-manager";
    }
}
