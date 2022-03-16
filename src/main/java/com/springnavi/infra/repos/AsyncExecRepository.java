package com.springnavi.infra.repos;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springnavi.infra.entity.AsyncExec;

@Repository
public interface AsyncExecRepository extends JpaRepository<AsyncExec,Long>{
	
}
