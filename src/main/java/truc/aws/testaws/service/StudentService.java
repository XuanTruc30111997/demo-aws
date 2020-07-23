package truc.aws.testaws.service;

import truc.aws.testaws.model.Student;

import java.util.List;

public interface StudentService {
    List<Student> all(String bucketName, String fileName);
}
