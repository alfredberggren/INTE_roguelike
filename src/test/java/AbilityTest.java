import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AbilityTest {
    private MagicAbility magicAbility;
    private PhysicalAbility physicalAbility;

    @BeforeEach
    void setUp() {
        magicAbility = new MagicAbility("Fireball",10,1,"A fiery projectile",1,2,5);
        physicalAbility = new PhysicalAbility("Slash",5,0,"Physical Attack");

    }

    @Test
    @DisplayName("Returns if both Magic Ability and Physical Ability exist")
    public void whenBothPhysicalAndMagicAbilityExist_thenReturnTheType() {
        assertEquals("MAGICAL PHYSICAL", magicAbility.toString() + " " + physicalAbility);
    }

    @Test
    @DisplayName("Test getting the description of a spell")
    public void whenGetDescriptionOfAbility_thenCheckIfItIsEquals() {
        assertEquals("A fiery projectile", magicAbility.getDescription());
    }


    @Test
    @DisplayName("Test setting and getting the name of a spell")
    public void whenSetAndGetNameOfAbility_thenCheckIfItReturnCorrect() {
        assertEquals("Fireball", magicAbility.getName());
        magicAbility.setName("Tornado");
        assertEquals("Tornado", magicAbility.getName());
    }

    @Test
    @DisplayName("Test setting and getting the description of a spell")
    public void whenSetAndGetDescription_thenCheckIfItReturnCorrect() {
        assertEquals("A fiery projectile", magicAbility.getDescription());
        magicAbility.setDescription("Revive the fallen");
        assertEquals("Revive the fallen", magicAbility.getDescription());
    }

    @Test
    @DisplayName("Test minimum required level cannot be negative")
    public void whenMinimumRequiredLevelIsSetForAbility_thenItCannotBeNegative() {
            assertThrows(IllegalArgumentException.class, () ->
                    new PhysicalAbility("NegativeLevel", 10,-1, "Negative Level"));
            assertThrows(IllegalArgumentException.class, () ->
                    new MagicAbility("Negative level", 10,-1,"Negative level",0,0,0));
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
