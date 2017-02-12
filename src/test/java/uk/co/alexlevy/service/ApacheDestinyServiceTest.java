package uk.co.alexlevy.service;

import com.fasterxml.jackson.databind.JsonNode;
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
import uk.co.alexlevy.auth.AuthenticationContext;
import uk.co.alexlevy.auth.AuthenticationService;
import uk.co.alexlevy.matcher.ToStringMatcher;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static uk.co.alexlevy.util.TestUtils.asJsonNode;

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

        givenHttpClientGet("SearchDestinyPlayer/2/username/").willReturn(new MockHttpResponse(json));

        // When
        JsonNode membership = classUnderTest.getMembership(membershipTypeId, username);

        // Then
        assertThat(membership, equalTo(asJsonNode(json)));
    }

    @Test
    public void shouldGetAccountSummary() throws IOException {
        // Given
        int membershipTypeId = 2;
        int membershipId = 1;
        String json = "{ \"characters\": [ ], \"level\": 40 }";

        givenHttpClientGet("2/Account/1/Summary/").willReturn(new MockHttpResponse(json));

        // When
        JsonNode summary = classUnderTest.getAccountSummary(membershipTypeId, membershipId);

        // Then
        assertThat(summary, equalTo(asJsonNode(json)));
    }

    @Test
    public void shouldGetInventoryForCharacter() throws IOException {
        // Given
        int membershipTypeId = 2;
        int membershipId = 1;
        int characterId = 3;

        String json = "{ \"data\": { } }";

        givenHttpClientGet("2/Account/1/Character/3/Inventory/?definitions=true").willReturn(new MockHttpResponse(json));

        // When
        JsonNode characters = classUnderTest.getInventory(membershipTypeId, membershipId, characterId);

        // Then
        assertThat(characters, equalTo(asJsonNode(json)));
    }

    public BDDMockito.BDDMyOngoingStubbing<CloseableHttpResponse> givenHttpClientGet(String url) throws IOException {
        HttpGet get = new HttpGet("https://www.bungie.net/Platform/Destiny/" + url);
        get.setHeader("X-API-KEY", authenticationContext.apiKey);
        get.setHeader("x-csrf", authenticationContext.xsrf);
        return given(httpClient.execute(argThat(equivalentTo(get)), eq(authenticationContext.context)));
    }

    private static <T> ArgumentMatcher<T> equivalentTo(T get) {
        return new ToStringMatcher<>(get);
    }

}