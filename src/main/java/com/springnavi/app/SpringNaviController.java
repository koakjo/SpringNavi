package com.springnavi.app;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.catalina.connector.Response;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springnavi.app.message.AsyncFurikomiControllMessage;
import com.springnavi.app.message.FurikomiCheckMessage;
import com.springnavi.app.message.FurikomiExecMessage;
import com.springnavi.app.message.LoginCheckMessage;
import com.springnavi.domain.AsyncBatchControllService;
import com.springnavi.domain.CheckService;
import com.springnavi.domain.FurikomiService;

@RestController
@RequestMapping(value = "/v1/backend")
public class SpringNaviController {

	@Autowired
	FurikomiService furikomiService;
	@Autowired
	CheckService checkService;
	@Autowired
	AsyncBatchControllService asyncBatchControllService;

	/*
	 * コンストラクタ。
	 */
	@PostConstruct
	public void SpringNaviConstruct() {

		// kafka
		Properties properties = new Properties();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		com.springnavi.app.GlobalValueables.producer = new KafkaProducer<>(properties, new StringSerializer(),
				new StringSerializer());
		// com.springnavi.app.GlobalValueables.producer.send(new ProducerRecord<>(
		// com.springnavi.app.GlobalValueables.topicname,"test from application"));

		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		com.springnavi.app.GlobalValueables.producer10 = new KafkaProducer<>(properties, new StringSerializer(),
				new StringSerializer());

		// ObjectMapper
		com.springnavi.app.GlobalValueables.mapper = new ObjectMapper();
	}

	/*
	 * ログイン処理時のPWとID（CIFの番号ですが）を確認する処理 ログインとか言ってるんですけど、セッション管理とかしてません。すみません。
	 */
	@RequestMapping(value = "/login")
	@PostMapping
	public ResponseEntity<?> loginCheck(@Validated @RequestBody LoginCheckMessage loginCheckMessage) {
		loginCheckMessage = checkService.loginCheck(loginCheckMessage);
		return new ResponseEntity<>(loginCheckMessage, HttpStatus.OK);
	}

	/*
	 * 振込処理が実行されたか、まだ未済かを確認する処理
	 */
	@RequestMapping(value = "/furikomicheck")
	@PostMapping
	public ResponseEntity<?> furikomiCheck(@Validated @RequestBody FurikomiCheckMessage furikomiCheckMessage) {
		furikomiCheckMessage = checkService.furikomiCheck(furikomiCheckMessage);
		return new ResponseEntity<>(furikomiCheckMessage, HttpStatus.OK);
	}

	/*
	 * kafka経由での非同期振込処理スタート処理
	 */
	@RequestMapping(value = "/furikomi")
	@PostMapping
	public ResponseEntity<?> furikomiExec(@Validated @RequestBody FurikomiExecMessage furikomiExecMessage) {
		furikomiExecMessage = furikomiService.furikomiQueuing(furikomiExecMessage,
				com.springnavi.app.GlobalValueables.topicname);
		return new ResponseEntity<>(furikomiExecMessage, HttpStatus.OK);
	}

	/*
	 * kafka経由での非同期振込処理スタート処理
	 */
	@RequestMapping(value = "/furikomi10")
	@PostMapping
	public ResponseEntity<?> furikomiExec10(@Validated @RequestBody FurikomiExecMessage furikomiExecMessage) {
		furikomiExecMessage = furikomiService.furikomiQueuing(furikomiExecMessage,
				com.springnavi.app.GlobalValueables.topicname10);
		return new ResponseEntity<>(furikomiExecMessage, HttpStatus.OK);
	}

	/*
	 * kafka経由での非同期振込処理ストップ処理
	 * 
	 */
	@RequestMapping(value = "/asyncfurikomistop")
	@PostMapping
	public ResponseEntity<?> asyncFurikomiStop(
			@Validated @RequestBody AsyncFurikomiControllMessage asyncFurikomiControllMessage) {
		asyncFurikomiControllMessage = asyncBatchControllService.asyncStop(asyncFurikomiControllMessage, 1);
		return new ResponseEntity<>(asyncFurikomiControllMessage, HttpStatus.OK);
	}

	/*
	 * kafka経由での非同期振込処理スタート処理
	 */
	@RequestMapping(value = "/asyncfurikomistart", produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping
	public ResponseEntity<?> asyncFurikomiStart(@RequestBody AsyncFurikomiControllMessage asyncFurikomiControllMessage)
			throws Exception {
		asyncFurikomiControllMessage = asyncBatchControllService.asyncStart(asyncFurikomiControllMessage, 1);
		return new ResponseEntity<>(asyncFurikomiControllMessage, HttpStatus.OK);
	}

	/*
	 * kafka経由での非同期振込処理ストップ処理（10秒スパンBatch）
	 * 
	 */
	@RequestMapping(value = "/asyncfurikomistop10")
	@PostMapping
	public ResponseEntity<?> asyncFurikomiStop10(
			@Validated @RequestBody AsyncFurikomiControllMessage asyncFurikomiControllMessage) {
		asyncFurikomiControllMessage = asyncBatchControllService.asyncStop(asyncFurikomiControllMessage, 10);
		return new ResponseEntity<>(asyncFurikomiControllMessage, HttpStatus.OK);
	}

	/*
	 * kafka経由での非同期振込処理スタート処理（10秒スパンBatch）
	 */
	@RequestMapping(value = "/asyncfurikomistart10", produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping
	public ResponseEntity<?> asyncFurikomiStart10(
			@RequestBody AsyncFurikomiControllMessage asyncFurikomiControllMessage) throws Exception {
		asyncFurikomiControllMessage = asyncBatchControllService.asyncStart(asyncFurikomiControllMessage, 10);
		return new ResponseEntity<>(asyncFurikomiControllMessage, HttpStatus.OK);
	}

	/*
	 * 疎通用テストメソッド テストコード書けよ、だとは思いますがお許しください
	 */
	@RequestMapping(value = "/test")
	public void test() {
		if (furikomiService.test()) {
			System.out.println("----- here test is succeeded-----");
		} else {
			System.out.println("!!!!! NO !!!!!");
		}
	}

	/*
	 * 疎通用テストメソッド テストコード書けよ、だとは思いますがお許しください
	 */
	@RequestMapping(value = "/testtimeout")
	public void testtimeout() {
		try {
			if (furikomiService.testTimeOut()) {
				System.out.println("----- here test is succeeded-----");
			} else {
				System.out.println("!!!!! NO !!!!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("timeout exception occurd. check details.");
		}
	}

}
