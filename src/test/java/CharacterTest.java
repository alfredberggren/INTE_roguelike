
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class CharacterTest {
    static Character DEFAULT_CHARACTER;
    static Character DEFAULT_CHARACTER_WITH_POS = new Character(0, 9, new Position(1, 2));

    @BeforeEach
    void setUp() {
        DEFAULT_CHARACTER = new Character(80, 20, 0);
    }

    @Test
    @DisplayName("Test character's health")
    public void testCharactersHealth() {
        assertEquals(80, DEFAULT_CHARACTER.getHealth());
    }

    @Test
    @DisplayName("Test character's position")
    public void testCharactersPosition() {
        assertEquals(new Position(1, 2), DEFAULT_CHARACTER_WITH_POS.getPosition());
        assertEquals(1, DEFAULT_CHARACTER_WITH_POS.getPosition().getX());
        assertEquals(2, DEFAULT_CHARACTER_WITH_POS.getPosition().getY());
    }

    @Test
    @DisplayName("Test to get character's speed")
    public void testCharactersSpeed() {
        assertEquals(20, DEFAULT_CHARACTER.getSpeed());
    }

    @Test
    @DisplayName("Test throws exception if health<0")
    public void testCharacterWithNegativeHealth() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Character(-100, 10, 0);
        });
    }

    @Test
    @DisplayName("Test throws exception if speed<0")
    public void testCharacterWithNegativeSpeed() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Character(100, -10, 0);
        });
    }

    @Test
    @DisplayName("Test to decrease health to 0")
    public void testToDecreaseHealthToZero() {
        DEFAULT_CHARACTER.decreaseHealth(80);
        assertEquals(0, DEFAULT_CHARACTER.getHealth());
        assertTrue(DEFAULT_CHARACTER.isDead());
    }

    @Test
    @DisplayName("Test to decrease health to negative value")
    public void testToDecreaseHealthToNegativeValue() {
        DEFAULT_CHARACTER.decreaseHealth(100);
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
    @DisplayName("Test to increase mana and ability to use magic if mana>0")
    public void testToIncreaseMana() {
        DEFAULT_CHARACTER.increaseMana(20);
        assertEquals(120, DEFAULT_CHARACTER.getMana());
        assertTrue(DEFAULT_CHARACTER.canUseMagic());
    }

    @Test
    @DisplayName("Test to decrease mana and ability to use magic if mana=0")
    public void testToDecreaseMana() {
        DEFAULT_CHARACTER.decreaseMana(100);
        assertEquals(0, DEFAULT_CHARACTER.getMana());
        assertFalse(DEFAULT_CHARACTER.canUseMagic());
    }

    @Test
    @DisplayName("Test to increase XP")
    public void testToIncreaseXP() {
        DEFAULT_CHARACTER.setExperiencePoint(10);
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

    @Test
    @DisplayName("Test that character get Magic Ability")
    public void testCharacterMagicAbility() {
        Character c = new Character(100, 10, new Position(0, 0));
        MagicAbility fireMagic = new MagicAbility("Fireball", 20, 1,"Shoots fire",2,1);
        c.setMagicAbility(fireMagic);
        //assertEquals("Wizard", c.getName());
        assertEquals("Fireball", fireMagic.name);
    }

    @Test
    @DisplayName("Test setting and getting Magic Ability")
    public void testCharacterSetAndGetMagicAbility() {
        MagicAbility fireMagic = new MagicAbility("Fireball", 20, 1,"Shoots fire",2,1);
        DEFAULT_CHARACTER.setMagicAbility(fireMagic);
        assertEquals(fireMagic, DEFAULT_CHARACTER.getMagicAbility());
    }

    @Test
    @DisplayName("Test adding and forgetting spells")
    public void testCharacterSpellHandling() {
        MagicAbility fireSpell = new MagicAbility("Fire", 10, 1,"Shoots fire", 1,2);
        MagicAbility iceSpell = new MagicAbility("Ice", 12, 1, "Shoots ice", 1,3);
        DEFAULT_CHARACTER.addSpell(fireSpell);
        assertTrue(DEFAULT_CHARACTER.getKnownSpell().contains(fireSpell));
        DEFAULT_CHARACTER.forgetSpell(fireSpell);
        assertFalse(DEFAULT_CHARACTER.getKnownSpell().contains(fireSpell));
        assertDoesNotThrow(() -> DEFAULT_CHARACTER.forgetSpell(iceSpell));
    }

    @Test
    @DisplayName("Test set-method at health=0")
    public void testIfCharacterIsDeadWhenHealthIsZero() {
        DEFAULT_CHARACTER.setHealth(0);
        assertTrue(DEFAULT_CHARACTER.isDead());
    }

    @Test
    @DisplayName("Test throws Exception at negative health")
    public void testThrowsExceptionWithNegativeHealth() {
        assertThrows(IllegalArgumentException.class, () -> {
            DEFAULT_CHARACTER.setHealth(-10);
        });
    }
}
