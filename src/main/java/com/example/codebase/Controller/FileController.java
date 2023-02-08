package com.example.codebase.Controller;

import com.example.codebase.Jwt.JwtProvider;
import com.example.codebase.Response.AttributesResponse;
import com.example.codebase.Response.BasicResponse;
import com.example.codebase.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
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
                                                 @RequestParam("dir") String dir) throws IOException {
        String accessToken = request.getHeader("accessToken");
        String id = jwtProvider.getIdFromToken(accessToken);

        return fileService.makeDir(id,dir);
    }

    @PostMapping("/renameFile")
    public ResponseEntity<BasicResponse> renameFile(HttpServletRequest request,
                                                    @RequestParam("dir") String dir,
                                                    @RequestParam("file") String file, @RequestParam("rename") String rename){
        String accessToken = request.getHeader("accessToken");
        String member_id = jwtProvider.getIdFromToken(accessToken);

        return fileService.renameFile(member_id, dir, file, rename);
    }

    @PostMapping("/renameDir")
    public ResponseEntity<BasicResponse> renameDir(HttpServletRequest request,
                                                   @RequestParam("dir") String dir,
                                                   @RequestParam("rename") String rename){
        String accessToken = request.getHeader("accessToken");
        String member_id = jwtProvider.getIdFromToken(accessToken);

        return fileService.renameDir(member_id, dir, rename);
    }

    @GetMapping("/getDir")
    public ResponseEntity<?> getDir(HttpServletRequest request,
                                                @RequestParam("dir") String dir){
        String accessToken = request.getHeader("accessToken");
        String member_id = jwtProvider.getIdFromToken(accessToken);

        return fileService.getDir(member_id, dir);
    }


    @PostMapping("/moveDir")
    public ResponseEntity<BasicResponse> getDir(HttpServletRequest request,
                                                @RequestParam("dir") String dir,
                                                @RequestParam("mv_dir") String mv_dir) throws IOException {
        String accessToken = request.getHeader("accessToken");
        String member_id = jwtProvider.getIdFromToken(accessToken);
        return fileService.moveDir(member_id, dir, mv_dir);
    }
  
    @PostMapping("/removeDir")
    public ResponseEntity<?> removeDir(HttpServletRequest request,
                                       @RequestParam("dir") String dir,
                                       @RequestParam("rm") String rm){
      
        String accessToken = request.getHeader("accessToken");
        String member_id = jwtProvider.getIdFromToken(accessToken);
        return fileService.removeDir(member_id,dir,rm);
    }

    @PostMapping("/copy")
    public ResponseEntity<BasicResponse> copy(HttpServletRequest request,
                                              @RequestParam("dir") String dir,
                                              @RequestParam("copyDir") String copyDir) throws IOException {
        String accessToken = request.getHeader("accessToken");
        String member_id = jwtProvider.getIdFromToken(accessToken);
        return fileService.copy(member_id, dir, copyDir);
    }

    @PostMapping("/removeFile")
    public ResponseEntity<?> removeFile(HttpServletRequest request,
                                       @RequestParam("dir") String dir,
                                       @RequestParam("file") String file){
        String accessToken = request.getHeader("accessToken");
        String member_id = jwtProvider.getIdFromToken(accessToken);
        return fileService.removeFile(member_id,dir,file);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(HttpServletRequest request,
                                        @RequestParam("dir") String dir,
                                        @RequestParam("file")MultipartFile file){
        String accessToken = request.getHeader("accessToken");
        String member_id = jwtProvider.getIdFromToken(accessToken);
        return fileService.uploadFile(member_id,dir,file);
    }

    @GetMapping("/image")
    public ResponseEntity<byte[]> readImage(HttpServletRequest request,
                                       @RequestParam String dir,
                                       @RequestParam String imagename){
        String accessToken = request.getHeader("accessToken");
        String member_id = jwtProvider.getIdFromToken(accessToken);
        return fileService.readImage(member_id,dir,imagename);
    }

    @GetMapping("/video")
    public ResponseEntity<?> playVideo(HttpServletRequest request,
                                              @RequestParam String dir,
                                              @RequestParam String videoname){

        String accessToken = request.getHeader("accessToken");
        String member_id = jwtProvider.getIdFromToken(accessToken);
        return fileService.playVideo(member_id,dir,videoname);
    }

    @GetMapping("/file")
    public ResponseEntity<?> readFile(HttpServletRequest request,
                                      @RequestParam String dir,
                                      @RequestParam String filename){
        String accessToken = request.getHeader("accessToken");
        String member_id = jwtProvider.getIdFromToken(accessToken);
        return fileService.readFile(Long.valueOf(member_id),dir,filename);
    }
    @GetMapping("/getAttribute")
    public ResponseEntity<AttributesResponse> getAttribute(HttpServletRequest request,
                                                           @RequestParam("file") String file) throws IOException {
        String accessToken = request.getHeader("accessToken");
        String member_id = jwtProvider.getIdFromToken(accessToken);
        return fileService.getAttribute(Long.valueOf(member_id), file);
    }

}
