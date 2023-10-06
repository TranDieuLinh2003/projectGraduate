package com.example.filmBooking.controllerApi;

import com.example.filmBooking.apis.Api;
import com.example.filmBooking.model.Cinema;
import com.example.filmBooking.model.Movie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Controller
@RequestMapping("/lichchieu")
public class ScheduleControllerApi {
    @Autowired
    private RestTemplate restTemplate;

    public static String API_GET_START_AT = Api.baseURL + "/api/schedule/start_at";
    public static String apiGetCinema = Api.baseURL + "/api/schedule/cinema_name";
    public static String apiGetMovie = Api.baseURL + "/api/schedule/movie_name";
    public static String apiGetTime = Api.baseURL + "/api/schedule/time";

    @GetMapping
    public String displaySchedulePage(@RequestParam String movieId, @RequestParam String cinemaId, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("cinemaId", cinemaId);
        model.addAttribute("cinemaId", cinemaId);

        session.setAttribute("movieId", movieId);
        model.addAttribute("movieId", movieId);
        // Gắn access token jwt vào header để gửi kèm request
        HttpHeaders headers = new HttpHeaders();
//        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
//        JwtResponseDTO jwtResponseDTO = (JwtResponseDTO)session.getAttribute("jwtResponse");
//        headers.set(HttpHeaders.AUTHORIZATION,"Bearer "+jwtResponseDTO.getAccessToken());
        HttpEntity<?> entity = new HttpEntity<>(headers);


        //lấy ra ngày chiếu phim
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(API_GET_START_AT)
                .queryParam("movieId", "{movieId}")
                .queryParam("cinemaId", "{cinemaId}")
                .encode()
                .toUriString();
        Map<String, String> listRequestParam = new HashMap<>();
        listRequestParam.put("movieId", movieId + "");
        listRequestParam.put("cinemaId", cinemaId + "");

        ResponseEntity<String[]> listStartAtEntity = restTemplate.exchange(urlTemplate,
                HttpMethod.GET,
                entity,
                String[].class,
                listRequestParam);
        List<String> listStartAt = Arrays.asList(listStartAtEntity.getBody());
        model.addAttribute("listStartAt", listStartAt);


//       lấy ra  giờ phim
        String urlTemplateTime = UriComponentsBuilder.fromHttpUrl(apiGetTime)
                .queryParam("movieId", "{movieId}")
                .queryParam("cinemaId", "{cinemaId}")
                .queryParam("start_at", "{start_at}")
                .encode()
                .toUriString();

            for (String ngay : listStartAt) {
                System.out.println(ngay);
                listRequestParam.put("start_at", ngay+"");
                model.addAttribute("start_at", ngay);
//                break;
//                continue;
            }
        ResponseEntity<String[]> listStartTimesEntity = restTemplate.exchange(
                urlTemplateTime,
                HttpMethod.GET,
                entity,
                String[].class,
                listRequestParam);
        List<String> listTime = Arrays.asList(listStartTimesEntity.getBody());
        model.addAttribute("listTime", listTime);


//        lấy ra rap
        String urlTemplate1 = UriComponentsBuilder.fromHttpUrl(apiGetCinema)
                .queryParam("movieId", "{movieId}")
                .queryParam("cinemaId", "{cinemaId}")
                .encode()
                .toUriString();
        HttpEntity<Cinema[]> responsecinema = restTemplate.exchange(
                urlTemplate1,
                HttpMethod.GET,
                null,
                Cinema[].class,
                listRequestParam
        );
        Cinema[] listcinema = responsecinema.getBody();
        model.addAttribute("listcinema", listcinema);


//        lấy ra movie
        String urlTemplate2 = UriComponentsBuilder.fromHttpUrl(apiGetMovie)
                .queryParam("movieId", "{movieId}")
                .queryParam("cinemaId", "{cinemaId}")
                .encode()
                .toUriString();
        HttpEntity<Movie[]> responseMovie = restTemplate.exchange(
                urlTemplate2,
                HttpMethod.GET,
                null,
                Movie[].class,
                listRequestParam
        );
        Movie[] listmovie = responseMovie.getBody();
        model.addAttribute("listmovie", listmovie);
        return "users/Schedule";
    }


}
