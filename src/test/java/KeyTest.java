import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class KeyTest {

    private static final int DEFAULT_AMOUNT_OF_USES_FOR_TEST = 1;
    private static final int AMOUNT_OF_USES_THAT_SHOULD_THROW = -1;


    //Used for implementing constructor and getKeyType
    @ParameterizedTest
    @EnumSource(value = Key.Type.class, names = {"YELLOW", "BLUE", "RED", "BROKEN"})
    public void test_ConstructorSetsTypeCorrectly(Key.Type k){
        Key key = new Key(k);
        assertEquals(k, key.getKeyType());
    }

    //Used for implementing amountOfUses and getUses()
    @Test
    public void test_ConstructorSetsUsesCorrectly() {
       Key key = new Key(Key.Type.RED, DEFAULT_AMOUNT_OF_USES_FOR_TEST);
       assertEquals(DEFAULT_AMOUNT_OF_USES_FOR_TEST, key.getUses());
    }

    //Tried to use it to implement a nullPointerException-throw, toString in constructor handles
    //it, in a not perfect way. However, it would be redundant to implement another.
    @Test
    public void test_constructorDoesNotAcceptNull() {
        assertThrows(NullPointerException.class, () -> new Key(null));
    }

    @Test
    public void test_ConstructorThrows_WhenNegativeInteger() {
        assertThrows(IllegalArgumentException.class, () -> new Key(Key.Type.RED, AMOUNT_OF_USES_THAT_SHOULD_THROW));
    }


    //Used to implement the uses == 0 -> broken behaviour.
    @Test
    public void test_whenAmountOfUsesIs0_thenKeyTypeIsBroken() {
        Key k = new Key(Key.Type.YELLOW, DEFAULT_AMOUNT_OF_USES_FOR_TEST);
        k.use();
        assertEquals(Key.Type.BROKEN, k.getKeyType());
    }

    @Test
    public void test_setBrokenSetsTypeToBROKEN(){
        Key k = new Key(Key.Type.RED, DEFAULT_AMOUNT_OF_USES_FOR_TEST);
        k.setBroken();
        assertEquals(Key.Type.BROKEN, k.getKeyType());
    }

    @Test
    public void test_whenSettingKeyTypeToNone_ExceptionIsCast() {
        assertThrows(IllegalArgumentException.class, () -> new Key(Key.Type.NONE, DEFAULT_AMOUNT_OF_USES_FOR_TEST));
    }


}
