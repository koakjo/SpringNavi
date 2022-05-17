package com.springnavi.infra.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "kafkaexec")
@Data
public class AsyncExec {

	@Id
	@Column(name = "id", nullable = false)
	private long id;

	@Column(name = "status", nullable = false)
	private String status;

}
