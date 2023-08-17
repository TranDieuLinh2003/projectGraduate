package com.example.filmBooking.controller;

import com.example.filmBooking.common.ResponseBean;
import com.example.filmBooking.model.Seat;
import com.example.filmBooking.model.SeatType;
import com.example.filmBooking.service.SeatTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/seat-type")
@Tag(name = "seat-type")
public class SeatTypeController {
    @Autowired
    private SeatTypeService seatTypeService;

    @GetMapping("/find-all")
    @Operation(summary = "[Hiển thị tất cả]")
    public ResponseEntity<?> findAll(){
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(HttpStatus.OK.toString());
        responseBean.setMessage("SUCCESS");
        responseBean.setData(seatTypeService.getAll());
        return new ResponseEntity<>(responseBean, HttpStatus.OK);
    }
    @PostMapping("/save")
    @Operation(summary = "[Thêm mới]")
    public ResponseEntity<Object> save(@RequestBody @Valid SeatType seatType) {
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(HttpStatus.OK.toString());
        responseBean.setMessage("SUCCESS");
        responseBean.setData(seatTypeService.save(seatType));
        return new ResponseEntity<>(responseBean, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "[Chỉnh sửa]")
    public ResponseEntity<Object> update(@PathVariable("id") UUID id, @RequestBody @Valid SeatType seatType) {
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(HttpStatus.OK.toString());
        responseBean.setMessage("SUCCESS");
        responseBean.setData(seatTypeService.update(id, seatType));
        return new ResponseEntity<>(responseBean, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "[Xóa]")
    public ResponseEntity<?> delete(@PathVariable("id") UUID id) {
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(HttpStatus.OK.toString());
        responseBean.setMessage("SUCCESS");
        seatTypeService.delete(id);
        responseBean.setData(id);
        return new ResponseEntity<>(responseBean, HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    @Operation(summary = "[Tìm kiếm theo id]")
    public ResponseEntity<?> findById(@PathVariable("id") UUID id) {
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(HttpStatus.OK.toString());
        responseBean.setMessage("SUCCESS");
        responseBean.setData(seatTypeService.findById(id));
        return new ResponseEntity<>(responseBean, HttpStatus.OK);
    }
}
