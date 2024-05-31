package io.s3test.controller;

import io.s3test.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class FileController {
    
    
    private final S3Service s3Service;
    
    @PostMapping("/upload")
    public List<String> uploadFile(@RequestParam("file") List<MultipartFile> files) {
        return s3Service.saveFile(files);
    }
    
    @DeleteMapping("/delete")
    public int deleteFile(@RequestBody List<String> files) {
        return s3Service.deleteFile(files);
    }
  
    
}
