package io.springboot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs;
import org.springframework.context.annotation.Bean;

import com.amazonaws.auth.BasicAWSCredentials;


@SpringBootApplication
@EnableSqs
public class AwsSqsSpringBootApplication {
	  
	@Value("${aws.accessKey}")
    private String awsAccessKey;
    @Value("${aws.secretKey}")
    private String awsSecretKey;
    
	public static void main(String[] args) {
		SpringApplication.run(AwsSqsSpringBootApplication.class, args);
	}
	
	@Bean
	public BasicAWSCredentials amazonAWSCredentials() {
	    return new BasicAWSCredentials(awsAccessKey, awsSecretKey);
	}
	
	
}
