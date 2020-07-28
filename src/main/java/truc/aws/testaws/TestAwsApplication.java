package truc.aws.testaws;

import com.amazonaws.services.s3.internal.SkipMd5CheckStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class TestAwsApplication {

	public static void main(String[] args) {
		System.setProperty(SkipMd5CheckStrategy.DISABLE_GET_OBJECT_MD5_VALIDATION_PROPERTY,"true");
		SpringApplication.run(TestAwsApplication.class, args);
	}

}
