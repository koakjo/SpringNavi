package com.springnavi.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.springnavi.app.message.FurikomiCheckMeisaiMessage;
import com.springnavi.app.message.FurikomiCheckMessage;
import com.springnavi.app.message.LoginCheckMessage;
import com.springnavi.infra.entity.CIF;
import com.springnavi.infra.entity.Idomeisai;
import com.springnavi.infra.repos.CIFRepository;
import com.springnavi.infra.repos.IdomeisaiReposotory;

@Service
public class CheckService {

	@Autowired
	CIFRepository cifRepos;
	@Autowired
	IdomeisaiReposotory idomeisaiRepos;

	/*
	 * ログインチェック
	 */
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public LoginCheckMessage loginCheck(LoginCheckMessage loginCheckMessage) {
		Optional<CIF> ocif;
		ocif = cifRepos.findById(loginCheckMessage.getCifno());
		if (ocif.get().getPassword().equals(loginCheckMessage.getPassword())) {
			loginCheckMessage.setOutcome("OK");
		} else {
			loginCheckMessage.setOutcome("NG");
		}
		return loginCheckMessage;
	}

	/*
	 * 振込チェック
	 */
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public FurikomiCheckMessage furikomiCheck(FurikomiCheckMessage furikomiCheckMessage) {
		ArrayList<FurikomiCheckMeisaiMessage> array = new ArrayList<FurikomiCheckMeisaiMessage>();
		List<Idomeisai> Lidomeisai = idomeisaiRepos.findByShimukekouza(furikomiCheckMessage.getShimukekouza());
		Iterator<Idomeisai> it = Lidomeisai.iterator();
		while (it.hasNext()) {
			// Autowiredにしない理由は明確に新しいオブジェクト使いたかったからです
			FurikomiCheckMeisaiMessage meisai = new FurikomiCheckMeisaiMessage();
			Idomeisai idomeisai = new Idomeisai();
			idomeisai = (Idomeisai) it.next();
			meisai.setIdono(idomeisai.getIdono());
			meisai.setStatus(idomeisai.getStatus());
			array.add(meisai);
		}
		furikomiCheckMessage.setMeisais(array);
		return furikomiCheckMessage;
	}

}
