import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AbilityTest {

    static Player DEFAULT_PLAYER;
    static Character DEFAULT_CHARACTER;

    @Test
    @DisplayName("Returns if it is a Magic Ability")
    public void testOnlyMagicAbility() {
        MagicAbility magicAbility = new MagicAbility("Fireball",25,"Magic", DEFAULT_CHARACTER, DEFAULT_PLAYER);
        assertEquals("Magic", magicAbility.toString());
    }

    @Test
    @DisplayName("Returns if it is a Physical Ability")
    public void testOnlyPhysicalAbility() {
        PhysicalAbility physicalAbility = new PhysicalAbility("Sword",15,"Physical");
        assertEquals("Physical", physicalAbility.toString());
    }

    @Test
    @DisplayName("Returns if it is a Magic Ability and a Physical Ability")
    public void testBothMagicAndPhysicalAbility() {
        MagicAbility magicAbility = new MagicAbility("Fireball", 20,"Magic", DEFAULT_CHARACTER, DEFAULT_PLAYER);
        PhysicalAbility physicalAbility = new PhysicalAbility("Sword", 10, "Physical");
        assertEquals("Magic" + "Physical", magicAbility.toString() + physicalAbility);
    }

    @Test
    @DisplayName("Test calculating damage for Fireball")
    public void testCalculateDamageForFireball() {
        MagicAbility fireBall = new MagicAbility("Fireball",10, "Magic", DEFAULT_CHARACTER, DEFAULT_PLAYER);
        DEFAULT_PLAYER.setLevel(2);
        DEFAULT_PLAYER.setExperiencePoint(50);
        int damage = fireBall.calculateDamage(DEFAULT_PLAYER);
        assertEquals(25, damage);
    }

    @Test
    @DisplayName("Test calculating damage for Sword")
    public void testCalculateDamageForSword() {
        PhysicalAbility sword = new PhysicalAbility("Sword",5, "Physical");
        DEFAULT_PLAYER.setLevel(2);
        DEFAULT_PLAYER.setExperiencePoint(50);
        int damage = sword.calculateDamage(DEFAULT_PLAYER);
        assertEquals(14, damage);
    }

    @Test
    @DisplayName("Testing that magic ability is affected")
    public void testMagicAbilityAffected() {
        Spell fireSpell = new Spell("Fire");
        DEFAULT_CHARACTER.addSpell(fireSpell);
        DEFAULT_PLAYER.setExperiencePoint(10);
        MagicAbility ability = new MagicAbility(fireSpell.getName(), 10,"Magic", DEFAULT_CHARACTER, DEFAULT_PLAYER);
        DEFAULT_PLAYER.decreaseXP(5);
        DEFAULT_CHARACTER.forgetSpell(fireSpell);
        ability.calculateAffect();
        String affectedDamage = ability.affectAbility();
        assertEquals("Forgotten spell", affectedDamage);
    }

}
