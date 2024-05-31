package io.s3test.repository;

import io.s3test.entity.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UploadFileRepository extends JpaRepository<UploadFile,Long> {
    
    List<UploadFile> findByFileUrlIn(List<String> fileUrl);
    @Modifying
    @Query("delete from UploadFile uf where uf.fileUrl in :fileUrl")
    int deleteByFileUrlIn(List<String> fileUrl);
}
