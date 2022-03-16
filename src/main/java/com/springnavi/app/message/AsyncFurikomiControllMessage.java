package com.springnavi.app.message;

import org.springframework.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Data;

/*
 * 本来はリクエストとレスポンスは分けるべきですが、面倒なので同じにしてしまいます
 */
@Data
@AllArgsConstructor
public class AsyncFurikomiControllMessage {
	
	//kafkaバッチのID
	public long id;
	//kafkaバッチの現在のステータス（レスポンス用）
	public String status;
	//kafkaバッチへのリクエスト
	//1:起動、2:停止
	public String request;
	//メッセージ（レスポンス用）
	public String message;

}
