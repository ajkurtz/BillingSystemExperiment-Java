package com.andykurtz.api.providers;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QueueProvider {

    @Value("${application.queueUrl}")
    private String queueUrl;

    @Value("${application.accessKey}")
    private String accessKey;

    @Value("${application.secretKey}")
    private String secretKey;

    @Value("${application.region}")
    private String region;

    public Message receiveMessage() {
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonSQS amazonSQS = AmazonSQSClientBuilder.standard().withRegion(region).withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials)).build();

        ReceiveMessageResult receiveMessageResult = amazonSQS.receiveMessage(queueUrl);
        List<Message> messages = receiveMessageResult.getMessages();
        if (messages.size() > 0) {
            return messages.get(0);
        } else {
            return null;
        }
    }

    public void deleteMessage(Message message) {
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonSQS amazonSQS = AmazonSQSClientBuilder.standard().withRegion(region).withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials)).build();

        amazonSQS.deleteMessage(queueUrl, message.getReceiptHandle());
    }
}
