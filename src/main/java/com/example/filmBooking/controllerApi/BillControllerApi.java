package com.example.filmBooking.controllerApi;

import com.example.filmBooking.model.Customer;
import com.example.filmBooking.model.Schedule;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/show/bill")
public class BillControllerApi {

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping
    public String displayBillPage(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        //Lấy ra những chỗ ngồi mà khách đặt,map sang list kiểu int rồi lưu lên session
        String[] seats = request.getParameterValues("seats");
        List<Integer> listSeatIds = Arrays.stream(seats).map(seat->Integer.parseInt(seat)).collect(Collectors.toList());
        session.setAttribute("listSelectedSeatIds",listSeatIds);

        // Đếm số ghế:
        Integer numberOfSelectedSeats= listSeatIds.size();
        model.addAttribute("numberOfSelectedSeats",numberOfSelectedSeats);

        // Lấy ra tổng tiền:
        Schedule scheduleFromSession = (Schedule)session.getAttribute("schedule");
//        Double totalAmount = scheduleFromSession.getPrice() * numberOfSelectedSeats;
        double totalAmount = scheduleFromSession.getPrice().doubleValue() * numberOfSelectedSeats.intValue();
        model.addAttribute("totalAmount",totalAmount);

        // Format laại ngày:
        model.addAttribute("formattedDate",
                scheduleFromSession.getStatus().format(String.valueOf(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));

        model.addAttribute("customer",new Customer());
        session.removeAttribute("bookedError");
        return "users/index";
    }
}
