package com.example.filmBooking.controller;

import com.example.filmBooking.model.Bill;
import com.example.filmBooking.model.Customer;
import com.example.filmBooking.repository.BillRepository;
import com.example.filmBooking.service.CustomerService;
import com.example.filmBooking.service.impl.CustomerServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/filmbooking")
public class ThongTinController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BillRepository billRepository;

    @GetMapping("/thongtincanhan")
    public String showThongTinCaNhan(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Customer customerSession = (Customer) session.getAttribute("customer");

        Customer customer = customerService.findByEmail(customerSession.getEmail());
        if (customer == null){
            return "redirect:/filmbooking/login";
        }
        List<Object[]> listBill = billRepository.findBillDetailsByCustomer(customer.getId());
        Map<String, List<Object[]>> groupedBillDetails = listBill.stream()
                .collect(Collectors.groupingBy(bill -> (String) bill[0])); // Assuming the transaction ID is at index 0 in the Object array

// Identify and collect duplicate and unique records
        Map<String, List<Object[]>> uniqueRecords = new HashMap<>();
        Map<String, List<Object[]>> duplicateRecords = new HashMap<>();
        groupedBillDetails.forEach((transactionId, details) -> {
            Set<String> existingSeats = new HashSet<>();
            List<Object[]> uniqueDetails = new ArrayList<>();
            List<Object[]> duplicateDetails = new ArrayList<>();
            for (Object[] detail : details) {
                String seatId = (String) detail[1]; // Assuming the seat ID is at index 1 in the Object array
                if (!existingSeats.contains(seatId)) {
                    existingSeats.add(seatId);
                    uniqueDetails.add(detail);
                } else {
                    duplicateDetails.add(detail);
                }
            }
            if (!uniqueDetails.isEmpty()) {
                uniqueRecords.put(transactionId, uniqueDetails);
            }
            if (!duplicateDetails.isEmpty()) {
                duplicateRecords.put(transactionId, duplicateDetails);
            }
        });

// Display unique records and all duplicate records
        uniqueRecords.forEach((transactionId, details) -> {
            System.out.println("Transaction ID: " + transactionId);
            details.forEach(detail -> System.out.println(Arrays.toString(detail)));
        });
        duplicateRecords.forEach((transactionId, details) -> {
            System.out.println("Duplicate Transaction ID: " + transactionId);
            details.forEach(detail -> System.out.println(Arrays.toString(detail)));
        });

        model.addAttribute("customer", customer);
        model.addAttribute("groupedBillDetails", groupedBillDetails);
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
