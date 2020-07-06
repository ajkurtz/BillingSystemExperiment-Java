package com.andykurtz.api.providers;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;

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

    public void sendMessage(String message) {
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonSQS amazonSQS = AmazonSQSClientBuilder.standard().withRegion(region).withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials)).build();

        String now = String.valueOf(Calendar.getInstance().getTimeInMillis());

        SendMessageRequest sendMsgRequest = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageGroupId("bills")
                .withMessageDeduplicationId(now)
                .withMessageBody(message);
        amazonSQS.sendMessage(sendMsgRequest);
    }

}
