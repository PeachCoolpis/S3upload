package io.s3test.service;


import io.s3test.entity.UploadFile;
import io.s3test.repository.UploadFileRepository;
import io.s3test.s3.S3FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class S3Service {

    private final UploadFileRepository uploadFileRepository;
    private final S3FileUtils s3FileUtils;
    
    public List<UploadFile> saveFile(List<MultipartFile> multipartFiles) {
        try {
            List<UploadFile> uploadFiles = s3FileUtils.storeFiles(multipartFiles);
            uploadFileRepository.saveAll(uploadFiles);
            return uploadFiles;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
