package com.example.FresherManage.service;

import com.example.FresherManage.Dto.request.UserCreationRequest;
import com.example.FresherManage.Dto.request.UserUpdateRequest;
import com.example.FresherManage.Dto.response.UserResponse;
import com.example.FresherManage.domain.Entity.Role;
import com.example.FresherManage.domain.Entity.User;
import com.example.FresherManage.domain.Exception.AppException;
import com.example.FresherManage.domain.Exception.ErrorCode;
import com.example.FresherManage.repository.RoleRepository;
import com.example.FresherManage.repository.UserRepository;
import com.example.FresherManage.Service.impl.UserServiceImpl;
import com.example.FresherManage.Mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.Authentication;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        // Arrange
        UserCreationRequest request = UserCreationRequest.builder()
                .username("john_doe")
                .password("password")
                .build();

        User user = User.builder()
                .username("john_doe")
                .password("encoded_password")
                .build();

        UserResponse response = UserResponse.builder()
                .username("john_doe")
                .build();

        when(userRepository.existsByUsername(request.getUsername())).thenReturn(false);
        when(userMapper.toUser(request)).thenReturn(user);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encoded_password");
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toUserResponse(user)).thenReturn(response);

        // Act
        UserResponse result = userService.createUser(request);

        // Assert
        assertEquals("john_doe", result.getUsername());
        verify(userRepository, times(1)).existsByUsername(request.getUsername());
        verify(userMapper, times(1)).toUser(request);
        verify(passwordEncoder, times(1)).encode(request.getPassword());
        verify(userRepository, times(1)).save(user);
        verify(userMapper, times(1)).toUserResponse(user);
    }

    @Test
    void testUpdateUser_Success() {
        // Arrange
        String userId = "1";
        UserUpdateRequest request = UserUpdateRequest.builder()
                .username("vinh")
                .password("534473")
                .roles(List.of("ROLE_USER"))
                .build();

        User existingUser = User.builder()
                .id(userId)
                .username("Moi")
                .password("old_password")
                .roles(new HashSet<>())
                .build();

        User updatedUser = User.builder()
                .id(userId)
                .username("Vinh")
                .password("encoded_new_password")
                .roles(new HashSet<>())
                .build();

        UserResponse response = UserResponse.builder()
                .username("vinh")
                .build();

        // Mock các đối tượng và phương thức
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encoded_new_password");
        when(roleRepository.findAllById(request.getRoles())).thenReturn(new ArrayList<>());
        when(userRepository.save(existingUser)).thenReturn(updatedUser);
        when(userMapper.toUserResponse(updatedUser)).thenReturn(response);

        // Act
        UserResponse result = userService.updateUser(userId, request);

        // Assert
        assertEquals("vinh", result.getUsername());
        verify(userRepository, times(1)).findById(userId);
        verify(passwordEncoder, times(1)).encode(request.getPassword());
        verify(roleRepository, times(1)).findAllById(request.getRoles());
        verify(userRepository, times(1)).save(existingUser);
        verify(userMapper, times(1)).toUserResponse(updatedUser);
    }

    @Test
    void testUpdateUser_UserNotFound() {
        // Arrange
        String userId = "999"; // Không tồn tại
        UserUpdateRequest request = UserUpdateRequest.builder()
                .username("john_doe_updated")
                .password("new_password")
                .roles(List.of("ROLE_USER"))
                .build();

        // Mock việc user không tồn tại
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Assert và Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.updateUser(userId, request);
        });

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).save(any(User.class));
    }


    @Test
    void testDeleteUser() {
        // Arrange
        String userId = "1";

        // Act
        userService.deleteUser(userId);

        // Assert
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testGetUser() {
        // Arrange
        String userId = "1";
        User user = User.builder()
                .id(userId)
                .username("john_doe")
                .build();

        UserResponse response = UserResponse.builder()
                .username("john_doe")
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toUserResponse(user)).thenReturn(response);

        // Act
        UserResponse result = userService.getUser(userId);

        // Assert
        assertEquals("john_doe", result.getUsername());
        verify(userRepository, times(1)).findById(userId);
        verify(userMapper, times(1)).toUserResponse(user);
    }

    @Test
    void testGetMyInfo_Success() {
        // Arrange
        String username = "john_doe";
        User user = User.builder().username(username).build();
        UserResponse userResponse = UserResponse.builder().username(username).build();

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(username);
        SecurityContextHolder.setContext(securityContext);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(userMapper.toUserResponse(user)).thenReturn(userResponse);

        // Act
        UserResponse result = userService.getMyInfo();

        // Assert
        assertEquals(username, result.getUsername());
        verify(userRepository, times(1)).findByUsername(username);
        verify(userMapper, times(1)).toUserResponse(user);
    }

    @Test
    void testGetMyInfo_UserNotFound() {
        // Arrange
        String username = "john_doe";

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(username);
        SecurityContextHolder.setContext(securityContext);

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act & Assert
        AppException exception = assertThrows(AppException.class, () -> userService.getMyInfo());
        assertEquals(ErrorCode.USER_NOT_EXIST, exception.getErrorCode());

        verify(userRepository, times(1)).findByUsername(username);
        verify(userMapper, never()).toUserResponse(any());
    }
}
