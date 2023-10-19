import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AbilityTest {
    //namnen för testerna kan var whenBlaBla_thenBlabla

    private Character character;

    @BeforeEach
    void setUp() {
        character = new Character("Rudolf", 10, 100, new Position(1,1), new TextIO());
    }

    @Test
    @DisplayName("Returns if both Magic Ability and Physical Ability exist")
    public void testBothMagicAndPhysicalAbility() {
        MagicAbility magicAbility = new MagicAbility("Fireball", 20,1,"A fiery ball",2,3,5);
        PhysicalAbility physicalAbility = new PhysicalAbility("Slash", 10,1,"Physical attack");
        assertEquals("MAGICAL" + "PHYSICAL", magicAbility.toString() + physicalAbility);
    }


    @Test //flytta till characters test
    @DisplayName("Testing that magic ability is affected")
    public void testMagicAbilityAffected() {
        MagicAbility fireSpell = new MagicAbility("Fire",10,1,"Shoots fire",1,3,5);
        character.addAbility(fireSpell);
        MagicAbility ability = new MagicAbility(fireSpell.getName(), 10,1, "Fire",2,3,5);
        character.setLevel(0);
        character.removeAbility(fireSpell);
        //assertTrue(ability.calculateImpactOnAbility(character));
    }

    @Test //flytta till characters test
    @DisplayName("Testing that magic ability is not affected")
    public void testMagicAbilityNotAffected() {
        MagicAbility iceSpell = new MagicAbility("Ice",5,1, "Shoots ice",1,2,5);
        character.addAbility(iceSpell);
        MagicAbility ability = new MagicAbility(iceSpell.getName(), 10,1,"Ice",2,3,5);
        character.setLevel(1);
        //assertFalse(ability.calculateImpactOnAbility(character));
    }


    @Test
    @DisplayName("Test getting the description of a spell")
    public void testSpellDescription() {
        MagicAbility spell = new MagicAbility("Ice Shard", 10,2, "A shard of ice",1,5,5);
        assertEquals("A shard of ice", spell.getDescription());
    }


    @Test
    @DisplayName("Test setting and getting the name of a spell")
    public void testSetAndGetSpellName() {
        MagicAbility spell = new MagicAbility("Earthquake", 10,5, "Shake the ground",5,20,5);
        assertEquals("Earthquake", spell.getName());
        spell.setName("Tornado");
        assertEquals("Tornado", spell.getName());
    }

    @Test
    @DisplayName("Test setting and getting the description of a spell")
    public void testSetAndGetSpellDescription() {
        MagicAbility spell = new MagicAbility("Healing Touch", 0,2, "Heal allies",2,10,5);
        assertEquals("Heal allies", spell.getDescription());
        spell.setDescription("Revive the fallen");
        assertEquals("Revive the fallen", spell.getDescription());
    }

    @Test
    @DisplayName("Test minimum level cannot be negative")
    public void testMinimumLevelCannotBeNegative() {
        MagicAbility ability = new MagicAbility("Fire",10,-2,"Shoots fire",1,1,5);
        assertEquals(1, ability.getRequiredLevel());
    }

    @Test
    @DisplayName("Test equals method for Ability")
    public void testAbilityEquals() {
        Ability ability1 = new PhysicalAbility("Physical Attack",20,1, "Physical attack");
        Ability ability2 = new PhysicalAbility("Physical Attack", 20,1, "Physical attack");
        Ability ability3 = new MagicAbility("Magical Spell",30,2, "Magic",1,1,5);
        assertEquals(ability1, ability1);
        assertEquals(ability1, ability2);
        assertNotSame(ability1, ability3);
    }

    @Test
    @DisplayName("Test hashCode method for Ability")
    public void testAbilityHashCode() {
        Ability ability1 = new PhysicalAbility("Physical Attack",20,1, "Physical attack");
        Ability ability2 = new PhysicalAbility("Physical Attack", 20,1, "Physical attack");
        Ability ability3 = new MagicAbility("Magical Spell",30,2, "Magic",2,3,5);
        assertEquals(ability1.hashCode(), ability2.hashCode());
        assertNotSame(ability1.hashCode(), ability3.hashCode());
    }
}
