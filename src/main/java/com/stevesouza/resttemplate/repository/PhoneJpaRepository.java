package com.stevesouza.resttemplate.repository;

import com.stevesouza.resttemplate.db.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

/** note because I include the rest data artifact endpoints will automatically be created to POST, DELETE, PATCH
 * GET, and PUT. artifactId that automatically generates these endpoints is spring-boot-starter-data-rest
 *
 * artifactId that gives this link is spring-data-rest-hal-browser - http://localhost:8080/browser/index.html
 * content-type: application/hal+json
 */

//@RepositoryRestResource(collectionResourceRel = "phone", path = "phone")
public interface PhoneJpaRepository extends JpaRepository<Phone, Long> {
// if you  want pagination use the following JpaRepository
//public interface PersonJpaRepository extends PagingAndSortingRepository<MyDbEntity, Long> {

}
