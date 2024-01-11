package com.example.filmBooking.controller;

import com.example.filmBooking.model.Food;
import com.example.filmBooking.model.Movie;
import com.example.filmBooking.model.Room;
import com.example.filmBooking.service.FoodService;
import com.example.filmBooking.util.UploadImage;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.filmBooking.model.ServiceType;
import com.example.filmBooking.service.ServiceTypeService;


import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/food")
@SessionAttributes("soldTicketsCount")

public class FoodAdminController {
    @Autowired
    private FoodService foodService;
    @Autowired
    private UploadImage uploadImage;

    @Autowired
    private ServiceTypeService serviceTypeService;

    @PostMapping("/save/{pageNumber}")
    @Operation(summary = "[Thêm dữ liệu Food]")
    public String addFood(Model model, @RequestParam(name = "id") String id, @RequestParam(name = "name") String name, @RequestParam(name = "price") BigDecimal priceFood,
                          @RequestParam(name = "description") String description, @RequestParam(name = "image")
                                  MultipartFile multipartFile,
                          @PathVariable("pageNumber") Integer currentPage, RedirectAttributes ra, @RequestParam(name = "service") ServiceType serviceType) {
        try {
            Food food = Food.builder()
                    .id(id)
                    .name(name)
                    .price(priceFood)
                    .description(description)
                    .serviceType(serviceType)
                    .image(multipartFile.getOriginalFilename())
                    .build();
            uploadImage.handerUpLoadFile(multipartFile);
            if (foodService.save(food) instanceof Food) {
                ra.addFlashAttribute("successMessage", "Thêm thành công");
            } else {
                ra.addFlashAttribute("errorMessage", "Thêm thất bại");
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
    public String findAll(Model model, @PathVariable("pageNumber") Integer currentPage,  @RequestParam(value = "keyword", required = false) String name) {
        Page<Food> page = foodService.findAll(currentPage);
        if (name != null) {
            page = foodService.findByNameContains(name, currentPage);
        }
        model.addAttribute("keyword", name);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listFood", page.getContent());
        List<ServiceType> listService = serviceTypeService.findAll();
        model.addAttribute("cbbServiceType", listService);
        model.addAttribute("food", new Food());
        return "admin/food";
    }

    @GetMapping("/test")
    public String test() {
        return "admin/movie";
    }

    @GetMapping("/delete/{id}")
    @Operation(summary = "[Xóa dữ liệu Food]")
    public String delete(@PathVariable(name = "id") String id,RedirectAttributes ra) {

        try {
            foodService.delete(id);
            ra.addFlashAttribute("successMessage", "Xóa thành công!!!");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Xóa thất bại!!!");
        }
        return "redirect:/food/find-all";
    }

    //    @GetMapping("/update/{pageNumber}/{id}")
//    public String detailUpdateFood(Model model,
//                                   @PathVariable(name = "id") String id,
//                                   @PathVariable("pageNumber") Integer currentPage) {
//
//        Page<Food> listFood = foodService.findAll(currentPage);
//        model.addAttribute("currentPage", currentPage);
//        model.addAttribute("totalPages", listFood.getTotalPages());
//        model.addAttribute("totalItems", listFood.getTotalElements());
//        model.addAttribute("food", foodService.findById(id));
//        model.addAttribute("listFood", listFood.getContent());
//        return "admin/food";
//    }
    @PostMapping("/update/{id}")
    public String updatePromotion(@PathVariable(name = "id") String id, Food updatedRoom, RedirectAttributes ra) {
        foodService.update(id, updatedRoom);
        ra.addFlashAttribute("successMessage", "Sửa thành công!!!");

        return "redirect:/food/find-all";   // Redirect to the promotion list page after update
    }
    @PostMapping("/service-type/save")
    public String addServiceType(@RequestParam("name") String name, Model model, RedirectAttributes ra){
        try{
            ServiceType serviceType = ServiceType.builder()
                    .name(name)
                    .build();
            if (serviceTypeService.addServiceType(serviceType) instanceof ServiceType) {
                ra.addFlashAttribute("successMessage", "Thêm thành công");
            } else {
                ra.addFlashAttribute("errorMessage", "Thêm thất bại");
            }
            model.addAttribute("serviceType", new ServiceType());
            return "redirect:/food/service-type";
        }catch (Exception e){
//            e.printStackTrace();
            return "admin/service-type";
        }
    }
    
    @PostMapping("/service-type/update/{id}")
    public String updateServiceType(@PathVariable(name = "id")String id, Model model, ServiceType serviceType, RedirectAttributes ra){
        serviceTypeService.updateServiceType(serviceType, id);
        ra.addFlashAttribute("successMessage", "Sửa thành công!!!");
        return "redirect:/food/service-type";
    }
    @GetMapping("/service-type/delete/{id}")
    public String deleteServiceType(Model model, @PathVariable("id") String id, RedirectAttributes ra){
        try{
            serviceTypeService.deleteServiceType(id);
            ra.addFlashAttribute("successMessage", "Xóa thành công!!!");
        }catch (Exception e){
            ra.addFlashAttribute("errorMessage", "Xóa thất bại!!!");
        }
        return "redirect:/food/service-type";
    }
    @GetMapping("/service-type")
    public String getAll(Model model){
        List<ServiceType> listServiceType = serviceTypeService.findAll();
        model.addAttribute("listServiceType", listServiceType);
        return "admin/service-type";
    }
}
