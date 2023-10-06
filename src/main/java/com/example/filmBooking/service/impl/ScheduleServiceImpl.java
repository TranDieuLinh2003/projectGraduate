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
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    public List<Schedule> findByNameContains(String name) {
        return repository.findByNameContains(name);
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
        if (checkScheduleConflict(schedule, schedule.getRoom().getId()) && timeSchedule(schedule) && dateSchedule(schedule.getMovie().getId(), schedule)) {
            // Lưu suất chiếu mới vào cơ sở dữ liệu
            repository.save(schedule);
            System.out.println("Lưu suất chiếu mới thành công.");
        } else {
            System.out.println("Xung đột suất chiếu hoặc suất chiếu nằm ngoài khoảng ngày chiếu của phim. Không thể lưu.");
//            return schedule.getId();
        }
//        suất chiếu phải nằm trong khoảng ngày bắt đầu và kết thúc của phim

//            System.out.println(finishAt);
        return null;

    }

    //        @Scheduled(cron = "* * * * * *")
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

    //check trùng suất chiếu
    public boolean checkScheduleConflict(Schedule newSchedule, String idRoom) {
        // Lấy ra các suất chiếu của phòng
        List<Schedule> roomSchedules = repository.findByRoom(idRoom);
        for (Schedule schedule : roomSchedules) {
            if ( //thời gian bắt đầu của suất chiếu mới trước thời gian kết thúc của suất chiếu cũ
                    roomSchedules != null && newSchedule.getStartAt().isBefore(schedule.getFinishAt()) &&
                            //thời gian bắt đầu của suất chiếu cũ trước thời gian kết thúc của suất chiếu mới
                            schedule.getStartAt().isBefore(newSchedule.getFinishAt())) {
                System.out.println("5");
                return false; // không có xung đột
            }
            System.out.println(roomSchedules);
        }
        System.out.println("6");
        return true; // Có xung đột
    }

    //     Lịch chiếu nằm trong khoảng bắt đầu và kết thúc của phim
    public boolean dateSchedule(String movieId, Schedule schedule) {
        // lấy ra khoảng thời gian phim chiếu
        Movie movie = movieRepository.findById(movieId).get();
        //ngày bắt đầu
        Date premiereDate = movie.getPremiereDate();
        //ngày kết thúc
        Date endDate = movie.getEndDate();
        //lấy ngày chiếu
        LocalDateTime startAt = schedule.getStartAt();
        LocalDateTime finishAt = schedule.getFinishAt();
        //chuyển từ datetime sang date
        Date scheduleDate = Date.from(startAt.atZone(ZoneId.systemDefault()).toInstant());

        //     Lịch chiếu nằm trong khoảng bắt đầu và kết thúc của phim
        if (premiereDate.before(scheduleDate) && scheduleDate.before(endDate)) {
            System.out.println("4");
            return true;
        }
        System.out.println("7");
        return false;


    }

    //     Lịch chiếu nằm trong khoảng bắt đầu và kết thúc của phim
    public boolean timeSchedule(Schedule schedule) {
        LocalDateTime startAt = schedule.getStartAt();
        LocalDateTime finishAt = schedule.getFinishAt();
        LocalTime scheduleStart = LocalTime.from(startAt.atZone(ZoneId.systemDefault()));
        LocalTime scheduleFinish = LocalTime.from(finishAt.atZone(ZoneId.systemDefault()));
        LocalTime startDay = LocalTime.of(8, 0);
        LocalTime endDay = LocalTime.of(2, 0);
        if (scheduleStart.isBefore(endDay) && scheduleFinish.isBefore(endDay)) {
            System.out.println("1");
            return true;
        } else if (scheduleStart.isAfter(startDay) && scheduleFinish.isAfter(startDay)) {
            System.out.println("2");
            return true;
        } else if (scheduleStart.isBefore(endDay) && scheduleFinish.isAfter(startDay)) {
            System.out.println("3");
            return true;
        }
        return false;
    }

//    public List<Schedule> findAll() {
//        List<Schedule> newList = new ArrayList<>();
//        LocalDateTime startTime = LocalDateTime.of(2023, 8, 20, 8, 0); // Thời gian bắt đầu đầu tiên
//        LocalDateTime endTime = LocalDateTime.of(2023, 8, 21, 2, 0); // Thời gian kết thúc
//        LocalDateTime currentStartTime = startTime;
//
//        List<Movie> movieList = movieRepository.findAll(); // Danh sách phim
//        List<Room> roomList = roomRepository.findAll(); // Danh sách phòng chiế
////        roomList.forEach(room -> {
////            movieList.forEach(movie -> {
////                Schedule roomSchedule = new Schedule();
////                roomSchedule.setRoom(room);
////                //process
////                int movieDuration = movie.getMovieDuration(); // Thời lượng phim (tính bằng phút)
////                LocalDateTime currentEndTime = currentStartTime.plusMinutes(movieDuration + 15); // Thời gian kết thúc = thời lượng phim + 15 phút
////
////
////
////                roomSchedule.setFinishAt(currentEndTime);
////                roomSchedule.setMovie(movie);
////                newList.add(roomSchedule);
////                if (currentEndTime.isAfter(endTime)) {
////                    break;
////                }
////            });
////        });
//        for(Room room: roomList){
//            for (Movie movie: movieList){
//                Schedule roomSchedule = new Schedule();
//                roomSchedule.setRoom(room);
//                //process
//                int movieDuration = movie.getMovieDuration(); // Thời lượng phim (tính bằng phút)
//                LocalDateTime currentEndTime = currentStartTime.plusMinutes(movieDuration + 15); // Thời gian kết thúc = thời lượng phim + 15 phút
//                roomSchedule.setStartAt(startTime);
//                roomSchedule.setMovie(movie);
//                roomSchedule.setFinishAt(currentEndTime);
//                newList.add(roomSchedule);
//                if (currentEndTime.isAfter(endTime)) {
//                    break;
//                }
//                currentStartTime = currentEndTime;
//            }
//        }
//        return newList;
//    }

    public List<Schedule> generateSchedule() throws ParseException {
        List<Schedule> newList = new ArrayList<>();
        LocalDateTime startTime = LocalDateTime.of(2023, 8, 20, 8, 0); // Thời gian bắt đầu đầu tiên
        LocalDateTime endTime = LocalDateTime.of(2023, 8, 21, 2, 0); // Thời gian kết thúc
        LocalDateTime currentStartTime = startTime;

        List<Movie> movieList = movieRepository.findAll(); // Danh sách phim
        List<Room> roomList = roomRepository.findAll(); // Danh sách phòng chiếu
        for (Room room : roomList) {
            for (Movie movie : movieList) {
                Schedule schedule = new Schedule();
                schedule.setMovie(movie);
                schedule.setRoom(room);
                schedule.setStartAt(currentStartTime);
                int movieDuration = movie.getMovieDuration(); // Thời lượng phim (tính bằng phút)
                LocalDateTime currentEndTime = currentStartTime.plusMinutes(movieDuration + 15); // Thời gian kết thúc = thời lượng phim + 15 phút

//                if (currentEndTime.isAfter(endTime)) {
//                    break; // Nếu thời gian kết thúc của suất chiếu sau vượt quá thời gian kết thúc của ngày, thoát khỏi vòng lặp
//                }
                schedule.setFinishAt(currentEndTime);

                // Lưu vào cơ sở dữ liệu hoặc thực hiện các xử lý khác tại đây
                System.out.println(schedule);
                save(schedule);
//                newList.add(schedule);
                currentStartTime = currentEndTime; // Lấy thời gian kết thúc của suất chiếu này làm thời gian bắt đầu cho suất chiếu tiếp theo
                newList.add(schedule);
            }
        }
        System.out.println(newList);
        return newList;
    }


}


