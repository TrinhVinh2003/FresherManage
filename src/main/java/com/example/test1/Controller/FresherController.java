package com.example.test1.Controller;


import com.example.test1.Dto.request.ApiResponse;
import com.example.test1.Dto.request.FresherRequest;
import com.example.test1.Dto.response.FresherReponse;
import com.example.test1.Entity.Fresher;
import com.example.test1.Service.FresherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fresher")
public class FresherController {
    @Autowired
    private FresherService fresherService;

    ApiResponse<FresherReponse> createFresher(FresherRequest fresherRequest){
        var fresher = fresherService.createFresher(fresherRequest);
        return ApiResponse.<FresherReponse>builder()
                .result(fresher)
                .build();
    }


}
