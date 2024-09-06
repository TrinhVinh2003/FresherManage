package com.example.FresherManage.service;

import com.example.FresherManage.Dto.request.CenterRequest;
import com.example.FresherManage.Dto.response.CenterResponse;
import com.example.FresherManage.Mapper.CenterMapper;
import com.example.FresherManage.Service.impl.CenterServiceImpl;
import com.example.FresherManage.domain.Entity.Center;
import com.example.FresherManage.domain.Exception.AppException;
import com.example.FresherManage.domain.Exception.ErrorCode;
import com.example.FresherManage.repository.CenterRepository;
import com.example.FresherManage.repository.FresherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CenterServiceTest {

    @Mock
    private CenterRepository centerRepository;

    @Mock
    private CenterMapper centerMapper;

    @Mock
    private FresherRepository fresherRepository;

    @InjectMocks
    private CenterServiceImpl centerService;

    private CenterRequest centerRequest;
    private Center center;
    private CenterResponse centerResponse;

    @BeforeEach
    void setUp() {
        centerRequest = CenterRequest.builder()
                .name("Center1")
                .location("Hanoi")
                .build();

        center = Center.builder()
                .id(1L)
                .name("Center1")
                .location("Hanoi")
                .build();

        centerResponse = CenterResponse.builder()
                .name(center.getName())
                .location(center.getLocation())
                .build();
    }

    @Test
    void createCenter_ValidRequest_ReturnsCenterResponse() {
        // Giả lập repository không chứa center có tên giống với yêu cầu
        when(centerRepository.existsByName(centerRequest.getName())).thenReturn(false);
        when(centerMapper.toCenter(centerRequest)).thenReturn(center);
        when(centerRepository.save(center)).thenReturn(center);
        when(centerMapper.toCenterReponse(center)).thenReturn(centerResponse);

        // Gọi service
        CenterResponse result = centerService.createCenter(centerRequest);

        // Kiểm tra kết quả
        assertNotNull(result);
        assertEquals(centerRequest.getName(), result.getName());
        assertEquals(centerRequest.getLocation(), result.getLocation());
    }

    @Test
    void createCenter_CenterAlreadyExists_ThrowsAppException() {
        // Giả lập center đã tồn tại
        when(centerRepository.existsByName(centerRequest.getName())).thenReturn(true);

        // Kiểm tra ngoại lệ
        AppException exception = assertThrows(AppException.class, () -> {
            centerService.createCenter(centerRequest);
        });

        assertEquals(ErrorCode.CENTER_EXIST, exception.getErrorCode());
    }

    @Test
    void getAll_ReturnsListOfCenterResponse() {
        // Giả lập repository trả về danh sách các center
        List<Center> centers = Arrays.asList(
                new Center(1L, "Center1", "Hanoi", null, null),
                new Center(2L, "Center2", "Danang", null, null)
        );
        when(centerRepository.findAll()).thenReturn(centers);

        when(centerMapper.toCenterReponse(any(Center.class))).thenAnswer(invocation -> {
            Center center = invocation.getArgument(0);
            return new CenterResponse(center.getName(), center.getLocation());
        });

        // Gọi service
        List<CenterResponse> result = centerService.getAll();

        // Kiểm tra kết quả
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Center1", result.get(0).getName());
        assertEquals("Hanoi", result.get(0).getLocation());
        assertEquals("Center2", result.get(1).getName());
        assertEquals("Danang", result.get(1).getLocation());
    }

    @Test
    void delete_ValidName_DeletesCenter() {
        Long centerId = 1L;

        // Gọi service
        centerService.deleteCenter(centerId);

        // Kiểm tra xem phương thức deleteByName được gọi
        verify(centerRepository, times(1)).deleteById(centerId);
    }

}
