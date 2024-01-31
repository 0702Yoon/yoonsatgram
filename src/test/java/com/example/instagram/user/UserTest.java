package com.example.instagram.user;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.instagram.exception.ValueExistsException;
import com.example.instagram.s3Service.S3Service;
import com.example.instagram.user.controller.dto.UserRequest;
import com.example.instagram.user.repositroy.UserRepository;
import com.example.instagram.user.service.UserService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
public class UserTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @MockBean
    private S3Service s3Service;

    @BeforeEach
    void setUp() {

    }

    @Test
    @DisplayName("유저 생성 테스트")
    public void joinUser() throws IOException {
        //given
        UserRequest userRequest = new UserRequest("testUser", "테스트", "test입니다.", "1234");
        Path path = Paths.get("C:\\Users\\gkstk\\Pictures\\Screenshots\\nike.png");
        byte[] content = Files.readAllBytes(path);
        MultipartFile image = new MockMultipartFile("nike.png", "nike.png", "image/jpeg", content);
        //when
        userService.join(userRequest, image);
        // then
        assertTrue(userRepository.findById("testUser").isPresent());
        userService.delete(userRequest.getUser_id());
    }

    @Test
    @DisplayName("유저 아이디 중복 테스트")
    public void 회원가입_테스트_중복() {
        UserRequest userRequest1 = new UserRequest("testUser2", "테스터", "test입니다.", "1234");
        UserRequest userRequest2 = new UserRequest("testUser2", "테스터", "test입니다.", "1234");
        MultipartFile image = null;
        Mockito.when(s3Service.uploadFile(Mockito.anyString(), Mockito.any()))
            .thenReturn("testImageUrl");

        userService.join(userRequest1, image);

        assertThrows(ValueExistsException.class, () -> userService.join(userRequest2, image));
        userService.delete(userRequest1.getUser_id());
    }

}
