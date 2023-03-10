package com.example.codebase.Service;

import com.example.codebase.Response.AttributesResponse;
import com.example.codebase.Response.BasicResponse;
import com.example.codebase.Response.getDirectory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import java.util.*;

@Service
public class FileService {


    public static String rootPath = "/mos_file/"; //root Path
    //public static String rootPath = "C:/Users/mun/Desktop/파일저장테스트/";

    public ResponseEntity<BasicResponse> makeDir(String member_id, String dir) {   //폴더 생성

        File newFile = new File(rootPath + member_id + dir);
        BasicResponse basicResponse = new BasicResponse();


        if (!newFile.exists()) {
            try {
                newFile.mkdir();
                basicResponse = BasicResponse.builder()
                        .code(HttpStatus.OK.value())
                        .httpStatus(HttpStatus.OK)
                        .message("폴더 생성 성공")
                        .build();
            } catch (Exception e) {

                basicResponse = BasicResponse.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message("폴더 생성 실패")
                        .build();
            }
        }
        else
            basicResponse = BasicResponse.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message("폴더 생성 실패")
                    .build();
        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

    public ResponseEntity<BasicResponse> makeFile(String member_id, String dir, String file) throws IOException { //파일 생성
        File newDir = new File(rootPath + member_id + dir);

        BasicResponse basicResponse = new BasicResponse();
        if (!newDir.exists())
            if (newDir.mkdir())
                System.out.println("폴더 생성 성공");

        File newFile = new File(newDir, file);
        if (!newFile.exists()) {

            try {
                newFile.createNewFile();

                basicResponse = BasicResponse.builder()
                        .code(HttpStatus.OK.value())
                        .httpStatus(HttpStatus.OK)
                        .message("파일 생성 성공")
                        .build();
            } catch (Exception e) {
                basicResponse = BasicResponse.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message("파일 생성 실패")
                        .build();

            }


        }
        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

    public ResponseEntity<BasicResponse> renameFile(String member_id, String dir, String file, String rename) { //파일 이름 변경
        File newDir = new File(rootPath + member_id+dir);
        File newFile = new File(newDir, file);

        BasicResponse basicResponse = new BasicResponse();

        File changeFile = new File(newDir, rename); //변경할 이름

        if (newFile.exists()) {    // 파일이 존재할 때만 이름 변경
            if (newFile.renameTo(changeFile))
                basicResponse = BasicResponse.builder()
                        .code(HttpStatus.OK.value())
                        .httpStatus(HttpStatus.OK)
                        .message("파일 이름 변경 성공")
                        .build();
            else
                basicResponse = BasicResponse.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message("파일 이름 변경 실패")
                        .build();
        } else {
            basicResponse = BasicResponse.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message("파일 이름 변경 실패")
                    .build();
        }
        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

    public ResponseEntity<BasicResponse> renameDir(String member_id, String dir, String rename) {  //폴더 이름 변경
        File newDir = new File(rootPath+member_id+dir);

        BasicResponse basicResponse = new BasicResponse();

        File changeFile = new File(rootPath+member_id, rename); //변경할 이름
        System.out.println(newDir);
        System.out.println(changeFile);
        if (newDir.exists()) {    // 파일이 존재할 때만 이름 변경
            if (newDir.renameTo(changeFile))
                basicResponse = BasicResponse.builder()
                        .code(HttpStatus.OK.value())
                        .httpStatus(HttpStatus.OK)
                        .message("폴더 이름 변경 성공")
                        .build();
            else
                basicResponse = BasicResponse.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message("폴더 이름 변경 실패")
                        .build();
        } else {
            basicResponse = BasicResponse.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message("폴더 이름 변경 실패")
                    .build();
        }
        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

    public ResponseEntity<BasicResponse> getDir(String member_id, String dir) {    //폴더 구조 가져오기
        File newDir = new File(rootPath + member_id + dir);

        File[] fileList = newDir.listFiles();

        BasicResponse basicResponse = new BasicResponse();

        List<getDirectory> list = new ArrayList<getDirectory>();

        if (fileList != null) {
            for (File file : fileList) {
                getDirectory insert = new getDirectory();
                if (file.getName().contains(".txt")) {
                    insert = getDirectory.builder()
                            .isDir(false)
                            .name(file.getName())
                            .ext("txt")
                            .build();
                    list.add(insert);
                } else if (file.getName().contains(".png")) {
                    insert = getDirectory.builder()
                            .isDir(false)
                            .name(file.getName())
                            .ext("png")
                            .build();
                    list.add(insert);
                } else if (file.getName().contains(".jpg")) {
                    insert = getDirectory.builder()
                            .isDir(false)
                            .name(file.getName())
                            .ext("jpg")
                            .build();
                    list.add(insert);
                } else if (file.getName().contains(".mp4")) {
                    insert = getDirectory.builder()
                            .isDir(false)
                            .name(file.getName())
                            .ext("mp4")
                            .build();
                    list.add(insert);
                }else if (file.getName().contains(".avi")) {
                    insert = getDirectory.builder()
                            .isDir(false)
                            .name(file.getName())
                            .ext("avi")
                            .build();
                    list.add(insert);
                }else if (file.getName().contains(".mov")) {
                    insert = getDirectory.builder()
                            .isDir(false)
                            .name(file.getName())
                            .ext("mov")
                            .build();
                    list.add(insert);
                }else  {
                    insert = getDirectory.builder()
                            .isDir(true)
                            .name(file.getName())
                            .ext("Directory")
                            .build();
                    list.add(insert);
                }
            }
        }

        basicResponse = BasicResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("폴더 내부 구조 확인")
                .result(list)
                .count(list.size()).build();
        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

    //디렉토리 삭제(하위폴더,파일 모두 삭제됨)

    public ResponseEntity removeDir(String member_id, String rm_dir) {
        File rm = new File(rootPath +member_id+ rm_dir);
        String response = new String();

        if (rm.exists() && rm.isDirectory()) {
            try {
                Files.walk(rm.toPath())
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            response = "폴더 삭제완료";
        } else if (rm.exists()) {
            response = "폴더가 아닌 파일 이름";
        } else {
            response = "존재하지 않는 폴더";
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    public ResponseEntity moveDir(String member_id, String dir, String mv_dir) throws IOException {

       

        File from = new File(rootPath + member_id + dir);
        File to = new File(rootPath+member_id+mv_dir+"/" +from.getName());
        if(from.isDirectory()&&from.exists()&& !to.exists()){
            FileUtils.moveDirectory(from,to);
        }
        else if(to.exists()) return new ResponseEntity<>("이미 폴더가 존재함",HttpStatus.OK);
        else if(!to.exists()){
            FileUtils.moveFile(from,to);
        }
        else return new ResponseEntity<>(from.getPath() + "\n" + to.getPath(),HttpStatus.OK);
        return new ResponseEntity<>("파일 이동 완료",HttpStatus.OK);
    }

    public ResponseEntity removeFile(String member_id, String file) {
        File rm_file = new File(rootPath+member_id + file);
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
    public ResponseEntity uploadFile(String member_id, String dir, MultipartHttpServletRequest mhsr) {
        String response = new String();
        Iterator<String> fileNames = mhsr.getFileNames();
        List<MultipartFile> files = new ArrayList<>();
        while(fileNames.hasNext())files.add(mhsr.getFile(fileNames.next()));
        if(files.isEmpty()) response = "파일이 비었음";
        else {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    try {
                        file.transferTo(new File(rootPath + member_id + dir +"/"+ file.getOriginalFilename()));

                        response = file.getOriginalFilename();
                    } catch (IOException e) {
                        response = "파일 업로드 실패";
                        throw new RuntimeException(e);
                    }
                } else {
                    response = "파일이 비어있음";
                }
            }
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    public ResponseEntity copy(Long member_id, String dir, String copyDir) {

        File from = new File(rootPath+member_id+dir);
        File cp_file = new File(rootPath+member_id+copyDir);//파일이름뽑아내기용
        File to = new File(rootPath+member_id+dir+"/"+cp_file.getName());
        File rt_file = new File(rootPath+member_id+dir+"/"+cp_file.getName());//파일반환용
        if(rt_file.exists()) {
            int i = 1;
            while (true) {
                rt_file = new File(to.getPath() + "복사본" + i);
                if (!rt_file.exists()) {
                    try {
                        if (to.isFile()) {
                            Files.copy(from.toPath(),rt_file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                            break;
                        } else {
                            rt_file.mkdir();
                            FileUtils.copyDirectory(cp_file, rt_file);
                            break;
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                i++;
            }
        }
        else{
            try{
                if(to.isFile()){
                    Files.copy(from.toPath(),to.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
                else {
                    rt_file.mkdir();
                    FileUtils.copyDirectory(cp_file, rt_file);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return new ResponseEntity<>("저장완료",HttpStatus.OK);
    }

    //텍스트 읽어오기
    public ResponseEntity readFile(String member_id,String filename){
        try {
            File file = new File(rootPath + member_id + filename);
            String ext = FilenameUtils.getExtension(file.getPath());

            //예외처리
            if(!ext.equals("txt")) return new ResponseEntity<>("읽을 수 없는 파일입니다.",HttpStatus.OK);

            FileReader fileReader = new FileReader(file);
            int singleCh = 0;
            String txt = "";
            while((singleCh=fileReader.read())!=-1){
                txt+=(char)singleCh;
            }
            fileReader.close();
            return new ResponseEntity<>(txt,HttpStatus.OK);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //이미지 읽어오기
    public ResponseEntity readImage(String member_id, String imagepath) {
        String path =rootPath + member_id + imagepath;
        String extend = FilenameUtils.getExtension(path);//이미지파일의 확장자
        //확장자 확인
        if (extend.equals("png") || extend.equals("jpg")) {
            try {
                InputStream imageStream = new FileInputStream(path);
                byte[] imageByteArray = imageStream.readAllBytes();
                imageStream.close();
                return new ResponseEntity<>(imageByteArray, HttpStatus.OK);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else return new ResponseEntity<>("해당파일은 웹에서 열 수 없습니다.", HttpStatus.OK);
    }

    //비디오 재생
    public ResponseEntity playVideo(String member_id, String dir, String videoname) {
        File file = new File(rootPath +member_id+ dir+videoname);
        String ext = file.getName().substring(file.getName().lastIndexOf(".")+1);
        //비디오 확장자 확인
        if(ext.equals("mp4")||ext.equals("avi")||ext.equals("mov")) {
            Resource resource = new FileSystemResource(rootPath+member_id+dir+videoname);
            HttpHeaders headers = new HttpHeaders();

            headers.set(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s",videoname));
            headers.setContentType(MediaType.parseMediaType("video/mp4"));

            return new ResponseEntity<Resource>(resource,headers,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("실행 할수 없는 확장자",HttpStatus.OK);
        }
    }

    public ResponseEntity<AttributesResponse> getAttribute(String member_id, String file) {
        Path getFile = Paths.get(rootPath+member_id+file);
        System.out.println(getFile);
        AttributesResponse attributesResponse = new AttributesResponse();

        try {

            // 파일 속성 찾기
            Map<String, Object> getResult
                    = Files.readAttributes(getFile, "*");
            FileTime creationTime = (FileTime) getResult.get("creationTime");
            FileTime lastAccessTime = (FileTime) getResult.get("lastAccessTime");
            FileTime lastModifiedTime = (FileTime) getResult.get("lastModifiedTime");

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

    //다운로드
    public ResponseEntity downloadFile(HttpServletResponse response, String member_id,String dir){
        try {
            File file = new File(rootPath + member_id + dir);
            byte fileByte[] = FileUtils.readFileToByteArray(file);
            response.setContentType("application/octet-stream");
            response.setContentLength(fileByte.length);

            response.setHeader("Content-Disposion","attach; fileName=\""+ URLEncoder.encode(file.getName(),"UTF-8")+"\";");
            response.setHeader("Content-Transfer-Encoding","binary");

            response.getOutputStream().write(fileByte);
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>("디렉토리는 다운로드 불가",HttpStatus.OK);
    }
}
