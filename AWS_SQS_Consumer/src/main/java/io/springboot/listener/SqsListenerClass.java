package io.springboot.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQSAsyncClient;

import io.springboot.model.Student;
import io.springboot.repository.StudentRepository;

@Component
public class SqsListenerClass {

	private static final Logger log = LoggerFactory.getLogger(SqsListenerClass.class);
	
    
    @Value("${sqs.url}")
    private String sqsUrl;

    @Autowired
    StudentRepository studentRepository;
    
    @Autowired
    BasicAWSCredentials awsCredentials;

	@SqsListener(value="myFirstQueue",deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
	public void getMessage(Student student,@Headers MessageHeaders headers) {
		log.info("Message "+student);
		log.info("message id is "+headers.get("MessageId"));
		log.info("student saved " +studentRepository.save(student));
		
	}
	
	
	@Primary
	@Bean
	public AmazonSQSAsyncClient amazonSQSAsyncClient()
	{

	    AmazonSQSAsyncClient amazonSQSAsyncClient= new AmazonSQSAsyncClient(awsCredentials);

	    if (!StringUtils.isEmpty(sqsUrl)) {
	        amazonSQSAsyncClient.setEndpoint(sqsUrl);

	    }
		return amazonSQSAsyncClient;

	}
}
