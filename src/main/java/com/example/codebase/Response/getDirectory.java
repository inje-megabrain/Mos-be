package com.example.codebase.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class getDirectory {
    private Boolean isDir;
    private String name;
    private String ext;
}
