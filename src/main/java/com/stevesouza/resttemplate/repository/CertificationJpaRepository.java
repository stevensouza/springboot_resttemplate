package com.stevesouza.resttemplate.repository;

import com.stevesouza.resttemplate.domain.Certification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CertificationJpaRepository extends JpaRepository<Certification, Long> {

}
