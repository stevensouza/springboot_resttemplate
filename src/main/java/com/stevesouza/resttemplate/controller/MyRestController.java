package com.stevesouza.resttemplate.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/restapi")
public class MyRestController {

    // get a person by Id
    @GetMapping("/searchgov/{job}")
    public Map<String, String> getPerson(@PathVariable("job") String job) {
    	 Map<String, String> map=new HashMap<>();
    	 map.put("fn", "steve");
    	 map.put("ln", "souza");
    	 return map;
    }
}
