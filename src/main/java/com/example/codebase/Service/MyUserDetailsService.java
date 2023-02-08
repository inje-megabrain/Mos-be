package com.example.codebase.Service;

import com.example.codebase.Model.Dao.Member;
import com.example.codebase.Repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String Id) throws UsernameNotFoundException {
        Long member_id = Long.parseLong(Id);

        Member member = memberRepository.findById(member_id).orElseGet(Member::new);

        if(member==null){
            throw new UsernameNotFoundException(member_id + " : 사용자 존재하지 않음");
        }
        return new Details(member);
    }
}