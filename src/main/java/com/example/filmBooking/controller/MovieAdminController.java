package com.example.filmBooking.controller;

import com.example.filmBooking.common.ResponseBean;
import com.example.filmBooking.model.*;
import com.example.filmBooking.service.*;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/movie")
@SessionAttributes("soldTicketsCount")

public class MovieAdminController {
    @Autowired
    private MovieService service;

    @Autowired
    private RatedService ratedService;

    @Autowired
    private DirectorService directorService;

    @Autowired
    private LanguageService languageService;

    @Autowired
    private MovieTypeService movieTypeService;

    @Autowired
    private PerformerService performerService;
    @Autowired
    private UploadImage uploadImage;


    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

//    @GetMapping("/find-all")
//    public String viewMovie(Model model) {
//        return findAll(model, 1, null, null, null, null, null, null);
//    }

    @GetMapping("/find-all/page/{pageNumber}")
    @Operation(summary = "[Hiển thị tất cả]")
    public String findAll(Model model, @PathVariable("pageNumber") Integer pageNumber,
                          @RequestParam(value = "keyword", required = false) String keyword,
                          @RequestParam(value = "status", required = false) String status,
                          @RequestParam(value = "director", required = false) String directors,
                          @RequestParam(value = "movieType", required = false) String movieTypes,
                          @RequestParam(value = "language", required = false) String languages,
                          @RequestParam(value = "performer", required = false) String performers) {
        Page<Movie> page;
        page = service.filterMovies(pageNumber, directors, languages, movieTypes, performers, status, keyword);
        List<Rated> ratedId = ratedService.fillAll();
        List<Director> directorId = directorService.fillAll();
        List<Language> languageId = languageService.fillAll();
        List<MovieType> movieTypeId = movieTypeService.fillAll();
        List<Performer> performerId = performerService.fillAll();

        model.addAttribute("ratedId", ratedId);
        model.addAttribute("languages", languageId);
        model.addAttribute("movieTypes", movieTypeId);
        model.addAttribute("directors", directorId);
        model.addAttribute("performers", performerId);
        model.addAttribute("keyword", keyword);
        model.addAttribute("status", status);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listMovie", page.getContent());

        return "admin/movie";
    }

    @GetMapping("/view-add")
    public String viewAdd(Model model) {
        List<Rated> ratedId = ratedService.fillAll();
        List<Director> directorId = directorService.fillAll();
        List<Language> languageId = languageService.fillAll();
        List<MovieType> movieTypeId = movieTypeService.fillAll();
        List<Performer> performerId = performerService.fillAll();

        model.addAttribute("ratedId", ratedId);
        model.addAttribute("languages", languageId);
        model.addAttribute("movieTypes", movieTypeId);
        model.addAttribute("directors", directorId);
        model.addAttribute("performers", performerId);
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
                       @RequestParam(name = "nameMovie") String name,
                       @RequestParam(name = "movieType") List<MovieType> movieTypes,
                       @RequestParam(name = "language") List<Language> languages,
                       @RequestParam(name = "trailler") String trailer,
                       @RequestParam(name = "performer") List<Performer> performers,
                       @RequestParam(name = "description") String description,
                       @RequestParam(name = "endDate") Date endDate,
                       @RequestParam(name = "premiereDate") Date premiereDate,
                       @RequestParam(name = "director") List<Director> directors,
                       @RequestParam(name = "image") MultipartFile multipartFile,
                       @RequestParam(name = "movieDuration") Integer movieDuration,
                       @RequestParam(name = "ratedId") Rated rated
    ) {
        uploadImage.handerUpLoadFile(multipartFile);
        try {
            Movie movie = Movie.builder()
                    .movieDuration(movieDuration)
                    .name(name)
                    .description(description)
                    .trailer(trailer)
                    .endDate(endDate)
                    .premiereDate(premiereDate)
                    .image(multipartFile.getOriginalFilename())
                    .rated(rated)
                    .directors(directors)
                    .movieTypes(movieTypes)
                    .languages(languages)
                    .performers(performers)
                    .build();

            if (service.save(movie) instanceof Movie) {
                model.addAttribute("thanhCong", "Thêm thành công!");
            } else {
                model.addAttribute("thatBai", "Thêm thất bại");
            }
            model.addAttribute("movie", new Movie());
            return "redirect:/movie/find-all";
        } catch (Exception e) {
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

    @PostMapping("/director/save")
    @Operation(summary = "[Thêm mới đạo diễn]")
    public String saveDirector(Model model,
                               @RequestParam(name = "name") String name) {
        try {
            Director director = Director.builder()
                    .name(name)
                    .build();

            directorService.save(director);

            model.addAttribute("thanhCong", "Thêm đạo diễn thành công!");
            return "redirect:/movie/view-add";
        } catch (Exception e) {
            e.printStackTrace();
            return "admin/movie";
        }
    }

    @PostMapping("/language/save")
    @Operation(summary = "[Thêm mới ngôn ngữ]")
    public String saveLanguage(Model model,
                               @RequestParam(name = "name") String name) {
        try {
            Language language = Language.builder()
                    .name(name)
                    .build();

            languageService.save(language);

            model.addAttribute("thanhCong", "Thêm ngôn ngữ thành công!");
            return "redirect:/movie/view-add";
        } catch (Exception e) {
            e.printStackTrace();
            return "admin/movie";
        }
    }

    @PostMapping("/performer/save")
    @Operation(summary = "[Thêm mới diễn viên]")
    public String savePerformer(Model model,
                                @RequestParam(name = "name") String name) {
        try {
            Performer performer = Performer.builder()
                    .name(name)
                    .build();

            performerService.save(performer);

            model.addAttribute("thanhCong", "Thêm diễn viên thành công!");
            return "redirect:/movie/view-add";
        } catch (Exception e) {
            e.printStackTrace();
            return "admin/movie";
        }
    }

    @PostMapping("/movie-type/save")
    @Operation(summary = "[Thêm mới thể loại phim]")
    public String saveMovieType(Model model,
                                @RequestParam(name = "name") String name) {
        try {
            MovieType movieType = MovieType.builder()
                    .name(name)
                    .build();

            movieTypeService.save(movieType);

            model.addAttribute("thanhCong", "Thêm thể loại phim thành công!");
            return "redirect:/movie/view-add";
        } catch (Exception e) {
            e.printStackTrace();
            return "admin/movie";
        }
    }
}
