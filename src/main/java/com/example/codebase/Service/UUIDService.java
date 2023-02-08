package com.example.codebase.Service;

import com.example.codebase.Model.Dao.Member;
import com.example.codebase.Model.Dao.Share;
import com.example.codebase.Repository.ShareRepository;
import com.example.codebase.Response.BasicResponse;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class UUIDService {
    @Autowired
    ShareRepository shareRepository;
    public ResponseEntity<BasicResponse> makeUUID(String member_id, String path) {

        Share share = new Share();
        share.setUri(member_id+path);

        BasicResponse basicResponse = new BasicResponse();

        shareRepository.save(share);

        basicResponse = BasicResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("UUID: "+share.getUuid())
                .accessToken("")
                .refreshToken("")
                .result(null)
                .count(1).build();

        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

    public ResponseEntity<BasicResponse> getUUID(String uuid, String path) {
        Share share = shareRepository.findById(uuid).orElseGet(Share::new);

        BasicResponse basicResponse = new BasicResponse();

        if(share.getUri().equals(path)) {

            basicResponse = BasicResponse.builder()
                    .code(HttpStatus.OK.value())
                    .httpStatus(HttpStatus.OK)
                    .message("성공")
                    .accessToken("")
                    .refreshToken("")
                    .result(null)
                    .count(1).build();
        }
        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());

    }
}
