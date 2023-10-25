import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DoorTest {

    //MERGEMERGE
    //TODO: needs a lot of refactoring, and also should make a decision table to be sure everything is tested

    private static final int DEFAULT_KEY_USES = 2;
    private static final int DEFAULT_USES_LEFT_AFTER_USE = 1;

    private static final Key.Type DEFAULT_CORRECT_KEY_TYPE = Key.Type.BLUE;
    private static final Key.Type DEFAULT_WRONG_KEY_TYPE = Key.Type.YELLOW;

    private static final boolean DOES_NOT_BREAK_KEY = false;

    private static final boolean BREAKS_KEY = true;

    private static final boolean CLOSED = false;
    private static final boolean OPEN = true;

    private Door d1;

    //Used for implementing default constructor
    @Test
    public void test_whenDoorCreatedWithEmptyArgs_thenDoorIsOpen(){
        d1 = new Door();
        assertEquals(true, d1.isOpen());
    }

    //Used for implementing KeyType constructor
    @Test
    public void test_whenDoorCreatedWithKeyTypeInArgs_DoorIsClosed() {
        d1 = new Door(DEFAULT_CORRECT_KEY_TYPE, DOES_NOT_BREAK_KEY);
        assertEquals(false, d1.isOpen());
    }

    //Used for implementing getRequiredKeyType
    @ParameterizedTest
    @EnumSource(value= Key.Type.class, names = {"YELLOW", "BLUE", "RED", "BROKEN", "NONE"})
    public void test_WhenDoorCreatedWithKeyType_thenRightKeyTypeSet(Key.Type k) {
        d1 = new Door(k, DOES_NOT_BREAK_KEY);
        assertEquals(k, d1.getRequiredKeyType());
    }

    @ParameterizedTest
    @EnumSource(Key.Type.class)
    public void test_whenKeyTypeSet_thenGetReturnsSame(Key.Type k){
        d1 = new Door();
        d1.setRequiredKeyType(k);
        assertEquals(k, d1.getRequiredKeyType());
    }

    //Used for implementing breaksKey in constructor
    @Test
    public void test_whenDoorCreated_setsConsumesKeyCorrectly() {
        d1 = new Door(DEFAULT_CORRECT_KEY_TYPE, BREAKS_KEY);
        assertEquals(BREAKS_KEY, d1.breaksKeyAfterUse());
    }


    //Used for implementing setOpen
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void test_whenSetOpen_thenSetOpenReturnsCorrectly(boolean b){
        d1 = new Door();
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
        d1 = new Door(Key.Type.BROKEN, DOES_NOT_BREAK_KEY);
        assertEquals(false, d1.open(), "could open");
        assertEquals(false, d1.close(), "could close");
    }

    @Test
    public void test_whenDoorIsBroken_ThenDoorCanNotBeOpenedOrClosedWithKey(){
        d1 = new Door(Key.Type.BROKEN, DOES_NOT_BREAK_KEY);
        Key k = new Key(DEFAULT_CORRECT_KEY_TYPE, DEFAULT_KEY_USES);
        assertEquals(false, d1.open());
        assertEquals(false, d1.close());

    }

    @Test
    public void test_whenDoorIsBroken_thenDoorDoesNotBreakKey(){
        d1 = new Door(Key.Type.BROKEN, BREAKS_KEY);
        Key k = new Key(DEFAULT_CORRECT_KEY_TYPE, DEFAULT_KEY_USES);
        d1.open(k);
        assertEquals(DEFAULT_CORRECT_KEY_TYPE, k.getKeyType(), "key changed type when opening door");
        d1.close(k);
        assertEquals(DEFAULT_CORRECT_KEY_TYPE, k.getKeyType(), "key's type was changed when closing door");
    }

    @Test
    public void test_whenDoorIsBroken_thenKeyIsNotUsed(){
        d1 = new Door(Key.Type.BROKEN, DOES_NOT_BREAK_KEY);
        Key k = new Key(DEFAULT_CORRECT_KEY_TYPE, DEFAULT_KEY_USES);
        d1.open(k);
        assertEquals(DEFAULT_KEY_USES, k.getUses());
        d1.close(k);
        assertEquals(DEFAULT_KEY_USES, k.getUses());
    }


    //when door has KeyType
        // door opens if correct key
    @Test
    public void test_whenDoorHasKeyType_thenDoorOpensWithCorrectKey(){
        d1 = new Door(DEFAULT_CORRECT_KEY_TYPE, DOES_NOT_BREAK_KEY);
        Key k = new Key(DEFAULT_CORRECT_KEY_TYPE, DEFAULT_KEY_USES);
        d1.open(k);
        assertEquals(true, d1.isOpen());
    }

        // door closes if correct key
    @Test
    public void test_whenDoorHasCorrectKeyType_doorClosesWithCorrectKey(){
        d1 = new Door(DEFAULT_CORRECT_KEY_TYPE, DOES_NOT_BREAK_KEY);
        Key k = new Key(DEFAULT_CORRECT_KEY_TYPE, DEFAULT_KEY_USES);
        d1.setOpen(true);
        d1.close(k);
        assertEquals(false, d1.isOpen());
    }
        //TODO: door does not open if not correct key
    @Test
    public void test_whenDoorIsNotTypeNone_thenWontOpenWithoutKey(){
        d1 = new Door(DEFAULT_CORRECT_KEY_TYPE, DOES_NOT_BREAK_KEY);
        d1.open();
        assertEquals(false, d1.isOpen());
    }


        //TODO: door does not close if not correct key
    @Test
    public void test_whenDoorHasKeyType_thenDoorDoesNotCloseWithWrongKey(){
        d1 = new Door(DEFAULT_CORRECT_KEY_TYPE, DOES_NOT_BREAK_KEY);
        Key k = new Key(Key.Type.YELLOW, DEFAULT_KEY_USES);
        d1.close(k);
        assertEquals(false, d1.isOpen());
    }



    //When door is typeNone
    //and open, Door closes
    @Test
    public void test_whenDoorIsOpenWithTypeNone_DoorClosesWithOutKey() {
        d1 = new Door(Key.Type.NONE, DOES_NOT_BREAK_KEY);
        d1.setOpen(true);
        d1.close();
        assertEquals(false, d1.isOpen());
    }
    //: and closed, Door opens
    @Test
    public void test_whenDoorIsTypeNoneAndClosed_thenDoorOpens(){
        d1 = new Door(Key.Type.NONE, DOES_NOT_BREAK_KEY);
        d1.setOpen(false);
        d1.open();
        assertEquals(true, d1.isOpen());
    }
    //: and open, Door does not open
    @Test
    public void test_whenDoorIsTypeNoneAndOpen_thenDoorDoesNotOpen(){
        d1 = new Door(Key.Type.NONE, DOES_NOT_BREAK_KEY);
        d1.setOpen(true);
        assertEquals(false, d1.open());
    }
    //: and closed, Door does not close
    @Test
    public void test_whenDoorIsTypeNoneAndClosed_thenDoorDoesNotClose(){
        d1 = new Door(Key.Type.NONE, DOES_NOT_BREAK_KEY);
        d1.setOpen(false);
        assertEquals(false, d1.close());
    }

    //when door has same type as key
        //: When door opened, key gets used
    @Test
    public void test_whenDoorHasSameTypeAsKeyAndOpens_thenKeyGetUsed(){
        d1 = new Door(DEFAULT_CORRECT_KEY_TYPE, DOES_NOT_BREAK_KEY);
        Key k = new Key(DEFAULT_CORRECT_KEY_TYPE, DEFAULT_KEY_USES);
        d1.open(k);
        assertEquals(DEFAULT_USES_LEFT_AFTER_USE, k.getUses());
    }
        //: when door closed, key gets used
    @Test
    public void test_whenDoorHasSameTypeAsKeyAndCloses_thenKeyGetUsed(){
        d1 = new Door(DEFAULT_CORRECT_KEY_TYPE, DOES_NOT_BREAK_KEY);
        d1.setOpen(true);
        Key k = new Key(DEFAULT_CORRECT_KEY_TYPE, DEFAULT_KEY_USES);
        d1.close(k);
        assertEquals(DEFAULT_USES_LEFT_AFTER_USE, k.getUses());
    }
        //if door consumes, key gets broken
    //Used for implementation of open with key
    @Test
    public void test_KeyGetsConsumedWhenConsumesIsTrueAndDoorOpensWithKey() {
        d1 = new Door(DEFAULT_CORRECT_KEY_TYPE, BREAKS_KEY);
        Key k = new Key(DEFAULT_CORRECT_KEY_TYPE);
        d1.setOpen(false);
        d1.open(k);
        assertEquals(Key.Type.BROKEN, k.getKeyType());

    }

    @Test
    public void test_KeyIsNotConsumedWhenConsumedIsFalseAndDoorOpensWithKey(){
        d1 = new Door(DEFAULT_CORRECT_KEY_TYPE, DOES_NOT_BREAK_KEY);
        Key k = new Key(DEFAULT_CORRECT_KEY_TYPE);
        d1.setOpen(false);
        d1.open(k);
        assertEquals(DEFAULT_CORRECT_KEY_TYPE, k.getKeyType());
    }

    @Test
    public void test_whenDoorOpen_thenDoorReturnsFalseWhenOpened() {
        d1 = new Door(Key.Type.NONE, DOES_NOT_BREAK_KEY);
        d1.setOpen(true);
        assertEquals(false, d1.open());
    }



    @Test
    public void test_whenDoorIsOpenWithKeyTypeNotNone_DoorDoesNotCloseWithoutKey() {
        d1 = new Door(DEFAULT_CORRECT_KEY_TYPE, DOES_NOT_BREAK_KEY);
        d1.open();
        assertEquals(false, d1.isOpen());
    }

    //TESTS BASED ON DECISIONTABLE:
    @Test
    public void test_R1_DoorTypeNone(){
        d1 = new Door(Key.Type.NONE, BREAKS_KEY);
        d1.setOpen(CLOSED);

        Key k = new Key(DEFAULT_CORRECT_KEY_TYPE, DEFAULT_KEY_USES);

        assertEquals(true, d1.use(), "use-method did not return true when it should from a closed door");
        assertEquals(OPEN, d1.isOpen(), "Door was not opened when closed");
        assertEquals(true, d1.use(), "use-method did not return true when it should from an open door");
        assertEquals(CLOSED, d1.isOpen(), "Door did not close when open");

        assertEquals(true, d1.use(k));
        assertEquals(OPEN, d1.isOpen());
        assertEquals(DEFAULT_KEY_USES, k.getUses(), "Key was used when Door had type NONE");
        assertEquals(DEFAULT_CORRECT_KEY_TYPE, k.getKeyType(), "Key was broken when door had type NONE");
    }

    @Test
    public void test_R2_DoorTypeBroken(){
        d1 = new Door(Key.Type.BROKEN, BREAKS_KEY);
        d1.setOpen(CLOSED);
        Key k = new Key(DEFAULT_CORRECT_KEY_TYPE, DEFAULT_KEY_USES);

        assertEquals(false, d1.use(), "The use-method returned true, when door type was BROKEN");
        assertEquals(CLOSED, d1.isOpen(), "The door was opened, when door type was BROKEN");

        assertEquals(false, d1.use(k), "The use method with key returned true, when door type was BROKEN");
        assertEquals(CLOSED, d1.isOpen(), "The door was opened when door type was BROKEN");

        assertEquals(DEFAULT_KEY_USES, k.getUses(), "The key was used even though door type was BROKEN");
        assertEquals(DEFAULT_CORRECT_KEY_TYPE, k.getKeyType(), "The key was broken although door was set to BROKEN");
    }

    @Test
    public void test_R3_NotCorrectKeyType(){
        d1 = new Door(DEFAULT_CORRECT_KEY_TYPE, BREAKS_KEY);
        d1.setOpen(CLOSED);

        Key k = new Key(DEFAULT_WRONG_KEY_TYPE, DEFAULT_KEY_USES);

        assertEquals(false, d1.use(), "Use method returned true, when door had a colored key type");
        assertEquals(CLOSED, d1.isOpen(), "The door was opened although it required a key");

        assertEquals(false, d1.use(k), "The use-method returned true when supplied with incorrect key");
        assertEquals(CLOSED, d1.isOpen(), "The door was opened although supplied with wrong key");

        assertEquals(DEFAULT_KEY_USES, k.getUses(), "The key was used even though it had wrong keytype");
        assertEquals(DEFAULT_WRONG_KEY_TYPE, k.getKeyType(), "The keys' type was changed although it had the wrong keytype");
    }




}
