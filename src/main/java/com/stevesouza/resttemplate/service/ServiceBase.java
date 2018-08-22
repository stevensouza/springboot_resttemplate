package com.stevesouza.resttemplate.service;

import com.stevesouza.resttemplate.controller.ResourceNotFound;
import com.stevesouza.resttemplate.domain.EntityBase;
import com.stevesouza.resttemplate.domain.Person;
import com.stevesouza.resttemplate.repository.MyPersonColumns;
import com.stevesouza.resttemplate.repository.PersonJpaRepository;
import com.stevesouza.resttemplate.utils.MiscUtils;
import com.stevesouza.resttemplate.vo.VOBase;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Data
@Slf4j
public abstract class ServiceBase<VO extends VOBase> implements ServiceInt<VO> {
    private PersonJpaRepository personJpaRepository;

    // can also use more standardized @Inject
    // The autowiring allows me to inject other implementations including a mock.
    // Autowire would work for any arguments in the constructor.
    // Also if there is only one constructor autowired isn't required.
    // Note the documentation doesn't make it clear if the RestTemplate should be shared or not....
    @Autowired
    public ServiceBase(PersonJpaRepository personJpaRepository) {
        this.personJpaRepository = personJpaRepository;
    }

    @Override
    public List<VO> getAll() {
         TypeToken<List<VO>> typeToken = new TypeToken<List<VO>>(){};
         List<VO> list = MiscUtils.convert(personJpaRepository.findAll(), typeToken);
         return list;
    }
    

//    @Override
//    public  VO create(VO vo) {
//        return  certificationJpaRepository.save(vo.toEntity()).toVo();
//    }

    // idempotent. returns 200 and content or 204 and no content
    @Override
    public void delete(long id) {
        personJpaRepository.deleteById(id);
    }

//    @Override
//    public VO get(long id) {
//        return certificationJpaRepository.getOne(id).toVo();
//    }

//    @Override
//    public  VO update(long id, VO vo) {
//        EntityBase<VO> submittedEnt = vo.toEntity();
//        log.info("submitted person {}", submittedEnt);
//        Person updated = certificationJpaRepository.findById(id).map((person)->{
//            submittedEnt.update(person);
//            return certificationJpaRepository.save(person);
//        }).orElseThrow(() -> new ResourceNotFound("id=" + id + " not found"));
//
//        return updated.toVo();
//    }


}
