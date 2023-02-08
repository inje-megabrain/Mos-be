package com.example.codebase.Service;

import com.example.codebase.Response.BasicResponse;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {
    //private String rootPath = "/Users/leeseonghyeon/Desktop/Mega/"; //root Path

    public ResponseEntity<BasicResponse> makeDir(Long member_id, String dir) {   //폴더 생성

        File newFile = new File(rootPath+dir);
        BasicResponse basicResponse = new BasicResponse();

        if(!newFile.exists()) {
            if(newFile.mkdir()) {
                basicResponse = BasicResponse.builder()
                        .code(HttpStatus.OK.value())
                        .httpStatus(HttpStatus.OK)
                        .message("폴더 생성 성공")
                        .accessToken("")
                        .refreshToken("")
                        .result(null)
                        .count(1).build();
            }
            else
                basicResponse = BasicResponse.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message("폴더 생성 실패")
                        .accessToken("")
                        .refreshToken("")
                        .result(null)
                        .count(1).build();
        }
        else{
            basicResponse = BasicResponse.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message("파일 이미 존재")
                    .accessToken("")
                    .refreshToken("")
                    .result(null)
                    .count(1).build();

        }
        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

    public ResponseEntity<BasicResponse> makeFile(Long member_id, String dir, String file) throws IOException { //파일 생성
        File newDir = new File(rootPath+dir);

        BasicResponse basicResponse = new BasicResponse();
        if(!newDir.exists())
            if(newDir.mkdir())
                System.out.println("폴더 생성 성공");

        File newFile = new File(newDir, file);
        if(!newFile.exists()) {

                if(newFile.createNewFile())
                    basicResponse = BasicResponse.builder()
                            .code(HttpStatus.OK.value())
                            .httpStatus(HttpStatus.OK)
                            .message("파일 생성 성공")
                            .accessToken("")
                            .refreshToken("")
                            .result(null)
                            .count(1).build();
                else
                    basicResponse = BasicResponse.builder()
                            .code(HttpStatus.BAD_REQUEST.value())
                            .httpStatus(HttpStatus.BAD_REQUEST)
                            .message("파일 생성 실패")
                            .accessToken("")
                            .refreshToken("")
                            .result(null)
                            .count(1).build();

        }
        else{
            basicResponse = BasicResponse.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message("파일 이미 존재")
                    .accessToken("")
                    .refreshToken("")
                    .result(null)
                    .count(1).build();

        }
        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

    public ResponseEntity<BasicResponse> renameFile(Long member_id, String dir, String file, String rename) { //파일 이름 변경
        File newDir = new File(rootPath+dir);
        File newFile = new File(newDir, file);

        BasicResponse basicResponse = new BasicResponse();

        File changeFile = new File(newDir, rename); //변경할 이름

        if (newFile.exists()) {	// 파일이 존재할 때만 이름 변경
            if(newFile.renameTo(changeFile))
                basicResponse = BasicResponse.builder()
                        .code(HttpStatus.OK.value())
                        .httpStatus(HttpStatus.OK)
                        .message("파일 이름 변경 성공")
                        .accessToken("")
                        .refreshToken("")
                        .result(null)
                        .count(1).build();
            else
                basicResponse = BasicResponse.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message("파일 이름 변경 실패")
                        .accessToken("")
                        .refreshToken("")
                        .result(null)
                        .count(1).build();
        } else {
            basicResponse = BasicResponse.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message("파일 이름 변경 실패")
                    .accessToken("")
                    .refreshToken("")
                    .result(null)
                    .count(1).build();
        }
        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

    public ResponseEntity<BasicResponse> renameDir(Long member_id, String dir, String rename) {  //폴더 이름 변경
        File newDir = new File(rootPath+dir);

        BasicResponse basicResponse = new BasicResponse();

        File changeFile = new File(rootPath, rename); //변경할 이름

        if (newDir.exists()) {	// 파일이 존재할 때만 이름 변경
            if(newDir.renameTo(changeFile))
                basicResponse = BasicResponse.builder()
                        .code(HttpStatus.OK.value())
                        .httpStatus(HttpStatus.OK)
                        .message("폴더 이름 변경 성공")
                        .accessToken("")
                        .refreshToken("")
                        .result(null)
                        .count(1).build();
            else
            basicResponse = BasicResponse.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message("폴더 이름 변경 실패")
                    .accessToken("")
                    .refreshToken("")
                    .result(null)
                    .count(1).build();
        } else {
            basicResponse = BasicResponse.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message("폴더 이름 변경 실패")
                    .accessToken("")
                    .refreshToken("")
                    .result(null)
                    .count(1).build();
        }
        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

    public ResponseEntity<BasicResponse> getDir(Long member_id, String dir) {    //폴더 구조 가져오기
        File newDir = new File(rootPath+dir);

        File[] fileList= newDir.listFiles();

        BasicResponse basicResponse = new BasicResponse();

        List<String> list = new ArrayList<>();

        for(File file : fileList) {
            if(file.exists()) {

                String fileName = file.getName();

                list.add(fileName);
                }

            }

        basicResponse = BasicResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("폴더 내부 구조 확인")
                .accessToken("")
                .refreshToken("")
                .result(list)
                .count(1).build();

        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }


    /*
    public void moveDir(Long member_id, String dir, String mv_dir) throws IOException {
        BasicResponse basicResponse = new BasicResponse();

        File src = new File(rootPath+dir);
        File dst = new File(rootPath+mv_dir;
        try {
            FileUtils.moveFile(src,dst);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

     */
}
