package io.s3test.service;


import io.s3test.entity.UploadFile;
import io.s3test.repository.UploadFileRepository;
import io.s3test.s3.S3FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class S3Service {
    
    private final UploadFileRepository uploadFileRepository;
    private final S3FileUtils s3FileUtils;
    
    public List<String> saveFile(List<MultipartFile> multipartFiles) {
        try {
            List<UploadFile> uploadFiles = s3FileUtils.storeFiles(multipartFiles);
            uploadFileRepository.saveAll(uploadFiles);
            return uploadFiles.stream()
                    .map(UploadFile::getFileUrl)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public int deleteFile(List<String> fileUrl) {
        for (String urlFile : fileUrl) {
            s3FileUtils.deleteFile(urlFile);
        }
        return uploadFileRepository.deleteByFileUrlIn(fileUrl);
    }
}
