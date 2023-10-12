package com.example.filmBooking.controller;

import com.example.filmBooking.common.ResponseBean;
import com.example.filmBooking.model.GeneralSetting;
import com.example.filmBooking.service.GeneralSettingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@CrossOrigin("*")
@RequestMapping("/general-setting")
@Tag(name = "general-setting")
public class GeneralSettingController {
    @Autowired
    private GeneralSettingService service;

    @GetMapping("/find-all")
    @Operation(summary = "[Hiển thị tất cả]")
    public ResponseEntity<?> findAll() {
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(HttpStatus.OK.toString());
        responseBean.setMessage("SUCCESS");
        responseBean.setData(service.fillAll());
        return new ResponseEntity<>(responseBean, HttpStatus.OK);
    }

    // @PostMapping("/save")
    // @Operation(summary = "[Thêm mới]")
    // public ResponseEntity<Object> save(@RequestBody @Valid GeneralSetting GeneralSetting) {
    //     ResponseBean responseBean = new ResponseBean();
    //     responseBean.setCode(HttpStatus.OK.toString());
    //     responseBean.setMessage("SUCCESS");
    //     responseBean.setData(service.save(GeneralSetting));
    //     return new ResponseEntity<>(responseBean, HttpStatus.OK);
    // }

    @PutMapping("/update")
    @Operation(summary = "[Chỉnh sửa]")
    public ResponseEntity<Object> update(@RequestParam("gio1") Integer gio1, @RequestParam("phut1") Integer phut1,
                                         @RequestParam("gio2") Integer gio2, @RequestParam("phut2") Integer phut2,
                                         @RequestParam("gio3") Integer gio3, @RequestParam("phut3") Integer phut3,
                                         @RequestParam("fixedTicketPrice") BigDecimal fixedTicketPrice,
                                         @RequestParam("percentDay") Integer percentDay,
                                         @RequestParam("percentWeekend") Integer percentWeekend,
                                         @RequestParam("breakTime") Integer breakTime) {
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(HttpStatus.OK.toString());
        responseBean.setMessage("SUCCESS");
        responseBean.setData(service.update(gio1, phut1, gio2, phut2, gio3, phut3, fixedTicketPrice, percentDay, percentWeekend, breakTime));
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
