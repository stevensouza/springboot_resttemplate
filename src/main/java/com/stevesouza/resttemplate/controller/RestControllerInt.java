package com.stevesouza.resttemplate.controller;

import com.stevesouza.resttemplate.vo.PersonVO;
import com.stevesouza.resttemplate.vo.VOBase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface RestControllerInt<VO extends VOBase>  {
    List<VO> getAll();

    VO post(VO vo);

    void delete(long id);

    VO get(long id);

    VO put(long id, VO vo);
}
