package com.example.filmBooking.service.impl;

import com.example.filmBooking.model.Movie;
import com.example.filmBooking.model.Room;
import com.example.filmBooking.repository.CinemaRepository;
import com.example.filmBooking.repository.RoomRepository;
import com.example.filmBooking.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;


@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository repository;
    @Autowired
    private CinemaRepository repositoryCinema;

    @Override
    public List<Room> fillAll() {
        return repository.findAll();
    }

    @Override
    public void saveAll(String idCinema, int quantity) {
        for (int i = 1; i < quantity + 1; i++) {
            Random generator = new Random();
            int value = generator.nextInt((100000 - 1) + 1) + 1;
            Room room = new Room();
            room.setId(UUID.randomUUID().toString());
            room.setCode("RM" + value);
            room.setCinema(repositoryCinema.findById(idCinema).get());
            room.setName("Room" + i);
            repository.save(room);
        }
    }

    @Override
    public Room save(Room room) {
        return repository.save(room);
    }

    @Override
    public Room update(String id, Room room) {
        Room customerNew = findById(id);
        customerNew.setName(room.getName());
        customerNew.setDescription(room.getDescription());
        customerNew.setType(room.getType());
        customerNew.setCapacity(room.getCapacity());
        customerNew.setCinema(room.getCinema());
        return repository.save(customerNew);
    }

//    @Scheduled(fixedRate = 6000)
//    public void scheduleFixedRate() {
//        // check danh sách movie
//        List<Room> movies = repository.findAll();
//        for (Room dto : movies) {
//            dto.setCapacity(repository.findNumber(dto.getId()));
//            repository.save(dto);
//        }
//
//    }

    @Override
    public Room findById(String id) {
        return repository.findById(id).get();
    }

    @Override
    public void delete(String id) {
        repository.delete(findById(id));
    }
}
