package com.springnavi.app.message;

import lombok.AllArgsConstructor;
import lombok.Data;

/*
 * 本来はリクエストとレスポンスは分けるべきですが、面倒なので同じにしてしまいます
 */
@Data
@AllArgsConstructor
public class LoginCheckMessage {
	
	private String cifno;
	private String password;
	private String outcome;

}
