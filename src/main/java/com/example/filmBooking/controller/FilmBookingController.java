package com.example.filmBooking.controller;

import com.example.filmBooking.model.*;
import com.example.filmBooking.model.dto.DtoMovie;
import com.example.filmBooking.repository.*;
import com.example.filmBooking.service.impl.*;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/filmbooking")
@SessionAttributes("soldTicketsCountBill")
public class FilmBookingController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MovieServiceImpl service;

    @Autowired
    private CinemaServiceImpl cinemaService;

    @Autowired
    private ScheduleRepository repository1;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private RankCustomerServiceImpl rankCustomerService;

    @Autowired
    private GeneralSettingRepository generalSettingRepository;

    @GetMapping("/trangchu")
    public String getAllPosts(Model model, HttpServletRequest request) {
//        String soldTicketsCount = billRepository.countSoldTicketsWithStatusZero();?
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("customer");
        model.addAttribute("customer", customer);
        if (customer == null) {
            // Phim đang chiếu
            List<DtoMovie> listmovie = (List<DtoMovie>) service.showPhimDangChieu().stream().map(movie -> modelMapper.map(movie, DtoMovie.class)).collect(Collectors.toList());
            model.addAttribute("listmovie", listmovie);
            // Phim sắp chiếu
            List<DtoMovie> listmovie1 = (List<DtoMovie>) service.showPhimSapChieu().stream().map(movie -> modelMapper.map(movie, DtoMovie.class)).collect(Collectors.toList());
            model.addAttribute("listmovie1", listmovie1);
        } else {
            List<DtoMovie> listmovie = (List<DtoMovie>) service.showPhimDangChieu().stream().map(movie -> modelMapper.map(movie, DtoMovie.class)).collect(Collectors.toList());
            model.addAttribute("listmovie", listmovie);
            // Phim sắp chiếu
            List<DtoMovie> listmovie1 = (List<DtoMovie>) service.showPhimSapChieu().stream().map(movie -> modelMapper.map(movie, DtoMovie.class)).collect(Collectors.toList());
            model.addAttribute("listmovie1", listmovie1);
            String soldTicketsCountBill = billRepository.countSoldTicket(customer.getId());
            model.addAttribute("soldTicketsCountBill", soldTicketsCountBill);
        }

        return "users/FilmBooking";
    }

    @GetMapping("/movie/edit/{id}")
    public String chiTietPhim(@PathVariable("id") String id, Model model) {
        Movie movie = service.findById(id);
        model.addAttribute("movie", movie);

        return "users/ChiTietPhim";

    }


    @GetMapping("/phimchieu")
    public String showPhimChieu(Model model, HttpServletRequest request) {

        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("customer");
        model.addAttribute("customer", customer);
        System.out.println(customer);
        List<DtoMovie> listmovie = (List<DtoMovie>) service.showPhimDangChieu().stream().map(movie -> modelMapper.map(movie, DtoMovie.class)).collect(Collectors.toList());
        model.addAttribute("listmovie", listmovie);

//      phim sap chiếu
        List<DtoMovie> listmovie1 = (List<DtoMovie>) service.showPhimSapChieu().stream().map(movie -> modelMapper.map(movie, DtoMovie.class)).collect(Collectors.toList());
        model.addAttribute("listmovie1", listmovie1);
        return "users/Phim";
    }

    @GetMapping("/lichchieu")
    public String showLichChieu(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("customer");
        model.addAttribute("customer", customer);


        List<Cinema> listcinema = (List<Cinema>) cinemaService.fillAll();
        model.addAttribute("listcinema", listcinema);

        List<DtoMovie> listmovie = (List<DtoMovie>) service.showPhishowPhimSapChieuAndDangChieumSapChieu().stream().map(movie -> modelMapper.map(movie, DtoMovie.class)).collect(Collectors.toList());
        model.addAttribute("listmovie", listmovie);
        return "users/LichChieu";
    }

    @GetMapping("/chitietphim")
    public String showChiTietPhim() {

        return "users/ChiTietPhim";
    }


    @GetMapping("/search/schedule")
    public String getSchedule(Model model,
                              @RequestParam(value = "name", required = false) String name,
                              @DateTimeFormat(pattern = "dd/MM/yyyy") @RequestParam(value = "nameMovie", required = false) String nameMovie,
                              @RequestParam(value = "startAt", required = false) LocalDate startAt,
                              @RequestParam(value = "startTime", required = false) Integer startTime,
                              @RequestParam(value = "endTime", required = false) Integer endTime) {
        name = Strings.isEmpty(name) ? null : name;
        nameMovie = Strings.isEmpty(nameMovie) ? null : nameMovie;
//        if (name != null || nameMovie != null || startAt != null || startTime != null || endTime != null) {
//            scheduleService.searchSchedule(name, startAt, nameMovie, startTime, endTime);
//        }
        List<Cinema> listcinema = (List<Cinema>) cinemaService.fillAll();
        model.addAttribute("listcinema", listcinema);
        List<DtoMovie> listmovie = (List<DtoMovie>) service.showPhishowPhimSapChieuAndDangChieumSapChieu().stream().map(movie
                -> modelMapper.map(movie, DtoMovie.class)).collect(Collectors.toList());
        model.addAttribute("listmovie", listmovie);
        List<Schedule> allSuatChieu = repository1.findByConditions(name, startAt, nameMovie, startTime, endTime);
        Map<String, Map<String, List<LocalDateTime>>> suatChieuMap = new HashMap<>();
        for (Schedule suatChieu : allSuatChieu) {
            String tenPhim = suatChieu.getMovie().getName();
            String theloai = String.valueOf(suatChieu.getMovie().getMovieTypes());
            Integer thoiluong = suatChieu.getMovie().getMovieDuration();
            String img = suatChieu.getMovie().getImage();
            String combinedKey = tenPhim + "_" + theloai + "_" + thoiluong + "_" + img; // Create a combined key by concatenating the two keys
            String phongChieu = suatChieu.getRoom().getName();
            String rapchieu = suatChieu.getRoom().getCinema().getName();
            LocalDateTime gioChieu = suatChieu.getStartAt();

            if (!suatChieuMap.containsKey(combinedKey)) {
                suatChieuMap.put(combinedKey, new HashMap<>());
            }

            Map<String, List<LocalDateTime>> phongChieuMap = suatChieuMap.get(combinedKey);

            if (!phongChieuMap.containsKey(phongChieu)) {
                phongChieuMap.put(phongChieu, new ArrayList<>());
            }

            List<LocalDateTime> gioChieuList = phongChieuMap.get(phongChieu);
            gioChieuList.add(gioChieu);

        }
        for (Map<String, List<LocalDateTime>> phongChieuMap : suatChieuMap.values()) {
            for (List<LocalDateTime> gioChieuList : phongChieuMap.values()) {
                Collections.sort(gioChieuList);
            }
        }
        model.addAttribute("suatChieuMap", suatChieuMap);
        return "users/timkiem";
    }

    @GetMapping("/rank-and-membership")
    public String rankAndMembership(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("customer");
        List<GeneralSetting> generalSettingList = generalSettingRepository.findAll();
        List<RankCustomer> rankCustomerList = rankCustomerService.fillAll();
        Collections.sort(rankCustomerList, new RankCustomerComparator());
        model.addAttribute("customer", customer);
        model.addAttribute("rankCustomerList", rankCustomerList);
        model.addAttribute("generalSettingList", generalSettingList);
        return "users/rankAndMembership";
    }

}

