package com.example.codebase.Jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@RequiredArgsConstructor
@Slf4j


//처츰으로 지나가는 Filter
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;


    //1번 필터
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = null;
        String refreshToken = null;
        Authentication authenticate;
        //사용자의 principal과 credential 정보를 Authentication에 담는다

        accessToken =  req.getHeader("accessToken");
        System.out.println("accessToken: "+accessToken);
        if(accessToken==null || jwtProvider.isTokenExpired(accessToken)) {  //유효기간 만료 또는 토큰 없을시

            refreshToken = req.getHeader("refreshToken"); //RefreshToken 쿠키에서 가져오기
            //  getMemberIdFromToken에 이미 유효성 검사가 있어서
            //jwtProvider.RefreshIsTokenExpired안해도 될 거 같다
            //리펙토링시 참고
            if(refreshToken!=null || !jwtProvider.RefreshisTokenExpired(refreshToken)) {
                System.out.println("refreshToken 유효합니다!!");
                String memberId = jwtProvider.RefreshgetMemberIdFromToken(refreshToken);
                // RefreshToken에 저장된 Token을 Decode해서 Id값을 가져온다
                try {
                    authenticate = jwtProvider
                            .authenticate(new UsernamePasswordAuthenticationToken(memberId, ""));
                    //UsernamePasswordAuthenticationToken은 추후 인증이 끝나고
                    //SecurityContextHolder.getContext()에 등록될 Authentication객체
                    //MyUserDetailsService의  loadUserByUsername()로 이동
                    // Details이동후 Authenticate객체 생성! (id, pw, 권한)

                    SecurityContextHolder.getContext().setAuthentication(authenticate);
                    //ID, PW가 존재하는 계정이면 Holder에 객체 저장

                    HashMap<String, String> m = new HashMap<>();
                    m.put("member_id", memberId);    //RefreshToken에 저장된 Id를 가져온다

                    accessToken = jwtProvider.generateToken(m);   //accessToken 재발급
                    res.addHeader("accessToken", accessToken);
                    System.out.println("새로 생성 access");

                } catch (Exception e) {
                    System.out.println("설마 여기로 들어오나?");
                    throw new RuntimeException(e.getMessage()+"여기서 에러");
                }
            }
            else if(refreshToken==null || jwtProvider.RefreshisTokenExpired(refreshToken)) {
                System.out.println("RefreshToken 휴효하지 않습니다");//토큰 유효기간 끝
                String memberId = jwtProvider.RefreshgetMemberIdFromToken(refreshToken);
                try {
                    authenticate = jwtProvider
                            .authenticate(new UsernamePasswordAuthenticationToken(memberId, ""));
                    //UsernamePasswordAuthenticationToken은 추후 인증이 끝나고
                    //SecurityContextHolder.getContext()에 등록될 Authentication객체
                    //MyUserDetailsService의  loadUserByUsername()로 이동
                    // Details이동후 Authenticate객체 생성! (id, pw, 권한)

                    SecurityContextHolder.getContext().setAuthentication(authenticate);
                    //ID, PW가 존재하는 계정이면 Holder에 객체 저장

                    HashMap<String, String> m = new HashMap<>();
                    m.put("member_id", memberId);    //RefreshToken에 저장된 Id를 가져온다

                    refreshToken= jwtProvider.generateRefreshToken(m);   //accessToken 재발급
                    res.setHeader("refreshToken", refreshToken);
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        }
        else if(accessToken!=null && !jwtProvider.isTokenExpired(accessToken)){
            System.out.println("accessToken이 유요합니다");
            String memberId = jwtProvider.getIdFromToken(accessToken);
            System.out.println(memberId);
            try {
                authenticate = jwtProvider
                        .authenticate(new UsernamePasswordAuthenticationToken(memberId, ""));
                //UsernamePasswordAuthenticationToken은 추후 인증이 끝나고
                //SecurityContextHolder.getContext()에 등록될 Authentication객체
                //MyUserDetailsService의  loadUserByUsername()로 이동
                // Details이동후 Authenticate객체 생성! (id, pw, 권한)

                SecurityContextHolder.getContext().setAuthentication(authenticate);
                //ID, PW가 존재하는 계정이면 Holder에 객체 저장

            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        filterChain.doFilter(req, res);

    }
}