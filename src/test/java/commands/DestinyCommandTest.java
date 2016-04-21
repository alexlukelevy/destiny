package commands;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DestinyCommandTest {

    @Test
    public void shouldGetCommandLineFromStringArgs(){
        // Given
        Option option = new Option("t", "test option");
        Options options = new Options().addOption(option);

        DestinyCommand command = new DestinyCommand(options, null, null) {
            @Override
            public void run(String[] args) throws IOException {

            }
        };

        // When
        CommandLine commandLine = command.getCommandLine(new String[]{"-t"});

        // Then
        assertThat(commandLine.hasOption("t"), is(true));
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionIfItCannotParseStringArgs(){
        // Given
        Option option = new Option("t", "test option");
        Options options = new Options().addOption(option);

        DestinyCommand command = new DestinyCommand(options, null, null) {
            @Override
            public void run(String[] args) throws IOException {

            }
        };

        // When
        CommandLine commandLine = command.getCommandLine(new String[]{"-brokenoption"});
        // Then throws RuntimeException
    }
}