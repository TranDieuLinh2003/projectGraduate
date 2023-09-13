package com.example.filmBooking.service.impl;

import com.example.filmBooking.model.Movie;
import com.example.filmBooking.model.Room;
import com.example.filmBooking.model.Schedule;
import com.example.filmBooking.repository.MovieRepository;
import com.example.filmBooking.repository.RoomRepository;
import com.example.filmBooking.repository.ScheduleRepository;
import com.example.filmBooking.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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

    @Override
    public List<Schedule> findAll() {
        return repository.findAll();
    }

    @Override
    public UUID save(Schedule schedule) {
        //Tạo lịch chiếu
        //random mã lịch
        Random generator = new Random();
        int value = generator.nextInt((100000 - 1) + 1) + 1;
        schedule.setCode("SCD" + value);
        Movie movie = movieRepository.findById(schedule.getMovie().getId()).get();
        Room room = roomRepository.findById(schedule.getRoom().getId()).get();
        schedule.setName(movie.getName() + "__" + room.getName());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        //lấy ngày hiện tại
        LocalDateTime localDateTime = LocalDateTime.now();
//        ZonedDateTime zdt = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
//        long date = zdt.toInstant().toEpochMilli();
        //lấy thời gian bắt đầu
        LocalDateTime startAt = schedule.getStartAt();
//        ZonedDateTime zdt1 = ZonedDateTime.of(startAt, ZoneId.systemDefault());
//        long date1 = zdt1.toInstant().toEpochMilli();
        //lấy thời gian kết thúc
        LocalDateTime finishAt = schedule.getFinishAt();
//        ZonedDateTime zdt2 = ZonedDateTime.of(finishAt, ZoneId.systemDefault());
//        long date2 = zdt2.toInstant().toEpochMilli();
        //lấy thời lượng phim( đơn vị phút)
        Integer movieDuration = movie.getMovieDuration();
        Timestamp timestamp = Timestamp.valueOf(startAt);
//        Set thời gian kết thúc= thời gian bắt đầu+ thời lượng phim(phút*60000= millisecond) + 900000(15 phút)
        timestamp.setTime(timestamp.getTime() + movieDuration * 60000 + 900000);
        finishAt = timestamp.toLocalDateTime();
        //set trạng thái lịch chiếu
//        if (date1 > date) {
//            schedule.setStatus("Sắp chiếu");
//            repository.save(schedule);
//        } else if (date2 <= date) {
//            schedule.setStatus("Đã chiếu");
//            repository.save(schedule);
//        } else {
//            schedule.setStatus("Đang chiếu");
//            repository.save(schedule);
//        }
//        scheduleFixedRate();
        schedule.setFinishAt(finishAt);
        System.out.println(finishAt);
        repository.save(schedule);
        return schedule.getId();
        return repository.save(schedule);
//        return null;
    }

    //    @Scheduled(fixedRate = 60000)

//    @Scheduled(cron = "* * * * * *")
    public void scheduleFixedRate() {
        // danh sách lịch chiếu
        List<Schedule> listSchedule = repository.findAll();
        // ngày hiện tai
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zdt = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        long date = zdt.toInstant().toEpochMilli();
        System.out.println(date);
        for (Schedule schedule : listSchedule) {
            //lấy thời gian bắt đầu
            LocalDateTime startAt = schedule.getStartAt();
            ZonedDateTime zdt1 = ZonedDateTime.of(startAt, ZoneId.systemDefault());
            long dateStartAt = zdt1.toInstant().toEpochMilli();
            //lấy thời gian kết thúc
            LocalDateTime finishAt = schedule.getFinishAt();
            ZonedDateTime zdt2 = ZonedDateTime.of(finishAt, ZoneId.systemDefault());
            long dateFinishAt = zdt2.toInstant().toEpochMilli();

            if (dateStartAt > date) {
                schedule.setStatus("Sắp chiếu");
                repository.save(schedule);
            } else if (dateFinishAt <= date) {
                schedule.setStatus("Đã chiếu");
                repository.save(schedule);
            } else {
                schedule.setStatus("Đang chiếu");
                repository.save(schedule);
            }
        }
    }


    // Lịch chiếu nằm trong khoảng bắt đầu và kết thúc của phim
//    public void timeSchedule(LocalDateTime premiereDate, LocalDateTime endTime){
//        for (premiereDate.get(); i < ; i++) {3
//
//        }
//    }
    @Override
    public Schedule update(UUID id, Schedule schedule) {
        Movie movie = movieRepository.findById(schedule.getMovie().getId()).get();
        Schedule scheduleNew = findById(id);
        scheduleNew.setRoom(schedule.getRoom());
        scheduleNew.setMovie(schedule.getMovie());
        scheduleNew.setName(schedule.getName());
        scheduleNew.setStartAt(schedule.getStartAt());
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
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
