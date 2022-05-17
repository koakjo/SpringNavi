package com.springnavi.infra.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "cif")
@Data
public class CIF {

	@Id
	@Column(name = "cifno", nullable = false)
	private String cifno;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "password", nullable = false)
	private String password;

}
