package io.dango.test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by 54472 on 2017/6/30.
 */
public class DemoControllerTest {

    HttpClient httpClient;

    @Before
    public void before() {
        HttpClient httpClient = new HttpClient();
    }

    @After
    public void after() {
        httpClient = null;
    }

    // Use HttpClient Get test
    @Test
    public void demo() throws Exception {
        HttpMethod method = new GetMethod("http://localhost:8080/demo");
        int statusCode = httpClient.executeMethod(method);

        // Assert HTTP request return OK
        Assert.assertEquals(HttpStatus.OK.value(), statusCode);

        byte[] responseBody = method.getResponseBody();

        // Assert HTTP response body not null
        Assert.assertNotNull(responseBody);

        ObjectMapper mapper = new ObjectMapper();
        String body = new String(responseBody);
        List<String> strList = mapper.readValue(body, new TypeReference<List<String>>() { });

        // Assert JSON parse
        Assert.assertEquals("Hello", strList.get(0));
        Assert.assertEquals("World", strList.get(1));
    }

    @Test
    public void error() throws Exception {

    }

    // Use RestTemplate test via getForObject
    @Test
    public void faceNotFound() throws Exception {
    }

    @Test
    public void faceByID() throws Exception {

    }

    @Test
    public void saveFace() throws Exception {

    }

    @Test
    public void saveFace2() throws Exception {

    }

    @Test
    public void saveFace3() throws Exception {

    }

}