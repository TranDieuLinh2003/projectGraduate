package com.example.filmBooking.controller;

import com.example.filmBooking.common.ResponseBean;
import com.example.filmBooking.model.Movie;
import com.example.filmBooking.model.Rated;
import com.example.filmBooking.service.MovieService;
import com.example.filmBooking.service.RatedService;
import com.example.filmBooking.util.UploadImage;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/movie")
public class MovieAdminController {
    @Autowired
    private MovieService service;

    @Autowired
    private RatedService ratedService;

    @Autowired
    private UploadImage uploadImage;

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @GetMapping("/find-all")
    public String viewMovie(Model model) {
        return findAll(model, 1, null, null);
    }

    @GetMapping("/find-all/page/{pageNumber}")
    @Operation(summary = "[Hiển thị tất cả]")
    public String findAll(Model model, @PathVariable("pageNumber") Integer pageNumber,
                          @Param("keyword") String keyword,
                          @RequestParam(name = "status", required = false) String status) {
        Page<Movie> page = service.getAll(pageNumber);
        if (status != null && !status.isEmpty()) {
            page = service.findAllByStatus(status, pageNumber);
        } else if (keyword != null) {
            page = service.searchMovie(keyword, pageNumber);
        } else {
            page = service.getAll(pageNumber);
        }

        model.addAttribute("keyword", keyword);
        model.addAttribute("status", status);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listMovie", page.getContent());
//        List<Movie> listMovie = service.findAll();
        List<Rated> ratedId = ratedService.fillAll();
//        model.addAttribute("listMovie", listMovie);
        model.addAttribute("ratedId", ratedId);

        return "admin/movie";
    }

    @GetMapping("/view-add")
    public String viewAdd(Model model) {
        List<Rated> ratedId = ratedService.fillAll();
        model.addAttribute("ratedId", ratedId);
        model.addAttribute("movie", new Movie());
        return "admin/form-add-movie";
    }

    @GetMapping("/detail/{id}")
    public String detailMovie(Model model, @PathVariable(name = "id") String id) {
        Movie movie = service.findById(id);
        model.addAttribute("detailMovie", movie);
        return "admin/chi-tiet-phim";
    }

    @PostMapping("/save")
    @Operation(summary = "[Thêm mới]")
    public String save(Model model,
                       @RequestParam(name = "id") String id,
                       @RequestParam(name = "name") String name,
                       @RequestParam(name = "movieType") String movieType,
                       @RequestParam(name = "languages") String languages,
                       @RequestParam(name = "trailer") String trailer,
                       @RequestParam(name = "performers") String performers,
                       @RequestParam(name = "description") String description,
                       @RequestParam(name = "endDate") Date endDate,
                       @RequestParam(name = "premiereDate") Date permiereDate,
                       @RequestParam(name = "director") String director,
                       @RequestParam(name = "image") MultipartFile multipartFile,
                       @RequestParam(name = "movieDuration") Integer movieDuration,
                       @RequestParam(name = "rated") Rated rated
    ) {
        uploadImage.handerUpLoadFile(multipartFile);
       try{
           Movie movie = Movie.builder()
                   .id(id)
                   .performers(performers)
                   .director(director)
                   .movieType(movieType)
                   .movieDuration(movieDuration)
                   .name(name)
                   .description(description)
                   .languages(languages)
                   .trailer(trailer)
                   .endDate(endDate)
                   .premiereDate(permiereDate)
                   .image(multipartFile.getOriginalFilename())
                   .rated(rated)
                   .build();
           if(service.save(movie) instanceof  Movie){
               model.addAttribute("thanhCong", "Thêm thành công!");
           }else {
               model.addAttribute("thatBai", "Thêm thất bại");
           }
           model.addAttribute("movie", new Movie());
           return "redirect:/movie/find-all";
       }catch (Exception e){
           e.printStackTrace();
           return "admin/movie";
       }
    }

    @GetMapping("/delete/{id}")
    public String deleteMovie(@PathVariable(name = "id") String id) {
        service.delete(id);
        return "redirect:/movie/find-all";
    }

    @GetMapping("/update/{pageNumber}/{id}")
    public String detailMovie(@PathVariable(name = "id") String id, Model model, @PathVariable("pageNumber") Integer currentPage) {
        List<Rated> ratedId = ratedService.fillAll();
        model.addAttribute("ratedId", ratedId);
        model.addAttribute("movie", service.findById(id));
        return "admin/form-add-movie";
    }

    //    @GetMapping("/search-movie/{pageNumber}")
//    public String searchMovie(Model model, @RequestParam(name = "keyword") String keyword, @PathVariable("pageNumber") Integer currentPage) {
//        Page<Movie> page = service.searchMovie(keyword, currentPage);
//        model.addAttribute("keyword", keyword);
//        model.addAttribute("currentPage", currentPage);
//        model.addAttribute("totalPages", page.getTotalPages());
//        model.addAttribute("totalItems", page.getTotalElements());
//        model.addAttribute("listMovie", page.getContent());
//        model.addAttribute("movie", new Movie());
//        return "admin/movie";
//    }
    @GetMapping("/checkDuplicateName")
    public ResponseEntity<Map<String, Boolean>> checkDuplicateName(@RequestParam("name") String name) {
        boolean isDuplicate = service.findByName(name) != null;
        Map<String, Boolean> response = new HashMap<>();
        response.put("isDuplicate", isDuplicate);
        return ResponseEntity.ok(response);
    }
}