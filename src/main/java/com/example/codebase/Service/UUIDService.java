package com.example.codebase.Service;

import com.example.codebase.Model.Dao.Member;
import com.example.codebase.Model.Dao.Share;
import com.example.codebase.Repository.ShareRepository;
import com.example.codebase.Response.BasicResponse;
import org.apache.commons.io.FileUtils;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

import static com.example.codebase.Service.FileService.rootPath;

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
                .message(Long.toString(share.getUuid()))
                .build();

        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

    public ResponseEntity<BasicResponse> getUUID(String member_id, Long uuid, String path) {
        Share share = shareRepository.findById(uuid).orElseGet(Share::new);

        BasicResponse basicResponse = new BasicResponse();

        String check = member_id+path;

        if(share.getUri().equals(check)) {

            basicResponse = BasicResponse.builder()
                    .code(HttpStatus.OK.value())
                    .httpStatus(HttpStatus.OK)
                    .message("성공")
                    .build();
        }
        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

    public ResponseEntity downloadUUID(HttpServletResponse response,Long uuid){
        try {
            Share share = shareRepository.findById(uuid).orElseGet(Share::new);
            String path = rootPath + share.getUri();
            File file = new File(path);
            if(!file.exists()){
                byte fileByte[] = FileUtils.readFileToByteArray(file);
                response.setContentType("application/octet-stream");
                response.setContentLength(fileByte.length);


                response.setHeader("Content-Disposion","attach; fileName=\""+ URLEncoder.encode(file.getName(),"UTF-8")+"\";");
                response.setHeader("Content-Transfer-Encoding","binary");

                response.getOutputStream().write(fileByte);
                response.getOutputStream().flush();
                response.getOutputStream().close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>("디렉토리는 다운로드 불가",HttpStatus.OK);
    }
}
