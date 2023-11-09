/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.test.factory.abstracts;

import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.AnnotatedElement;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fbl.common.annotations.interfaces.ControllerJwt;
import com.fbl.environment.EnvironmentService;
import com.fbl.ftp.client.FTPServerClient;
import com.fbl.jwt.domain.JwtClaims;
import com.fbl.jwt.domain.JwtPair;
import com.fbl.jwt.utility.JwtTokenUtil;

/**
 * Base Test class for controllers performing rest endpoint calls.
 * 
 * @author Sam Butler
 * @since July 27, 2021
 */
@AutoConfigureMockMvc
public abstract class BaseControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private EnvironmentService environmentService;

    @MockBean
    private FTPServerClient ftpServerClient;

    private HttpHeaders headers;

    private JwtPair testJwtPair;

    @BeforeEach
    public void setup(TestInfo info) {
        headers = new HttpHeaders();
        checkControllerJwtAnnotation(info);
    }

    @AfterEach
    public void clearTestData() {
        headers = new HttpHeaders();
        testJwtPair = null;
    }

    /**
     * Checks to see if the currently running test has a {@link ControllerJwt}
     * annotation present either on the method or the class itself. If it does then
     * it will set the authorization headers based on the set values. If no
     * {@link ControllerJwt} is present then it will NOT set the authorization
     * headers or or content-type.
     * 
     * @param info The test information for the currently running test.
     */
    public void checkControllerJwtAnnotation(TestInfo info) {
        ControllerJwt annClass = getJwtControllerAnnotation(info.getTestClass().get());
        ControllerJwt annMethod = getJwtControllerAnnotation(info.getTestMethod().get());

        if (annMethod != null) {
            setHeaders(annMethod);
        } else if (annClass != null) {
            setHeaders(annClass);
        }
    }

    /**
     * Parse response from the result action.
     * 
     * @param <T>          The generic class type
     * @param ra           The result action
     * @param responseType The type to cast the result too
     * @return The resposne data
     */
    protected <T> T parseResultActionResponse(ResultActions ra, Class<T> responseType) {
        try {
            MvcResult result = mockMvc.perform(get("/api/users")).andReturn();
            String contentAsString = result.getResponse().getContentAsString();
            return objectMapper.readValue(contentAsString, responseType);
        } catch (Exception e) {
            fail("Unable to parse response body", e);
        }
        return null;
    }

    /**
     * Perform a GET call on the given api.
     * 
     * @param <T> The response type of the call.
     * @param api The endpoint to consume.
     * @return The request builder
     */
    protected MockHttpServletRequestBuilder get(String api) {
        return get(api, null);
    }

    /**
     * Perform a GET call on the given api.
     *
     * @param <T>          The response type of the call.
     * @param api          The endpoint to consume.
     * @param responseType What the object return should be cast as.
     * @return The request builder
     */
    protected <T> MockHttpServletRequestBuilder get(String api, T params) {
        MockHttpServletRequestBuilder requestBuilder = addBody(MockMvcRequestBuilders.get(api), params);
        return complete(requestBuilder, MediaType.APPLICATION_JSON);
    }

    /**
     * Perform a POST call on the given api.
     *
     * @param api The endpoint to consume.
     * @return The request builder
     */
    protected MockHttpServletRequestBuilder post(String api) {
        return post(api, null);
    }

    /**
     * Perform a POST call on the given api.
     *
     * @param api     The endpoint to consume.
     * @param request The request to send with the post.
     * @return The request builder
     */
    protected <T> MockHttpServletRequestBuilder post(String api, T body) {
        MockHttpServletRequestBuilder requestBuilder = addBody(MockMvcRequestBuilders.post(api), body);
        return complete(requestBuilder, MediaType.APPLICATION_JSON);
    }

    /**
     * Perform a PUT call on the given api.
     *
     * @param api The endpoint to consume.
     * @return The request builder
     */
    protected MockHttpServletRequestBuilder put(String api) {
        return post(api, null);
    }

    /**
     * Perform a POST call on the given api.
     *
     * @param api     The endpoint to consume.
     * @param request The request to send with the post.
     * @return The request builder
     */
    protected <T> MockHttpServletRequestBuilder put(String api, T body) {
        MockHttpServletRequestBuilder requestBuilder = addBody(MockMvcRequestBuilders.put(api), body);
        return complete(requestBuilder, MediaType.APPLICATION_JSON);
    }

    /**
     * Perform a DELETE call on the given api.
     *
     * @param api The endpoint to consume.
     * @return The request builder
     */
    protected MockHttpServletRequestBuilder delete(String api) {
        return complete(MockMvcRequestBuilders.delete(api), MediaType.APPLICATION_JSON);
    }

    /**
     * Perform a MULTIPART call on the given api.
     *
     * @param <T>  The response type of the call.
     * @param api  The endpoint to consume.
     * @param file The mock multipart file to send
     * @return The request builder
     */
    protected <T> MockHttpServletRequestBuilder multipart(String api, MockMultipartFile file) {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart(api).file(file);
        return complete(requestBuilder, MediaType.MULTIPART_FORM_DATA);
    }

    /**
     * Complete the request by adding the content type and headers to the request.
     * 
     * @param b           The builder to append too
     * @param contentType The content type to set
     * @return The new http request builder.
     */
    protected MockHttpServletRequestBuilder complete(MockHttpServletRequestBuilder b, MediaType contentType) {
        return b.contentType(contentType).headers(headers);
    }

    /**
     * Add the body to the request.
     * 
     * @param <T>  The generic body type
     * @param b    The builder to append too
     * @param body The body to be sent with the request
     * @return The updated http request builder.
     */
    private <T> MockHttpServletRequestBuilder addBody(MockHttpServletRequestBuilder b, T body) {
        try {
            return b.content(objectMapper.writeValueAsString(body));
        } catch (JsonProcessingException e) {
            fail("Unable to write object as a string", e);
        }
        return null;
    }

    /**
     * Gets the test JWT Pair for the test. If the test being run does not have a
     * {@link ControllerJwt} annotation, then the pair will be null.
     * 
     * @return {@link JwtPair} information
     */
    protected JwtPair getJwtPair() {
        return testJwtPair;
    }

    /**
     * Set headers on instance class. Used for authenticated endpoint calls.
     * 
     * @param jwtController The annotation containing the user information.
     */
    private void setHeaders(ControllerJwt jwtController) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaims.USER_ID, jwtController.userId());
        claims.put(JwtClaims.FIRST_NAME, jwtController.firstName());
        claims.put(JwtClaims.LAST_NAME, jwtController.lastName());
        claims.put(JwtClaims.EMAIL, jwtController.email());
        claims.put(JwtClaims.WEB_ROLE, jwtController.webRole());
        claims.put(JwtClaims.ENVIRONMENT, environmentService.getEnvironment());
        claims.put(JwtClaims.PASSWORD_RESET, false);

        testJwtPair = new JwtPair(jwtTokenUtil.buildTokenClaims(claims, 600000), environmentService.getSigningKey());
        headers.setBearerAuth(testJwtPair.getToken());
    }

    /**
     * Gets the annotation instance of the element type. If none is found, it will
     * return null.
     * 
     * @param elementType     The element type (Class, method, etc.)
     * @param annotationClass What the annotation class is.
     * @return The {@link ControllerJwt} instance.
     */
    private ControllerJwt getJwtControllerAnnotation(AnnotatedElement elementType) {
        return AnnotatedElementUtils.findMergedAnnotation(elementType, ControllerJwt.class);
    }
}
