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
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

import static com.example.codebase.Service.FileService.rootPath;

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
            Member result = new Member();
            Member member = memberRepository.findBymId(memberDto.getId());
            result.setId(memberDto.getId());
            result.setPw(memberDto.getPw());


            File file = new File(rootPath+memberDto.getId());
            file.mkdir();
            member.setRoles(Collections.singletonList("USER")); //권한 설정

            memberRepository.save(result);


            BasicResponse basicResponse = new BasicResponse();

                basicResponse = BasicResponse.builder()
                        .code(HttpStatus.OK.value())
                        .httpStatus(HttpStatus.OK)
                        .message("회원가입 정상적으로 완료")
                        .build();

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
            }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public ResponseEntity<BasicResponse> login(HttpServletResponse response, MemberDto memberDto) { //로그인 API

           //회원가입
            Member member = memberRepository.findBymId(memberDto.getId());
            BasicResponse basicResponse = new BasicResponse();

            if(member==null || !member.getPw().equals(memberDto.getPw()))
                basicResponse = BasicResponse.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message("로그인 실패")
                        .build();
            else {
                HashMap<String, String> m = new HashMap<>();

                m.put("id", memberDto.getId());
                String accessToken, refreshToken;

                accessToken = jwtProvider.generateToken(m);
                refreshToken = jwtProvider.generateRefreshToken(m);

                response.setHeader("accessToken", accessToken);
                response.setHeader("refreshToken", refreshToken);


                basicResponse = BasicResponse.builder()
                        .code(HttpStatus.OK.value())
                        .httpStatus(HttpStatus.OK)
                        .message("로그인 성공 & 파일 생성")
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
            }
            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }
}
