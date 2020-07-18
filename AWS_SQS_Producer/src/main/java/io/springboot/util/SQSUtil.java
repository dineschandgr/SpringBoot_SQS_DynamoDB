package io.springboot.util;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.core.env.ResourceIdResolver;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageResult;

import io.springboot.model.Student;

@Component
public class SQSUtil {

    @Value("${sqs.url}")
    private String sqsUrl;

    @Value("${aws.accessKey}")
    private String awsAccessKey;

    @Value("${aws.secretKey}")
    private String awsSecretKey;

	/*
	 * @Value("${aws.region}") private String awsRegion;
	 */
    
    @Autowired
    QueueMessagingTemplate messagingTemplate;
    
    private static final Logger logger = LoggerFactory.getLogger(SQSUtil.class);

	/*
	 * @PostConstruct private void postConstructor() {
	 * 
	 * logger.info("SQS URL: " + sqsUrl);
	 * 
	 * AWSCredentialsProvider awsCredentialsProvider = new
	 * AWSStaticCredentialsProvider( new BasicAWSCredentials(awsAccessKey,
	 * awsSecretKey) );
	 * 
	 * this.amazonSQS =
	 * AmazonSQSClientBuilder.standard().withCredentials(awsCredentialsProvider).
	 * build(); }
	 */

    public void sendSQSMessage(Student student) {

        logger.info("Sending SQS message: " + student);
        messagingTemplate.convertAndSend("myFirstQueue",student);
        //SendMessageResult result = this.amazonSQS.sendMessage(this.sqsUrl, message);

        //logger.info("SQS Message ID: " + result.getMessageId());
    }
}