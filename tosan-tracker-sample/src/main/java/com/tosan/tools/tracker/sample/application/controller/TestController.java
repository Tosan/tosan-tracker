package com.tosan.tools.tracker.sample.application.controller;

import com.tosan.tools.tracker.sample.application.dto.UserRequestDto;
import com.tosan.tools.tracker.sample.application.dto.UserResponseDto;
import com.tosan.tools.tracker.sample.application.exception.SampleException;
import com.tosan.tools.tracker.starter.api.Tracking;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author M.khoshnevisan
 * @since 8/28/2023
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping(value = "/info",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Tracking
    public UserResponseDto saveUser(@RequestBody UserRequestDto request) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(56L);
        return userResponseDto;
    }

    @PostMapping(value = "/exception",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Tracking
    public UserResponseDto testException(@RequestBody UserRequestDto request) throws SampleException {
        throw new SampleException("testMessage", "4050");
    }

    @PostMapping(value = "/runtime/exception",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Tracking
    public UserResponseDto testRuntimeException(@RequestBody UserRequestDto request) throws SampleException {
        throw new RuntimeException("testMessage");
    }
}
