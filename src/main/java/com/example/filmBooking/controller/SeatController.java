package com.example.filmBooking.controller;

import com.example.filmBooking.common.ResponseBean;
import com.example.filmBooking.model.Seat;
import com.example.filmBooking.service.SeatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.UUID;

@Controller
@CrossOrigin("*")
@RequestMapping("/seat")
@Tag(name = "seat")
public class SeatController {
    @Autowired
    private SeatService seatService;


    @GetMapping("/find-all")
    @Operation(summary = "[Hiển thị tất cả]")
    public ResponseEntity<?> findAll() {
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(HttpStatus.OK.toString());
        responseBean.setMessage("SUCCESS");
        responseBean.setData(seatService.getAll());
        return new ResponseEntity<>(responseBean, HttpStatus.OK);
    }

    @PostMapping("/save")
    @Operation(summary = "[Thêm mới]")
    public ResponseEntity<Object> save(@RequestBody @Valid Seat seat, @RequestParam("lineNumber") Integer lineNumber, @RequestParam("number") Integer number, @RequestParam("idSeatType") UUID idSeatType) {
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(HttpStatus.OK.toString());
        responseBean.setMessage("SUCCESS");
        responseBean.setData(seatService.save(seat, lineNumber, number, idSeatType));
        return new ResponseEntity<>(responseBean, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "[Chỉnh sửa]")
    public ResponseEntity<Object> update(@PathVariable("id") UUID id, @RequestBody @Valid Seat seat) {
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(HttpStatus.OK.toString());
        responseBean.setMessage("SUCCESS");
        responseBean.setData(seatService.update(id, seat));
        return new ResponseEntity<>(responseBean, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "[Xóa]")
    public ResponseEntity<?> delete(@PathVariable("id") UUID id) {
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(HttpStatus.OK.toString());
        responseBean.setMessage("SUCCESS");
        seatService.delete(id);
        responseBean.setData(id);
        return new ResponseEntity<>(responseBean, HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    @Operation(summary = "[Tìm kiếm theo id]")
    public ResponseEntity<?> findById(@PathVariable("id") UUID id) {
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(HttpStatus.OK.toString());
        responseBean.setMessage("SUCCESS");
        responseBean.setData(seatService.findById(id));
        return new ResponseEntity<>(responseBean, HttpStatus.OK);
    }

}
