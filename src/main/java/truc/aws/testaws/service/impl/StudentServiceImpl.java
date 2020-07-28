package truc.aws.testaws.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import truc.aws.testaws.helper.CSVHelper;
import truc.aws.testaws.model.Student;
import truc.aws.testaws.service.S3Service;
import truc.aws.testaws.service.StudentService;

import java.util.List;
import java.util.logging.Logger;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    S3Service s3Service;

    @Autowired
    CSVHelper csvHelper;

    @Override
    public List<Student> all(String bucketName, String fileName) {
        List<Student> students = null;
        try {
            students = csvHelper.ahihi(s3Service.getContent(bucketName, fileName));
            System.out.println(students);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return students;
    }
}
