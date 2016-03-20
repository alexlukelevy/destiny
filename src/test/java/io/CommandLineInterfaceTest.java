package io;

import commands.DestinyCommand;
import org.junit.Before;
import org.junit.Test;

import java.util.EnumMap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CommandLineInterfaceTest {

    private EnumMap<CommandType, DestinyCommand> commands;
    private CommandLineInterface cli;

    @Before
    public void setUp() {
        commands = new EnumMap<>(CommandType.class);
        cli = new CommandLineInterface(commands);
    }

    @Test
    public void shouldDelegateAppropriatelyWhenGivenSupportedCliArgument() {
        // Given
        String[] args = new String[]{"character", "BeneficialBewick"};

        DestinyCommand command = mock(DestinyCommand.class);
        commands.put(CommandType.character, command);

        // When
        cli.run(args);

        // Then
        verify(command).run(args[1]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenGivenAnUnsupportedCliArgument() {
        // Given
        String[] args = new String[]{"geeza"};

        // When
        cli.run(args);

        // Then an IllegalArgumentException is thrown
    }
}