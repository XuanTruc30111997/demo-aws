package truc.aws.testaws.service;

import com.amazonaws.services.s3.model.S3ObjectInputStream;

import java.io.File;
import java.util.List;

public interface S3Service {
    void uploadFile(String bucketName, String originalFileName, byte[] bytes) throws Exception;

    byte[] downloadFile(String bucketName, String fileUrl) throws Exception;

    void deleteFile(String bucketName, String fileUrl) throws Exception;

    List<String> listFiles(String bucketName) throws Exception;

    S3ObjectInputStream getContent(String bucketName, String fileName);
}
