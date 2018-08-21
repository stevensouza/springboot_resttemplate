package com.stevesouza.resttemplate.service;

import com.stevesouza.resttemplate.vo.VOBase;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ServiceBase<VO extends VOBase> {
    List<VO> getAll();

    VO create(VO vo);

    // idempotent. returns 200 and content or 204 and no content
    void delete(long id);

    VO get(long id);

    VO update(long id, VO vo);
}
