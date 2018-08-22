package com.stevesouza.resttemplate.controller;


import com.stevesouza.resttemplate.service.ServiceInt;
import com.stevesouza.resttemplate.utils.MiscUtils;
import com.stevesouza.resttemplate.vo.VOBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.GenericTypeResolver;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Note when you have a @RestController you don't need to specify the return type as @ResponseBody as that is implied.
 *
 * Note static images can be placed in various locations.  I put one in src/main/resources/static/mystaticimage.png and
 * it is served with the following url: http://localhost:8080/mystaticimage.png
 *
 * RestController annotation includes both Controller and ResponseBody annotations. So @ResponseBody is not needed as an annotation
 * on the method return value.
 *
 * Note: I don't use a JpaRepository in this class, but a typical use case is to Autowire it into the constructor and call its methods
 * from the RestController.
 *
 *  HttpServletRequest, HttpServlet- Response, String, or numeric parameters that
 *  correspond to query parameters in the request, model, cookie values, HTTP request header values, or a number of other possibilities.
 *  p 172 spring in action.
 *
 * public String serveRest(@RequestBody String body, @RequestHeader HttpHeaders headers) {
 *
 * @author stevesouza
 */
@Slf4j
public abstract class RestControllerBase<VO extends VOBase, S extends ServiceInt<VO>> {

    private S service;

    public S getService() {
        return service;
    }

    public void setService(S service) {
        this.service = service;
    }

    @GetMapping()
    public List<VO> getAll() {
        return service.getAll();
    }


    // Content-Type should be application/json and passed on from httpheaders.  methods post1, post2, post3
    // all create a mydbentity though slightly different approaches.
    // The following is probably preferred as it lets you pass in headers to the request as well as
    // return json+hal format (i.e. a string)
    @PostMapping()
    public  VO post(@RequestBody VO vo) {
        log.debug("POST {}",vo.toString());
        VO savedVo = service.create(vo);
        log.debug(" CREATED {}",savedVo.toString());
        return savedVo;
    }

    @DeleteMapping("/{id}")
    // idempotent. returns 200 and content or 204 and no content
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") long id) {
        log.debug("DELETE {}, id={}", getClassOfParameterType(), id);
        service.delete(id);
    }

    @GetMapping("/{id}")
    public VO get(@PathVariable("id") long id) {
        log.debug("GET {}, id={}", getClassOfParameterType(), id);
        return service.get(id);
    }

    @PutMapping("/{id}")
    public  VO put(@PathVariable("id") long id, @RequestBody VO vo) {
        log.debug("PUT {}",vo.toString());
        VO updated = service.update(id, vo);
        log.debug(" UPDATED {}", updated.toString());
        return updated;
    }

    @GetMapping("/random")
    public VO getRandom() {
        return MiscUtils.randomData(getClassOfParameterType());
    }

    private Class<VO> getClassOfParameterType() {
        final int FIRST_TYPE_PARAMETER = 0;
        Class<VO> clazz = (Class<VO>) GenericTypeResolver.resolveTypeArguments(getClass(), RestControllerBase.class)[FIRST_TYPE_PARAMETER];
        return clazz;
    }

}
