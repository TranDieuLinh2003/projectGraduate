package com.example.filmBooking.component.rank;

import com.example.filmBooking.common.ResponseBean;
import com.example.filmBooking.model.Rank;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rank")
@Tag(name = "rank")
public class RankController {
    @Autowired
    private RankService service;

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
    public ResponseEntity<Object> save(@RequestBody @Valid Rank rank) {
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(HttpStatus.OK.toString());
        System.out.println(rank);
        responseBean.setMessage("SUCCESS");
        responseBean.setData(service.save(rank));
        return new ResponseEntity<>(responseBean, HttpStatus.OK);
    }
}
