package io.s3test.s3;


import io.s3test.entity.UploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3FileUtils {
    
    private final S3Client s3Client;
    
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> uploadFiles = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                uploadFiles.add(storeFile(multipartFile));
            }
        }
        
        return uploadFiles;
    }
    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {
        
        if (multipartFile.isEmpty()) {
            return null;
    }
    String originalFilename = multipartFile.getOriginalFilename();
    String storeFileName = createStoreFileName(originalFilename);
    // S3에 파일 업로드
        s3Client.putObject(PutObjectRequest.builder()
                .bucket(bucketName)
                .key(storeFileName)
                .build(), software.amazon.awssdk.core.sync.RequestBody.fromBytes(multipartFile.getBytes()));
        String fileUrl  = s3Client.utilities().getUrl(b -> b.bucket(bucketName).key(storeFileName)).toExternalForm();
        return new UploadFile(originalFilename, storeFileName,fileUrl);
    }
    
    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
    
    
}
