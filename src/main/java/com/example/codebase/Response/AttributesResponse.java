package com.example.codebase.Response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.nio.file.attribute.FileTime;
import java.sql.Time;
import java.util.Date;
import java.util.Map;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class AttributesResponse {
    private Integer code;
    private HttpStatus httpStatus;
    private String message;
    private String lastAccessTime;
    private String lastModifiedTime;
    private String size;
    private String creationTime;

}


