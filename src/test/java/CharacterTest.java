
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class CharacterTest {
    static Character DEFAULT_CHARACTER;
    static Character DEFAULT_CHARACTER_WITH_POS = new Character(0, 9, new Position(1, 2));
    @BeforeEach
    void setUp() {
        DEFAULT_CHARACTER = new Character(80, 20, 10);
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
            new Character(-100, 10, 50);
        });
    }

    @Test
    @DisplayName("Test throws exception if speed<0")
    public void testCharacterWithNegativeSpeed() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Character(100, -10, 50);
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

    @Test
    @DisplayName("Test to increase mana")
    public void testToIncreaseMana() {
        DEFAULT_CHARACTER.increaseMana(20);
        assertEquals(120, DEFAULT_CHARACTER.getMana());
    }
    @Test
    @DisplayName("Test to decrease mana and use magic if mana=0")
    public void testToDecreaseMana(){
        DEFAULT_CHARACTER.decreaseMana(100);
        assertEquals(0, DEFAULT_CHARACTER.getMana());
        assertFalse(DEFAULT_CHARACTER.canUseMagic());
    }

    @Test
    @DisplayName("Test to increase XP")
    public void testToIncreaseXP() {
        DEFAULT_CHARACTER.increaseXP(10);
        assertEquals(20, DEFAULT_CHARACTER.getExperiencePoint());
    }

    @Test
    @DisplayName("Test to decrease XP")
    public void testToDecreaseXP() {
        DEFAULT_CHARACTER.decreaseXP(10);
        assertEquals(0, DEFAULT_CHARACTER.getExperiencePoint());
    }

    @Test
    @DisplayName("Test that character level up")
    public void testCharacterLevelUpWhenXPReaches100() {
        DEFAULT_CHARACTER.setLevel(0);
        DEFAULT_CHARACTER.setExperiencePoint(100);
        DEFAULT_CHARACTER.checkLevelUp();
        assertEquals(1, DEFAULT_CHARACTER.getLevel());
        assertEquals(0, DEFAULT_CHARACTER.getExperiencePoint());
        DEFAULT_CHARACTER.setExperiencePoint(100);
        DEFAULT_CHARACTER.checkLevelUp();
        assertEquals(2, DEFAULT_CHARACTER.getLevel());
        assertEquals(0, DEFAULT_CHARACTER.getExperiencePoint());
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



