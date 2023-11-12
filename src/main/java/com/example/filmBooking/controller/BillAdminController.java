package com.example.filmBooking.controller;

import com.example.filmBooking.common.ResponseBean;
import com.example.filmBooking.model.Bill;
import com.example.filmBooking.model.Food;
import com.example.filmBooking.service.BillService;
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


import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/bill")
public class BillAdminController {
    @Autowired
    private BillService service;

    @GetMapping("/find-all")
    public String viewBill(Model model) {
        return findAll(model, 1, null, null);
    }


    @GetMapping("/find-all/page/{pageNumber}")
    @Operation(summary = "[Hiển thị tất cả]")
    public String findAll(Model model, @PathVariable(name = "pageNumber") Integer currentPage, @Param("startDate") Date startDate, @Param("endDate") Date endDate) {

        Page<Bill> billStatusOne = service.findStatusOne(currentPage);
        if (startDate != null && endDate != null) {
            billStatusOne = service.searchDateAndDate(startDate, endDate, currentPage);
        }
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("currentPageOne", currentPage);
        model.addAttribute("totalPagesOne", billStatusOne.getTotalPages());
        model.addAttribute("totalItemsOne", billStatusOne.getTotalElements());
        model.addAttribute("billOne", billStatusOne.getContent());
        model.addAttribute("bill", new Bill());
        return "admin/bill";
    }

    @GetMapping("/xac-nhan")
    public String viewCho(Model model) {
        return viewXacNhan(model, 1);
    }

    @GetMapping("/xac-nhan/page/{pageNumber}")
    public String viewXacNhan(Model model, @PathVariable("pageNumber") Integer currentPage) {
        Page<Bill> biilStatusZero = service.findStatusZero(currentPage);
        model.addAttribute("currentPageZero", currentPage);
        model.addAttribute("totalPagesZero", biilStatusZero.getTotalPages());
        model.addAttribute("totalItemsZero", biilStatusZero.getTotalElements());
        model.addAttribute("billZero", biilStatusZero.getContent());
        model.addAttribute("bill", new Bill());
        return "admin/xac-nhan";
    }

    //
    @GetMapping("/update/{id}")
    public String updateStatus(Model model, @PathVariable(name = "id") String id) {
        Bill bill = service.findById(id);
        bill.setStatus(1);
        try {
            if (service.update(id, bill) instanceof Bill) {
                model.addAttribute("thanhCong", "Xác nhận hóa đơn thành công");
            } else {
                model.addAttribute("thatBai", "Xác nhận hóa đơn thất bại");
            }
            return "redirect:/bill/find-all";
        } catch (Exception e) {
            e.printStackTrace();
            return "admin/xac-nhan";
        }

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) {
        service.delete(id);
        return "redirect:/bill/xac-nhan";
    }


}
