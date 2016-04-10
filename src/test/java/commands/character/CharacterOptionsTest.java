package commands.character;

import commands.character.CharacterOptions;
import commands.character.CharacterOptionNames;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CharacterOptionsTest {

    private CharacterOptions characterOptions;

    @Before
    public void setUp() {
        characterOptions = new CharacterOptions();
    }

    @Test
    public void shouldGetAllOptions(){
        // Given
        // When
        Options options = characterOptions.getOptions();

        // Then
        assertThat(options.getOptions().size(), equalTo(2));
    }

    @Test
    public void shouldGetUsernameOption(){
        // Given
        // When
        Option option = characterOptions.getUsernameOption();

        // Then
        assertThat(option.getOpt(), equalTo(CharacterOptionNames.Username.getShortOpt()));
        assertThat(option.getLongOpt(), equalTo(CharacterOptionNames.Username.getLongOpt()));
        assertThat(option.getDescription(), equalTo("the username of your character"));
        assertThat(option.isRequired(), is(true));
        assertThat(option.getArgName(), equalTo("username"));
        assertThat(option.hasArg(), is(true));
        assertThat(option.getArgs(), equalTo(1));
    }

    @Test
    public void shouldGetClassTypeOption(){
        // Given
        // When
        Option option = characterOptions.getClassTypeOption();

        // Then
        assertThat(option.getOpt(), equalTo(CharacterOptionNames.ClassType.getShortOpt()));
        assertThat(option.getLongOpt(), equalTo(CharacterOptionNames.ClassType.getLongOpt()));
        assertThat(option.getDescription(), equalTo("the class of your character (hunter, titan or warlock)"));
        assertThat(option.isRequired(), is(true));
        assertThat(option.getArgName(), equalTo("classtype"));
        assertThat(option.hasArg(), is(true));
        assertThat(option.getArgs(), equalTo(1));
    }
}