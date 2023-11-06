package com.example.filmBooking.controller;

import com.example.filmBooking.model.*;
import com.example.filmBooking.repository.TicketRepository;
import com.example.filmBooking.service.impl.VNPayService;
import com.example.filmBooking.service.impl.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class VNPAYController {
    @Autowired
    private VNPayService vnPayService;

    @Autowired
    private BillServiceImpl billService;

    @Autowired
    private BillTicketServiceImpl billTicketService;

    @Autowired
    private BillFoodServiceImpl billFoodService;

    @Autowired
    private TicketRepository ticketService;

    @Autowired
    private PromotionServiceImpl promotionService;


    @GetMapping("/index")
    public String home() {
        return "users/orderfail";
    }

    @PostMapping("/submitOrder")
    public String submidOrder(@RequestParam("amount") int orderTotal,
                              @RequestParam("orderInfo") String orderInfo,
//                              @RequestParam("vnp_TransactionNo") String vnp_TransactionNo,
                              HttpServletRequest request,
                              @RequestParam("selectedSeats") List<Ticket> selectedSeats,
                              @RequestParam("priceTicket") BigDecimal priceTicket,
                              @RequestParam(value = "selectedFood", required = false) List<Food> selectedFood,
                              @RequestParam(value = "selectedQuantity", required = false) List<Integer> selectedQuantity,
                              @RequestParam(value = "selectedPrice", required = false) List<BigDecimal> selectedPrice,
                              @RequestParam(value = "selectedPromition", required = false) Promotion selectedPromition) {

        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(orderTotal, orderInfo, baseUrl);

        BigDecimal orderTotalDecimal = new BigDecimal(orderTotal);
//        System.out.println(selectedPromition.);
        HttpSession session1 = request.getSession();
//thêm bill
        Bill bill = new Bill();
        Promotion promotion = new Promotion();
        bill.setStatus(1);
//        bill.setTradingCode(vnp_TransactionNo);
        bill.setDateCreate(LocalDateTime.now());
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("customer");
        bill.setCustomer(customer);
        bill.setTotalMoney(orderTotalDecimal);

        if (selectedPromition == null) {
            // Xử lý khi selectedFood là null hoặc rỗng
            System.out.println("Dữ liệu  không tồn tại hoặc rỗng");
        } else {
            bill.setPromotion(selectedPromition);
            selectedPromition.setQuantity(selectedPromition.getQuantity() - 1);
        }
//        Bill createdBill = billService.save(bill);
//        HttpSession session1 = request.getSession();
//        session1.setAttribute("bill", createdBill);

        session1.setAttribute("bill", bill);
        session1.setAttribute("selectedPromition", selectedPromition);

//thêm bill_ticket
        List<BillTicket> billTickets = new ArrayList<>();
        List<Ticket> tickets = new ArrayList<>();
        for (Ticket ticket : selectedSeats) {
            // Lưu seatId vào cơ sở dữ liệu
            BillTicket billTicket = new BillTicket();
            billTicket.setBill(bill);
            billTicket.setTotalMoney(priceTicket);
            billTicket.setStatus(0);
            billTicket.setTicket(ticket);
            billTickets.add(billTicket);
            tickets.add(ticket);
            session1.setAttribute("billTickets", billTickets);
            session1.setAttribute("tickets", tickets);
        }
////thêm bill_food
        List<BillFood> billFoods = new ArrayList<>();
        if (selectedFood == null || selectedFood.isEmpty()) {
            // Xử lý khi selectedFood là null hoặc rỗng
            System.out.println("Dữ liệu không tồn tại hoặc rỗng");
        } else {
            for (int i = 0; i < selectedFood.size() && i < selectedQuantity.size() && i < selectedPrice.size(); i++) {
                Food food = selectedFood.get(i);
                Integer quantity = selectedQuantity.get(i);
                BigDecimal price = selectedPrice.get(i);
                BillFood billFood = new BillFood();

                billFood.setBill(bill);
                billFood.setStatus(0);
                billFood.setFood(food);
                billFood.setQuantity(quantity);
                billFood.setTotalMoney(price);
//            billFoodService.save(billFood);
                billFoods.add(billFood);
                session1.setAttribute("billFoods", billFoods);
            }
        }
        return "redirect:" + vnpayUrl;


    }

    @GetMapping("/vnpay-payment")
    public String GetMapping(HttpServletRequest request, Model model) {
        int paymentStatus = vnPayService.orderReturn(request);
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("customer");
        model.addAttribute("customer", customer);
        HttpEntity<?> entity = new HttpEntity<>(customer);
        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

        if (paymentStatus == 1) {
//            HttpSession session = request.getSession();
            Bill bill = (Bill) session.getAttribute("bill");
            Promotion promotion = (Promotion) session.getAttribute("selectedPromition");
            List<BillTicket> billTickets = (List<BillTicket>) session.getAttribute("billTickets");
            List<BillFood> billFoods = (List<BillFood>) session.getAttribute("billFoods");
            List<Ticket> tickets = (List<Ticket>) session.getAttribute("tickets");
// Lưu đối tượng Bill vào cơ sở dữ liệu
            bill.setTradingCode(transactionId);
            billService.save(bill);
            if (promotion == null) {
                // Xử lý khi selectedFood là null hoặc rỗng
                System.out.println("Dữ liệu  không tồn tại hoặc rỗng");
            } else {
                promotionService.save(promotion);
            }
            for (BillTicket billTicket : billTickets) {
                billTicketService.save(billTicket);
            }
            for (Ticket ticket : tickets) {
                ticket.setStatus("đã bán");
                ticketService.save(ticket);
            }
            if (billFoods == null || billFoods.isEmpty()) {
                // Xử lý khi selectedFood là null hoặc rỗng
                System.out.println("Dữ liệu không tồn tại hoặc rỗng");
            } else {
                for (BillFood billFood : billFoods) {
                    billFoodService.save(billFood);
                }
            }

            return "users/ordersuccess";
        } else {
            return "users/orderfail";
        }
    }
}
