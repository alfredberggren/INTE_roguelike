import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

}
