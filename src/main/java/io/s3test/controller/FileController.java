package io.s3test.controller;

import io.s3test.entity.UploadFile;
import io.s3test.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class FileController {
    
    
    private final S3Service s3Service;
    
    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadFile(@RequestParam("file") List<MultipartFile> files) {
        try {
            List<UploadFile> uploadFiles = s3Service.saveFile(files);
            
            List<String> fileUrls = uploadFiles.stream()
                    .map(UploadFile::getFileUrl)
                    .collect(Collectors.toList());
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Files uploaded successfully");
            response.put("files", fileUrls);
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "File upload failed");
            errorResponse.put("error", e.getMessage());
            
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
}
