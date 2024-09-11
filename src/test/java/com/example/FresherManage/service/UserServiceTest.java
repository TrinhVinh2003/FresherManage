package com.example.FresherManage.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.FresherManage.Constant.PredefinedRole;
import com.example.FresherManage.Dto.request.UserCreationRequest;
import com.example.FresherManage.Dto.request.UserUpdateRequest;
import com.example.FresherManage.Dto.response.UserDetailResponse;
import com.example.FresherManage.Dto.response.UserResponse;
import com.example.FresherManage.Mapper.UserMapper;
import com.example.FresherManage.Service.impl.UserServiceImpl;
import com.example.FresherManage.domain.Entity.Fresher;
import com.example.FresherManage.domain.Entity.Role;
import com.example.FresherManage.domain.Entity.User;
import com.example.FresherManage.domain.Exception.AppException;
import com.example.FresherManage.domain.Exception.ErrorCode;
import com.example.FresherManage.repository.FresherRepository;
import com.example.FresherManage.repository.RoleRepository;
import com.example.FresherManage.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private FresherRepository fresherRepository;

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
                .email("john@example.com")
                .build();

        User user = User.builder()
                .username("john_doe")
                .password("encoded_password")
                .email("john@example.com")
                .build();

        Role userRole = new Role(PredefinedRole.USER_ROLE, "Default user role", new HashSet<>());

        UserResponse response = UserResponse.builder().username("john_doe").build();

        // Mock behavior
        when(userRepository.existsByUsername(request.getUsername())).thenReturn(false);
        when(userMapper.toUser(request)).thenReturn(user);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encoded_password");
        when(roleRepository.findById(PredefinedRole.USER_ROLE)).thenReturn(Optional.of(userRole));
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toUserResponse(user)).thenReturn(response);

        // Act
        UserResponse result = userService.createUser(request);

        // Assert
        assertNotNull(result);
        assertEquals("john_doe", result.getUsername());

        // Verify interactions with mocks

        verify(userMapper, times(1)).toUser(request);
        verify(passwordEncoder, times(1)).encode(request.getPassword());
        verify(roleRepository, times(1)).findById(PredefinedRole.USER_ROLE);
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
                .email("old_email@example.com")
                .roles(new HashSet<>())
                .build();

        User updatedUser = User.builder()
                .id(userId)
                .username("Vinh")
                .password("encoded_new_password")
                .email("new_email@example.com")
                .roles(new HashSet<>())
                .build();

        UserResponse response = UserResponse.builder().username("vinh").build();

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
        String userId = "999"; // Non-existent user ID
        UserUpdateRequest request = UserUpdateRequest.builder()
                .username("john_doe_updated")
                .password("new_password")
                .roles(List.of("ROLE_USER"))
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
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
        User user = User.builder().id(userId).username("john_doe").build();

        UserResponse response = UserResponse.builder().username("john_doe").build();

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
        String email = "john@example.com";
        User user = User.builder().username(username).email(email).build();
        UserDetailResponse userDetailResponse =
                UserDetailResponse.builder().username(username).build();
        Fresher fresher = Fresher.builder().email(email).build();

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(username);
        SecurityContextHolder.setContext(securityContext);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(userMapper.toUserDetailResponse(user)).thenReturn(userDetailResponse);
        when(fresherRepository.findByEmail(email)).thenReturn(fresher);

        // Act
        UserDetailResponse result = userService.getMyInfo();

        // Assert
        assertEquals(username, result.getUsername());
        assertEquals(fresher, result.getFresher());
        verify(userRepository, times(1)).findByUsername(username);
        verify(userMapper, times(1)).toUserDetailResponse(user);
        verify(fresherRepository, times(1)).findByEmail(email);
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
        verify(userMapper, never()).toUserDetailResponse(any());
        verify(fresherRepository, never()).findByEmail(any());
    }
}
