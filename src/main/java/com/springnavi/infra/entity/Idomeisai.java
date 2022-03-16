package com.springnavi.infra.entity;

import java.security.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "idomeisai")
@Data
public class Idomeisai {

	@Id
	@Column(name = "idono", nullable = false)
	private UUID idono;

	
	@Column(name = "kingaku", nullable = false)
	private long kingaku;
	
	@Column(name = "shimukekouza", nullable = false)
	private String shimukekouza;
	
	@Column(name = "hishimukekouza", nullable = false)
	private String hishimukekouza;
	
	@Column(name = "status", nullable = false)
	private String status;
	
	@Column(name = "exectime", nullable = false)
	private String exectime;
	
}
