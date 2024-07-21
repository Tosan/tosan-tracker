package com.tosan.tools.tracker.sample;

import com.tosan.tools.tracker.sample.application.dto.UserRequestDto;
import com.tosan.tools.tracker.sample.application.dto.UserResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.DefaultResponseErrorHandler;

/**
 * @author M.khoshnevisan
 * @since 8/28/2023
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TosanTrackerSpringBootSampleApplication.class)
@ActiveProfiles("dev")
public class TestControllerITest {

    @Autowired
    protected TestRestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        restTemplate.getRestTemplate().setErrorHandler(new DefaultResponseErrorHandler());
    }

    @Test
    public void testGetInfo() {
        UserRequestDto requestDto = new UserRequestDto();
        requestDto.setFirstname("name1");
        requestDto.setLastname("name2");
        HttpEntity<UserRequestDto> requestEntity = new HttpEntity<>(requestDto, new HttpHeaders());
        ResponseEntity<UserResponseDto> response = restTemplate.exchange("/test/info", HttpMethod.POST,
                requestEntity, UserResponseDto.class);
        System.out.println(response.getBody());
    }

    @Test
    public void testException() {
        UserRequestDto requestDto = new UserRequestDto();
        requestDto.setFirstname("name1");
        requestDto.setLastname("name2");
        HttpEntity<UserRequestDto> requestEntity = new HttpEntity<>(requestDto, new HttpHeaders());
        ResponseEntity<UserResponseDto> response = restTemplate.exchange("/test/exception", HttpMethod.POST,
                requestEntity, UserResponseDto.class);
        System.out.println(response.getBody());
    }

    @Test
    public void testRuntimeException() {
        UserRequestDto requestDto = new UserRequestDto();
        requestDto.setFirstname("name1");
        requestDto.setLastname("name2");
        HttpEntity<UserRequestDto> requestEntity = new HttpEntity<>(requestDto, new HttpHeaders());
        ResponseEntity<UserResponseDto> response = restTemplate.exchange("/test/runtime/exception", HttpMethod.POST,
                requestEntity, UserResponseDto.class);
        System.out.println(response.getBody());
    }
}
