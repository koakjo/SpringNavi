package com.springnavi.app.message;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FurikomiCheckMeisaiMessage {

	public FurikomiCheckMeisaiMessage() {
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
	public UUID idono;
	// status - 0:undone 1:done
	public String status;
	
}
