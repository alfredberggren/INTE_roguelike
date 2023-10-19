import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AbilityTest {
    //namnen för testerna kan var whenBlaBla_thenBlabla

    private Character character;
    private MagicAbility magicAbility;
    private PhysicalAbility physicalAbility;

    @BeforeEach
    void setUp() {
        character = new Character("Rudolf", 10, 100, new Position(1,1), new TextIO());
        magicAbility = new MagicAbility("Fireball",10,1,"A fiery projectile",1,2,5);
        physicalAbility = new PhysicalAbility("Slash",5,0,"Physical Attack");

    }

    @Test
    @DisplayName("Returns if both Magic Ability and Physical Ability exist")
    public void testBothMagicAndPhysicalAbility() {
        assertEquals("MAGICAL PHYSICAL", magicAbility.toString() + physicalAbility);
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
        assertEquals("A fiery projectile", magicAbility.getDescription());
    }


    @Test
    @DisplayName("Test setting and getting the name of a spell")
    public void testSetAndGetSpellName() {
        assertEquals("Fireball", magicAbility.getName());
        magicAbility.setName("Tornado");
        assertEquals("Tornado", magicAbility.getName());
    }

    @Test
    @DisplayName("Test setting and getting the description of a spell")
    public void testSetAndGetSpellDescription() {
        assertEquals("A fiery projectile", magicAbility.getDescription());
        magicAbility.setDescription("Revive the fallen");
        assertEquals("Revive the fallen", magicAbility.getDescription());
    }

    @Test
    @DisplayName("Test minimum level cannot be negative") //skriv om
    public void testMinimumLevelCannotBeNegative() {
        magicAbility.getRequiredLevel();
        assertEquals(1, magicAbility.getRequiredLevel());
    }

    @Test
    @DisplayName("Test equals method for Ability")
    public void testAbilityEquals() {
        assertEquals(physicalAbility, physicalAbility);
        assertEquals(physicalAbility, physicalAbility);
        assertNotSame(physicalAbility, magicAbility);
    }

    @Test
    @DisplayName("Test hashCode method for Ability")
    public void testAbilityHashCode() {
        assertEquals(physicalAbility.hashCode(), physicalAbility.hashCode());
        assertNotSame(physicalAbility.hashCode(), magicAbility.hashCode());
    }
}
