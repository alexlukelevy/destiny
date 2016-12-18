package service;

import auth.AuthenticationContext;
import auth.AuthenticationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import matchers.ToStringMatcher;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class ApacheDestinyServiceTest {

    @InjectMocks
    private ApacheDestinyService classUnderTest;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private CloseableHttpClient httpClient;

    private AuthenticationContext authenticationContext;

    @Before
    public void setUp() throws IOException {
        HttpClientContext context = mock(HttpClientContext.class);
        authenticationContext = new AuthenticationContext(context, "xsrf");
        given(authenticationService.authenticate()).willReturn(authenticationContext);
    }

    @Test
    public void shouldGetMembership() throws IOException {
        // Given
        int membershipTypeId = 2;
        String username = "username";
        String json = "{ \"id\": 1 }";

        givenHttpClient("/SearchDestinyPlayer/2/username").willReturn(new MockHttpResponse(json));

        // When
        JsonNode membership = classUnderTest.getMembership(membershipTypeId, username);

        // Then
        assertThat(membership, equalTo(new ObjectMapper().readTree(json)));
    }

    public BDDMockito.BDDMyOngoingStubbing<CloseableHttpResponse> givenHttpClient(String url) throws IOException {
        HttpGet get = new HttpGet("http://www.bungie.net/Platform/Destiny" + url);
        get.setHeader("X-API-KEY", authenticationContext.apiKey);
        get.setHeader("x-csrf", authenticationContext.xsrf);
        return given(httpClient.execute(argThat(equivalentTo(get)), eq(authenticationContext.context)));
    }

    private static <T> ArgumentMatcher<T> equivalentTo(T get) {
        return new ToStringMatcher<>(get);
    }

}