package matchers;

import org.mockito.ArgumentMatcher;

public class ToStringMatcher<T> implements ArgumentMatcher<T> {

    private Object expected;

    public ToStringMatcher(Object expected) {
        this.expected = expected;
    }

    @Override
    public boolean matches(Object o) {
        return expected.toString().equals(o.toString());
    }
}