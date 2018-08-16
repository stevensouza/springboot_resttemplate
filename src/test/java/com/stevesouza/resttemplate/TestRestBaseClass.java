package com.stevesouza.resttemplate;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

// import for asssertJ
//import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public abstract class TestRestBaseClass {

    public Logger log = LoggerFactory.getLogger(this.getClass());

    // annotation populates with random port generated.
    // However, TestRestTemplate successfully uses the rigth port so no need to do this.
    // Just doing it for demonstration purposes.
    @LocalServerPort
    private int port;

    // ${local.server.port} is another way to the the random port number property
    @Value("http://localhost:${local.server.port}/api")
    public String BASE_URL;

    @Autowired
    public TestRestTemplate testRestTemplate;

    public int getPort() {
        return port;
    }

    public TestRestTemplate getTestRestTemplate() {
        return testRestTemplate;
    }

    public void setTestRestTemplate(TestRestTemplate testRestTemplate) {
        this.testRestTemplate = testRestTemplate;
    }

    public  URL getResourceURL(String fileName) {
        URL url = getClass().getClassLoader().getResource(fileName);
        if (null==url) {
            throw new RuntimeException(new FileNotFoundException(fileName));
        }
        return url;
    }

    public String readFile(URL urlOfFile) throws IOException {
        return IOUtils.toString(urlOfFile, "UTF-8");
    }

    /**
     * method that allow for the reading of text files in the 'resources' directory
     *
     * @param fileName resource file to read
     * @return contents of the file as a String
     * @throws IOException
     */
    public String readResourceFile(String fileName) throws IOException {
        return readFile(getResourceURL(fileName));
    }

    /**
     * http basic auth TestRestTemplate
     *
     * @param userName
     * @param password
     * @return
     */
    public TestRestTemplate getTestRestTemplate(String userName, String password) {
        return new TestRestTemplate(userName, password);
    }



}
