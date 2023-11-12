package com.example.filmBooking.controller;

import com.example.filmBooking.model.Food;
import com.example.filmBooking.model.Movie;
import com.example.filmBooking.service.FoodService;
import com.example.filmBooking.util.UploadImage;
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
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Controller
@RequestMapping("/food")
public class FoodAdminController {
    @Autowired
    private FoodService foodService;
    @Autowired
    private UploadImage uploadImage;

    @PostMapping("/save/{pageNumber}")
    @Operation(summary = "[Thêm dữ liệu Food]")
    public String addFood(Model model, @RequestParam(name = "id") String id, @RequestParam(name = "name") String name, @RequestParam(name = "price") BigDecimal priceFood,
                          @RequestParam(name = "description") String description, @RequestParam(name = "image")
                                  MultipartFile multipartFile,
                          @PathVariable("pageNumber") Integer currentPage) {
        try {
            Food food = Food.builder()
                    .id(id)
                    .name(name)
                    .price(priceFood)
                    .description(description)
                    .image(multipartFile.getOriginalFilename())
                    .build();
            uploadImage.handerUpLoadFile(multipartFile);
            if (foodService.save(food) instanceof Food){
                model.addAttribute("tnanhCong", "Thêm đồ ăn thành công !");
            }else {
                model.addAttribute("thatBai", "Thêm đồ ăn thất bại !");
            }
            model.addAttribute("food", new Food());
            model.addAttribute("currentPage", currentPage);
            return "redirect:/food/find-all";
        } catch (Exception e) {
            e.printStackTrace();
            return "admin/food";
        }
    }

    @GetMapping("/find-all")
    public String viewFood(Model model) {
        return findAll(model, 1, null);
    }

    @GetMapping("/find-all/page/{pageNumber}")
    public String findAll(Model model, @PathVariable("pageNumber") Integer currentPage, @Param("keyword") String name) {
        Page<Food> page = foodService.findAll(currentPage);
        if (name != null) {
            page = foodService.findByNameContains(name, currentPage);
        }
        model.addAttribute("keyword", name);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listFood", page.getContent());
        model.addAttribute("food", new Food());
        return "admin/food";
    }
    @GetMapping("/test")
    public String test() {
        return "admin/movie";
    }

    @GetMapping("/delete/{id}")
    @Operation(summary = "[Xóa dữ liệu Food]")
    public String delete(@PathVariable(name = "id") String id) {
        foodService.delete(id);
        return "redirect:/food/find-all";
    }

    @GetMapping("/update/{pageNumber}/{id}")
    public String detailUpdateFood(Model model,
                                   @PathVariable(name = "id") String id,
                                   @PathVariable("pageNumber") Integer currentPage) {

        Page<Food> listFood = foodService.findAll(currentPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", listFood.getTotalPages());
        model.addAttribute("totalItems", listFood.getTotalElements());
        model.addAttribute("food", foodService.findById(id));
        model.addAttribute("listFood", listFood.getContent());
        return "admin/food";
    }

}
