# SpringBoot_SQS_DynamoDB
Spring Boot Publishes publishes message to AWS SQS. A Spring boot listener reads from SQS Queue and loads the data in Dynamo DB. DynamoDB Stream triggers a lambda function which inserts data to SNS topic and sends an email upon record insertion

**This project  focuses on**
1. Writing a Spring Boot Producer application that writes messages to an AWS SQS queue
2. Writing a Spring Boot Listener application that consumes message from the queue asynchronously
3. The SQS Listener writes the message to AWS Dynamo DB
4. The DynamoDB Stream captures the new data and calls a lambda function
5. The Lambda function writes the newly inserted data to an SNS topic
6. The SNS topic will send the data to email

**Producer Application**

1. Create a Queue in SQS and copy the url of the queue in Application.properties file
2. Add the AWS Access key and Secret access key in the same file. Make sure that the AWS user has the right permission to write and read data from the AWS SQS
3. The producer application needs the maven dependency **spring-cloud-aws-messaging**
4. We need to define a bean called QueueMessagingTemplate
5. We need to define another bean called AmazonSQSAsync
6. We need to define a component where we use QueueMessagingTemplate to convert and send the message to the SQS queue with the queue url and AWS Credentials. We need not set the credentials explicitly as it will be set by Spring Boot automatically
7. Run this application and send a post request from postman with the url
    http://localhost:8090/sqs/sendMessageQueue
8. If all the configuration is done correctly, your message will be posted in the AWS SQS queue

**Consumer Application**

a. Retrieve Message

1. The Listener application needs the dependency spring-cloud-aws-messaging to receive message from AWS SQS queue
2. This application also needs couple of dependencies like aws-java-sdk-dynamodb and spring-data-dynamodb to write data to AWS dynamoDB
3. Define a bean called BasicAWSCredentials which takes in AWS Access Key and AWS Secret Key
4. Define a Component for the SQSListener and define a bean called AmazonSQSAsyncClient
5. Annotate the getMessage with SqsListener Annotation. This enables this method to always listen to the queue and aysnchronously retrieve the messages. The retrieved Student object is written to the DynamoDB Student table
6. Start the Spring Boot application and it starts listening to the Queue immediately and reads the message as you post from your producer

b. Write message to DynamoDB

1. Define the student model object with @DynamoDBTable(tableName = “Student”). This makes the model object as DynamoDB entity
2. Also define other dynamoDB annotations for the attributes
3. Define a repository interface for DynamoDB. This repository is similar to Spring Data JPA
4. Create a configuration class called DynamDBConfig which scans the DynamoDB repository
5. We define the AmazonDynamoDB bean in this config

**DynamoDB Streams, Lambda & SNS**

1. Create an SNS topic and create the subscribers like Email. This could be any email service like Gmail or Mailinator
2. Create a Lambda function to read newly inserted data from the DynamoDB Stream
3. Provide role for the Lambda to access DynamoDB data and push data to the SNS
4. Enable DynamoDB Streams for the student Table
5. The Streams help to capture the newly inserted record
6. Create a trigger to the Lambda function
7. Once the data is inserted to DynamoDB by the SQS Listener, automatically Lambda function is executed and the data sent to SNS. SNS will send email to you


This may look like a complicated use-case but it makes the makes the best use of all relevant technologies

And this is a completely serverless solution and you can make it highly scalable without the need to maintain any Servers
