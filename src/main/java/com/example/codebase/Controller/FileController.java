package com.example.codebase.Controller;

import com.example.codebase.Jwt.JwtProvider;
import com.example.codebase.Response.BasicResponse;
import com.example.codebase.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class FileController {

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    FileService fileService;

    @PostMapping("/makeDir") //---> 폴더만 생성하는 API
    public ResponseEntity<BasicResponse> makeDir(HttpServletRequest request,
                                                 @RequestParam("dir") String dir){
        String accessToken = request.getHeader("accessToken");
        String member_id = jwtProvider.getMemberIdFromToken(accessToken);

        return fileService.makeDir(Long.valueOf(member_id),dir);
    }

    @PostMapping("/makeFile") //---> 파일 생성하는 API
    public ResponseEntity<BasicResponse> makeDir(HttpServletRequest request,
                                                 @RequestParam("dir") String dir,
                                                 @RequestParam("file") String file) throws IOException {
        String accessToken = request.getHeader("accessToken");
        String member_id = jwtProvider.getMemberIdFromToken(accessToken);

        return fileService.makeFile(Long.valueOf(member_id), dir, file);  //-->directory 객체 전달
    }

    @PostMapping("/renameFile")
    public ResponseEntity<BasicResponse> renameFile(HttpServletRequest request,
                                                    @RequestParam("dir") String dir,
                                                    @RequestParam("file") String file, @RequestParam("rename") String rename){
        String accessToken = request.getHeader("accessToken");
        String member_id = jwtProvider.getMemberIdFromToken(accessToken);

        return fileService.renameFile(Long.valueOf(member_id), dir, file, rename);
    }

    @PostMapping("/renameDir")
    public ResponseEntity<BasicResponse> renameDir(HttpServletRequest request,
                                                   @RequestParam("dir") String dir,
                                                   @RequestParam("rename") String rename){
        String accessToken = request.getHeader("accessToken");
        String member_id = jwtProvider.getMemberIdFromToken(accessToken);

        return fileService.renameDir(Long.valueOf(member_id), dir, rename);
    }

    @GetMapping("/getDir")
    public ResponseEntity<BasicResponse> getDir(HttpServletRequest request,
                                                @RequestParam("dir") String dir){
        String accessToken = request.getHeader("accessToken");
        String member_id = jwtProvider.getMemberIdFromToken(accessToken);

        return fileService.getDir(Long.valueOf(member_id), dir);
    }


    @PostMapping("/moveDir")
    public ResponseEntity<BasicResponse> getDir(HttpServletRequest request,
                                                @RequestParam("dir") String dir,
                                                @RequestParam("mv_dir") String mv_dir) throws IOException {
        return fileService.moveDir(Long.valueOf(member_id), dir, mv_dir);
    }
  
    @PostMapping("/removeDir")
    public ResponseEntity<?> removeDir(HttpServletRequest request,
                                                   @RequestParam("dir") String dir){
      
        String accessToken = request.getHeader("accessToken");
        String member_id = jwtProvider.getMemberIdFromToken(accessToken);
        return fileService.removeDir(Long.valueOf(member_id),dir);
    }





    @PostMapping("/removeFile")
    public ResponseEntity<?> removeFile(HttpServletRequest request,
                                       @RequestParam("dir") String dir,
                                       @RequestParam("file") String file){
        String accessToken = request.getHeader("accessToken");
        String member_id = jwtProvider.getMemberIdFromToken(accessToken);
        return fileService.removeFile(Long.valueOf(member_id),dir,file);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(HttpServletRequest request,
                                        @RequestParam("dir") String dir,
                                        @RequestParam("file") MultipartFile file){
        String accessToken = request.getHeader("accessToken");
        String member_id = jwtProvider.getMemberIdFromToken(accessToken);
        return fileService.uploadFile(Long.valueOf(member_id),dir,file);
    }

}
