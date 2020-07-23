package truc.aws.testaws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import truc.aws.testaws.model.Student;
import truc.aws.testaws.model.StudentList;
import truc.aws.testaws.service.StudentService;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    StudentService studentService;

    @GetMapping()
    public ResponseEntity<StudentList> all(@RequestParam("bucketName") String bucketName, @RequestParam("fileName") String fileName)
    {
        StudentList students = new StudentList();
        students.setStudents(studentService.all(bucketName, fileName));
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

}
