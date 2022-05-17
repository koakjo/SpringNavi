package com.springnavi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/*
 * このアプリは
 * ①kafka経由で行う非同期振込処理（簡易DBQ）の起動、監視APIの提供
 * ②振込処理が実行されたかの照会APIの提供
 * ③ログイン処理時のCIF照会APIの提供
 * 本来であれば別アプリでマイクロサービス的にやるのが良いですが
 * k8s、OpenShiftのような仕組みを考えた時に
 * システムの運用もきっとそのうちGUIで行うようになると思ったので
 * このような設計でしています
 */
@SpringBootApplication
@ComponentScan("com.springnavi.infra.repos")
@ComponentScan("com.springnavi.infra.entity")
@ComponentScan("com.springnavi.domain")
@ComponentScan("com.springnavi.app")
@ComponentScan("com.springnavi.app.message")
@ComponentScan
public class SpringNaviApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringNaviApplication.class, args);
		System.out.println("----- App is started -----");
	}
}
