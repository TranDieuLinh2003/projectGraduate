package com.example.filmBooking.controllerApi;

import com.example.filmBooking.apis.Api;
import com.example.filmBooking.model.Food;
import com.example.filmBooking.model.Room;
import com.example.filmBooking.model.Schedule;
import com.example.filmBooking.model.dto.DtoSeat;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Controller
@RequestMapping("/show/booking")
public class BookingControllerApi {
    @Autowired
    private RestTemplate restTemplate;

    public static String apiGetSchedule = Api.baseURL + "/api/ticket/schedule";
    public static String apiGetSeat = Api.baseURL + "/api/ticket/seat";
    public static String apiGetFoot = Api.baseURL + "/api/ticket/food";

    @GetMapping
    public String displaySchedulePage(@RequestParam String cinemaId,
                                      @RequestParam String movieId,
                                      @RequestParam String startTime,
                                      @RequestParam String startAt,
                                      Model model,
                                      HttpServletRequest request) {
        HttpSession session = request.getSession();
//        session.setAttribute("cinemaId", cinemaId);
//        model.addAttribute("cinemaId", cinemaId);
//
//        session.setAttribute("movieId", movieId);
//        model.addAttribute("movieId", movieId);

        session.setAttribute("startTime", request.getParameter("startTime"));
        model.addAttribute("startTime", startTime);

        session.setAttribute("startAt", request.getParameter("startAt"));
        model.addAttribute("startAt", startAt);

//        System.out.println(start_time);


        //lấy ra lịch chiếu
        String urlTemplateSchdeule = UriComponentsBuilder.fromHttpUrl(apiGetSchedule)
                .queryParam("movieId", "{movieId}")
                .queryParam("cinemaId", "{cinemaId}")
                .queryParam("startTime", "{startTime}")
                .queryParam("startAt", "{startAt}")
                .encode()
                .toUriString();
        Map<String, String> listRequestParam = new HashMap<>();
        listRequestParam.put("movieId", movieId + "");
        listRequestParam.put("cinemaId", cinemaId + "");
        listRequestParam.put("startTime", startTime + "");
        listRequestParam.put("startAt", startAt + "");
        ResponseEntity<Schedule[]> listSchedule = restTemplate.exchange(urlTemplateSchdeule,
                HttpMethod.GET,
                null,
                Schedule[].class,
                listRequestParam);
        model.addAttribute("listSchedule", listSchedule.getBody());
        session.setAttribute("schedule", listSchedule.getBody());

        //lấy ra seat
        String urlTemplateSeat = UriComponentsBuilder.fromHttpUrl(apiGetSeat)
                .queryParam("movieId", "{movieId}")
                .queryParam("cinemaId", "{cinemaId}")
                .queryParam("startTime", "{startTime}")
                .queryParam("startAt", "{startAt}")
                .encode()
                .toUriString();
        ResponseEntity<DtoSeat[]> listSeat = restTemplate.exchange(urlTemplateSeat,
                HttpMethod.GET,
                null,
                DtoSeat[].class,
                listRequestParam);

        DtoSeat[] listSeatDTOS = (DtoSeat[]) listSeat.getBody();
        DtoSeat[] listA = new DtoSeat[8];
        DtoSeat[] listB = new DtoSeat[8];
        DtoSeat[] listC = new DtoSeat[8];
        DtoSeat[] listD = new DtoSeat[8];
        DtoSeat[] listE = new DtoSeat[8];
        for (int i = 0; i <= 7; i++) {
            listA[i] = listSeatDTOS[i];
            listB[i] = listSeatDTOS[i + 8];
            listC[i] = listSeatDTOS[i + 16];
            listD[i] = listSeatDTOS[i + 24];
            listE[i] = listSeatDTOS[i + 32];
        }

        model.addAttribute("listA", listA);
        model.addAttribute("listB", listB);
        model.addAttribute("listC", listC);
        model.addAttribute("listD", listD);
        model.addAttribute("listE", listE);


        //lấy ra room
        String urlTemplateFood= UriComponentsBuilder.fromHttpUrl(apiGetFoot)
                .encode()
                .toUriString();
        HttpEntity<Food[]> listFood = restTemplate.exchange(urlTemplateFood,
                HttpMethod.GET,
                null,
                Food[].class);
        model.addAttribute("listFood", listFood.getBody())    ;
        session.setAttribute("listFood",  listFood.getBody());
        System.out.println(listFood.getBody());

        return "users/DatVe";
    }
}
