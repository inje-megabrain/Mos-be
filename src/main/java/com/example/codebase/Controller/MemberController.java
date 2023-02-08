package com.example.codebase.Controller;

import com.example.codebase.Jwt.JwtProvider;
import com.example.codebase.Response.BasicResponse;
import com.example.codebase.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class MemberController {

    @Autowired
    MemberService memberService;

    @Autowired
    JwtProvider jwtProvider;
    @PostMapping("/join")   //회원가입 API
    public ResponseEntity<BasicResponse> join(HttpServletResponse response, @RequestParam("id") String id,
                                              @RequestParam("pw") String pw) throws Exception {
        return memberService.join(response, id, pw);
    }

    @PostMapping("login") //로그인 API
    public ResponseEntity<BasicResponse> login(HttpServletRequest req,
                                               @RequestParam("id") String id,
                                               @RequestParam("pw") String pw) {

        String accessToken = req.getHeader("accessToken");
        String member_id = jwtProvider.getMemberIdFromToken(accessToken);
        return memberService.login((Long.valueOf(member_id)), id, pw);
    }
}
