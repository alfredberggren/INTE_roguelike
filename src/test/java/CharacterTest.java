
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CharacterTest {
    static Character DEFAULT_CHARACTER;
    static Character DEFAULT_CHARACTER_WITH_POS = new Character(0, 9, new Position(1, 2));
    @BeforeEach
    void setUp() {
        DEFAULT_CHARACTER = new Character(80, 20);
    }

    @Test
    @DisplayName("Testar att entitetens hälsa blir korrekt")
    public void testCharacterHealth() {
        assertEquals(80, DEFAULT_CHARACTER.getHealth());

    }

    @Test
    @DisplayName("Testar att en entitets position blir korrekt")
    public void testCharacterPosition() {
        assertEquals(new Position(1, 2), DEFAULT_CHARACTER_WITH_POS.getPosition());
        assertEquals(1, DEFAULT_CHARACTER_WITH_POS.getPosition().getX());
        assertEquals(2, DEFAULT_CHARACTER_WITH_POS.getPosition().getY());
    }

    @Test
    @DisplayName("assert throws exception if health<0")
    public void testCharacterWithNegativeHealth() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Character(-100, 10);
        });
    }

    @Test
    @DisplayName("assert throws exception if speed<0")
    public void testCharacterWithNegativeSpeed() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Character(100, -10);
        });
    }

    @Test
    @DisplayName("Test to decrease health to 0")
    public void testToDecreaseHealth() {
        DEFAULT_CHARACTER.decreaseHealth(80);
        assertEquals(0, DEFAULT_CHARACTER.getHealth());
        assertTrue(DEFAULT_CHARACTER.isDead());
    }

    @Test
    @DisplayName("Test to increase health")
    public void testToIncreaseHealth() {
        DEFAULT_CHARACTER.increaseHealth(20);
        assertEquals(100, DEFAULT_CHARACTER.getHealth());
    }

   /* @Test
    @DisplayName("Test that character get Magic Ability")
    public void testCharacterMagicAbility() {
        Character c = new Character(100, 10);
        MagicAbility fireMagic = new MagicAbility("Fireball",20,"Magic", DEFAULT_CHARACTER, DEFAULT_PLAYER);
        c.setMagicAbility(fireMagic);
        assertEquals("Wizard", c.getName());
        assertEquals("Fireball", c.getMagicAbility().toString());
    }*/


}



