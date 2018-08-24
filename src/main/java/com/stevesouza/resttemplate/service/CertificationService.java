package com.stevesouza.resttemplate.service;

import com.stevesouza.resttemplate.controller.ResourceNotFound;
import com.stevesouza.resttemplate.domain.Certification;
import com.stevesouza.resttemplate.repository.CertificationJpaRepository;
import com.stevesouza.resttemplate.utils.MiscUtils;
import com.stevesouza.resttemplate.vo.CertificationVO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Data
@Slf4j
public class CertificationService implements ServiceInt<CertificationVO> {
    private CertificationJpaRepository certificationJpaRepository;

    // can also use more standardized @Inject
    // The autowiring allows me to inject other implementations including a mock.
    // Autowire would work for any arguments in the constructor.
    // Also if there is only one constructor autowired isn't required.
    // Note the documentation doesn't make it clear if the RestTemplate should be shared or not....
    @Autowired
    public CertificationService(CertificationJpaRepository certificationJpaRepository) {
        this.certificationJpaRepository = certificationJpaRepository;
    }

    @Override
    public List<CertificationVO> getAll() {
         TypeToken<List<CertificationVO>> typeToken = new TypeToken<List<CertificationVO>>(){};
         return MiscUtils.convert(certificationJpaRepository.findAll(), typeToken);
    }


    @Override
    public  CertificationVO create(CertificationVO vo) {
        return  certificationJpaRepository.save(vo.toEntity()).toVo();
    }

    // idempotent. returns 200 and content or 204 and no content
    @Override
    public void delete(long id) {
        certificationJpaRepository.deleteById(id);
    }

    @Override
    public CertificationVO get(long id) {
        return certificationJpaRepository.getOne(id).toVo();
    }

    @Override
    public  CertificationVO update(long id, CertificationVO vo) {
        Certification submittedEntity = vo.toEntity();
        log.info("submitted certification {}", submittedEntity);
        Certification updated = certificationJpaRepository.findById(id).map(certification -> {
            submittedEntity.update(certification);
            return certificationJpaRepository.save(certification);
        }).orElseThrow(() -> new ResourceNotFound("id=" + id + " not found"));

        return updated.toVo();
    }


}
