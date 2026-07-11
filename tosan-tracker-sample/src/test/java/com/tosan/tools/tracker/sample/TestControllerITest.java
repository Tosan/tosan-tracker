package com.tosan.tools.tracker.sample;

import com.tosan.tools.tracker.sample.application.dto.UserRequestDto;
import com.tosan.tools.tracker.sample.application.dto.UserResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.Arrays;

/**
 * @author M.khoshnevisan
 * @since 8/28/2023
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TosanTrackerSpringBootSampleApplication.class)
@ActiveProfiles("dev")
public class TestControllerITest {

    @Autowired
    protected RestTestClient restTestClient;

    @Test
    public void testGetInfo() {
        UserRequestDto requestDto = new UserRequestDto();
        requestDto.setFirstname("name1");
        requestDto.setLastname("name2");
        restTestClient.post()
                .uri("/test/info")
                .body(requestDto)
                .exchange()
                .expectBody(UserResponseDto.class)
                .consumeWith(result -> System.out.println(result.getResponseBody()));
    }

    @Test
    public void testException() {
        UserRequestDto requestDto = new UserRequestDto();
        requestDto.setFirstname("name1");
        requestDto.setLastname("name2");
        restTestClient.post()
                .uri("/test/exception")
                .body(requestDto)
                .exchange()
                .expectBody(UserResponseDto.class)
                .consumeWith(result -> System.out.println(result.getResponseBody()));
    }

    @Test
    public void testRuntimeException() {
        UserRequestDto requestDto = new UserRequestDto();
        requestDto.setFirstname("name1");
        requestDto.setLastname("name2");
        restTestClient.post()
                .uri("/test/runtime/exception")
                .body(requestDto)
                .exchange()
                .expectBody()
                .consumeWith(result -> System.out.println(Arrays.toString(result.getResponseBody())));
    }
}
