import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PhysicalAbilityTest {

    private Character character;
    private Player player;

    @BeforeEach
    void setUp() {
        character = new Character("Rudolf", 10, 100, new Position(1,1), new TextIO());
        player = new Player("Ragnar",100,10, new Position(1,1), new TextIO());
    }

    @Test
    @DisplayName("Returns if a Physical Ability exist")
    public void testOnlyPhysicalAbility() {
        PhysicalAbility physicalAbility = new PhysicalAbility("Slash",15,1,"Physical attack");
        assertEquals("PHYSICAL", physicalAbility.toString());
    }

    @Test
    @DisplayName("Test calculating damage for Slash")
    public void testCalculateDamageForSword() {
        PhysicalAbility slash = new PhysicalAbility("Slash",5,1, "Physical attack");
        character.setLevel(2);
        player.increaseXP(50);
        int damage = slash.calculateDamageOfPhysicalAbility(character);
        assertEquals(14, damage);
    }

    @Test
    @DisplayName("Test for calculating damage for Physical Ability when character has low level")
    public void testPhysicalCalculateDamageForLowLevelPlayer() {
        PhysicalAbility slash = new PhysicalAbility("Slash", 5,1, "Physical attack");
        character.setLevel(1);
        player.increaseXP(0);
        int damage = slash.calculateDamageOfPhysicalAbility(character);
        assertEquals(7, damage);
    }

    @Test
    @DisplayName("Test for calculating damage for Physical Ability when character has high level")
    public void testPhysicalCalculateDamageForHighLevelPlayer() {
        PhysicalAbility slash = new PhysicalAbility("Slash", 5,1, "Physical attack");
        character.setLevel(10);
        player.increaseXP(100);
        int damage = slash.calculateDamageOfPhysicalAbility(character);
        assertEquals(35, damage);
    }

    @Test
    @DisplayName("Test getting the name of a physical ability")
    public void testSpellName() {
        PhysicalAbility slash = new PhysicalAbility("Slash", 5,1, "Physical attack");
        assertEquals("Slash", slash.getName());
    }
}
