package com.example.filmBooking.service.impl;

import com.example.filmBooking.model.Room;
import com.example.filmBooking.model.Schedule;
import com.example.filmBooking.model.Seat;
import com.example.filmBooking.model.Ticket;
import com.example.filmBooking.repository.RoomRepository;
import com.example.filmBooking.repository.SeatRepository;
import com.example.filmBooking.repository.TicketRepository;
import com.example.filmBooking.service.ScheduleService;
import com.example.filmBooking.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;


@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository repository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private ScheduleService scheduleRepository;
    @Autowired
    private SeatRepository seatRepository;

    @Override
    public List<Ticket> fillAll() {
        return repository.findAll();
    }

    @Override
    public Ticket autoSave(String idSchedule) {
        Schedule schedule = scheduleRepository.findById(idSchedule);
        Room room = roomRepository.findById(schedule.getRoom().getId()).get();
        Ticket ticket = new Ticket();
        List<Seat> numberSeat1 = new ArrayList<>();
        List<Seat> seats = seatRepository.findAllByRoom(room.getId());
        for (Seat seat : seats) {
            numberSeat1.add(seat);
        }
        for (int i = 0; i < numberSeat1.size(); i++) {
            ticket.setId(UUID.randomUUID().toString());
            Random generator = new Random();
            int value = generator.nextInt((1000 - 1) + 1) + 1;
            ticket.setCode("TK" + value);
            ticket.setSchedule(schedule);
            ticket.setSeat(numberSeat1.get(i));
            repository.save(ticket);
        }
        return null;
    }

    // Đổi trạng thái vé
//    @Scheduled(fixedRate = 60000)
    public void scheduleFixedRate() {
        // danh sách lịch chiếu
        List<Ticket> listTicket = repository.findAll();
        for (Ticket ticket : listTicket) {
//            Schedule schedule = scheduleRepository.findById(ticket.getSchedule().getId());
//            System.out.println(ticket.getSchedule().getId());
//            if (schedule.getStatus() == "Đã chiếu") {
//                ticket.setStatus("Hết hạn");
//                repository.save(ticket);
//            } else {
//                ticket.setStatus("Hạn sử dụng đến: " + schedule.getFinishAt());
//                repository.save(ticket);
//            }
        }
    }

    @Override
    public Ticket update(String id, Ticket ticket) {
        Ticket ticketNew = findById(id);
        ticketNew.setSchedule(ticket.getSchedule());
        ticketNew.setSeat(ticket.getSeat());
        return repository.save(ticketNew);
    }

    @Override
    public Ticket findById(String id) {
        return repository.findById(id).get();
    }

    @Override
    public void delete(String id) {
        repository.delete(findById(id));
    }

    public static void main(String[] args) {
        // Giá ban đầu
        BigDecimal originalPrice = new BigDecimal("100.00");

        // Thời gian suất chiếu
        LocalTime showTime = LocalTime.of(10, 0);

        // Thời gian hiện tại
        LocalTime currentTime = LocalTime.now();

        // Kiểm tra xem nếu là suất chiếu sau 17 giờ
        if (currentTime.isAfter(showTime)) {
            // Tính giá mới với tăng 10%
            BigDecimal newPrice = originalPrice.multiply(BigDecimal.valueOf(1.1));

            // In ra giá mới
            System.out.println("Giá mới: " + newPrice);
        } else {
            // Suất chiếu trước 17 giờ, giá không thay đổi
            System.out.println("Giá không thay đổi: " + originalPrice);
        }
    }

}
