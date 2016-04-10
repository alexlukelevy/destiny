package commands.character;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ClassTypeTest {

    @Test
    public void shouldResolveTitanClassId(){
        // Given
        String className = "titan";

        // When
        int id = ClassType.getId(className);

        // Then
        assertThat(id, equalTo(0));
    }

    @Test
    public void shouldResolveHunterClassId(){
        // Given
        String className = "hunter";

        // When
        int id = ClassType.getId(className);

        // Then
        assertThat(id, equalTo(1));
    }

    @Test
    public void shouldResolveWarlockClassId(){
        // Given
        String className = "warlock";

        // When
        int id = ClassType.getId(className);

        // Then
        assertThat(id, equalTo(2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfClassNameNotFound(){
        // Given
        String className = "wizard";

        // When
        ClassType.getId(className);

        // Then throws IllegalArgumentException
    }
}