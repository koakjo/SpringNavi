package com.springnavi.domain;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.springnavi.app.message.FurikomiExecMessage;
import com.springnavi.infra.entity.CIF;
import com.springnavi.infra.entity.Idomeisai;
import com.springnavi.infra.repos.CIFRepository;
import com.springnavi.infra.repos.IdomeisaiReposotory;
import com.springnavi.infra.repos.YokinRepository;

@Service
public class FurikomiService {

	@Autowired
	CIFRepository cifRepos;
	@Autowired
	IdomeisaiReposotory idomeisaiRepos;
	@Autowired
	YokinRepository yokinRepos;
	
	/*
	 * 振込電文キューイング
	 */
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public FurikomiExecMessage furikomiQueuing (FurikomiExecMessage furikomiExecMessage, String topicname){
		
		try {
		//明細番号の生成を行う
		furikomiExecMessage.setIdono(UUID.randomUUID());
		//タイムスタンプ取得
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		//異動明細へインサート
		Idomeisai idomeisai = new Idomeisai();
		idomeisai.setIdono(furikomiExecMessage.getIdono());
		idomeisai.setShimukekouza(furikomiExecMessage.getShimukekouza());
		idomeisai.setHishimukekouza(furikomiExecMessage.getHishimukekouza());
		idomeisai.setKingaku(furikomiExecMessage.getKingaku());
		idomeisai.setExectime(format.format(calendar.getTime()));
		idomeisai.setStatus("QUEUING");
		
		idomeisaiRepos.saveAndFlush(idomeisai);
		
		//キューイング
		com.springnavi.app.GlobalValueables.producer.send(
				new ProducerRecord<>(
						topicname,
						com.springnavi.app.GlobalValueables.mapper.writeValueAsString(furikomiExecMessage)));
		return furikomiExecMessage;
		
		} catch (Exception e) {
			e.printStackTrace();
			furikomiExecMessage.setIdono(null);
			return furikomiExecMessage;
		}
	}
	
	/*
	 * 疎通用テストメソッド
	 */
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public boolean test() {
		Optional<CIF> cifLists = cifRepos.findById("0000001");
		return cifLists.isPresent();
	}
	
}
