package com.springnavi.domain;

import java.util.Optional;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.springnavi.app.message.AsyncFurikomiControllMessage;
import com.springnavi.infra.entity.AsyncExec;
import com.springnavi.infra.repos.AsyncExecRepository;

@Service
public class AsyncBatchControllService {
	
	@Autowired
	AsyncExecRepository asyncExecRepos;
	
	/*
	 * kafkaバッチON(1→2)
	 * バッチjarが動いていないと（statusが"1"でないと）エラー返します
	 */
	@Transactional(isolation = Isolation.SERIALIZABLE)
	//@RolesAllowed("Admin")
	public AsyncFurikomiControllMessage asyncStart(AsyncFurikomiControllMessage asyncFurikomiControllMessage, long BatchNo) {
		//毎回Newします
		AsyncExec exec = new AsyncExec();
		exec.setId(BatchNo);;
		System.out.println(exec.getId());
		System.out.println(exec.getStatus());
		//バッチJar起動確認
		Optional<AsyncExec> oasync = asyncExecRepos.findById(exec.getId());
		String status = oasync.get().getStatus();
		System.out.println(status);
		if (status.equals("1")) {
			//なにもしない
		} else if (status.equals("0")) {
			//起動していない場合
			asyncFurikomiControllMessage.setStatus("0");
			asyncFurikomiControllMessage.setMessage("-----Batch App is not running-----");
			return asyncFurikomiControllMessage;
		} else if (status.equals("2")) {
			//起動中
			asyncFurikomiControllMessage.setStatus("2");
			asyncFurikomiControllMessage.setMessage("-----Batch App is already running-----");
			return asyncFurikomiControllMessage;
		} else {
			//なんかやばい
			asyncFurikomiControllMessage.setStatus("9");
			asyncFurikomiControllMessage.setMessage("-----Batch App is Emergency. Please Check.-----");
			return asyncFurikomiControllMessage;
		}
		
		//起動処理フェーズ
		if (status.equals("1")) {
			exec.setStatus("2");
			asyncExecRepos.saveAndFlush(exec);
			asyncFurikomiControllMessage.setStatus("2");
			asyncFurikomiControllMessage.setMessage("-----Batch App is running. OK.-----");
		}
		return asyncFurikomiControllMessage;
	}
	
	/*
	 * kafkaバッチOFF(2→1)
	 * バッチjarが稼働中でないと（statusが"2"でないと）エラー返します
	 */
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public AsyncFurikomiControllMessage asyncStop(AsyncFurikomiControllMessage asyncFurikomiControllMessage, long BatchNo) {
		
		//毎回Newします
		AsyncExec exec = new AsyncExec();
		exec.setId(BatchNo);
		
		//バッチJar起動確認
		Optional<AsyncExec> oasync = asyncExecRepos.findById(exec.getId());
		String status = oasync.get().getStatus();
		if (status.equals("2")) {
			//なにもしない
		} else if (status.equals("0")) {
			//起動していない場合
			asyncFurikomiControllMessage.setStatus("0");
			asyncFurikomiControllMessage.setMessage("-----Batch App is not running-----");
			return asyncFurikomiControllMessage;
		} else if (status.equals("1")) {
			//起動中
			asyncFurikomiControllMessage.setStatus("1");
			asyncFurikomiControllMessage.setMessage("-----Batch App is already stopped-----");
			return asyncFurikomiControllMessage;
		} else {
			//なんかやばい
			asyncFurikomiControllMessage.setStatus("9");
			asyncFurikomiControllMessage.setMessage("-----Batch App is Emergency. Please Check.-----");
			return asyncFurikomiControllMessage;
		}
		
		//起動処理フェーズ
		if (status.equals("2")) {
			exec.setStatus("1");
			asyncExecRepos.saveAndFlush(exec);
			asyncFurikomiControllMessage.setStatus("1");
			asyncFurikomiControllMessage.setMessage("-----Batch App is Stopped. OK.-----");
		}
		return asyncFurikomiControllMessage;
	}
	
}
