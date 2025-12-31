package com.ggomi.memo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@Tag(name = "Hello API", description = "Hello API 관련 기능")
public class HelloApiController {
    @GetMapping("/hello")
    @Operation(summary = "헬로우 API", description = "헬로우 월드를 반환하는 API")
    public String hello() {
        return "hello world";
    }

    @GetMapping("/")
    @Operation(summary = "메인 API", description = "endPoint가 없는 기본 메인 API")
    public String helloKorean() {
        return "안녕하세요";
    }
    
}