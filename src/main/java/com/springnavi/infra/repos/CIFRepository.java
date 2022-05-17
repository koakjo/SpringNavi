package com.springnavi.infra.repos;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.springnavi.infra.entity.CIF;

@Repository
public interface CIFRepository extends JpaRepository<CIF, String> {

}
