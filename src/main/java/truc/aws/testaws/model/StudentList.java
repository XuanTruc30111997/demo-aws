package truc.aws.testaws.model;

import java.util.List;

public class StudentList {
    private List<Student> students;

    public StudentList(){

    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Student> getStudents() {
        return students;
    }
}
