package com.stevesouza.resttemplate.service;

import com.stevesouza.resttemplate.vo.VOBase;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ServiceInt<V extends VOBase> {
    List<V> getAll();

    V create(V vo);

    // idempotent. returns 200 and content or 204 and no content
    void delete(long id);

    V get(long id);

    V update(long id, V vo);
}
