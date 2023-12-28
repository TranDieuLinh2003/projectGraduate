package com.example.filmBooking.controller;

import com.example.filmBooking.common.ResponseBean;
import com.example.filmBooking.model.*;
import com.example.filmBooking.service.RoomService;
import com.example.filmBooking.service.SeatService;
import com.example.filmBooking.service.impl.RoomServiceImpl;
import com.example.filmBooking.service.impl.SeatServiceImpl;
import com.example.filmBooking.service.impl.SeatTypeServiceImpl;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Controller
@RequestMapping("/seat")
@Tag(name = "Seat")
@SessionAttributes("soldTicketsCount")

public class SeatAdminController {

    @Autowired
    private RoomServiceImpl roomServiceI;

    @Autowired
    private SeatService seatService;

    @Autowired
    private SeatTypeServiceImpl seatTypeService;

    @Autowired
    private SeatServiceImpl saveCustomersToDatabase;

    @Autowired
    private RoomService roomService;

    @GetMapping("/find-all")
    public String viewSeat(Model model) {
        List<Room> getAll = roomService.fillAll();
        model.addAttribute("getAll", getAll);

        return "admin/seat";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") String id, RedirectAttributes ra) {

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
//    public ResponseEntity<?> save( Model model,@RequestParam("listLineCodes") List<String> listLineCodes,@RequestParam("listSeatTypeId") List<String> listSeatTypeId, @RequestParam("listNumberOfSeatPerLine") List<Integer> listNumberOfSeatPerLine,@RequestParam("roomId") String roomId,  RedirectAttributes ra) {
//         ResponseBean responseBean= new ResponseBean();
//         responseBean.setCode(HttpStatus.OK.toString());
//         responseBean.setMessage("success");
//         responseBean.setData(seatService.save(listLineCodes, listSeatTypeId, listNumberOfSeatPerLine, roomId));
//
//            return new ResponseEntity<>(responseBean, HttpStatus.OK);
//
//    }
//

    @PostMapping("/update/{id}")
    public String updatePromotion(Model model, @PathVariable(name = "id") String id, Seat updatedRoom, RedirectAttributes ra) {
        List<SeatType> seatTypeList = seatTypeService.findAll();
        System.out.println(seatTypeList);
        model.addAttribute("seatTypeList", seatTypeList);
        seatService.update(id, updatedRoom);
        ra.addFlashAttribute("successMessage", "Sửa thành công!!!");
        return "redirect:/seat/find-all";   // Redirect to the promotion list page after update
    }

    @GetMapping("/seat-manager")
    public String viewSeatCustomer(Model model) {

        return "admin/seat-manager";
    }

    @GetMapping("/view-upload")
    public String viewUpload() {
        return "admin/ghe";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {

        seatService.readExcel(file);
        return "redirect:/seat/find-all";
    }

//    @PostMapping("/save")
//    public String save( Model model,
//                                   @RequestParam(value = "listLineCodes", required = false) List<String> listLineCodes,
//                                   @RequestParam(value = "listSeatTypeId", required = false) List<String> listSeatTypeId,
//                                   @RequestParam(value = "listNumberOfSeatPerLine", required = false) List<Integer> listNumberOfSeatPerLine,
//                                   @RequestParam("roomId") String roomId,  RedirectAttributes ra) {
//
//        try {
//            seatService.save(listLineCodes, listSeatTypeId, listNumberOfSeatPerLine, roomId);
//            return "redirect:/seat/find-all";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "admin/seat";
//        }
//
//    }

    @PostMapping("/save")
    public String save(Model model,
                       @RequestParam(value = "listLineCodes", required = false) String[] listLineCodes,
                       @RequestParam(value = "listSeatTypeId", required = false) String[] listSeatTypeId,
                       @RequestParam(value = "listNumberOfSeatPerLine", required = false) String[] listNumberOfSeatPerLine,
                       @RequestParam("roomId") String roomId,
                       RedirectAttributes ra, Room updatedRoom) {
        try {

            int totalNumberOfSeats = 0;
            if (listNumberOfSeatPerLine != null) {
                for (String numberOfSeats : listNumberOfSeatPerLine) {
                    try {
                        int seats = Integer.parseInt(numberOfSeats);
                        totalNumberOfSeats += seats;
                        System.out.println("tao là:" + totalNumberOfSeats);
                        updatedRoom.setCapacity(totalNumberOfSeats);
                        roomServiceI.updateSeat(roomId, updatedRoom);

                    } catch (NumberFormatException e) {
                        // Xử lý ngoại lệ nếu dữ liệu không thể chuyển thành số nguyên
                    }
                }
            }
            CompletableFuture<Void> saveTask = CompletableFuture.runAsync(() -> {
                List<String> lineCodes = new ArrayList<>(Arrays.asList(listLineCodes));
                List<String> seatTypeIds = new ArrayList<>(Arrays.asList(listSeatTypeId));
                List<Integer> numberOfSeatPerLine = new ArrayList<>();
                for (String numberOfSeat : listNumberOfSeatPerLine) {
                    numberOfSeatPerLine.add(Integer.parseInt(numberOfSeat));
                }
                seatService.save(lineCodes, seatTypeIds, numberOfSeatPerLine, roomId);
            });

            saveTask.get(); // Ensure save operation is completed before redirecting

            return "redirect:/seat/find-all";
        } catch (Exception e) {
            // Generic exception handling
            e.printStackTrace();
            return "admin/seat";
        }
    }
}
