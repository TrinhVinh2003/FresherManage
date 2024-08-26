package com.example.test1.Controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.test1.Dto.request.ApiResponse;
import com.example.test1.Dto.request.FresherRequest;
import com.example.test1.Dto.response.FresherReponse;
import com.example.test1.Service.FresherService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/fresher")
@RequiredArgsConstructor
public class FresherController {
    private static final Logger log = LoggerFactory.getLogger(FresherController.class);

    @Autowired
    private final FresherService fresherService;

    @PostMapping
    ApiResponse<FresherReponse> createFresher(@RequestBody FresherRequest fresherRequest) {
        var fresher = fresherService.createFresher(fresherRequest);

        return ApiResponse.<FresherReponse>builder().result(fresher).build();
    }

    @GetMapping
    ApiResponse<List<FresherReponse>> getAll() {
        return ApiResponse.<List<FresherReponse>>builder()
                .result(fresherService.getFreshers())
                .build();
    }
}
