package com.example.FresherManage.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.FresherManage.Dto.request.ApiResponse;
import com.example.FresherManage.Dto.request.CenterRequest;
import com.example.FresherManage.Dto.response.CenterResponse;
import com.example.FresherManage.Service.impl.CenterServiceImpl;
import com.example.FresherManage.repository.CenterRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/center")
@RequiredArgsConstructor
public class CenterController {
    @Autowired
    private final CenterServiceImpl centerService;

    @Autowired
    private final CenterRepository centerRepository;

    @PostMapping
    public ApiResponse<CenterResponse> createCenter(@RequestBody CenterRequest centerRequest) {
        return ApiResponse.<CenterResponse>builder()
                .result(centerService.createCenter(centerRequest))
                .build();
    }

    @GetMapping
    public ApiResponse<List<CenterResponse>> getAll() {
        return ApiResponse.<List<CenterResponse>>builder()
                .result(centerService.getAll())
                .build();
    }

    @DeleteMapping("/{name}")
    public ApiResponse<Void> deleteCenter(String name) {
        centerService.delete(name);
        return ApiResponse.<Void>builder().build();
    }
}
