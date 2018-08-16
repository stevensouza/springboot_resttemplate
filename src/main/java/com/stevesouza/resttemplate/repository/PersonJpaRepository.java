package com.stevesouza.resttemplate.repository;

import com.stevesouza.resttemplate.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/** note because I include the rest data artifact endpoints will automatically be created to POST, DELETE, PATCH
 * GET, and PUT. artifactId that automatically generates these endpoints is spring-boot-starter-data-rest
 *
 * artifactId that gives this link is spring-data-rest-hal-browser - http://localhost:8080/browser/index.html
 * content-type: application/hal+json
 */

//@RepositoryRestResource(collectionResourceRel = "person", path = "person")
public interface PersonJpaRepository extends JpaRepository<Person, Long> {
// if you  want pagination use the following JpaRepository
//public interface PersonJpaRepository extends PagingAndSortingRepository<MyDbEntity, Long> {

    /**
     * http://localhost:8080/person/search/findByFirstName?name=SteveSouza
     *
     * The implemenation will be automatically generated.
     *
     * @param name
     * @return List of Post objects
     */
    List<Person> findByFirstName(@Param("name") String name);

    /** http://localhost:8080/person/search/findByAgeLessThan?age=50 */
    List<Person> findByAgeLessThan(@Param("age") int age);

    /**   http://localhost:8080/mydbentity/search/countByAgeLessThan?age=60 */
    long countByAgeLessThan(@Param("age") int age);

    @Query("SELECT e.firstName FROM Person e where e.firstName = :firstname")
    String findByFirstNameWithQuery(@Param("firstname") String str);

    @Query("SELECT p from Person p")
    List<Person> selectAll();
    // could also do
    //     List<Person> selectAll(Sort sort);

    @Query("SELECT p from Person p  JOIN PersonCertification pc ON p.id = pc.person.id and pc.certification.id=:id")
    List<Person> getAllUsersWithCertificateId(@Param("id") long id);

    @Query("SELECT p.id, p.firstName, p.lastName from Person p")
    List<Object[]> selectColumns();

    //@Query("SELECT new MyPersonColumns(p.id, p.firstName) from Person p")
    @Query("SELECT new com.stevesouza.resttemplate.repository.MyPersonColumns(p.id, p.firstName) from Person p")
    List<MyPersonColumns> selectColumnsAsObject();


}
