package org.fexisaf.savebyteimgonaws;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@RestController
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service service;

    @PostMapping("/saveimage")
    public String saveImageToS3(@RequestParam("image")MultipartFile file) throws IOException {

        PutObjectResult s3 = service.saveImageToS3(file);
        return "{message: image save successfully...,}";
    }

    @GetMapping("/img")
    public ResponseEntity<?> getImageFromS3(@RequestParam("image") String id) throws IOException {
        InputStream obj = service.getImage(id);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(obj));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteImageFromS3(@RequestParam("image") String id) throws IOException {
        String msg = service.deleteImage(id);
        return ResponseEntity.status(HttpStatus.OK).body(msg);
    }

    @PutMapping("/update")
    public String updateImageOnS3(@RequestParam("update") String id,
                                  @RequestParam("file") MultipartFile file) throws Exception {
       return service.updateImage(id, file);

    }
}
