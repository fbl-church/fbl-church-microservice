package com.fbl.gateway.validator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.test.util.ReflectionTestUtils;

import com.fbl.common.enums.Environment;
import com.fbl.environment.EnvironmentService;
import com.fbl.exception.types.JwtTokenException;
import com.fbl.jwt.domain.JwtClaims;
import com.fbl.jwt.domain.JwtPair;
import com.fbl.jwt.utility.JwtHolder;
import com.fbl.test.factory.annotations.InsiteServiceTest;
import com.fbl.test.factory.data.JwtTestFactoryData;

import io.jsonwebtoken.ExpiredJwtException;

@InsiteServiceTest
public class CommonTokenValidatorTest {

    private CommonTokenValidator commonTokenValidator;

    @Mock
    private JwtHolder jwtHolder;

    @Mock
    private EnvironmentService environmentService;

    @BeforeEach
    void setup() {
        commonTokenValidator = mock(CommonTokenValidator.class, Mockito.CALLS_REAL_METHODS);
        ReflectionTestUtils.setField(commonTokenValidator, "jwtHolder", jwtHolder);
        ReflectionTestUtils.setField(commonTokenValidator, "environmentService", environmentService);
    }

    @Test
    void testRunTokenValidationVALID() {
        when(environmentService.getEnvironment()).thenReturn(Environment.TEST);
        when(environmentService.getSigningKey()).thenReturn(JwtTestFactoryData.testSigningKey());

        String token = JwtTestFactoryData.testToken();
        commonTokenValidator.runTokenValidation(token, false);
    }

    @Test
    void testRunTokenValidationTokenExpired() {
        when(environmentService.getSigningKey()).thenReturn(JwtTestFactoryData.testSigningKey());

        String token = JwtTestFactoryData.testToken(-600000L);
        assertThrows(ExpiredJwtException.class, () -> commonTokenValidator.runTokenValidation(token, false));
    }

    @Test
    void testRunTokenValidationNoBearerPrefix() {
        String token = JwtTestFactoryData.testToken();
        JwtTokenException ex = assertThrows(JwtTokenException.class,
                () -> commonTokenValidator.runTokenValidation(token, true));
        assertEquals("JWT Token does not begin with 'Bearer'", ex.getMessage(), "Exception Message");
    }

    @Test
    void testShouldNOTFilterTheRequest() {
        List<AntPathRequestMatcher> matchers = new ArrayList<>();
        matchers.add(new AntPathRequestMatcher("/api/authenticate", HttpMethod.POST.name()));

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setMethod(HttpMethod.POST.name());
        request.setServletPath("/api/authenticate");

        assertFalse(commonTokenValidator.shouldFilterRequest(request, matchers), "Should NOT Filter request");
    }

    @Test
    void testShouldFilterTheRequest() {
        List<AntPathRequestMatcher> matchers = new ArrayList<>();
        matchers.add(new AntPathRequestMatcher("/api/authenticate", HttpMethod.POST.name()));

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setMethod(HttpMethod.GET.name());
        request.setServletPath("/api/users");

        assertTrue(commonTokenValidator.shouldFilterRequest(request, matchers), "Should Filter request");
    }

    @ParameterizedTest(name = "[{index}] token = {0}")
    @ValueSource(strings = { "Bearer SDgkljsg32tn2lknlskdn", "Bearer s", "Bearer" })
    void testContainsBearerPrefixVALID(String token) {
        assertTrue(commonTokenValidator.containsBearerPrefix(token), "Contains Bearer Prefix");
    }

    @ParameterizedTest(name = "[{index}] token = {0}")
    @ValueSource(strings = { "TEST SDgkljsg32tn2lknlskdn", "SDgkljsg32tn2lknlskdn", "fake" })
    @NullAndEmptySource
    void testContainsBearerPrefixINVALID(String token) {
        assertFalse(commonTokenValidator.containsBearerPrefix(token), "Does NOT Contain Bearer Prefix");
    }

    @ParameterizedTest(name = "[{index}] token = {0}")
    @ValueSource(strings = { "Bearer SDgkljsg32tn2lknlskdn", "Bearer s", "Bearer" })
    void testCheckValidTokenVALID(String token) {
        commonTokenValidator.checkValidToken(token, true);
    }

    @ParameterizedTest(name = "[{index}] token = {0}")
    @NullAndEmptySource
    void testCheckValidTokenEmptyNullINVALID(String token) {
        JwtTokenException ex = assertThrows(JwtTokenException.class,
                () -> commonTokenValidator.checkValidToken(token, true));
        assertEquals("Missing JWT Token.", ex.getMessage(), "Exception Message");
    }

    @ParameterizedTest(name = "[{index}] token = {0}")
    @ValueSource(strings = { "Bearer SDgkljsg32tn2lknlskdn", "Bearer s", "Bearer", "SDgkljsg32tn2lknlskdn", "s" })
    void testCheckValidTokenNoPrefixCheckVALID(String token) {
        commonTokenValidator.checkValidToken(token, false);
    }

    @ParameterizedTest(name = "[{index}] token = {0}")
    @ValueSource(strings = { "TEST SDgkljsg32tn2lknlskdn", "SDgkljsg32tn2lknlskdn", "fake" })
    void testCheckValidTokenWithPrefixCheckINVALID(String token) {
        JwtTokenException ex = assertThrows(JwtTokenException.class,
                () -> commonTokenValidator.checkValidToken(token, true));
        assertEquals("JWT Token does not begin with 'Bearer'", ex.getMessage(), "Exception Message");
    }

    @ParameterizedTest(name = "[{index}] token = {0}")
    @EnumSource(Environment.class)
    void testCheckEnvironmentVALID(Environment env) {
        when(environmentService.getEnvironment()).thenReturn(env);
        when(environmentService.getSigningKey()).thenReturn(JwtTestFactoryData.testSigningKey());

        Map<String, Object> testClaims = JwtTestFactoryData.testClaims();
        testClaims.put(JwtClaims.ENVIRONMENT, env);

        JwtPair pair = new JwtPair(JwtTestFactoryData.testToken(testClaims), environmentService);
        commonTokenValidator.checkCorrectEnvironment(pair);
    }

    @Test
    void testCheckEnvironmentINVALID() {
        when(environmentService.getEnvironment()).thenReturn(Environment.PRODUCTION);
        when(environmentService.getSigningKey()).thenReturn(JwtTestFactoryData.testSigningKey());

        Map<String, Object> testClaims = JwtTestFactoryData.testClaims();
        testClaims.put(JwtClaims.ENVIRONMENT, Environment.DEVELOPMENT);

        JwtPair pair = new JwtPair(JwtTestFactoryData.testToken(testClaims), environmentService);
        JwtTokenException ex = assertThrows(JwtTokenException.class, () -> commonTokenValidator.checkCorrectEnvironment(pair));
        assertEquals("JWT token does not match accessing environment", ex.getMessage(), "Exception Message");
    }

    @Test
    void testCheckTokenExpirationVALID() {
        when(environmentService.getSigningKey()).thenReturn(JwtTestFactoryData.testSigningKey());

        JwtPair pair = new JwtPair(JwtTestFactoryData.testToken(), environmentService);
        commonTokenValidator.checkTokenExpiration(pair);
    }

    @Test
    void testCheckTokenExpirationINVALID() {
        when(environmentService.getSigningKey()).thenReturn(JwtTestFactoryData.testSigningKey());
        assertThrows(ExpiredJwtException.class, () -> new JwtPair(JwtTestFactoryData.testToken(-600000L), environmentService));
    }

    @ParameterizedTest
    @MethodSource("provideStringsForExtractToken")
    void testExtractToken(String token, String result) {
        assertEquals(result, commonTokenValidator.extractToken(token), "Result Match");
    }

    @ParameterizedTest
    @MethodSource("provideStringsForExtractToken")
    void testStoreToken(String token, String result) {
        doNothing().when(jwtHolder).setToken(any());
        commonTokenValidator.storeToken(token);

        verify(jwtHolder).setToken(result);
    }

    private static Stream<Arguments> provideStringsForExtractToken() {
        return Stream.of(
                Arguments.of("Bearer SDgkljsg32tn2lknlskdn", "SDgkljsg32tn2lknlskdn"),
                Arguments.of("Bearer s", "s"),
                Arguments.of("Bearer ", ""),
                Arguments.of("SDgkljsg32tn2lknlskdn", "SDgkljsg32tn2lknlskdn"),
                Arguments.of("  ", null),
                Arguments.of(null, null));
    }
}
