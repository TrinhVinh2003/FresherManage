package com.example.FresherManage.Controller;


import com.example.FresherManage.Dto.request.FresherRequest;
import com.example.FresherManage.Dto.response.FresherReponse;
import com.example.FresherManage.Mapper.FresherMapper;
import com.example.FresherManage.Service.FresherService;
import com.example.FresherManage.Service.impl.FresherServiceImpl;
import com.example.FresherManage.domain.Entity.Center;
import com.example.FresherManage.domain.Entity.Fresher;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FresherController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class FresherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FresherServiceImpl fresherService;

    @Autowired
    private ObjectMapper objectMapper;

    private Fresher fresher;
    private Center center;
    private FresherRequest fresherRequest;
    private FresherReponse fresherResponse;
    private FresherMapper fresherMapper;


    @BeforeEach
    public void init(){
        fresherRequest = FresherRequest.builder().name("vinh").email("vinh534473@gmail.com").programmingLanguage("Java").build();
        fresherResponse = FresherReponse.builder().name("vinh").email("vinh534473@gmail.com").programmingLanguage("Java").build();


    }

    @Test
    public void FresherController_CreateFresher_ReturnCreated() throws Exception {

        given(fresherService.createFresher(ArgumentMatchers.any(FresherRequest.class))).willReturn(fresherResponse);


        ResultActions response = mockMvc.perform(post("/fresher")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fresherRequest)));

        // check return result
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.result.name").value(fresherResponse.getName()))
                .andExpect(jsonPath("$.result.email").value(fresherResponse.getEmail()));
    }


    @Test
    public void FresherController_GetAllFresher_ReturnFresherList() throws Exception {
        List<FresherReponse> fresherReponseList = Arrays.asList(fresherResponse);
        when(fresherService.getFreshers()).thenReturn(fresherReponseList);


        //
        ResultActions reponse =  mockMvc.perform(get("/fresher")
                .contentType(MediaType.APPLICATION_JSON));


        reponse.andExpect(status().isOk())
                .andExpect(jsonPath("$.result.size()").value(fresherReponseList.size()))
                .andExpect(jsonPath("$.result[0].name").value(fresherResponse.getName()))
                .andExpect(jsonPath("$.result[0].email").value(fresherResponse.getEmail()));


    }

    @Test
    public void FresherController_UpdateFresher_ReturnUpdatedFresher() throws Exception {
        Long fresherId = 1L;
        given(fresherService.updateFresher(ArgumentMatchers.eq(fresherId), ArgumentMatchers.any()))
                .willReturn(fresherResponse);

        ResultActions response = mockMvc.perform(put("/fresher/{id}", fresherId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fresherRequest)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.name").value(fresherRequest.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.email").value(fresherRequest.getEmail()));
    }

    @Test
    public void FresherController_DeleteFresher_ReturnOk() throws Exception {
        Long fresherId = 1L;
        doNothing().when(fresherService).deleteFresher(fresherId);

        ResultActions response = mockMvc.perform(delete("/fresher/{id}", fresherId)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }



}
