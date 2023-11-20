package com.example.filmBooking.controller;

import com.example.filmBooking.model.Customer;
import com.example.filmBooking.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customer")
@SessionAttributes("soldTicketsCount")

public class CustomerAdminController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/find-all")
    public String viewCustomer(Model model){
        return findAll(model, 1);
    }
    @GetMapping("/find-all/page/{pageNumber}")
    public String findAll(Model model, @PathVariable("pageNumber") Integer currentPage) {
        Page<Customer> page = customerService.getAll(currentPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listCustomer", page.getContent());
        model.addAttribute("customer", new Customer());
        return "admin/customer";
    }
        @PostMapping("/save")
    @Operation(summary = "[Thêm mới]")
    public String save(@RequestParam(name = "id") String id ,
//                       @RequestParam(name = "code") String code,
                       @RequestParam(name = "name") String name,
                       @RequestParam(name = "phoneNumber") String phoneNumber,
                       @RequestParam(name = "email") String email,
                       @RequestParam(name = "password") String password,
                       Model model) {
        Customer customer = Customer.builder()
                .id(id)
//                .code(code)
                .name(name)
                .email(email)
                .phoneNumber(phoneNumber)
                .password(password)
                .build();
        customerService.save(customer);
        model.addAttribute("customer", new Customer());
        return "redirect:/customer/find-all";
    }

    @GetMapping("/update/{pageNumber}/{id}")
    public String update(Model model, @PathVariable(name = "id") String id, @PathVariable("pageNumber") Integer currentPage){
        Page<Customer> page = customerService.getAll(currentPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listCustomer", page.getContent());
        model.addAttribute("customer", customerService.findById(id));
        return "admin/customer";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") String id){
        customerService.delete(id);
        return "redirect:/customer/find-all";
    }

    @GetMapping("/search-customer/{pageNumber}")
    public String serachCustomer(Model model, @RequestParam("keyword") String keyword,
                                 @PathVariable(name = "pageNumber") Integer currentPage){
        Page<Customer> page = customerService.searchCustomer(keyword,currentPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listCustomer", page.getContent());
        model.addAttribute("customer", new Customer());
        return "admin/customer";
    }
}
