package com.example.filmBooking.controller;

import com.example.filmBooking.model.Cinema;
import com.example.filmBooking.model.Room;
import com.example.filmBooking.service.CinemaService;
import com.example.filmBooking.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/room")
public class RoomAdminController {
    @Autowired
    private RoomService roomService;

    @Autowired
    private CinemaService cinemaService;

    @PostMapping("/save")
    @Operation(summary = "[Thêm dữ liệu room]")
    public String addRoom(Model model, @RequestParam(name = "cinema") Cinema idCinema, @RequestParam(name = "quantity") int quantity) {
        try {
            boolean saveRoom = roomService.saveAll(idCinema, quantity);
            if (saveRoom == true) {
                model.addAttribute("thanhCong", "Thêm phòng thành công");
            } else {
                model.addAttribute("thatBai", "Thêm phòng thất bại");
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
    public String delete(@PathVariable(name = "id") String id) {
        roomService.delete(id);
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

    @PostMapping("/update")
    @Operation(summary = "[Thêm dữ liệu room]")
    public String updateRoom(Model model,
                             @RequestParam(name = "id") String id,
                             @RequestParam(name = "code") String code,
                             @RequestParam(name = "name") String name,
                             @RequestParam(name = "cinema") Cinema idCinema,
                             @RequestParam(name = "description") String description) {
        try {
            Room room = Room.builder()
                    .id(id)
                    .code(code)
                    .name(name)
                    .type(0)
                    .cinema(idCinema)
                    .description(description)
                    .build();
            if (roomService.update(id,room) instanceof Room) {
                model.addAttribute("thanhCong", "Sửa phòng thành công");
            }else {
                model.addAttribute("thatBai", "Sửa phòng thất bại");
            }
            model.addAttribute("room", new Room());
            return "redirect:/room/find-all";
        } catch (Exception e) {
            e.printStackTrace();
            return "admin/room";
        }
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
}
