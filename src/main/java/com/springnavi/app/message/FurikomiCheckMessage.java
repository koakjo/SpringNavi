package com.springnavi.app.message;

import java.util.List;

import org.springframework.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Data;

/*
 * 本来はリクエストとレスポンスは分けるべきですが、面倒なので同じにしてしまいます
 */
@Data
@AllArgsConstructor
public class FurikomiCheckMessage {

	@NonNull
	public String shimukekouza;
	public List<FurikomiCheckMeisaiMessage> meisais;

}
