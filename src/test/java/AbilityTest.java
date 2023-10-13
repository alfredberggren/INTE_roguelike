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
        MagicAbility magicAbility = new MagicAbility("Fireball",25,1,"Magic");
        assertEquals("Magic", magicAbility.toString());
    }

    @Test
    @DisplayName("Returns if a Physical Ability exist")
    public void testOnlyPhysicalAbility() {
        PhysicalAbility physicalAbility = new PhysicalAbility("Sword",15,1,"Physical");
        assertEquals("Physical", physicalAbility.toString());
    }

    @Test
    @DisplayName("Returns if both Magic Ability and Physical Ability exist")
    public void testBothMagicAndPhysicalAbility() {
        MagicAbility magicAbility = new MagicAbility("Fireball", 20,1,"Magic");
        PhysicalAbility physicalAbility = new PhysicalAbility("Sword", 10,1, "Physical");
        assertEquals("Magic" + "Physical", magicAbility.toString() + physicalAbility);
    }

    @Test
    @DisplayName("Test calculating damage for Fireball")
    public void testCalculateDamageForFireball() {
        MagicAbility fireBall = new MagicAbility("Fireball",10,1, "Magic");
        DEFAULT_CHARACTER.setLevel(2);
        DEFAULT_CHARACTER.setExperiencePoint(50);
        int damage = fireBall.calculateDamage(DEFAULT_CHARACTER);
        assertEquals(25, damage);
    }

    @Test
    @DisplayName("Test calculating damage for Sword")
    public void testCalculateDamageForSword() {
        PhysicalAbility sword = new PhysicalAbility("Sword",5,1, "Physical");
        DEFAULT_CHARACTER.setLevel(2);
        DEFAULT_CHARACTER.setExperiencePoint(50);
        int damage = sword.calculateDamage(DEFAULT_CHARACTER);
        assertEquals(14, damage);
    }

    @Test
    @DisplayName("Testing that magic ability is affected")
    public void testMagicAbilityAffected() {
        Spell fireSpell = new Spell("Fire");
        DEFAULT_CHARACTER.addSpell(fireSpell);
        DEFAULT_CHARACTER.setExperiencePoint(100);
        MagicAbility ability = new MagicAbility(fireSpell.getName(), 10,1,"Magic");
        ability.setCharacter(DEFAULT_CHARACTER);
        DEFAULT_CHARACTER.decreaseXP(20);
        DEFAULT_CHARACTER.forgetSpell(fireSpell);
        //boolean affectedDamage = ability.calculateAffect();
        assertFalse(ability.calculateAffect());
    }

    @Test
    @DisplayName("Testing that magic ability is not affected")
    public void testMagicAbilityNotAffected() {
        Spell iceSpell = new Spell("Ice");
        DEFAULT_CHARACTER.addSpell(iceSpell);
        MagicAbility ability = new MagicAbility(iceSpell.getName(), 10,1,"Magic");
        ability.setCharacter(DEFAULT_CHARACTER);
        //boolean affectedDamage = ability.calculateAffect();
        assertTrue(ability.calculateAffect());
    }

    @Test
    @DisplayName("Test for calculating damage for Magic Ability when character has low level")
    public void testMagicCalculateDamageForLowLevelPlayer() {
        MagicAbility fireBall = new MagicAbility("Fireball",10,1,"Magic");
        DEFAULT_CHARACTER.setLevel(1);
        DEFAULT_CHARACTER.setExperiencePoint(0);
        int damage = fireBall.calculateDamage(DEFAULT_CHARACTER);
        assertEquals(15, damage);
    }

    @Test
    @DisplayName("Test for calculating damage for Magic Ability when character has high level")
    public void testMagicCalculateDamageForHighLevelPlayer() {
        MagicAbility fireBall = new MagicAbility("Fireball",10,1,"Magic");
        DEFAULT_CHARACTER.setLevel(10);
        DEFAULT_CHARACTER.setExperiencePoint(100);
        int damage = fireBall.calculateDamage(DEFAULT_CHARACTER);
        assertEquals(70, damage);
    }

    @Test
    @DisplayName("Test for calculating damage for Physical Ability when character has low level")
    public void testPhysicalCalculateDamageForLowLevelPlayer() {
        PhysicalAbility sword = new PhysicalAbility("Sword", 5,1, "Physical");
        DEFAULT_CHARACTER.setLevel(1);
        DEFAULT_CHARACTER.setExperiencePoint(0);
        int damage = sword.calculateDamage(DEFAULT_CHARACTER);
        assertEquals(7, damage);
    }

    @Test
    @DisplayName("Test for calculating damage for Physical Ability when character has high level")
    public void testPhysicalCalculateDamageForHighLevelPlayer() {
        PhysicalAbility sword = new PhysicalAbility("Sword", 5,1, "Physical");
        DEFAULT_CHARACTER.setLevel(10);
        DEFAULT_CHARACTER.setExperiencePoint(100);
        int damage = sword.calculateDamage(DEFAULT_CHARACTER);
        assertEquals(35, damage);
    }

}
