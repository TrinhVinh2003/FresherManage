package com.example.FresherManage.Controller;

import com.example.FresherManage.Dto.request.CenterRequest;
import com.example.FresherManage.Dto.response.CenterResponse;
import com.example.FresherManage.Service.impl.CenterServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@SpringBootTest
@WebMvcTest(controllers = CenterController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CenterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CenterServiceImpl centerService;

    @Autowired
    private ObjectMapper objectMapper;

    private CenterRequest centerRequest;
    private CenterResponse centerResponse;

    @BeforeEach
    public void setUp() {
        centerRequest = CenterRequest.builder()
                .name("VMO")
                .location("Hanoi")
                .build();

        centerResponse = CenterResponse.builder()
                .name("VMO")
                .location("Hanoi")
                .build();
    }

    @Test
    public void createCenter_ReturnCreated() throws Exception {
        given(centerService.createCenter(ArgumentMatchers.any())).willReturn(centerResponse);

        ResultActions response = mockMvc.perform(post("/center")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(centerRequest)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.name").value(centerRequest.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.location").value(centerRequest.getLocation()));
    }

    @Test
    public void getAllCenters_ReturnCentersList() throws Exception {
        List<CenterResponse> centerList = Arrays.asList(
                CenterResponse.builder().name("VMO").location("Hanoi").build(),
                CenterResponse.builder().name("VMO").location("HCM").build()
        );

        given(centerService.getAll()).willReturn(centerList);

        ResultActions response = mockMvc.perform(get("/center")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].name").value("VMO"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].name").value("VMO"));
    }

    @Test
    public void updateCenter_ReturnUpdatedCenter() throws Exception {
        Long centerId = 1L;
        CenterRequest updateRequest = CenterRequest.builder()
                .name("VMO1")
                .location("Updated Location")
                .build();

        CenterResponse updatedResponse = CenterResponse.builder()
                .name("VMO1")
                .location("Updated Location")
                .build();

        given(centerService.updateCenter(ArgumentMatchers.eq(centerId), ArgumentMatchers.any()))
                .willReturn(updatedResponse);

        ResultActions response = mockMvc.perform(put("/center/{id}", centerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.name").value("VMO1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.location").value("Updated Location"));
    }

    @Test
    public void deleteCenter_ReturnOk() throws Exception {
        Long centerId = 1L;
        doNothing().when(centerService).deleteCenter(centerId);

        ResultActions response = mockMvc.perform(delete("/center/{id}", centerId)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
