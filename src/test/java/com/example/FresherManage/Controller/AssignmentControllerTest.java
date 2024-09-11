package com.example.FresherManage.Controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

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

import com.example.FresherManage.Dto.request.AssignmentRequest;
import com.example.FresherManage.Dto.response.AssignmentResponse;
import com.example.FresherManage.Service.impl.AssignmentServiceImpl;
import com.example.FresherManage.domain.Entity.Fresher;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = AssignmentController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AssignmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AssignmentServiceImpl assignmentService;

    @Autowired
    private ObjectMapper objectMapper;

    private AssignmentRequest assignmentRequest;
    private AssignmentResponse assignmentResponse;

    @BeforeEach
    public void setUp() {
        assignmentRequest = AssignmentRequest.builder()
                .score1(8.5f)
                .score2(9.0f)
                .score3(8.5f)
                .fresher_id(1L)
                .build();

        Fresher fresher = Fresher.builder().id(1L).name("John Doe").build();

        assignmentResponse = AssignmentResponse.builder()
                .score1(8.5f)
                .score2(9.0f)
                .score3(8.5f)
                .result((8.5f + 9.0f + 8.5f) / 3) // Tính điểm trung bình
                .fresher(fresher)
                .build();
    }

    @Test
    public void createAssignment_ReturnCreated() throws Exception {
        given(assignmentService.createAssignment(ArgumentMatchers.any())).willReturn(assignmentResponse);

        ResultActions response = mockMvc.perform(post("/assignment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(assignmentRequest)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.score1").value(assignmentRequest.getScore1()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.score2").value(assignmentRequest.getScore2()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.score3").value(assignmentRequest.getScore3()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.result").value(assignmentResponse.getResult()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.fresher.id")
                        .value(assignmentResponse.getFresher().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.fresher.name")
                        .value(assignmentResponse.getFresher().getName()));
    }

    @Test
    public void updateAssignment_ReturnUpdatedAssignment() throws Exception {
        Long assignmentId = 1L;
        given(assignmentService.updateScore(ArgumentMatchers.eq(assignmentId), ArgumentMatchers.any()))
                .willReturn(assignmentResponse);

        ResultActions response = mockMvc.perform(put("/assignment/fresher/{id}", assignmentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(assignmentRequest)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.score1").value(assignmentRequest.getScore1()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.score2").value(assignmentRequest.getScore2()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.score3").value(assignmentRequest.getScore3()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.result").value(assignmentResponse.getResult()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.fresher.id")
                        .value(assignmentResponse.getFresher().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.fresher.name")
                        .value(assignmentResponse.getFresher().getName()));
    }

    @Test
    public void deleteAssignment_ReturnOk() throws Exception {
        Long assignmentId = 1L;
        doNothing().when(assignmentService).deleteAssignment(assignmentId);

        ResultActions response =
                mockMvc.perform(delete("/assignment/{id}", assignmentId).contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
