package io.springboot.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.springboot.model.Student;
import io.springboot.util.SQSUtil;

@RestController
@RequestMapping(value="/sqs")
public class SqsResource {

	@Autowired
	SQSUtil sqsUtil;
	
	@RequestMapping(value = "/sendMessageQueue", method = RequestMethod.POST)
    public @ResponseBody void write(@RequestBody Student student){
		
			sqsUtil.sendSQSMessage(student);

	}
	
}