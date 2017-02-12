package cli;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class CommandLineInterfaceTest {

    @InjectMocks
    private CommandLineInterface classUnderTest;

    @Mock
    private Environment environment;

    @Test
    public void shouldResolveConfigurationFromEnvironmentVariables() {
        // Given
        String gamerTag = "tag";
        String psnId = "id";
        String psnPass = "pass";

        given(environment.getVariable("PSN_ID")).willReturn(psnId);
        given(environment.getVariable("PSN_PASS")).willReturn(psnPass);

        // When
        RunConfiguration config = classUnderTest.parse(new String[]{gamerTag});

        // Then
        assertThat(config.gamerTag, equalTo(gamerTag));
        assertThat(config.psnId, equalTo(psnId));
        assertThat(config.psnPass, equalTo(psnPass));
    }

    @Test
    public void shouldResolveConfigurationFromUserInputIfEnvironmentVariablesNotSet() {
        // Given
        String gamerTag = "tag";
        String psnId = "id";
        String psnPass = "pass";

        given(environment.readLine("Please enter your PSN ID")).willReturn(psnId);
        given(environment.readLine("Please enter your PSN password")).willReturn(psnPass);

        // When
        RunConfiguration config = classUnderTest.parse(new String[]{gamerTag});

        // Then
        assertThat(config.gamerTag, equalTo(gamerTag));
        assertThat(config.psnId, equalTo(psnId));
        assertThat(config.psnPass, equalTo(psnPass));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenGamerTagNotSupplied() {
        // Given
        String[] args = new String[0];

        // When
        RunConfiguration config = classUnderTest.parse(args);

        // Then IllegalArgumentException is thrown
    }

}