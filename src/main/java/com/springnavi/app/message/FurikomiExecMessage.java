package com.springnavi.app.message;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

/*
 * 本来はリクエストとレスポンスは分けるべきですが、面倒なので同じにしてしまいます
 * こちらに至ってはとても最悪な設計（①振込依頼②振込実行で使い回し、、）なのでとても参考になりません
 */
@Data
@AllArgsConstructor
public class FurikomiExecMessage {
	
	public String shimukekouza;
	public String hishimukekouza;
	public long kingaku;
	public UUID idono;

}
