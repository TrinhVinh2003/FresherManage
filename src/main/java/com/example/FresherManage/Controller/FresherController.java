package com.example.FresherManage.Controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.FresherManage.Dto.request.ApiResponse;
import com.example.FresherManage.Dto.request.FresherRequest;
import com.example.FresherManage.Dto.response.FresherReponse;
import com.example.FresherManage.Dto.response.ProjectResponse;
import com.example.FresherManage.Service.impl.FresherServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/fresher")
@RequiredArgsConstructor
public class FresherController {
    private static final Logger log = LoggerFactory.getLogger(FresherController.class);

    @Autowired
    private final FresherServiceImpl fresherService;

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

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteFresher(@PathVariable("id") Long id) {
        fresherService.deleteFresher(id);

        return ApiResponse.<Void>builder().build();
    }

    @PutMapping("/{id}")
    ApiResponse<FresherReponse> updateFresher(@PathVariable("id") Long id, @RequestBody FresherRequest fresherRequest) {
        var updateFresher = fresherService.updateFresher(id, fresherRequest);
        return ApiResponse.<FresherReponse>builder().result(updateFresher).build();
    }

    @GetMapping("/{id}")
    ApiResponse<FresherReponse> getFresherById(@PathVariable("id") Long id) {
        return ApiResponse.<FresherReponse>builder()
                .result(fresherService.getFresherById(id))
                .build();
    }

    @GetMapping("/project/{id}")
    ApiResponse<List<ProjectResponse>> getProjectByFresher(@PathVariable("id") Long id) {
        var project = fresherService.getProjectsForFresher(id);
        return ApiResponse.<List<ProjectResponse>>builder().result(project).build();
    }

    @GetMapping("/searchByLanguage/{languagePro}")
    ApiResponse<List<FresherReponse>> searchFresherByProgrammingLanguage(@PathVariable("languagePro") String languageProgramming){
        return ApiResponse.<List<FresherReponse>>builder()
                .result(fresherService.searchByProgrammingLanguage(languageProgramming))
                .build();
    }

    @GetMapping("/searchByEmail/{email}")
    ApiResponse<List<FresherReponse>> searchFresherByEmail(@PathVariable("email") String email){
        return ApiResponse.<List<FresherReponse>>builder()
                .result(fresherService.searchByEmail(email))
                .build();
    }

    @GetMapping("/searchByName/{name}")
    ApiResponse<List<FresherReponse>> searchByName(@PathVariable("name") String name){
        return ApiResponse.<List<FresherReponse>>builder()
                .result(fresherService.searchByName(name))
                .build();
    }
}
