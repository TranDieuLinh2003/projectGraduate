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

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Random;


@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository repository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private RoomRepository roomRepository;


    @Override
    public List<Time> layThoiGian() {
//        List<LocalDateTime> times = new ArrayList<LocalDateTime>();
        List<Schedule> listSchedule = findAll();
        for (int i = 0; i < listSchedule.size(); i++) {
            LocalDateTime end = listSchedule.get(i).getFinishAt();
            LocalDateTime start = listSchedule.get(i + 1).getStartAt();
            java.sql.Time startCv = java.sql.Time.valueOf(start.toLocalTime());
            java.sql.Time endCv = java.sql.Time.valueOf(end.toLocalTime());
//            times = repository.layKhoangTrong(startCv, endCv);
            System.out.println(repository.layKhoangTrong(startCv, endCv));
        }
        return null;
    }

    @Override
    public List<Schedule> findAll() {
        return repository.findAll();
    }

    @Override
    public String save(Schedule schedule) throws ParseException {
        //tạo mã suất chiếu
        Random generator = new Random();
        int value = generator.nextInt((100000 - 1) + 1) + 1;
        schedule.setCode("SCD" + value);
        // lấy thông tin phim của suất chiếu
        Movie movie = movieRepository.findById(schedule.getMovie().getId()).get();
        // lấy thông tin phòng chiếu
        Room room = roomRepository.findById(schedule.getRoom().getId()).get();
        //tạo tên suất chiếu = tên phim + tên phòng
        schedule.setName(movie.getName() + "__" + room.getName());
        // tính thời gian kết thúc = thời gian bắt đầu+ thời lượng phim(phút*60000= millisecond) + 900000(15 phút)
        //lấy thời gian bắt đầu
        LocalDateTime startAt = schedule.getStartAt();
        //lấy thời lượng phim( đơn vị phút)
        Integer movieDuration = movie.getMovieDuration();
        Timestamp timestamp = Timestamp.valueOf(startAt);
        timestamp.setTime(timestamp.getTime() + movieDuration * 60000 + 900000);
        LocalDateTime finishAt = timestamp.toLocalDateTime();
        schedule.setFinishAt(finishAt);
        if (checkScheduleConflict(schedule, schedule.getRoom().getId())) {
            // Lưu suất chiếu mới vào cơ sở dữ liệu
            repository.save(schedule);
            System.out.println("Lưu suất chiếu mới thành công.");
        } else {
            System.out.println("Xung đột suất chiếu. Không thể lưu.");
//            return schedule.getId();
        }
//        suất chiếu phải nằm trong khoảng ngày bắt đầu và kết thúc của phim

//            System.out.println(finishAt);
        return null;

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
    public void delete(String id) {
        repository.delete(findById(id));
    }

    public boolean checkScheduleConflict(Schedule newSchedule, String idRoom) {
        // Lấy ra các suất chiếu của phòng
        List<Schedule> roomSchedules = repository.findByRoom(idRoom);
        for (Schedule schedule : roomSchedules) {
            if ( //thời gian bắt đầu của suất chiếu mới trước thời gian kết thúc của suất chiếu cũ
                    roomSchedules != null && newSchedule.getStartAt().isBefore(schedule.getFinishAt()) &&
                            //thời gian bắt đầu của suất chiếu cũ trước thời gian kết thúc của suất chiếu mới
                            schedule.getStartAt().isBefore(newSchedule.getFinishAt())
            ) {
                return false; // không có xung đột
            }
            System.out.println(roomSchedules);
        }
        return true; // Có xung đột
    }
    // ngày bắt đầu suất chiếu mới nằm trong khoảng ngày bắt đầu và kết thúc suất cũ
//                    (schedule.getStartAt().isBefore(newSchedule.getStartAt()) && newSchedule.getStartAt().isBefore(schedule.getFinishAt())
//            ) || (
    // ngày kết thúc suất chiếu mới nằm trong khoảng ngày bắt đầu và kết thúc suất cũ
//            schedule.getStartAt().isBefore(newSchedule.getFinishAt()) && newSchedule.getFinishAt().isBefore(schedule.getFinishAt())
//            )


}


