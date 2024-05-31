package io.s3test.repository;

import io.s3test.entity.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadFileRepository extends JpaRepository<UploadFile,Long> {
}
