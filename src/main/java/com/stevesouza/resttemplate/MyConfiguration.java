package com.stevesouza.resttemplate;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
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
    // https://howtodoinjava.com/spring-restful/resttemplate-httpclient-java-config/
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
        return restTemplate;
    }

//    @Autowired
//    private CloseableHttpClient httpClient;



    // don't think this needs to be a bean.  note i use httpcomments component as the default used
    // by string is the jdk classes of HttpClient, but it doesn't support patch.
    @Bean
    public HttpComponentsClientHttpRequestFactory clientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        //clientHttpRequestFactory.setHttpClient(HttpClientBuilder.create().build());
        return clientHttpRequestFactory;
    }

}
