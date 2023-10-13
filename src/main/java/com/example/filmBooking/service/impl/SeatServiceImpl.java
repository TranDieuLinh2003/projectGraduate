package com.example.filmBooking.service.impl;


import com.example.filmBooking.model.Room;
import com.example.filmBooking.model.Seat;
import com.example.filmBooking.model.dto.DtoSeat;
import com.example.filmBooking.model.dto.SeatDTO;
import com.example.filmBooking.repository.RoomRepository;
import com.example.filmBooking.repository.ScheduleRepository;
import com.example.filmBooking.repository.SeatRepository;
import com.example.filmBooking.repository.TicketRepository;
import com.example.filmBooking.service.SeatService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class SeatServiceImpl implements SeatService {
    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Seat> getAll() {
        return seatRepository.findAll();
    }

    @Override
    public Seat save(Integer lineNumber, Integer number, String idRoom) {
        //Nhập số hàng ghế
        //Nhập số lượng ghế/hàng
        //Lấy ra thông tin loại ghế
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
    
    @Override
    public List<SeatDTO> getSeatsByScheduleId(String scheduleId) {
        Room room = scheduleRepository.getById(scheduleId).getRoom();

        List<Seat> listSeat = seatRepository.getSeatByRoomId(room.getId());

        List<Seat> occupiedSeats = ticketRepository.findTicketByScheduleId(scheduleId)
                .stream().map(ticket -> ticket.getSeat())
                .collect(Collectors.toList());
        List<SeatDTO> filteredSeats = listSeat.stream().map(seat -> {
                    SeatDTO seatDTO = modelMapper.map(seat, SeatDTO.class);
                    if (occupiedSeats.stream()
                            .map(occupiedSeat -> occupiedSeat.getId())
                            .collect(Collectors.toList()).contains(seat.getId())) {
                        seatDTO.setIsOccupied(1);
                    }
                    return seatDTO;
                }
        ).collect(Collectors.toList());
        return filteredSeats;
    }

    @Override
    public List<Object[]> getSeatsByCustomerId(String customerId) {
        return seatRepository.findSeatsByCustomerId(customerId);
    }

    @Override
    public List<DtoSeat> getSeats(String cinemaId, String movieId, String startAt, String startTime) {
        return seatRepository.getSeat(cinemaId, movieId, startAt, startTime).stream().map(seat -> modelMapper.map(seat, DtoSeat.class))
                .collect(Collectors.toList());
    }
}
