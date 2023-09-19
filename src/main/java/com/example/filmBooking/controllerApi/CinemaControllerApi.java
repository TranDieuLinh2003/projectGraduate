package com.example.filmBooking.controllerApi;

import com.example.filmBooking.apis.Api;
import com.example.filmBooking.model.Cinema;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/cinema/api")
public class CinemaControllerApi {
    @Autowired
    private RestTemplate restTemplate;

    public static String apiGetBranches = Api.baseURL+"/api/cinema";
    @GetMapping
    public String displayBranchesPage(@RequestParam UUID movieId, Model model, HttpServletRequest request){
        // Gắn movie id vào session lát sau dùng tiếp để tìm ra lịch xem cụ thể dựa trên movie id đó
        HttpSession session = request.getSession();
        session.setAttribute("movieId",movieId);

        // Gắn access token jwt vào header để gửi kèm request
        HttpHeaders headers = new HttpHeaders();
//        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
//        JwtResponseDTO jwtResponseDTO = (JwtResponseDTO)session.getAttribute("jwtResponse");
//        headers.set(HttpHeaders.AUTHORIZATION,"Bearer "+jwtResponseDTO.getAccessToken());
        HttpEntity<?> entity = new HttpEntity<>(headers);

        // Truyền tham số movieId vào query string rồi gửi request
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(apiGetBranches)
                .queryParam("movieId", "{movieId}")
                .encode()
                .toUriString();
        Map<String, UUID> params = new HashMap<>();
        params.put("movieId", movieId);

        HttpEntity<Cinema[]> response = restTemplate.exchange(
                urlTemplate,
                HttpMethod.GET,
                entity,
                Cinema[].class,
                params
        );
        Cinema[] cinema = response.getBody();
        model.addAttribute("cinema",cinema);

        return "users/FilmBooking";

    }

}
