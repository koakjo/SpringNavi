package com.springnavi.infra.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springnavi.infra.entity.Yokin;

@Repository
public interface YokinRepository extends JpaRepository<Yokin,String>{

}
