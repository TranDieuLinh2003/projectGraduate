package com.example.filmBooking.service.impl;

import com.example.filmBooking.model.Movie;
import com.example.filmBooking.model.Room;
import com.example.filmBooking.model.Schedule;
import com.example.filmBooking.model.dto.ScheduleDto;
import com.example.filmBooking.repository.MovieRepository;
import com.example.filmBooking.repository.RoomRepository;
import com.example.filmBooking.repository.ScheduleRepository;
import com.example.filmBooking.service.ScheduleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.swing.text.DateFormatter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository repository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Schedule> findAll() {
        return repository.findAll();
    }

    @Override
    public String save(Schedule schedule) throws ParseException {
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
        //suất chiếu phải nằm trong khoảng ngày bắt đầu và kết thúc của phim
//        String date = schedule.getStartAt().format(formatter);
//        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
//        if (date1.before(movie.getPremiereDate()) && date1.after(movie.getEndDate())) {
//            System.out.println("lỗi rồi má oi");
//        } else {
//            System.out.println("Thêm được nhe");
            schedule.setFinishAt(finishAt);
            System.out.println(finishAt);
            repository.save(schedule);
//        }
      
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

        return schedule.getId();
    }

   
    //    @Scheduled(cron = "* * * * * *")
    public void scheduleFixedRate() {
        // danh sách lịch chiếu
        List<Schedule> listSchedule = repository.findAll();
        // ngày hiện tại
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

    //     Lịch chiếu nằm trong khoảng bắt đầu và kết thúc của phim
    public void timeSchedule(String movieId) {
        // lấy ra khoảng thời gian phim chiếu
        Movie movie = movieRepository.findById(movieId).get();
        //ngày bắt đầu
        Date premiereDate = movie.getPremiereDate();
        //ngày kết thúc
        Date endDate = movie.getEndDate();
        //lấy ngày chiếu
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        for (Schedule schedule : repository.findAll()
//        ) {
//        Schedule schedule = findById(String.fromString("0e0dc221-764f-4959-bd26-2024b1a8ab6f"));
//        String date = schedule.getStartAt().format(formatter);
//
//            if()
//        System.out.println("rhioehgihe" + date);
    }

//    }
    @Override
    public Schedule update(String id, Schedule schedule) {
        Movie movie = movieRepository.findById(schedule.getMovie().getId()).get();
        Schedule scheduleNew = findById(id);
        scheduleNew.setRoom(schedule.getRoom());
        scheduleNew.setMovie(schedule.getMovie());
        scheduleNew.setName(schedule.getName());
        scheduleNew.setStartAt(schedule.getStartAt());
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
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
    public Schedule findById(String id) {
        return repository.findById(id).get();
    }

    @Override
    public List<String> getTimeAt(String movieId, String cinemaId ) {
        return repository.getstartAtAndFinishAt(movieId,cinemaId)
                .stream().map(localTime -> localTime.format(DateTimeFormatter.ofPattern("HH:mm")))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getStartAt(String movieId, String cinemaId ) {
        return repository.getstartAtAndFinishAt(movieId,cinemaId)
                .stream().map(localDateTime -> localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .collect(Collectors.toList());
    }
    @Override
    public List<String> getFinishAt(String movieId, String cinemaId ) {
        return repository.getstartAtAndFinishAt(movieId,cinemaId)
                .stream().map(localDateTime -> localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .collect(Collectors.toList());
    }
//
//    @Override
//    public List<ScheduleDto> getSchedules(String movieId, String cinemaId, String startAt,  String roomId) {
//        return repository.getSchedulesByMovie_IdAndCinema_IdAndStartDateAndStartTimeAndRoom_Id(movieId,cinemaId
//                , LocalDateTime.parse(startAt), roomId)
//                .stream().map(schedule -> modelMapper.map(schedule,ScheduleDto.class))
//                .collect(Collectors.toList());
//    }

    @Override
    public void delete(String id) {
        repository.delete(findById(id));
    }
}
