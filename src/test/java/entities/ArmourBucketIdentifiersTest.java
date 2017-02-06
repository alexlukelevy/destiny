package entities;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ArmourBucketIdentifiersTest {

    @Test
    public void shouldCheckEnumIsContained() {
        // Given
        String ghost = "BUCKET_GHOST";
        String head = "BUCKET_HEAD";
        String arms = "BUCKET_ARMS";
        String chest = "BUCKET_CHEST";
        String legs = "BUCKET_LEGS";
        String classItems = "BUCKET_CLASS_ITEMS";
        String artifact = "BUCKET_ARTIFACT";

        // When
        // Then
        assertThat(ArmourBucketIdentifiers.contains(ghost), is(true));
        assertThat(ArmourBucketIdentifiers.contains(head), is(true));
        assertThat(ArmourBucketIdentifiers.contains(arms), is(true));
        assertThat(ArmourBucketIdentifiers.contains(chest), is(true));
        assertThat(ArmourBucketIdentifiers.contains(legs), is(true));
        assertThat(ArmourBucketIdentifiers.contains(classItems), is(true));
        assertThat(ArmourBucketIdentifiers.contains(artifact), is(true));
        assertThat(ArmourBucketIdentifiers.contains("BUCKET_FAKE"), is(false));
    }
}
