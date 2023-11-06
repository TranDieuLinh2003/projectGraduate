package com.example.filmBooking.controller;

import com.example.filmBooking.model.Customer;
import com.example.filmBooking.service.CustomerService;
import com.example.filmBooking.service.impl.CustomerServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/filmbooking")
public class ThongTinController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/thongtincanhan")
    public String showThongTinCaNhan(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Customer customerSession = (Customer) session.getAttribute("customer");

        Customer customer = customerService.findByEmail(customerSession.getEmail());

        model.addAttribute("customer", customer);
        return "users/ThongTinCaNhan";
    }

    @PostMapping("/update-thongtin/{id}")
    public String updateAcc(
            @PathVariable(name = "id") String id,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "phoneNumber") String phoneNumber) {

        Customer customer = customerService.findById(id);
        if (customer != null) {
            customer.setEmail(email);
            customer.setName(name);
            customer.setPhoneNumber(phoneNumber);
//            customer.setPassword(password);
            customerService.save(customer);
        } else {
            System.out.println("không tìm thấy account");
        }

        return "redirect:/thongtincanhan";
    }
}
