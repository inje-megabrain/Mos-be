package com.example.codebase.Service;

import com.example.codebase.Model.Dao.Member;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class Details extends User { //Authentication 객체 생성 super()을 이용하여!

    public Details(Member member) {
        super(member.getId(), member.getPw(),
                AuthorityUtils.createAuthorityList(String.valueOf(member.getRoles())));
    }



}