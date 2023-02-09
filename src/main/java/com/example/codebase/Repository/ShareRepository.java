package com.example.codebase.Repository;

import com.example.codebase.Model.Dao.Share;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShareRepository extends JpaRepository<Share, Long> {
    @Query("Select u From Share u Where uri = :uri")
    public Share findBymUri(@Param("uri") String uri);

}
