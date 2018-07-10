package com.stevesouza.resttemplate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Note @SpringBootApplication annotation also has @Configuration as part of its definition so this @Bean definition could
 * just as easily been put in there.  Note this is the equivalent of defining spring beans in xml.
 */
@Configuration
public class MyConfiguration {
    /** factory method for spring bean creation of RestTemplate. Note although I found a pivotal article that said after
     * construction RestTemplate is thread safe I also, so conflicting information.
     *
     * @return RestTemplate
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
