package io.s3test.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class UploadFile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String originalFilename;
    private String locationFilename;
    private String fileUrl;
    private LocalDateTime uploadTime;
    
    public UploadFile(String originalFilename, String storeFilename, String fileUrl) {
        this.originalFilename = originalFilename;
        this.locationFilename = storeFilename;
        this.fileUrl = fileUrl;
        this.uploadTime = LocalDateTime.now();
    }
}
