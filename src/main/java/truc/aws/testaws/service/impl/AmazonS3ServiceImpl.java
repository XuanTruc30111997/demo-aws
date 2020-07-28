package truc.aws.testaws.service.impl;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import truc.aws.testaws.service.S3Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Service
public class AmazonS3ServiceImpl implements S3Service {

    private final Logger LOGGER = LoggerFactory.getLogger(AmazonS3ServiceImpl.class);
    private final AmazonS3 s3;

    @Autowired
    public AmazonS3ServiceImpl(AmazonS3 s3) {
        this.s3 = s3;
    }

    @Override
    public void uploadFile(String bucketName, String originalFileName, byte[] bytes) throws FileNotFoundException {
        File file = upload(bucketName, originalFileName, bytes);
        s3.putObject(bucketName, originalFileName,file);
    }

    @Override
    public byte[] downloadFile(String bucketName, String fileUrl) {
        return getFile(bucketName, fileUrl);
    }

    @Override
    public void deleteFile(String bucketName, String fileUrl) {
        s3.deleteObject(bucketName, fileUrl);
    }

    @Override
    public List<String> listFiles(String bucketName) {
        List<String> files = new LinkedList<>();
        s3.listObjects(bucketName).getObjectSummaries().forEach(item -> {
            files.add(item.getKey());
        });

        return files;
    }

    @Override
    public S3ObjectInputStream getContent(String bucketName, String fileName) {
        S3Object obj = s3.getObject(bucketName, fileName);
        return obj.getObjectContent();
    }

    private File upload(String bucketName, String name, byte[] content) throws FileNotFoundException {
        LOGGER.info("Start upload file");
        File file = new File("/" + name);
        try {
            file.canWrite();
            file.canRead();
            FileOutputStream iofs = null;
            iofs = new FileOutputStream(file);
            iofs.write(content);

            return file;
        }

        catch (IOException e) {
            LOGGER.error("File not found");
            throw new FileNotFoundException(e.getMessage());
        }
    }

    private byte[] getFile(String bucketName, String key) {
        LOGGER.info("Start download file");
        try
        {
            S3Object obj = s3.getObject(bucketName, key);
            S3ObjectInputStream stream = obj.getObjectContent();
            byte[] content = IOUtils.toByteArray(stream);
            obj.close();
            LOGGER.error("End download");
            return content;
        }

        catch (AmazonS3Exception e)
        {
            LOGGER.error("Key not found in S3: " + key, e);
        }

        catch (IOException ioe) {
            LOGGER.error("IOException when trying to parse S3ObjectInputStream to byte[]", ioe);
        }

        catch (NullPointerException e)
        {
            LOGGER.error("Null input stream", e);
        }

        LOGGER.error("End download");

        return null;
    }
}
