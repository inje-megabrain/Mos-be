package com.example.codebase.Repository;

import com.example.codebase.Model.Dao.Member;
import com.example.codebase.Model.Dto.MemberDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("Select u From Member u Where id = :id")
    public Member findBymId(@Param("id") String id);
}

