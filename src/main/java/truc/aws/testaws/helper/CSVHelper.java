package truc.aws.testaws.helper;

import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.springframework.stereotype.Component;
import truc.aws.testaws.model.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
public class CSVHelper {
    public List<Student> ahihi(S3ObjectInputStream content) throws IOException {
        List<Student> students = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(content));
        while (true)
        {
            String line = reader.readLine();
            if(line == null)
            {
                break;
            }

            String[] strings = line.split(",");
            students.add(new Student(Integer.parseInt(strings[0]), strings[1], strings[2], Integer.parseInt(strings[3])));
        }

        return students;
    }
}
