package com.example.filmBooking.controller;

import com.example.filmBooking.common.ResponseBean;
import com.example.filmBooking.model.Cinema;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/seat")
@SessionAttributes("soldTicketsCount")

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
        Page<Seat> page = seatService.searchByRoom(id, currentPage);
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
    public String delete(@PathVariable(name = "id") String id,RedirectAttributes ra) {

        try {
            seatService.delete(id);
            ra.addFlashAttribute("successMessage", "Xóa thành công!!!");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Xóa thất bại!!!");
        }
        return "redirect:/seat/find-all";
    }

    @PostMapping("/save")
    @Operation(summary = "[Thêm mới]")
    public String save( Model model,@RequestParam("lineNumber") Integer lineNumber, @RequestParam("number") Integer number, @RequestParam("room") Room room, RedirectAttributes ra) {
        try {
            if ( seatService.save(lineNumber, number, room) instanceof Seat){
                ra.addFlashAttribute("successMessage", "Thêm thành công!!!");
            }else {
                ra.addFlashAttribute("errorMessage", "Thêm thất bại!!!");
            }
            model.addAttribute("seat", new Seat());
            return "redirect:/seat/find-all";
        }catch (Exception e){
            e.printStackTrace();
            return "admin/seat";
        }
    }

//    @GetMapping("/update/{pageNumber}/{id}")
//    public String updateSeat(Model model, @PathVariable("id") String id, @PathVariable("pageNumber") Integer currentPage) {
//        model.addAttribute("seat", seatService.findById(id));
//        List<Room> getAll = roomService.fillAll();
//        model.addAttribute("listRoomId", getAll);
//        model.addAttribute("currentPage", currentPage);
//        return "admin/seat";
//    }
//
//    @PostMapping("/update/{pageNumber}")
//    public String update(Model model, @RequestParam("id") String id,
//                         @RequestParam(name = "status") Integer status) {
//        try {
//            Seat seat = Seat.builder()
//                    .id(id)
//                    .status(status)
//                    .build();
//            if (seatService.update(id, seat) instanceof Seat) {
//                model.addAttribute("thanhCong", "Sửa thành công");
//            } else {
//                model.addAttribute("thatBai", "Sửa thất bại");
//            }
//            return "redirect:/seat/find-all";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "admin/seat";
//        }
//    }

    @PostMapping("/update/{id}")
    public String updatePromotion(@PathVariable(name = "id") String id, Seat updatedRoom, RedirectAttributes ra) {
        seatService.update(id, updatedRoom);
        ra.addFlashAttribute("successMessage", "Sửa thành công!!!");
        return "redirect:/seat/find-all";   // Redirect to the promotion list page after update
    }
    @GetMapping("/seat-manager")
    public String viewSeatCustomer() {
        return "admin/seat-manager";
    }
}
