package com.example.filmBooking.controller;

import com.example.filmBooking.model.Promotion;
import com.example.filmBooking.model.RankCustomer;
import com.example.filmBooking.service.PromotionService;
import com.example.filmBooking.service.RankCustomerService;
import com.example.filmBooking.util.UploadImage;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/promotion")
@SessionAttributes("soldTicketsCount")

public class PromotionAdminController {
    @Autowired
    private PromotionService promotionService;
    @Autowired
    private RankCustomerService rankCustomerService;

    @PostMapping("/save")
    @Operation(summary = "[Thêm dữ liệu promotion]")
    public String addPromotion(Model model, @RequestParam(name = "id") String id,
                               @RequestParam(name = "name") String name,
                               @RequestParam(name = "code") String code,
                               @RequestParam(name = "type") Integer type,
                               @RequestParam(name = "percent") Integer percent,
                               @RequestParam(name = "rankCustomer") RankCustomer rankCustomer,
                               @RequestParam(name = "startDate") LocalDateTime startDate,
                               @RequestParam(name = "endDate") LocalDateTime endDate,
                               @RequestParam(name = "quantity") Integer quantity,
                               @RequestParam(name = "description") String description
    ) {
        try {
            Promotion promotion = Promotion.builder()
                    .id(id)
                    .code(code)
                    .name(name)
                    .type(type)
                    .description(description)
                    .percent(percent)
                    .rankCustomer(rankCustomer)
                    .startDate(startDate)
                    .endDate(endDate)
                    .quantity(quantity)
                    .build();
            if (promotionService.save(promotion) instanceof Promotion){
                model.addAttribute("thanhCong", "Thêm phiếu thành công");
            }else {
                model.addAttribute("thatBai", "Thêm phiếu thất bại");
            }
            return "redirect:/promotion/find-all";
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("promotion", new Promotion());
            return "admin/promotion";
        }

    }

    @GetMapping("/find-all")
    public String viewPromotion(Model model) {
        return findAll(model, 1);
    }

    @GetMapping("/find-all/page/{pageNumber}")
    public String findAll(Model model, @PathVariable("pageNumber") Integer currentPage) {
        Page<Promotion> page = promotionService.getAll(currentPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listPromotion", page.getContent());
        List<RankCustomer> rankCustomer = rankCustomerService.fillAll();
        model.addAttribute("rankCustomer", rankCustomer);
        model.addAttribute("promotion", new Promotion()); // bắt buộc. không có là lỗi
        return "admin/promotion";
    }

    @GetMapping("/delete/{id}")
    @Operation(summary = "[Xóa dữ liệu promotion]")
    public String delete(@PathVariable(name = "id") String id) {

        promotionService.delete(id);

        return "redirect:/promotion/find-all";
    }

    @GetMapping("/update/{id}")
    public String detailUpdatepromotion(Model model, @PathVariable(name = "id") String id) {

        model.addAttribute("detailPromotion", promotionService.findById(id));
        model.addAttribute("promotion", new Promotion());
        return "admin/promotion";
    }

    @GetMapping("/findById/{pageNumber}/{id}")
    @Operation(summary = "[Tìm kiếm theo id]")
    public String findById(Model model, @PathVariable("id") String id, @PathVariable("pageNumber") Integer currentPage) {
        Page<Promotion> listPromotion = promotionService.getAll(currentPage);
        List<RankCustomer> rankCustomer = rankCustomerService.fillAll();
        model.addAttribute("rankCustomer", rankCustomer);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", listPromotion.getTotalPages());
        model.addAttribute("totalItems", listPromotion.getTotalElements());
        model.addAttribute("listPromotion", listPromotion.getContent());
        model.addAttribute("promotion", promotionService.findById(id));
        return "admin/promotion";

    }

    @GetMapping("/search-by-name-promotion/{pageNumber}")
    public String searchPromotion(@RequestParam("keyword") String keyword, Model model, @PathVariable("pageNumber") Integer currentPage) {
        Page<Promotion> page = promotionService.searchByNamePromotion(keyword, currentPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listPromotion", page.getContent());
        model.addAttribute("promotion", new Promotion());
        return "admin/promotion";
    }
}

