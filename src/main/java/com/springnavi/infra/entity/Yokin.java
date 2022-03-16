package com.springnavi.infra.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "yokin")
@Data
public class Yokin {
	
	@Id
	@Column(name = "kouzabangou", nullable = false)
	private String kouzabangou;

	
	@Column(name = "zandaka", nullable = false)
	private long zandaka;
	
	@Column(name = "cifno", nullable = false)
	private String cifno;
	
}
