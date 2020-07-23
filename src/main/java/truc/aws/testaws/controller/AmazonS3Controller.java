package truc.aws.testaws.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import truc.aws.testaws.service.S3Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/amazonS3")
public class AmazonS3Controller {

    private S3Service s3Service;

    public AmazonS3Controller(@Qualifier("amazonS3ServiceImpl") S3Service s3Service)
    {
        this.s3Service = s3Service;
    }

    @PostMapping(value = "/{bucketName}/files", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Map<String, String> upload(@PathVariable("bucketName") String bucketName, @RequestPart(value = "file")MultipartFile files) throws Exception
    {
        s3Service.uploadFile(bucketName, files.getOriginalFilename(), files.getBytes());
        Map<String, String> result = new HashMap<>();
        result.put("key", files.getOriginalFilename());

        return result;
    }

    @GetMapping(value = "/{bucketName}/{keyName}", consumes = "application/octet-stream")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable("bucketName") String bucketName, @PathVariable("keyName") String keyName) throws Exception {
        byte[] data = s3Service.downloadFile(bucketName, keyName);
        ByteArrayResource resource = new ByteArrayResource(data);
        
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + keyName + "\"")
                .body(resource);
    }

    @DeleteMapping("/{bucketName}/files/{keyName}")
    public void delete(@PathVariable("bucketName") String bucketName, @PathVariable(value = "keyName") String keyName) throws Exception {
        s3Service.deleteFile(bucketName, keyName);
    }

    @GetMapping("/{bucketName}/files")
    public List<String> listObjects(@PathVariable("bucketName") String bucketName) throws Exception {
        return s3Service.listFiles(bucketName);
    }
}
