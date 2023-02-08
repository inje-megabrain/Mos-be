package com.example.codebase.Model.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MemberDto {

    private Long member_id;
    private String id;
    private String pw;

}
