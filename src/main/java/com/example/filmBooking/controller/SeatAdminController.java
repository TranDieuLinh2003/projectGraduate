package com.example.filmBooking.controller;

import com.example.filmBooking.common.ResponseBean;
import com.example.filmBooking.model.Cinema;
import com.example.filmBooking.model.Room;
import com.example.filmBooking.model.Seat;
import com.example.filmBooking.model.Ticket;
import com.example.filmBooking.service.RoomService;
import com.example.filmBooking.service.SeatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Seat")
@SessionAttributes("soldTicketsCount")

public class SeatAdminController {

    @Autowired
    private SeatService seatService;

    @Autowired
    private RoomService roomService;

    @GetMapping("/find-all")
    public String viewSeat(Model model) {
        List<Room> getAll = roomService.fillAll();
        model.addAttribute("getAll", getAll);
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

//    @PostMapping("/save")
//    @Operation(summary = "[Thêm mới]")
//    public String save( Model model,@RequestParam("listLineCodes") List<String> listLineCodes,@RequestParam("listSeatTypeId") List<String> listSeatTypeId, @RequestParam("listNumberOfSeatPerLine") List<Integer> listNumberOfSeatPerLine, RedirectAttributes ra) {
//        try {
//            if ( seatService.save(listLineCodes, listSeatTypeId, listNumberOfSeatPerLine) instanceof Seat){
//                ra.addFlashAttribute("successMessage", "Thêm thành công!!!");
//            }else {
//                ra.addFlashAttribute("errorMessage", "Thêm thất bại!!!");
//            }
//            model.addAttribute("seat", new Seat());
//            return "redirect:/seat/find-all";
//        }catch (Exception e){
//            e.printStackTrace();
//            return "admin/seat";
//        }
//    }

    @PostMapping("/save")
    @Operation(summary = "[Thêm mới]")
    public ResponseEntity<?> save( Model model,@RequestParam("listLineCodes") List<String> listLineCodes,@RequestParam("listSeatTypeId") List<String> listSeatTypeId, @RequestParam("listNumberOfSeatPerLine") List<Integer> listNumberOfSeatPerLine,@RequestParam("roomId") String roomId,  RedirectAttributes ra) {
         ResponseBean responseBean= new ResponseBean();
         responseBean.setCode(HttpStatus.OK.toString());
         responseBean.setMessage("success");
         responseBean.setData(seatService.save(listLineCodes, listSeatTypeId, listNumberOfSeatPerLine, roomId));

            return new ResponseEntity<>(responseBean, HttpStatus.OK);

    }


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
