import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DoorTest {

    private static final int DEFAULT_KEY_USES = 1;

    //Used for implementing default constructor
    @Test
    public void test_whenDoorCreatedWithEmptyArgs_thenDoorIsOpen(){
        Door d1 = new Door();
        assertEquals(true, d1.isOpen());
    }

    //Used for implementing KeyType constructor
    @Test
    public void test_whenDoorCreatedWithKeyTypeInArgs_DoorIsClosed() {
        Door d1 = new Door(Key.Type.RED, false);
        assertEquals(false, d1.isOpen());
    }

    //Used for implementing getRequiredKeyType
    @ParameterizedTest
    @EnumSource(value= Key.Type.class, names = {"YELLOW", "BLUE", "RED", "BROKEN", "NONE"})
    public void test_WhenDoorCreatedWithKeyType_thenRightKeyTypeSet(Key.Type k) {
        Door d1 = new Door(k, false);
        assertEquals(k, d1.getRequiredKeyType());
    }

    @ParameterizedTest
    @EnumSource(Key.Type.class)
    public void test_whenKeyTypeSet_thenGetReturnsSame(Key.Type k){
        Door d1 = new Door();
        d1.setRequiredKeyType(k);
        assertEquals(k, d1.getRequiredKeyType());
    }

    //Used for implementing breaksKey in constructor
    @Test
    public void test_whenDoorCreated_setsConsumesKeyCorrectly() {
        boolean consumesKey = true;
        Door d1 = new Door(Key.Type.BLUE, consumesKey);
        assertEquals(consumesKey, d1.breaksKeyAfterUse());
    }


    //Used for implementing setOpen
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void test_whenSetOpen_thenSetOpenReturnsCorrectly(boolean b){
        Door d1 = new Door();
        d1.setOpen(b);
        assertEquals(b, d1.isOpen());
    }

    /**
    //Used to implement a throw if initializing with keytype BROKEN. A change in design led me to skip it
    @Test
    public void test_whenConstructorGetsKeyTypeBroken_thenThrowsIllegalArgument(){
        assertThrows(IllegalArgumentException.class, () -> new Door(Key.Type.BROKEN, false));
    }
    **/




    //Tests used to implement open/close behaviour
    //OPEN/CLOSE-TESTS:


    //when door is broken
        // door does not open, with or without key
        // door does not close, -::-
        //: does not break key
        //: DOES NOt use keey
    //Used to implement broken Door behaviour, Open and Close
    @Test
    public void test_whenDoorIsBroken_thenDoorCanNotBeOpenedOrClosed() {
        Door d1 = new Door(Key.Type.BROKEN, false);
        assertEquals(false, d1.open(), "could open");
        assertEquals(false, d1.close(), "could close");
    }

    @Test
    public void test_whenDoorIsBroken_ThenDoorCanNotBeOpenedOrClosedWithKey(){
        Door d1 = new Door(Key.Type.BROKEN, false);
        Key k = new Key(Key.Type.YELLOW, 3);
        assertEquals(false, d1.open());
        assertEquals(false, d1.close());

    }

    @Test
    public void test_whenDoorIsBroken_thenDoorDoesNotBreakKey(){
        Door d1 = new Door(Key.Type.BROKEN, true);
        Key k = new Key(Key.Type.YELLOW, 3);
        d1.open(k);
        assertEquals(Key.Type.YELLOW, k.getKeyType(), "key changed type when opening door");
        d1.close(k);
        assertEquals(Key.Type.YELLOW, k.getKeyType(), "key's type was changed when closing door");
    }

    @Test
    public void test_whenDoorIsBroken_thenKeyIsNotUsed(){
        Door d1 = new Door(Key.Type.BROKEN, false);
        Key k = new Key(Key.Type.YELLOW, DEFAULT_KEY_USES);
        d1.open(k);
        assertEquals(DEFAULT_KEY_USES, k.getUses());
        d1.close(k);
        assertEquals(DEFAULT_KEY_USES, k.getUses());
    }


    //when door has KeyType
        //TODO: door opens if correct key
        //TODO: door closes if correct key
        //TODO: door does not open if not correct key
    @Test
    public void test_whenDoorIsNotTypeNone_thenWontOpenWithoutKey(){
        Door d1 = new Door(Key.Type.BLUE, false);
        d1.open();
        assertEquals(false, d1.isOpen());
    }

        //TODO: door does not close if not correct key


    //When door is typeNone
    //and open, Door closes
    @Test
    public void test_whenDoorIsOpenWithTypeNone_DoorClosesWithOutKey() {
        Door d1 = new Door(Key.Type.NONE, false);
        d1.setOpen(true);
        d1.close();
        assertEquals(false, d1.isOpen());
    }
    //: and closed, Door opens
    @Test
    public void test_whenDoorIsTypeNoneAndClosed_thenDoorOpens(){
        Door d1 = new Door(Key.Type.NONE, false);
        d1.setOpen(false);
        d1.open();
        assertEquals(true, d1.isOpen());
    }
    //: and open, Door does not open
    @Test
    public void test_whenDoorIsTypeNoneAndOpen_thenDoorDoesNotOpen(){
        Door d1 = new Door(Key.Type.NONE, false);
        d1.setOpen(true);
        assertEquals(false, d1.open());
    }
    //: and closed, Door does not close
    @Test
    public void test_whenDoorIsTypeNoneAndClosed_thenDoorDoesNotClose(){
        Door d1 = new Door(Key.Type.NONE, false);
        d1.setOpen(false);
        assertEquals(false, d1.close());
    }

    //when door has same type as key
        //: When door opened, key gets used
    @Test
    public void test_whenDoorHasSameTypeAsKeyAndOpens_thenKeyGetUsed(){
        Door d1 = new Door(Key.Type.BLUE, false);
        Key k = new Key(Key.Type.BLUE, 2);
        d1.open(k);
        assertEquals(1, k.getUses());
    }
        //: when door closed, key gets used
    @Test
    public void test_whenDoorHasSameTypeAsKeyAndCloses_thenKeyGetUsed(){
        Door d1 = new Door(Key.Type.BLUE, false);
        d1.setOpen(true);
        Key k = new Key(Key.Type.BLUE, 2);
        d1.close(k);
        assertEquals(1, k.getUses());
    }
        //if door consumes, key gets broken
    //Used for implementation of open with key
    @Test
    public void test_KeyGetsConsumedWhenConsumesIsTrueAndDoorOpensWithKey() {
        Door d1 = new Door(Key.Type.BLUE, true);
        Key k = new Key(Key.Type.BLUE);
        d1.setOpen(false);
        d1.open(k);
        assertEquals(Key.Type.BROKEN, k.getKeyType());

    }

    @Test
    public void test_KeyIsNotConsumedWhenConsumedIsFalseAndDoorOpensWithKey(){
        Door d1 = new Door(Key.Type.BLUE, false);
        Key k = new Key(Key.Type.BLUE);
        d1.setOpen(false);
        d1.open(k);
        assertEquals(Key.Type.BLUE, k.getKeyType());
    }

    @Test
    public void test_whenDoorOpen_thenDoorReturnsFalseWhenOpened() {
        Door d1 = new Door(Key.Type.NONE, false);
        d1.setOpen(true);
        assertEquals(false, d1.open());
    }



    @Test
    public void test_whenDoorIsOpenWithKeyTypeNotNone_DoorDoesNotCloseWithoutKey() {
        Door d1 = new Door(Key.Type.BLUE, false);
        d1.open();
        assertEquals(false, d1.isOpen());
    }









}
