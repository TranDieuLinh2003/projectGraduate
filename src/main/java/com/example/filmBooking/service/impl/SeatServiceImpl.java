package com.example.filmBooking.service.impl;


import com.example.filmBooking.model.Room;
import com.example.filmBooking.model.Seat;
import com.example.filmBooking.model.Ticket;
import com.example.filmBooking.model.dto.DtoSeat;
import com.example.filmBooking.model.dto.SeatDTO;
import com.example.filmBooking.repository.*;
import com.example.filmBooking.service.RoomService;
import com.example.filmBooking.service.SeatService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@Service
public class
SeatServiceImpl implements SeatService {
    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private SeatTypeRepository seatTypeRepository;


    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Seat> getAll() {
        return seatRepository.findAll();
    }

    @Override
    public List<Seat> findAllByRoom(String roomId) {
        return seatRepository.findAllByRoom(roomId);
    }

    @Override
    public Seat save(List<String> listLineCodes, List<String> listSeatTypeId, List<Integer> listNumberOfSeatPerLine, String roomId) {
        List<Seat> seatList = new ArrayList<>();
        for (int i = 0; i < listLineCodes.size(); i++) {
            for (int j = 1; j <= listNumberOfSeatPerLine.get(i); j++) {
                Seat seat = new Seat();
                seat.setId(UUID.randomUUID().toString());
                seat.setLine(listLineCodes.get(i));
                seat.setNumber(j);
                seat.setCode(seat.getLine() + seat.getNumber());
                seat.setRoom(roomRepository.findById(roomId).get());
                seat.setSeatType(seatTypeRepository.findById(listSeatTypeId.get(i)).get());
                seat.setStatus(0);
                System.out.println(seat);
                seatList.add(seat);
            }
        }
        seatRepository.saveAll(seatList);
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
    public List<DtoSeat> getSeats(String cinemaId, String movieId, String startAt, String startTime, String nameRoom) {

        List<Seat> listSeat = seatRepository.getSeat(cinemaId, movieId, startAt, startTime, nameRoom);
// Lấy ra các vé đã được đặt trong lịch đó rồi map sang các chỗ ngồi
        List<Seat> occupiedSeats = ticketRepository.findTicketsBySchedule_Id(cinemaId, movieId, startAt, startTime, nameRoom)
                .stream().map(ticket -> ticket.getSeat())
                .collect(Collectors.toList());
//        System.out.println(occupiedSeats);
// Lấy ra các vé
        List<Ticket> tickets = ticketRepository.ticketShow(cinemaId, movieId, startAt, startTime, nameRoom);
//        System.out.println(tickets);
//        tickets.forEach(ticket -> {
//            String ticketId = ticket.getId();
//            System.out.println("Ticket ID: " + ticketId);
//        });
        // Map list chỗ ngồi của phòng ở bước 1 sang list dto
        List<DtoSeat> filteredSeats = listSeat.stream().map(seat -> {
            DtoSeat dtoSeat = modelMapper.map(seat, DtoSeat.class);

            if (occupiedSeats.stream()
                    .map(occupiedSeat -> occupiedSeat.getId())
                    .collect(Collectors.toList()).contains(seat.getId())) {
                dtoSeat.setIsOccupied("1"); // Nếu ghế nào nằm trong list ghế đã được occupied thì set = 1
            }
//            List<Integer> ticketIdsWithSeat = new ArrayList<>();

            for (Ticket ticket : tickets) {
                if (ticket.getSeat().getId() == seat.getId()) {
                    dtoSeat.setTicketId(ticket.getId());
                }
            }
            return dtoSeat;
        }).collect(Collectors.toList());

        return filteredSeats;
    }

    @Override
    public List<DtoSeat> getSeats1(String movieName, String startAt, String nameRoom) {

        List<Seat> listSeat = seatRepository.getSeat1(movieName, startAt, nameRoom);
// Lấy ra các vé đã được đặt trong lịch đó rồi map sang các chỗ ngồi
        List<Seat> occupiedSeats = ticketRepository.findTicketsBySchedule_Id1(movieName, startAt, nameRoom)
                .stream().map(ticket -> ticket.getSeat())
                .collect(Collectors.toList());

        List<Ticket> tickets = ticketRepository.ticketShow1(movieName, startAt, nameRoom);

        // Map list chỗ ngồi của phòng ở bước 1 sang list dto
        List<DtoSeat> filteredSeats = listSeat.stream().map(seat -> {
            DtoSeat seatDTO = modelMapper.map(seat, DtoSeat.class);
            if (occupiedSeats.stream()
                    .map(occupiedSeat -> occupiedSeat.getId())
                    .collect(Collectors.toList()).contains(seat.getId())) {
                seatDTO.setIsOccupied("1"); // Nếu ghế nào nằm trong list ghế đã được occupied thì set = 1
            }
            for (Ticket ticket : tickets) {
                if (ticket.getSeat().getId() == seat.getId()) {
                    seatDTO.setTicketId(ticket.getId());
                }
            }
            return seatDTO;
        }).collect(Collectors.toList());

        return filteredSeats;
    }

    @Override
    public Page<Seat> findAll(Integer currentPage) {
        return seatRepository.findAll(pageSeat(currentPage));
    }

    @Override
    public Pageable pageSeat(Integer pagaNumber) {
        Pageable pageable = PageRequest.of(pagaNumber - 1, 8);
        return pageable;
    }

    @Override
    public Page<Seat> searchByRoom(String id, Integer currentPage) {
        return seatRepository.searchByRoom(id, pageSeat(currentPage));
    }

}
