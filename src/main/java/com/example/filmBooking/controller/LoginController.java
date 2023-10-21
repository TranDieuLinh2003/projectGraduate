package com.example.filmBooking.controller;

import com.example.filmBooking.model.Customer;
import com.example.filmBooking.service.impl.CustomerServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/filmbooking")
public class LoginController {

    @Autowired
    private CustomerServiceImpl customerService;

//    @Autowired
//    private HttpServletRequest request;

    @GetMapping(value = "/login")
    public String hienThiFormLogin() {
        return "users/DangNhap";
    }
////
//    @PostMapping("/login")
//    public String login(
//            @RequestParam(name = "email") String email,
//            @RequestParam(name = "password") String password, HttpServletRequest request
//    ) {
//        Customer customer = customerService.findByEmail(email);
//        HttpSession sessionLogin = request.getSession();
//        if (customer == null) {
//            sessionLogin.setAttribute("ERR_LOGIN", "Sai Tên Tài Khoản Hoặc Mật Khẩu");
//            return "redirect:/login";
//        } else {
//
//                sessionLogin.setAttribute("customer", customer);
//                return "redirect:/filmbooking/trangchu";
//        }
//    }

    @PostMapping("/login")
    public String login(
            @RequestParam(name = "email") String email,
            @RequestParam(name = "password") String password,
            HttpServletRequest request
    ) {
//        Customer customer = customerService.findByEmail(email);?
        Customer customer = customerService.findByEmail(email);
        HttpSession sessionLogin = request.getSession();

        if (customer == null || !customer.getPassword().equals(password)) {
            sessionLogin.setAttribute("ERR_LOGIN", "Sai tên tài khoản hoặc mật khẩu");
            return "redirect:/filmbooking/login";
        } else {
            sessionLogin.setAttribute("customer", customer);
            return "redirect:/filmbooking/trangchu";
        }
    }
    @GetMapping("/logout")
    public String logout( HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("customer", null);
        return "redirect:/filmbooking/login";
    }


}
