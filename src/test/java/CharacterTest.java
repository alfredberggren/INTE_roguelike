
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CharacterTest {

    @Test
    @DisplayName("Testar att entitetens hälsa blir korrekt")
    public void testCharacterHealth() {
        Character character1 = new Character("Rudolf", 100, 10);
        assertEquals(100, character1.getHealth());

    }

    @Test
    @DisplayName("Testar att en entitets position blir korrekt")
    public void testCharacterPosition() {
        Character character1 = new Character("Rudolf", 0, 9, new Position(1, 2));
        assertEquals(new Position(1, 2), character1.getPosition());
        assertEquals(1, character1.getPosition().getX());
        assertEquals(2, character1.getPosition().getY());
    }

    @Test
    @DisplayName("assert throws exception if health<0")
    public void testCharacterWithNegativeHealth() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Character("Rudolf", -100, 10);
        });
    }

    @Test
    @DisplayName("assert throws exception if speed<0")
    public void testCharacterWithNegativeSpeed() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Character("Rudolf", 100, -10);
        });
    }

    @Test
    @DisplayName("Test to decrease health to 0")
    public void testToDecreaseHealth() {
        Character character1 = new Character("Rudolf", 100, 10);
        character1.decreaseHealth(100);
        assertEquals(0, character1.getHealth());
        assertTrue(character1.isDead());
    }

    @Test
    @DisplayName("Test to increase health")
    public void testToIncreaseHealth() {
        Character character1 = new Character("Rudolf", 80, 10);
        character1.increaseHealth(20);
        assertEquals(100, character1.getHealth());
    }

    @Test
    @DisplayName("Test that character get Magic Ability")
    public void testCharacterMagicAbility() {
        Character c = new Character("Wizard", 100, 10);
        MagicAbility fireMagic = new MagicAbility("Fireball");
        c.setMagicAbility(fireMagic);
        assertEquals("Wizard", c.getName());
        assertEquals("Fireball", c.getMagicAbility().toString());
    }


}



