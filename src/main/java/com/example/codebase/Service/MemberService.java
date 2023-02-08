package com.example.codebase.Service;

import com.example.codebase.Jwt.JwtProvider;
import com.example.codebase.Model.Dao.Member;
import com.example.codebase.Model.Dto.MemberDto;
import com.example.codebase.Repository.MemberRepository;
import com.example.codebase.Response.BasicResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

@Service
public class MemberService {
    @Autowired
    JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public ResponseEntity<BasicResponse> join(HttpServletResponse response, MemberDto memberDto) throws Exception {
        //회원가입
        try {
            Member member = new Member();
            member.setId(memberDto.getId());
            member.setPw(memberDto.getPw());

            member.setRoles(Collections.singletonList("USER")); //권한 설정

            memberRepository.save(member);



            BasicResponse basicResponse = new BasicResponse();

                basicResponse = BasicResponse.builder()
                        .code(HttpStatus.OK.value())
                        .httpStatus(HttpStatus.OK)
                        .message("회원가입 정상적으로 완료")
                        .accessToken("")
                        .refreshToken("")
                        .result(null)
                        .count(1).build();

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
            }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public ResponseEntity<BasicResponse> login(HttpServletResponse response, MemberDto memberDto) { //로그인 API

           //회원가입

            Member member = memberRepository.findBymId(memberDto.getId());

            HashMap<String, String> m = new HashMap<>();

            m.put("member_id", String.valueOf(member.getMember_id()));
            String accessToken, refreshToken;

            accessToken = jwtProvider.generateToken(m);
            refreshToken = jwtProvider.generateRefreshToken(m);

            response.setHeader("accessToken", accessToken);
            response.setHeader("refreshToken", refreshToken);
            BasicResponse basicResponse = new BasicResponse();

                basicResponse = BasicResponse.builder()
                        .code(HttpStatus.OK.value())
                        .httpStatus(HttpStatus.OK)
                        .message("로그인 성공")
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .result(null)
                        .count(1).build();

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());

    }
}
