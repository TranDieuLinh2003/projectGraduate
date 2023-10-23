package com.example.filmBooking.controller;

import com.example.filmBooking.common.ResponseBean;
import com.example.filmBooking.model.Promotion;
import com.example.filmBooking.service.PromotionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/promotion")
@CrossOrigin("*")
@Tag(name = "promotion")
public class PromotionController {
    @Autowired
    private PromotionService promotionService;
    @GetMapping("/find-all")
    @Operation(summary = "[Hiển thị dữ liệu Promotion]")
    public ResponseEntity<?>getAll(){
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(HttpStatus.OK.toString());
        responseBean.setMessage("SUCCESS");
        responseBean.setData(promotionService.fillAll());
        return new ResponseEntity<>(responseBean, HttpStatus.OK);
    }
    @PostMapping("/save")
    @Operation(summary = "[Thêm dữ liệu vào Promotion]")
    public ResponseEntity<Object>save(@RequestBody @Valid Promotion promotion){
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(HttpStatus.OK.toString());
        responseBean.setMessage("SUCCESS");
        responseBean.setData(promotionService.save(promotion));
        return new ResponseEntity<>(responseBean, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "[Cập nhật dữ liệu Promotion]")
    public ResponseEntity<Object>update(@PathVariable("id") String id, @RequestBody @Valid Promotion promotion){
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(HttpStatus.OK.toString());
        responseBean.setMessage("SUCCESS");
        responseBean.setData(promotionService.update(id, promotion));
        return new ResponseEntity<>(responseBean, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "[Xóa dữ liệu Promotion]")
    public ResponseEntity<?>delete(@PathVariable("id") String id){
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(HttpStatus.OK.toString());
        responseBean.setMessage("SUCCESS");
        promotionService.delete(id);
        responseBean.setData(id);
        return new ResponseEntity<>(responseBean, HttpStatus.OK);
    }

    @GetMapping("/customer={customerId}")
    @Operation(summary = "[Lấy ra voucher từng khách hàng]")
    public ResponseEntity<?> listVoucherCustomer(@PathVariable("customerId") String customerId){
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(HttpStatus.OK.toString());
        responseBean.setMessage("SUCCESS");
        responseBean.setData(promotionService.listVoucherCustomer(customerId));
        return new ResponseEntity<>(responseBean, HttpStatus.OK);
    }
}
