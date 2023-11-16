package com.example.filmBooking.controller;

import com.example.filmBooking.common.ResponseBean;
import com.example.filmBooking.model.Bill;
import com.example.filmBooking.model.Food;
import com.example.filmBooking.repository.BillRepository;
import com.example.filmBooking.service.BillService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Controller
@RequestMapping("/bill")
@SessionAttributes("soldTicketsCount")
public class BillAdminController {
    @Autowired
    private BillService service;

    @Autowired
    private BillRepository repository;

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
        String soldTicketsCount = repository.countSoldTicketsWithStatusZero();
        model.addAttribute("soldTicketsCount", soldTicketsCount);
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
    public String viewCho(Model model)
    {
        return viewXacNhan(model, 1);
    }
//    @ModelAttribute("soldTicketsCount")
//    public Long getSoldTicketsCount() {
//        return Long.valueOf(repository.countSoldTicketsWithStatusZero());
//    }
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
                String email = bill.getCustomer().getEmail();
                final String username = "toanhd290803@gmail.com";
                final String password = "tjfv pjmw qsca jkkr"; // Replace <your-password> with your actual password

                Properties props = new Properties();
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.port", "587");

                Session session = Session.getInstance(props, new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

                try {
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress("toanhd290803@gmail.com"));

                    // Populate multiple recipients
                    String[] recipients = {email}; // Replace with actual recipient emails
                    InternetAddress[] recipientAddresses = new InternetAddress[recipients.length];
                    for (int i = 0; i < recipients.length; i++) {
                        recipientAddresses[i] = new InternetAddress(recipients[i]);
                    }
                    message.setRecipients(Message.RecipientType.TO, recipientAddresses);
                    message.setSubject("Đơn hàng đã được xác nhận!");
                    StringBuilder emailContent = new StringBuilder();
                    emailContent.append("Đơn hàng của bạn đã được xác nhận  ").append(LocalDateTime.now()).append("\n");
                    emailContent.append("Mã bil: ").append(bill.getCode()).append("\n");
                    emailContent.append("Mã giao dịch đơn hàng : ").append(bill.getTradingCode()).append("\n");
                    message.setText(emailContent.toString());

                    Transport.send(message);
                    System.out.println("Email sent successfully");
                } catch (MessagingException e) {
                    // Handle the exception, for example:
                    System.out.println("An error occurred while sending the email: " + e.getMessage());
                }
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
