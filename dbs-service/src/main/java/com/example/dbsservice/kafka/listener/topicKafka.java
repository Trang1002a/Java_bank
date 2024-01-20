package com.example.dbsservice.kafka.listener;

import org.springframework.kafka.annotation.KafkaListener;

public class topicKafka {

    @KafkaListener(topics = "kafka-topic")
    public void listenerKafkaTopic(String message) {
        System.out.println(message);
    }
}
