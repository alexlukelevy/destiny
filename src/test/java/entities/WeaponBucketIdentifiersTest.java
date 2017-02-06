package entities;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class WeaponBucketIdentifiersTest {

    @Test
    public void shouldCheckEnumIsContained() {
        // Given
        String primary = "BUCKET_PRIMARY_WEAPON";
        String special = "BUCKET_SPECIAL_WEAPON";
        String heavy = "BUCKET_HEAVY_WEAPON";

        // When
        // Then
        assertThat(WeaponBucketIdentifiers.contains(primary), is(true));
        assertThat(WeaponBucketIdentifiers.contains(special), is(true));
        assertThat(WeaponBucketIdentifiers.contains(heavy), is(true));
        assertThat(WeaponBucketIdentifiers.contains("BUCKET_FAKE"), is(false));
    }
}
