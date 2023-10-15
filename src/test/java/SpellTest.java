import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class SpellTest {

    @Test
    @DisplayName("Test getting the name of a spell")
    public void testSpellName() {
        Spell spell = new Spell("Fireball", "A fiery projectile", 2,10);
        assertEquals("Fireball", spell.getName());
    }

    @Test
    @DisplayName("Test getting the description of a spell")
    public void testSpellDescription() {
        Spell spell = new Spell("Ice Shard", "A shard of ice",1,5);
        assertEquals("A shard of ice", spell.getDescription());
    }

    @Test
    @DisplayName("Test getting the casting time of a spell")
    public void testSpellCastingTime() {
        Spell spell = new Spell("Thunderbolt", "A powerful lightning spell", 3,15);
        assertEquals(3, spell.getCastingTime());
    }

    @Test
    @DisplayName("Test getting the cool-down time of a spell")
    public void testSpellCoolDown() {
        Spell spell = new Spell("Lightning Strike", "A fast lightning attack",2,8);
        assertEquals(8, spell.getCoolDown());
    }

    @Test
    @DisplayName("Test setting and getting the name of a spell")
    public void testSetAndGetSpellName() {
        Spell spell = new Spell("Earthquake", "Shake the ground",5,20);
        assertEquals("Earthquake", spell.getName());
        spell.setName("Tornado");
        assertEquals("Tornado", spell.getName());
    }

    @Test
    @DisplayName("Test setting and getting the description of a spell")
    public void testSetAndGetSpellDescription() {
        Spell spell = new Spell("Healing Touch", "Heal allies",2,10);
        assertEquals("Heal allies", spell.getDescription());
        spell.setDescription("Revive the fallen");
        assertEquals("Revive the fallen", spell.getDescription());
    }

    @Test
    @DisplayName("Test setting and getting the casting time of a spell")
    public void testSetAndGetSpellCastingTime() {
        Spell spell = new Spell("Firestorm", "A blaze of fire",4,15);
        assertEquals(4, spell.getCastingTime());
        spell.setCastingTime(3);
        assertEquals(3, spell.getCastingTime());
    }

    @Test
    @DisplayName("Test setting and getting the cool-down time of a spell")
    public void testSetAndGetSpellCoolDown() {
        Spell spell = new Spell("Blizzard", "Summon icy winds",2,10);
        assertEquals(10, spell.getCoolDown());
        spell.setCoolDown(5);
        assertEquals(5, spell.getCoolDown());
    }

    @Test
    @DisplayName("Test equals method for Spell")
    public void testSpellEquals() {
       Spell spell1 = new Spell("Fireball", "A powerful fire spell",2,3);
       Spell spell2 = new Spell("Fireball", "A powerful fire spell",2,3);
       Spell spell3 = new Spell("Frostbolt", "A freezing spell",3,4);
       assertEquals(spell1, spell1);
       assertEquals(spell1, spell2);
       assertNotSame(spell1, spell3);
    }

    @Test
    @DisplayName("Test hashCode method for Spell")
    public void testSpellHashCode() {
        Spell spell1 = new Spell("Fireball", "A powerful fire spell",2,3);
        Spell spell2 = new Spell("Fireball", "A powerful fire spell",2,3);
        Spell spell3 = new Spell("Frostbolt", "A freezing spell",3,4);
        assertEquals(spell1.hashCode(), spell1.hashCode());
        assertEquals(spell1.hashCode(), spell2.hashCode());
        assertNotSame(spell1, spell3);
    }

}
