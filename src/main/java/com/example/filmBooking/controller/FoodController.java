package com.example.filmBooking.controller;

import com.example.filmBooking.common.ResponseBean;
import com.example.filmBooking.model.Food;
import com.example.filmBooking.service.FoodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/filmbookings")
@Tag(name = "food")
public class FoodController {
    @Autowired
    private FoodService foodService;

    @GetMapping("/find-all")
    @Operation(summary = "[Hiển thị tất cả]")
    public ResponseEntity<?> getAll() {
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(HttpStatus.OK.toString());
        responseBean.setMessage("SUCCESS");
        responseBean.setData(foodService.fillAll());
        return new ResponseEntity<>(responseBean, HttpStatus.OK);
    }

    @PostMapping("/save")
    @Operation(summary = "[Thêm dữ liệu vào Food]")
    public ResponseEntity<Object> save(@RequestBody @Valid Food food) {
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(HttpStatus.OK.toString());
        responseBean.setMessage("SUCCESS");
        responseBean.setData(foodService.save(food));
        return new ResponseEntity<>(responseBean, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "[Xóa dữ liệu Food]")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(HttpStatus.OK.toString());
        responseBean.setMessage("SUCCESS");
        foodService.delete(id);
        responseBean.setData(id);
        return new ResponseEntity<>(responseBean, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "[Cập nhật dữ liệu Food]")
    public ResponseEntity<Object> update(@PathVariable UUID id, @RequestBody @Valid Food food) {
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(HttpStatus.OK.toString());
        responseBean.setMessage("SUCCESS");
        responseBean.setData(foodService.update(id, food));
        return new ResponseEntity<>(responseBean, HttpStatus.OK);
    }
}
