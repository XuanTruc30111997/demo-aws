package truc.aws.testaws.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class S3Properties {

    private static final String S3_PREFIX = "s3";

    @Autowired
    Environment environment;

    public String getUrl() {
        return getS3Propeties("url");
    }

    public String getAccessKey() {
        return getS3Propeties("accessKey");
    }

    public String getSecretKey() {
        return getS3Propeties("secretKey");
    }

    public String getTestBucket()
    {
        return getS3Bucket("testBucket");
    }

    public String getRegion()
    {
        return environment.getProperty("cloud.aws.region.static");
    }

    private String getS3Propeties(String properties)
    {
        return environment.getProperty(S3_PREFIX.concat(".").concat(properties));
    }

    private String getS3Bucket(String bucketName)
    {
        return environment.getProperty(S3_PREFIX.concat(".bucket.").concat(bucketName));
    }

}
