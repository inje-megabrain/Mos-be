package com.example.codebase.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasicResponse {
    private Integer code;
    private HttpStatus httpStatus;
    private String message;
    private String accessToken;
    private String refreshToken;
    private Integer count;
    private List<getDirectory> result;

}
