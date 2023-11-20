package com.example.filmBooking.controller;

import com.example.filmBooking.common.ResponseBean;
import com.example.filmBooking.model.RankCustomer;
import com.example.filmBooking.service.RankCustomerService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/rank")
@SessionAttributes("soldTicketsCount")

public class RankAdminController {
    @Autowired
    private RankCustomerService service;

    @GetMapping("/find-all")
    @Operation(summary = "[Hiển thị tất cả]")
    public String findAll(Model model) {
        List<RankCustomer> listRank = service.fillAll();
        model.addAttribute("listRank", listRank);
        model.addAttribute("rank", new RankCustomer());
        return "admin/rank";
    }

    @PostMapping("/save")
    @Operation(summary = "[Thêm mới]")
    public String save(Model model, @RequestParam(name = "id") String id,
                       @RequestParam(name = "name") String name,
                       @RequestParam(name = "point") Integer point,
                       @RequestParam(name = "description") String description) {
        try {
            RankCustomer rankCustomer = RankCustomer.builder()
                    .id(id)
                    .name(name)
                    .point(point)
                    .description(description)
                    .build();
            if (service.save(rankCustomer) instanceof RankCustomer) {
                model.addAttribute("thanhCong", "Thêm hạng thành công");
            } else {
                model.addAttribute("thatBai", "Thêm hạng thất bại");
            }
            model.addAttribute("rank", new RankCustomer());
            return "redirect:/rank/find-all";
        } catch (Exception e) {
            e.printStackTrace();
            return "admin/rank";
        }

    }

    @GetMapping("/update/{id}")
    @Operation(summary = "[Chỉnh sửa]")
    public String update(@PathVariable("id") String id, Model model) {
        List<RankCustomer> listRank = service.fillAll();
        model.addAttribute("listRank", listRank);
        model.addAttribute("rank", service.findById(id));
        return "admin/rank";
    }

    @GetMapping("/delete/{id}")
    @Operation(summary = "[Xóa]")
    public String delete(@PathVariable("id") String id) {
        service.delete(id);
        return "redirect:/rank/find-all";
    }

    @GetMapping("/search-rank")
    public String searchRank(Model model, @RequestParam("keyword") String keyword) {
        List<RankCustomer> searchRank = service.searchNameRank(keyword);
        model.addAttribute("listRank", searchRank);
        model.addAttribute("rank", new RankCustomer());
        return "admin/rank";
    }
}
