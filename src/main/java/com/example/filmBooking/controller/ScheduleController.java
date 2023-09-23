package com.example.filmBooking.controller;

import com.example.filmBooking.common.ResponseBean;
import com.example.filmBooking.model.Schedule;
import com.example.filmBooking.service.ScheduleService;
import com.example.filmBooking.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.time.LocalDateTime;


@Controller
@CrossOrigin("*")
@RequestMapping("/schedule")
@Tag(name = "schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService service;

    @Autowired
    private TicketService ticketService;

    @GetMapping("/find-all")
    @Operation(summary = "[Hiển thị tất cả]")
    public ResponseEntity<?> findAll() {
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(HttpStatus.OK.toString());
        responseBean.setMessage("SUCCESS");
        responseBean.setData(service.findAll());
        return new ResponseEntity<>(responseBean, HttpStatus.OK);
    }

    @GetMapping("/find-time")
    @Operation(summary = "[lấy khoảng thời gian]")
    public ResponseEntity<?> findTime(@RequestParam("id")String id,
                                      @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss") @Nullable @RequestParam(name = "from_startAt", required = false) LocalDateTime from_startAt,
                                      @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss") @Nullable @RequestParam(name = "to_startAt", required = false) LocalDateTime to_startAt,
                                      @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss") @Nullable @RequestParam(name = "from_finishAt", required = false) LocalDateTime from_finishAt,
                                      @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss") @Nullable @RequestParam(name = "to_finishAt", required = false) LocalDateTime to_finishAt) {
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(HttpStatus.OK.toString());
        responseBean.setMessage("SUCCESS");
        responseBean.setData(service.CheckConstraint(id,from_startAt, to_startAt, from_finishAt, to_finishAt));
        return new ResponseEntity<>(responseBean, HttpStatus.OK);
    }

    @PostMapping("/save")
    @Operation(summary = "[Thêm mới]")
    public ResponseEntity<Object> save(@RequestBody @Valid Schedule schedule) throws ParseException {
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(HttpStatus.OK.toString());
        responseBean.setMessage("SUCCESS");
//        responseBean.setData(ticketService.autoSave(service.save(schedule)));
        responseBean.setData(service.save(schedule));
        return new ResponseEntity<>(responseBean, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "[Chỉnh sửa]")
    public ResponseEntity<Object> update(@PathVariable("id") String id, @RequestBody @Valid Schedule schedule) {
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(HttpStatus.OK.toString());
        responseBean.setMessage("SUCCESS");
        responseBean.setData(service.update(id, schedule));
        return new ResponseEntity<>(responseBean, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "[Xóa]")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(HttpStatus.OK.toString());
        responseBean.setMessage("SUCCESS");
        service.delete(id);
        responseBean.setData(id);
        return new ResponseEntity<>(responseBean, HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    @Operation(summary = "[Tìm kiếm theo id]")
    public ResponseEntity<?> findById(@PathVariable("id") String id) {
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(HttpStatus.OK.toString());
        responseBean.setMessage("SUCCESS");
        responseBean.setData(service.findById(id));
        return new ResponseEntity<>(responseBean, HttpStatus.OK);
    }
}
