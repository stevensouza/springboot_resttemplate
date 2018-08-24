package com.stevesouza.resttemplate.repository;

import com.stevesouza.resttemplate.domain.MyDbEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/** note because I include the rest data artifact endpoints will automatically be created to POST, DELETE, PATCH
 * GET, and PUT. artifactId that automatically generates these endpoints is spring-boot-starter-data-rest
 *
 * artifactId that gives this link is spring-data-rest-hal-browser - http://localhost:8080/browser/index.html
 * content-type: application/hal+json
 */

public interface MyDbEntityJpaRepository extends PagingAndSortingRepository<MyDbEntity, Long> {
    /**
     * http://localhost:8080/mydbentity/search/findByFirstName?name=SteveSouza
     *
     * The implemenation will be automatically generated.
     *
     * @param name
     * @return List of Post objects
     */
    List<MyDbEntity> findByFirstName(@Param("name") String name);

    /** http://localhost:8080/mydbentity/search/findByAgeLessThan?age=50 */
    List<MyDbEntity> findByAgeLessThan(@Param("age") int age);

     /**   http://localhost:8080/mydbentity/search/countByAgeLessThan?age=60 */
     long countByAgeLessThan(@Param("age") int age);

     @Query("SELECT e.firstName FROM MyDbEntity e where e.firstName = :firstname")
     String findByFirstNameWithQuery(@Param("firstname") String str);

}
