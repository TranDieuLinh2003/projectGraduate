package com.example.filmBooking.service.impl;

import com.example.filmBooking.model.Movie;
import com.example.filmBooking.model.Room;
import com.example.filmBooking.model.Schedule;
import com.example.filmBooking.repository.MovieRepository;
import com.example.filmBooking.repository.RoomRepository;
import com.example.filmBooking.repository.ScheduleRepository;
import com.example.filmBooking.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository repository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private RoomRepository roomRepository;

//    public static void main(String[] args) {
//        ScheduleServiceImpl scheduleService = new ScheduleServiceImpl();
//        scheduleService.handleDatetime(null, 0);
//
//    }

    @Override
    public List<Schedule> fillAll() {
        return repository.findAll();
    }

    @Override
    public Schedule save(Schedule schedule) {
        Random generator = new Random();
        int value = generator.nextInt((100000 - 1) + 1) + 1;
        schedule.setCode("SCD" + value);
        Movie movie = movieRepository.findById(schedule.getMovie().getId()).get();
        Room room = roomRepository.findById(schedule.getRoom().getId()).get();
        schedule.setName(movie.getName() + "__" + room.getName());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        //lấy thời gian bắt đầu
        LocalDateTime startAt = schedule.getStartAt();
        //lấy thời gian kết thúc
        LocalDateTime finishAt = schedule.getFinishAt();
        //lấy thời lượng phim( đơn vị phút)
        Integer movieDuration = movie.getMovieDuration();
        Timestamp timestamp = Timestamp.valueOf(startAt);
//        Set thời gian kết thúc= thời gian bắt đầu+ thời lượng phim(phút*60000= millisecond) + 900000(15 phút)
        timestamp.setTime(timestamp.getTime() + movieDuration * 60000 + 900000);
        finishAt = timestamp.toLocalDateTime();
        schedule.setFinishAt(finishAt);
        System.out.println(finishAt);
        return repository.save(schedule);
//        return null;
    }

//    private void handleDatetime(LocalDateTime startAt, Integer movieDuration) {
////
//        DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
//        LocalDateTime now = LocalDateTime.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
//        String formattedString = now.format(formatter);
////        startAt.getHour();
//
//        Timestamp timestamp = Timestamp.valueOf(now);
//
//        timestamp.setTime(timestamp.getTime() + 7200000 + 3540000);
//        now = timestamp.toLocalDateTime();
//
//        String formattedString1 = now.format(CUSTOM_FORMATTER);
//        System.out.println(formattedString);
//        System.out.println(now);
//        System.out.println(timestamp.getTime() + 7200000 + 3540000);
//        System.out.println(formattedString1);
//
////
//    }

    @Override
    public Schedule update(UUID id, Schedule schedule) {
        Schedule scheduleNew = findById(id);
        scheduleNew.setRoom(schedule.getRoom());
        scheduleNew.setMovie(schedule.getMovie());
        scheduleNew.setName(schedule.getName());
        scheduleNew.setStartAt(schedule.getStartAt());
        Movie movie = movieRepository.findById(schedule.getMovie().getId()).get();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        //lấy thời gian bắt đầu
        LocalDateTime startAt = schedule.getStartAt();
        //lấy thời gian kết thúc
        LocalDateTime finishAt = schedule.getFinishAt();
        //lấy thời lượng phim( đơn vị phút)
        Integer movieDuration = movie.getMovieDuration();
        Timestamp timestamp = Timestamp.valueOf(startAt);
//        Set thời gian kết thúc= thời gian bắt đầu+ thời lượng phim(phút*60000= millisecond) + 900000(15 phút)
        timestamp.setTime(timestamp.getTime() + movieDuration * 60000 + 900000);
        finishAt = timestamp.toLocalDateTime();
        scheduleNew.setFinishAt(finishAt);
        return repository.save(scheduleNew);
    }

    @Override
    public Schedule findById(UUID id) {
        return repository.findById(id).get();
    }

    @Override
    public void delete(UUID id) {
        repository.delete(findById(id));
    }
}
