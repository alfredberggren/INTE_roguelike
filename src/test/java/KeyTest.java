import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KeyTest {

    @ParameterizedTest
    @EnumSource(Key.Type.class)
    public void test_ConstructorSetsTypeCorrectly(Key.Type k){
        Key key = new Key(k);
        assertEquals(k, key.getKeyType());
    }
}
