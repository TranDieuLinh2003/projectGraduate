package com.example.filmBooking.service.impl;


import com.example.filmBooking.model.Room;
import com.example.filmBooking.model.Seat;
import com.example.filmBooking.model.SeatType;
import com.example.filmBooking.repository.RoomRepository;
import com.example.filmBooking.repository.SeatRepository;
import com.example.filmBooking.repository.SeatTypeRepository;
import com.example.filmBooking.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class SeatServiceImpl implements SeatService {
    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private SeatTypeRepository seatTypeRepository;
    @Autowired
    private RoomRepository roomRepository;


    @Override
    public List<Seat> getAll() {
        return seatRepository.findAll();
    }

    @Override
    public Seat save(Integer lineNumber, Integer number, String idRoom) {
        //Nhập số hàng ghế
        //Nhập số lượng ghế/hàng
        //Lấy ra thông tin loại ghế
        SeatType seatType = seatTypeRepository.findById(idSeatType).get();
        //Tạo mảng line
        List<Character> listLine = new ArrayList<>();
        //Thêm dữ liệu vào line
        for (char i = 'A'; i <= 'Z'; i++) {
            listLine.add(i);
        }
        char line = listLine.get(lineNumber);
        Seat seat = new Seat();
        for (char i = 'A'; i <= line - 1; i++) {
            for (int j = 1; j <= number; j++) {
                seat.setId(UUID.randomUUID().toString());
                seat.setRoom(roomRepository.findById(idRoom).get());
                seat.setCode(i + "" + j);
                seat.setLine(i + "");
                seat.setNumber(j);
                seatRepository.save(seat);
            }
        }
        Room room = roomRepository.findById(idRoom).get();
        room.setCapacity(roomRepository.findNumber(room.getId()));
        roomRepository.save(room);
        return null;
    }

    @Override
    public Seat update(String id, Seat seat) {
        Seat seatNew = findById(id);
        seatNew.setNumber(seat.getNumber());
        seatNew.setLine(seat.getLine());
        seatNew.setStatus(seat.getStatus());
        seatNew.setDescription(seat.getDescription());
        return seatRepository.save(seatNew);
    }

    @Override
    public void delete(String id) {
        seatRepository.delete(findById(id));
    }

    @Override
    public Seat findById(String id) {
        return seatRepository.findById(id).get();
    }
}
