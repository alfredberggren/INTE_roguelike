import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DoorTest {

    //Used for implementing default constructor
    @Test
    public void test_whenDoorCreatedWithEmptyArgs_thenDoorIsOpen(){
        Door d1 = new Door();
        assertEquals(true, d1.isOpen());
    }

    //Used for implementing KeyType constructor
    @Test
    public void test_whenDoorCreatedWithKeyTypeInArgs_DoorIsClosed() {
        Door d1 = new Door(Key.Type.RED);
        assertEquals(false, d1.isOpen());
    }

    //Used for implementing getRequiredKeyType
    @ParameterizedTest
    @EnumSource(Key.Type.class)
    public void test_WhenDoorCreatedWithKeyType_thenRightKeyTypeSet(Key.Type k) {
        Door d1 = new Door(k);
        assertEquals(k, d1.getRequiredKeyType());
    }



}
