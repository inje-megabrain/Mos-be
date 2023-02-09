package com.example.codebase.Controller;

import com.example.codebase.Jwt.JwtProvider;
import com.example.codebase.Response.BasicResponse;
import com.example.codebase.Service.UUIDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class UUIDController {

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    UUIDService uuidService;


    @PostMapping("/makeUUID")
    public ResponseEntity<BasicResponse> makeUUID(HttpServletRequest request,
                                                  @RequestParam("path") String path) {
        String accessToken = request.getHeader("accessToken");
        String member_id = jwtProvider.getIdFromToken(accessToken);

        return uuidService.makeUUID(member_id, path);
    }

    @GetMapping("/getUUID")
    public ResponseEntity<BasicResponse> getUUID(HttpServletRequest request,
                                                 @RequestParam("path") String path,
                                                 @RequestParam("UUID") Long uuid) {
        String accessToken = request.getHeader("accessToken");
        String member_id = jwtProvider.getIdFromToken(accessToken);
        return uuidService.getUUID(member_id, uuid, path);
    }

    @GetMapping("/downloadUUID")
    public ResponseEntity<?> downloadUUID(HttpServletRequest request,
                                          @RequestParam("UUID")Long uuid,
                                          HttpServletResponse response){
        String accessToken = request.getHeader("accessToken");
        String member_id = jwtProvider.getIdFromToken(accessToken);
        return uuidService.downloadUUID(response,uuid);
    }

}
