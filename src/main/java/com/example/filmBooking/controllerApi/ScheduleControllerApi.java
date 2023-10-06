package com.example.filmBooking.controllerApi;

import com.example.filmBooking.apis.Api;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/schedule/api")
public class ScheduleControllerApi {
    @Autowired
    private RestTemplate restTemplate;

    public static String API_GET_START_TIMES = Api.baseURL+"/api/schedule/time-at";
    public static String API_GET_START_AT = Api.baseURL+"/api/schedule/startAt-at";
    @GetMapping
    public String displaySchedulePage(@RequestParam String movieId, @RequestParam String cinemaId, Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("cinemaId",cinemaId);

        // Gắn access token jwt vào header để gửi kèm request
        HttpHeaders headers = new HttpHeaders();
//        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
//        JwtResponseDTO jwtResponseDTO = (JwtResponseDTO)session.getAttribute("jwtResponse");
//        headers.set(HttpHeaders.AUTHORIZATION,"Bearer "+jwtResponseDTO.getAccessToken());
        HttpEntity<?> entity = new HttpEntity<>(headers);

        // Lấy ra ngày
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(API_GET_START_AT)
                .queryParam("movieId", "{movieId}")
                .queryParam("branchId","{branchId}")
                .encode()
                .toUriString();
        Map<String,String> liststarAt = new HashMap<>();
        liststarAt.put("movieId", movieId+"");
        liststarAt.put("branchId",cinemaId+"");
        ResponseEntity<String[]> listStart = restTemplate.exchange(urlTemplate,
                HttpMethod.GET,entity,String[].class,liststarAt);

        //Lấy ra những thời điểm bắt đầu tính từ hôm nay:
        String urlTemplate1 = UriComponentsBuilder.fromHttpUrl(API_GET_START_TIMES)
                .queryParam("movieId", "{movieId}")
                .queryParam("branchId","{branchId}")
                .encode()
                .toUriString();
        Map<String,String> listRequestParam = new HashMap<>();
        listRequestParam.put("movieId", movieId+"");
        listRequestParam.put("branchId",cinemaId+"");


        ResponseEntity<String[]> listStartTimesEntity = restTemplate.exchange(urlTemplate1,
                HttpMethod.GET,entity,String[].class,listRequestParam);

        model.addAttribute("listDates", listStart.getBody());
        model.addAttribute("listStartTimes",listStartTimesEntity.getBody());
//        model.addAttribute("user",new User());
        return "users/Schedule";
    }
}
