import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlayerTest {
    static Player DEFAULT_PLAYER;

    @BeforeEach
    void setUp() {
        DEFAULT_PLAYER = new Player(80, 20, new Position(0,0));
    }


    //name tests
    @Test
    public void testCorrectName() {
        DEFAULT_PLAYER.setName("Rudolf");
        assertEquals("Rudolf", DEFAULT_PLAYER.getName());
    }

    @Test
    public void testNameInputIsNull() {
        assertThrows(NullPointerException.class, () -> {
            DEFAULT_PLAYER.setName(null);
        });
    }

    @Test
    public void testNameInputIsEmpty() {
        DEFAULT_PLAYER.setName("");
        assertEquals(null, DEFAULT_PLAYER.getName());
    }

    @Test
    public void testTooShortName() {
        DEFAULT_PLAYER.setName("A");
        assertEquals(null, DEFAULT_PLAYER.getName());
    }

    @Test
    public void testNameBeginsWithDigit() {
        DEFAULT_PLAYER.setName("1Ab");
        assertEquals(null, DEFAULT_PLAYER.getName());
    }

    @Test
    public void testTooLongName() {
        DEFAULT_PLAYER.setName("tooLongNameToBeAccepted");
        assertEquals(null, DEFAULT_PLAYER.getName());
    }

    @Test
    public void testNameContainsNotOnlyAlphanumericCharactersAndUnderscores() {
        DEFAULT_PLAYER.setName("Aabba&&");
        assertEquals(null, DEFAULT_PLAYER.getName());
    }
//name tests

    @Test
    @DisplayName("Test that new player has 0 XP")
    public void testCharactersXP(){
        assertEquals(0, DEFAULT_PLAYER.getExperiencePoint());
    }

}
