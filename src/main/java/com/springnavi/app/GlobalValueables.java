package com.springnavi.app;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GlobalValueables {
	public static KafkaProducer<String, String> producer;
	public static KafkaProducer<String, String> producer10;
	public static ObjectMapper mapper;
	public static String topicname = "furikomi";
	public static String topicname10 = "furikomi";
	public static final long furikomiBatchNo = 1;

}
