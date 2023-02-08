package com.example.codebase.Service;

import com.example.codebase.Response.AttributesResponse;
import com.example.codebase.Response.BasicResponse;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FileService {
    //private String rootPath = "/mos_file/"; //root Path
    //private String rootPath = "/Users/leeseonghyeon/Desktop/"; //root Path
    private String rootPath = "C:/Users/mun/Desktop/파일저장테스트/"; //root Path



    public ResponseEntity<BasicResponse> makeDir(Long member_id, String dir) {   //폴더 생성

        File newFile = new File(rootPath + dir);
        BasicResponse basicResponse = new BasicResponse();

        if (!newFile.exists()) {
            if (newFile.mkdir()) {
                basicResponse = BasicResponse.builder()
                        .code(HttpStatus.OK.value())
                        .httpStatus(HttpStatus.OK)
                        .message("폴더 생성 성공")
                        .accessToken("")
                        .refreshToken("")
                        .result(null)
                        .count(1).build();
            } else
                basicResponse = BasicResponse.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message("폴더 생성 실패")
                        .accessToken("")
                        .refreshToken("")
                        .result(null)
                        .count(1).build();
        } else {
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
        File newDir = new File(rootPath + dir);

        BasicResponse basicResponse = new BasicResponse();
        if (!newDir.exists())
            if (newDir.mkdir())
                System.out.println("폴더 생성 성공");

        File newFile = new File(newDir, file);
        if (!newFile.exists()) {

            if (newFile.createNewFile())
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

        } else {
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
        File newDir = new File(rootPath + dir);
        File newFile = new File(newDir, file);

        BasicResponse basicResponse = new BasicResponse();

        File changeFile = new File(newDir, rename); //변경할 이름

        if (newFile.exists()) {    // 파일이 존재할 때만 이름 변경
            if (newFile.renameTo(changeFile))
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
        File newDir = new File(rootPath + dir);

        BasicResponse basicResponse = new BasicResponse();

        File changeFile = new File(rootPath, rename); //변경할 이름

        if (newDir.exists()) {    // 파일이 존재할 때만 이름 변경
            if (newDir.renameTo(changeFile))
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
            File newDir = new File(rootPath + dir);

            File[] fileList = newDir.listFiles();

            BasicResponse basicResponse = new BasicResponse();

            List<String> list = new ArrayList<>();

            for (File file : fileList) {
                if (file.exists()) {

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

    //디렉토리 삭제(하위폴더,파일 모두 삭제됨)
    public ResponseEntity removeDir(Long member_id, String dir, String rm) {
        File rm_dir = new File(rootPath + dir);
        String response = new String();
        if (rm_dir.exists() && rm_dir.isDirectory()) {
            try {
                FileUtils.deleteDirectory(rm_dir);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            response = "폴더 삭제완료";
        } else if (rm_dir.exists()) {
            response = "폴더가 아닌 파일 이름";
        } else {
            response = "존재하지 않는 폴더";
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    public ResponseEntity<BasicResponse> moveDir(Long member_id, String dir, String mv_dir) throws IOException {
        BasicResponse basicResponse = new BasicResponse();

        char[] ch = dir.toCharArray();

        int cnt=0;

        for(int i=ch.length-1; i>=0; i--){
            if(ch[i]=='/'){
                cnt=i+1;
                break;
            }
        }
        char[] result= new char[ch.length-cnt];

        for(int i=cnt; i<ch.length; i++) {
            result[i-cnt]=ch[i];
        }

        String path = String.valueOf(result);

        File from = new File(rootPath+dir);
        File to = new File(rootPath+mv_dir+"/"+path);

            try {
                if(from.isFile()){
                    System.out.println("!");
                    FileUtils.copyFile(from, to);
                    FileUtils.deleteQuietly(from);
                }
                else {
                    FileUtils.copyDirectory(from, to);
                    FileUtils.deleteDirectory(from);

                }
                basicResponse = BasicResponse.builder()
                        .code(HttpStatus.OK.value())
                        .httpStatus(HttpStatus.OK)
                        .message("파일 이동 완료")
                        .accessToken("")
                        .refreshToken("")
                        .result(null)
                        .count(1).build();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                basicResponse = BasicResponse.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message("파일 이동 실패")
                        .accessToken("")
                        .refreshToken("")
                        .result(null).count(1).build();
            }


        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

    public ResponseEntity removeFile(Long member_id, String dir, String file) {
        File rm_file = new File(rootPath + dir + "/" + file);
        String response = new String();
        if (rm_file.exists() && rm_file.isFile()) {
            rm_file.delete();
            response = "파일 삭제 완료";
        } else if (rm_file.exists()) {
            response = "폴더로 존재함";
        } else {
            response = "존재하지 않는 파일";
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //파일업로드드
    public ResponseEntity uploadFile(Long member_id, String dir, MultipartFile file) {
        String response = new String();
        if (!file.isEmpty()) {
            try {
                file.transferTo(new File(rootPath + dir + "/" + file.getOriginalFilename()));
                response = file.getOriginalFilename();
            } catch (IOException e) {
                response = "파일 업로드 실패";
                throw new RuntimeException(e);
            }
        } else {
            response = "파일이 비어있음";
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    public ResponseEntity<BasicResponse> copy(Long member_id, String dir, String copyDir) {
        BasicResponse basicResponse = new BasicResponse();

        char[] ch = dir.toCharArray();

        int cnt=0;

        for(int i=ch.length-1; i>=0; i--){
            if(ch[i]=='/'){
                cnt=i+1;
                break;
            }
        }
        char[] result= new char[ch.length-cnt];

        for(int i=cnt; i<ch.length; i++) {
            result[i-cnt]=ch[i];
        }

        String path = String.valueOf(result);

        File from = new File(rootPath+dir);
        File to = new File(rootPath+copyDir+"/"+path);
        System.out.println(from);
        System.out.println(to);
        try {
            if(from.equals(to))
                to.renameTo(new File(from.getPath() + "복사본"));
            if(from.isFile()){

                FileUtils.copyFile(from, to);
            }
            else {
                FileUtils.copyDirectory(from, to);
            }
            basicResponse = BasicResponse.builder()
                    .code(HttpStatus.OK.value())
                    .httpStatus(HttpStatus.OK)
                    .message("파일 이동 완료")
                    .accessToken("")
                    .refreshToken("")
                    .result(null)
                    .count(1).build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            basicResponse = BasicResponse.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message("파일 이동 실패")
                    .accessToken("")
                    .refreshToken("")
                    .result(null).count(1).build();
        }


        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());



    }

    //이미지 읽어오기
    public ResponseEntity readImage(Long member_id, String dir, String image_name) {
        String path = rootPath + dir + image_name;
        String extend = FilenameUtils.getExtension(path);//이미지파일의 확장자
        //확장자 확인
        if (extend.equals("png") && extend.equals("jpg")) {
            try {
                InputStream imageStream = new FileInputStream(rootPath + dir + "/" + image_name);
                byte[] imageByteArray = imageStream.readAllBytes();
                imageStream.close();
                return new ResponseEntity<>(imageByteArray, HttpStatus.OK);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else return new ResponseEntity<>("해당파일은 웹에서 열 수 없습니다.", HttpStatus.OK);
    }

    //비디오 재생
    public ResponseEntity playVideo(Long member_id, String dir, String videoname) {
        File file = new File(rootPath+dir+"/"+videoname);
        String ext = file.getName().substring(file.getName().lastIndexOf(".")+1);
        //비디오 확장자 확인
        if(ext.equals("mp4")||ext.equals("avi")||ext.equals("mov")) {
            Resource resource = new FileSystemResource(rootPath+dir+"/"+videoname);
            HttpHeaders headers = new HttpHeaders();

            headers.set(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s",videoname));
            headers.setContentType(MediaType.parseMediaType("video/mp4"));

            return new ResponseEntity<Resource>(resource,headers,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("실행 할수 없는 확장자",HttpStatus.OK);
        }
    }

    public ResponseEntity<AttributesResponse> getAttribute(Long member_id, String file) {
        Path getFile = Paths.get(rootPath+file);
        System.out.println(getFile);
        AttributesResponse attributesResponse = new AttributesResponse();

        try {

            // 파일 속성 찾기
            Map<String, Object> getResult
                    = Files.readAttributes(getFile, "*");
            System.out.println("!");
            FileTime creationTime = (FileTime) getResult.get("creationTime");
            FileTime lastAccessTime = (FileTime) getResult.get("lastAccessTime");
            FileTime lastModifiedTime = (FileTime) getResult.get("lastModifiedTime");
            System.out.println("2");
            System.out.println(creationTime);


            attributesResponse = AttributesResponse.builder()
                    .code(HttpStatus.OK.value())
                    .httpStatus(HttpStatus.OK)
                    .message("상세정보 열람 생성")
                    .size(attributesResponse.getSize())
                    .lastModifiedTime(String.valueOf(lastModifiedTime))
                    .lastAccessTime(String.valueOf(lastAccessTime))
                    .creationTime(String.valueOf(creationTime))
                    .build();


        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(attributesResponse, attributesResponse.getHttpStatus());
    }
}
