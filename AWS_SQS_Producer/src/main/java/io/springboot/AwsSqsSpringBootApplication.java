package io.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.core.env.ResourceIdResolver;
import org.springframework.cloud.aws.messaging.config.QueueMessageHandlerFactory;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;

@SpringBootApplication
public class AwsSqsSpringBootApplication {

	 public static void main(String[] args) {
		SpringApplication.run(AwsSqsSpringBootApplication.class, args);
	 }
	
	 @Bean
	 public AmazonSQSAsync amazonSQSAsync() {
	    return AmazonSQSAsyncClientBuilder.defaultClient();
	 }
	 
	 @Bean
	 public QueueMessagingTemplate queueMessagingTemplate(AmazonSQSAsync amazonSqs) {
	    return new QueueMessagingTemplate(amazonSqs);
	 }


}
