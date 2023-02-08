package com.example.codebase.Service;

import com.example.codebase.Jwt.JwtProvider;
import com.example.codebase.Model.Dao.Member;
import com.example.codebase.Repository.MemberRepository;
import com.example.codebase.Response.BasicResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.Basic;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.HashMap;

@Service
public class MemberService {
    @Autowired
    JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public ResponseEntity<BasicResponse> join(HttpServletResponse response, String id, String pw) throws Exception {
        //회원가입
        try {
            Member member = new Member();
            member.setId(id);
            member.setPw(pw);

            member.setRoles(Collections.singletonList("USER")); //권한 설정

            memberRepository.save(member);

            HashMap<String, String> m = new HashMap<>();
            System.out.println(member.getMember_id());
            m.put("member_id", String.valueOf((member.getMember_id())));

            String accessToken, refreshToken;

            accessToken = jwtProvider.generateToken(m);
            refreshToken = jwtProvider.generateRefreshToken(m);

            response.setHeader("accessToken", accessToken);
            response.setHeader("refreshToken", refreshToken);

            BasicResponse basicResponse = new BasicResponse();
            if(accessToken!=null && refreshToken!=null) {
                basicResponse = BasicResponse.builder()
                        .code(HttpStatus.OK.value())
                        .httpStatus(HttpStatus.OK)
                        .message("회원가입 정상적으로 완료")
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .result(null)
                        .count(1).build();
            }
            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
            }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public ResponseEntity<BasicResponse> login(Long member_id, String id, String pw)  { //로그인 API
        Member member = memberRepository.findById(member_id).orElseGet(Member::new);

        BasicResponse basicResponse = new BasicResponse();
        if(member==null || !member.getId().equals(id) || !member.getPw().equals(pw)){
            basicResponse = BasicResponse.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message("잘못된 요청")
                    .accessToken("")
                    .refreshToken("")
                    .result(null)
                    .count(1).build();
        }
        else {
            basicResponse = BasicResponse.builder()
                    .code(HttpStatus.OK.value())
                    .httpStatus(HttpStatus.OK)
                    .message("로그인 성공")
                    .accessToken("")
                    .refreshToken("")
                    .result(null)
                    .count(1).build();
        }
        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }
}
