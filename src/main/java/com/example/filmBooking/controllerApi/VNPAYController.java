package com.example.filmBooking.controllerApi;

import com.example.filmBooking.model.*;
import com.example.filmBooking.repository.CustomerRepository;
import com.example.filmBooking.repository.TicketRepository;
import com.example.filmBooking.service.impl.VNPayService;
import com.example.filmBooking.service.impl.*;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.*;

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

    @Autowired
    private CustomerRepository customerRepository;


    @GetMapping("/index")
    public String home() {
        return "users/orderfail";
    }

    @PostMapping("/submitOrder")
    public String submidOrder(@RequestParam("amount") int orderTotal,
                              @RequestParam("orderInfo") String orderInfo,
                              @RequestParam("nameFiml") String nameFiml,
                              @RequestParam("roomm") String roomm,
                              @RequestParam("datedate") String datedate,
                              @RequestParam("timetime") String timetime,
                              @RequestParam("foodfood") String foodfood,
                              @RequestParam("seatseat") String seatseat,
                              @RequestParam("seatCountCount") String seatCountCount,
                              @RequestParam("priceSeatSeat") String priceSeatSeat,
                              @RequestParam("priceFoodFood") String priceFoodFood,
                              @RequestParam("discountcount") String discountcount,
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
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String formattedPriceVN = currencyVN.format(orderTotal);
//        System.out.println(selectedPromition.);
        HttpSession session1 = request.getSession();
//thêm bill
        Bill bill = new Bill();
//        Customer customer1 = new Customer();
        bill.setStatus(1);
//        bill.setTradingCode(vnp_TransactionNo);
        bill.setDateCreate(LocalDateTime.now());
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("customer");
        bill.setCustomer(customer);
        bill.setTotalMoney(orderTotalDecimal);
        BigDecimal phantram = BigDecimal.valueOf(0.05);
        BigDecimal diemKhachHang = orderTotalDecimal.multiply(phantram);
        customer.setPoint(customer.getPoint() + diemKhachHang.intValue());
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
        session1.setAttribute("customer", customer);

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

        //        gửi về mail
        final String username = "toanhd290803@gmail.com";
        final String password = "qgup pdli eiwq emhv";
        String thongbao = "không có";
        String thongbaos = "0";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Tạo một phiên session
        Session session2 = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            // Tạo message và thực hiện gửi email
            Message message = new MimeMessage(session2);
            message.setFrom(new InternetAddress("toanhd290803@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("toanhd290803@gmail.com"));
            StringBuilder emailContent = new StringBuilder();
            message.setSubject("Thông tin đơn hàng của bạn(Đơn hàng thanh toán thành công)");
            emailContent.append("Tên phim : ").append(nameFiml).append("\n");
            emailContent.append("Rạp/Phòng chiếu : ").append(roomm).append("\n");
            emailContent.append("Ngày chiếu : ").append(datedate).append("\n");
            emailContent.append("Giờ chiếu : ").append(timetime).append("\n");
            if (foodfood == null || foodfood.isEmpty()) {
                emailContent.append("Đồ ăn : ").append(thongbao).append("\n");
            } else {
                emailContent.append("Đồ ăn : ").append(foodfood).append("\n");
            }
            emailContent.append("Ghế : ").append("(" + seatCountCount + ")" + seatseat).append("\n");
            emailContent.append("Tổng tiền vé : ").append(priceSeatSeat).append("\n");
            if (priceFoodFood == null || priceFoodFood.isEmpty()) {
                emailContent.append("Tổng tiền đồ ăn : ").append(thongbaos).append("\n");
            } else {
                emailContent.append("Tổng tiền đồ ăn : ").append(priceFoodFood).append("\n");
            }
            if (discountcount == null || discountcount.isEmpty()) {
                emailContent.append("Tiền được giảm : ").append(thongbaos).append("\n");
            } else {
                emailContent.append("Tiền được giảm : ").append(discountcount).append("\n");
            }
            emailContent.append("Thành tiền : ").append(formattedPriceVN).append("\n");
            emailContent.append("Đơn hàng của bạn được đặt thành công! Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi.");
            message.setText(emailContent.toString());
//            Transport.send(message);
            session1.setAttribute("message", message);
            System.out.println("Email sent successfully");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return "redirect:" + vnpayUrl;
    }

    @PostMapping("/filmbooking/thongtinthanhtoan")
    public String submidOrder1(@RequestParam("amount1") int orderTotal,
                               @RequestParam("orderInfor") String orderInfor,
                               @RequestParam("room") String room,
                               @RequestParam("date") String date,
                               @RequestParam("time") String time,
                               @RequestParam("foodd") String foodd,
                               @RequestParam("seat") String seat,
                               @RequestParam("seatCount") String seatCount,
                               @RequestParam("priceSeat") String priceSeat,
                               @RequestParam("priceFood") String priceFood,
                               @RequestParam("discount") String discount,
                               Model model,
                               HttpServletRequest request,
                               @RequestParam("selectedSeats1") List<Ticket> selectedSeats1,
                               @RequestParam("priceTicket1") BigDecimal priceTicket1,
                               @RequestParam(value = "selectedFood1", required = false) List<Food> selectedFood1,
                               @RequestParam(value = "selectedQuantity1", required = false) List<Integer> selectedQuantity1,
                               @RequestParam(value = "selectedPrice1", required = false) List<BigDecimal> selectedPrice1,
                               @RequestParam(value = "selectedPromition1", required = false) Promotion selectedPromition1) {

        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("customer");
        model.addAttribute("customer", customer);
        String transactionCode = generateTransactionCode();
        BigDecimal orderTotalDecimal = new BigDecimal(orderTotal);
        String thongtin = "Giao dịch được thực hiện tại Web FimlBooking";
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String formattedPriceVN = currencyVN.format(orderTotal);
        model.addAttribute("orderId", thongtin);
        model.addAttribute("totalPrice", formattedPriceVN);
        model.addAttribute("paymentTime", LocalDateTime.now());
        model.addAttribute("transactionId", transactionCode);
//thêm bill
        Bill bill = new Bill();

//        Customer customer1 = new Customer();
        bill.setStatus(0);
        bill.setDateCreate(LocalDateTime.now());
        bill.setCustomer(customer);
        bill.setTotalMoney(orderTotalDecimal);
        bill.setTradingCode(transactionCode);
        BigDecimal phantram = BigDecimal.valueOf(0.05);
        BigDecimal diemKhachHang = orderTotalDecimal.multiply(phantram);
        customer.setPoint(customer.getPoint() + diemKhachHang.intValue());

        if (selectedPromition1 == null) {
            // Xử lý khi selectedFood là null hoặc rỗng
            System.out.println("Dữ liệu  không tồn tại hoặc rỗng");
        } else {
            Promotion promotion = promotionService.findById(selectedPromition1.getId());
            bill.setPromotion(selectedPromition1);
            promotion.setQuantity(promotion.getQuantity() - 1);
            promotionService.save(selectedPromition1);
        }

        Bill createdBill = billService.save(bill);
        customerRepository.save(customer);


//thêm bill_ticket

        for (Ticket ticket : selectedSeats1) {
            // Lưu seatId vào cơ sở dữ liệu
            BillTicket billTicket = new BillTicket();
            billTicket.setBill(createdBill);
            billTicket.setTotalMoney(priceTicket1);
            billTicket.setStatus(0);
            billTicket.setTicket(ticket);
            billTicketService.save(billTicket);
            ticket.setStatus("đã bán");
            ticketService.save(ticket);
        }
////thêm bill_food
        if (selectedFood1 == null || selectedFood1.isEmpty()) {
            // Xử lý khi selectedFood là null hoặc rỗng
            System.out.println("Dữ liệu không tồn tại hoặc rỗng");
        } else {
            for (int i = 0; i < selectedFood1.size() && i < selectedQuantity1.size() && i < selectedPrice1.size(); i++) {
                Food food = selectedFood1.get(i);
                Integer quantity = selectedQuantity1.get(i);
                BigDecimal price = selectedPrice1.get(i);
                BillFood billFood = new BillFood();

                billFood.setBill(createdBill);
                billFood.setStatus(0);
                billFood.setFood(food);
                billFood.setQuantity(quantity);
                billFood.setTotalMoney(price);
                billFoodService.save(billFood);

            }
        }
//        gửi về mail
        final String username = "toanhd290803@gmail.com";
        final String password = "qgup pdli eiwq emhv";
        String thongbao = "không có";
        String thongbaos = "0";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Tạo một phiên session
        Session session1 = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            // Tạo message và thực hiện gửi email
            Message message = new MimeMessage(session1);
            message.setFrom(new InternetAddress("toanhd290803@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("toanhd290803@gmail.com"));
            StringBuilder emailContent = new StringBuilder();
            message.setSubject("Thông tin đơn hàng của bạn(Đơn hàng đang chờ thanh toán)");
            emailContent.append("Tên phim : ").append(orderInfor).append("\n");
            emailContent.append("Rạp/Phòng chiếu : ").append(room).append("\n");
            emailContent.append("Ngày chiếu : ").append(date).append("\n");
            emailContent.append("Giờ chiếu : ").append(time).append("\n");
            if (foodd == null || foodd.isEmpty()) {
                emailContent.append("Đồ ăn : ").append(thongbao).append("\n");
            } else {
                emailContent.append("Đồ ăn : ").append(foodd).append("\n");
            }
            emailContent.append("Ghế : ").append("(" + seatCount + ")" + seat).append("\n");
            emailContent.append("Tổng tiền vé : ").append(priceSeat).append("\n");
            if (priceFood == null || priceFood.isEmpty()) {
                emailContent.append("Tổng tiền đồ ăn : ").append(thongbaos).append("\n");
            } else {
                emailContent.append("Tổng tiền đồ ăn : ").append(priceFood).append("\n");
            }
            if (discount == null || discount.isEmpty()) {
                emailContent.append("Tiền được giảm : ").append(thongbaos).append("\n");
            } else {
                emailContent.append("Tiền được giảm : ").append(discount).append("\n");
            }
            emailContent.append("Thành tiền : ").append(formattedPriceVN).append("\n");
            emailContent.append("Mã đơn hàng : ").append(transactionCode).append("\n");
            emailContent.append("Đơn hàng của bạn đang chờ xác nhận! Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi.");
            message.setText(emailContent.toString());
            Transport.send(message);

            System.out.println("Email sent successfully");

        } catch (
                MessagingException e) {
            throw new RuntimeException(e);
        }
        return "users/ordersuccessfull";
    }

    public static String generateTransactionCode() {
        Random random = new Random();
        StringBuilder transactionCode = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            transactionCode.append(random.nextInt(10)); // Sinh số ngẫu nhiên từ 0 đến 9 và thêm vào chuỗi
        }
        return transactionCode.toString();
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
            Customer customer1 = (Customer) session.getAttribute("customer");
            Promotion promotion = (Promotion) session.getAttribute("selectedPromition");
            List<BillTicket> billTickets = (List<BillTicket>) session.getAttribute("billTickets");
            List<BillFood> billFoods = (List<BillFood>) session.getAttribute("billFoods");
            List<Ticket> tickets = (List<Ticket>) session.getAttribute("tickets");
// Lưu đối tượng Bill vào cơ sở dữ liệu
            bill.setTradingCode(transactionId);
            billService.save(bill);
            customerRepository.save(customer1);
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
            final String username = "toanhd290803@gmail.com";
            final String password = "qgup pdli eiwq emhv";

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            // Tạo một phiên session
            Session session2 = Session.getDefaultInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
            try {
                Message message = (Message) session.getAttribute("message");
                Transport.send(message);
                System.out.println("Email sent successfully");
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
            return "users/ordersuccess";
        } else {
            return "users/orderfail";
        }
    }
}
