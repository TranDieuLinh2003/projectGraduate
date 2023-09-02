package com.example.filmBooking.controller;

import com.example.filmBooking.common.ResponseBean;
import com.example.filmBooking.model.Movie;
import com.example.filmBooking.service.MovieService;
import com.example.filmBooking.service.impl.ExportMovie;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/movie")
@Tag(name = "movie")
public class MovieController {
    @Autowired
    private MovieService service;

    @GetMapping("/find-all")
    @Operation(summary = "[Hiển thị tất cả]")
    public ResponseEntity<?> findAll() {
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(HttpStatus.OK.toString());
        responseBean.setMessage("SUCCESS");
        responseBean.setData(service.fillAll());


        return new ResponseEntity<>(responseBean, HttpStatus.OK);
    }

    @PostMapping("/save")
    @Operation(summary = "[Thêm mới]")
    public ResponseEntity<Object> save(@RequestBody @Valid Movie movie) {
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(HttpStatus.OK.toString());
        responseBean.setMessage("SUCCESS");
        responseBean.setData(service.save(movie));
        return new ResponseEntity<>(responseBean, HttpStatus.OK);
    }

//    @PutMapping("/update/{id}")
//    @Operation(summary = "[Chỉnh sửa]")
//    public ResponseEntity<Object> update(@PathVariable("id") UUID id, @RequestBody @Valid Rank rank) {
//        ResponseBean responseBean = new ResponseBean();
//        responseBean.setCode(HttpStatus.OK.toString());
//        responseBean.setMessage("SUCCESS");
//        responseBean.setData(service.update(id, rank));
//        return new ResponseEntity<>(responseBean, HttpStatus.OK);
//    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "[Xóa]")
    public ResponseEntity<?> delete(@PathVariable("id") UUID id) {
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(HttpStatus.OK.toString());
        responseBean.setMessage("SUCCESS");
        service.delete(id);
        responseBean.setData(id);
        return new ResponseEntity<>(responseBean, HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    @Operation(summary = "[Tìm kiếm theo id]")
    public ResponseEntity<?> findById(@PathVariable("id") UUID id) {
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(HttpStatus.OK.toString());
        responseBean.setMessage("SUCCESS");
        responseBean.setData(service.findById(id));
        return new ResponseEntity<>(responseBean, HttpStatus.OK);
    }

    @PostMapping("/import")
    @Operation(summary = "[Thêm mới bằng file excel]")
    public ResponseEntity<Object> importExcel() {
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(HttpStatus.OK.toString());
        responseBean.setMessage("SUCCESS");
        responseBean.setData(service.readExcel());
        return new ResponseEntity<>(responseBean, HttpStatus.OK);
    }

    @GetMapping("/export/excel")
    @Operation(summary = "[Xuất excel]")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=movie" + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Movie> listMovie = service.fillAll();

        ExportMovie excelExporter = new ExportMovie(listMovie);

        excelExporter.export(response);
    }
}
