import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AbilityTest {

    static Character DEFAULT_CHARACTER;

    @BeforeEach
    void setUp() {
        DEFAULT_CHARACTER = new Character(100,10, 100);
    }
    @Test
    @DisplayName("Returns if a Magic Ability exist")
    public void testOnlyMagicAbility() {
        MagicAbility magicAbility = new MagicAbility("Fireball",25,1);
        assertEquals("MAGICAL", magicAbility.toString());
    }

    @Test
    @DisplayName("Returns if a Physical Ability exist")
    public void testOnlyPhysicalAbility() {
        PhysicalAbility physicalAbility = new PhysicalAbility("Sword",15,1);
        assertEquals("PHYSICAL", physicalAbility.toString());
    }

    @Test
    @DisplayName("Returns if both Magic Ability and Physical Ability exist")
    public void testBothMagicAndPhysicalAbility() {
        MagicAbility magicAbility = new MagicAbility("Fireball", 20,1);
        PhysicalAbility physicalAbility = new PhysicalAbility("Sword", 10,1);
        assertEquals("MAGICAL" + "PHYSICAL", magicAbility.toString() + physicalAbility);
    }

    @Test
    @DisplayName("Test calculating damage for Fireball")
    public void testCalculateDamageForFireball() {
        MagicAbility fireBall = new MagicAbility("Fireball",10,1);
        DEFAULT_CHARACTER.setLevel(2);
        DEFAULT_CHARACTER.setExperiencePoint(50);
        int damage = fireBall.calculateDamageOfAbility(DEFAULT_CHARACTER);
        assertEquals(25, damage);
    }

    @Test
    @DisplayName("Test calculating damage for Sword")
    public void testCalculateDamageForSword() {
        PhysicalAbility sword = new PhysicalAbility("Sword",5,1);
        DEFAULT_CHARACTER.setLevel(2);
        DEFAULT_CHARACTER.setExperiencePoint(50);
        int damage = sword.calculateDamageOfAbility(DEFAULT_CHARACTER);
        assertEquals(14, damage);
    }

    @Test
    @DisplayName("Testing that magic ability is affected")
    public void testMagicAbilityAffected() {
        Spell fireSpell = new Spell("Fire","Shoots fire");
        DEFAULT_CHARACTER.addSpell(fireSpell);
        DEFAULT_CHARACTER.setExperiencePoint(100);
        MagicAbility ability = new MagicAbility(fireSpell.getName(), 10,1);
        ability.setCharacter(DEFAULT_CHARACTER);
        DEFAULT_CHARACTER.decreaseXP(20);
        DEFAULT_CHARACTER.forgetSpell(fireSpell);
        assertFalse(ability.calculateImpactOnAbility());
    }

    @Test
    @DisplayName("Testing that magic ability is not affected")
    public void testMagicAbilityNotAffected() {
        Spell iceSpell = new Spell("Ice","Shoots ice");
        DEFAULT_CHARACTER.addSpell(iceSpell);
        MagicAbility ability = new MagicAbility(iceSpell.getName(), 10,1);
        ability.setCharacter(DEFAULT_CHARACTER);
        DEFAULT_CHARACTER.setExperiencePoint(150);
        assertTrue(ability.calculateImpactOnAbility());
    }

    @Test
    @DisplayName("Test for calculating damage for Magic Ability when character has low level")
    public void testMagicCalculateDamageForLowLevelPlayer() {
        MagicAbility fireBall = new MagicAbility("Fireball",10,1);
        DEFAULT_CHARACTER.setLevel(1);
        DEFAULT_CHARACTER.setExperiencePoint(0);
        int damage = fireBall.calculateDamageOfAbility(DEFAULT_CHARACTER);
        assertEquals(15, damage);
    }

    @Test
    @DisplayName("Test for calculating damage for Magic Ability when character has high level")
    public void testMagicCalculateDamageForHighLevelPlayer() {
        MagicAbility fireBall = new MagicAbility("Fireball",10,1);
        DEFAULT_CHARACTER.setLevel(10);
        DEFAULT_CHARACTER.setExperiencePoint(100);
        int damage = fireBall.calculateDamageOfAbility(DEFAULT_CHARACTER);
        assertEquals(70, damage);
    }

    @Test
    @DisplayName("Test for calculating damage for Physical Ability when character has low level")
    public void testPhysicalCalculateDamageForLowLevelPlayer() {
        PhysicalAbility sword = new PhysicalAbility("Sword", 5,1);
        DEFAULT_CHARACTER.setLevel(1);
        DEFAULT_CHARACTER.setExperiencePoint(0);
        int damage = sword.calculateDamageOfAbility(DEFAULT_CHARACTER);
        assertEquals(7, damage);
    }

    @Test
    @DisplayName("Test for calculating damage for Physical Ability when character has high level")
    public void testPhysicalCalculateDamageForHighLevelPlayer() {
        PhysicalAbility sword = new PhysicalAbility("Sword", 5,1);
        DEFAULT_CHARACTER.setLevel(10);
        DEFAULT_CHARACTER.setExperiencePoint(100);
        int damage = sword.calculateDamageOfAbility(DEFAULT_CHARACTER);
        assertEquals(35, damage);
    }

    @Test
    @DisplayName("Test that a character is able to learn Magic")
    public void testCharacterCanLearnMagicAbility() {
        DEFAULT_CHARACTER.setLevel(10);
        MagicAbility fireMagic = new MagicAbility("Fireball", 10, 5);
        assertTrue(fireMagic.isLearnable(DEFAULT_CHARACTER));
    }

    @Test
    @DisplayName("Test that a character is not able to learn Magic")
    public void testCharacterCannotLearnMagicAbility() {
        DEFAULT_CHARACTER.setLevel(3);
        MagicAbility fireMagic = new MagicAbility("Fireball", 10, 5);
        assertFalse(fireMagic.isLearnable(DEFAULT_CHARACTER));
    }

}
