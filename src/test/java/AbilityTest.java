import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AbilityTest {

    private Character character;
    private Player player;

    @BeforeEach
    void setUp() {
        character = new Character("Rudolf", 10, 100, new Position(1,1), new TextIO());
        player = new Player("Rudolf",100,10, new Position(1,1), new TextIO());
    }
    @Test
    @DisplayName("Returns if a Magic Ability exist")
    public void testOnlyMagicAbility() {
        MagicAbility magicAbility = new MagicAbility("Fireball",25,1,"A fiery ball",2,3);
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
        MagicAbility magicAbility = new MagicAbility("Fireball", 20,1,"A fiery ball",2,3);
        PhysicalAbility physicalAbility = new PhysicalAbility("Sword", 10,1);
        assertEquals("MAGICAL" + "PHYSICAL", magicAbility.toString() + physicalAbility);
    }

    @Test
    @DisplayName("Test calculating damage for Fireball")
    public void testCalculateDamageForFireball() {
        MagicAbility fireBall = new MagicAbility("Fireball",10,1,"A fiery ball",2,3);
        character.setLevel(2);
        player.increaseXP(50);
        int damage = fireBall.calculateDamageOfAbility(character);
        assertEquals(25, damage);
    }

    @Test
    @DisplayName("Test calculating damage for Sword")
    public void testCalculateDamageForSword() {
        PhysicalAbility sword = new PhysicalAbility("Sword",5,1);
        character.setLevel(2);
        player.increaseXP(50);
        int damage = sword.calculateDamageOfAbility(character);
        assertEquals(14, damage);
    }

    @Test
    @DisplayName("Testing that magic ability is affected")
    public void testMagicAbilityAffected() {
        MagicAbility fireSpell = new MagicAbility("Fire",10,1,"Shoots fire",1,3);
        character.addAbility(fireSpell);
        player.increaseXP(100);
        MagicAbility ability = new MagicAbility(fireSpell.getName(), 10,1, "Fire",2,3);
        ability.setCharacter(character);
        player.decreaseXP(20);
        character.forgetAbility(fireSpell);
        assertFalse(ability.calculateImpactOnAbility());
    }

    @Test
    @DisplayName("Testing that magic ability is not affected")
    public void testMagicAbilityNotAffected() {
        MagicAbility iceSpell = new MagicAbility("Ice",5,1, "Shoots ice",1,2);
        character.addAbility(iceSpell);
        MagicAbility ability = new MagicAbility(iceSpell.getName(), 10,1,"Ice",2,3);
        ability.setCharacter(character);
        player.increaseXP(150);
        assertTrue(ability.calculateImpactOnAbility());
    }

    @Test
    @DisplayName("Test for calculating damage for Magic Ability when character has low level")
    public void testMagicCalculateDamageForLowLevelPlayer() {
        MagicAbility fireBall = new MagicAbility("Fireball",10,1,"A fiery ball",2,3);
        character.setLevel(1);
        player.increaseXP(0);
        int damage = fireBall.calculateDamageOfAbility(character);
        assertEquals(15, damage);
    }

    @Test
    @DisplayName("Test for calculating damage for Magic Ability when character has high level")
    public void testMagicCalculateDamageForHighLevelPlayer() {
        MagicAbility fireBall = new MagicAbility("Fireball",10,1,"A fiery ball",2,3);
        character.setLevel(10);
        player.increaseXP(100);
        int damage = fireBall.calculateDamageOfAbility(character);
        assertEquals(70, damage);
    }

    @Test
    @DisplayName("Test for calculating damage for Physical Ability when character has low level")
    public void testPhysicalCalculateDamageForLowLevelPlayer() {
        PhysicalAbility sword = new PhysicalAbility("Sword", 5,1);
        character.setLevel(1);
        player.increaseXP(0);
        int damage = sword.calculateDamageOfAbility(character);
        assertEquals(7, damage);
    }

    @Test
    @DisplayName("Test for calculating damage for Physical Ability when character has high level")
    public void testPhysicalCalculateDamageForHighLevelPlayer() {
        PhysicalAbility sword = new PhysicalAbility("Sword", 5,1);
        character.setLevel(10);
        player.increaseXP(100);
        int damage = sword.calculateDamageOfAbility(character);
        assertEquals(35, damage);
    }

    @Test
    @DisplayName("Test that a character is able to learn Magic")
    public void testCharacterCanLearnMagicAbility() {
        character.setLevel(10);
        MagicAbility fireMagic = new MagicAbility("Fireball", 10, 5, "A fiery ball",2,3);
        assertTrue(fireMagic.isLearnable(character));
    }

    @Test
    @DisplayName("Test that a character is not able to learn Magic")
    public void testCharacterCannotLearnMagicAbility() {
        character.setLevel(3);
        MagicAbility fireMagic = new MagicAbility("Fireball", 10, 5,"A fiery ball",2,3);
        assertFalse(fireMagic.isLearnable(character));
    }

    @Test
    @DisplayName("Test equals method for Ability")
    public void testAbilityEquals() {
        Ability ability1 = new PhysicalAbility("Physical Attack",20,1);
        Ability ability2 = new PhysicalAbility("Physical Attack", 20,1);
        Ability ability3 = new MagicAbility("Magical Spell",30,2, "Magic",1,1);
        assertEquals(ability1, ability1);
        assertEquals(ability1, ability2);
        assertNotSame(ability1, ability3);
    }

    @Test
    @DisplayName("Test hashCode method for Ability")
    public void testAbilityHashCode() {
        Ability ability1 = new PhysicalAbility("Physical Attack",20,1);
        Ability ability2 = new PhysicalAbility("Physical Attack", 20,1);
        Ability ability3 = new MagicAbility("Magical Spell",30,2, "Magic",2,3);
        assertEquals(ability1.hashCode(), ability2.hashCode());
        assertNotSame(ability1.hashCode(), ability3.hashCode());
    }

    @Test
    @DisplayName("Test getting the name of a spell")
    public void testSpellName() {
        MagicAbility spell = new MagicAbility("Fireball", 10, 2, "A fiery projectile",2,10);
        assertEquals("Fireball", spell.getName());
    }

    @Test
    @DisplayName("Test getting the description of a spell")
    public void testSpellDescription() {
        MagicAbility spell = new MagicAbility("Ice Shard", 10,2, "A shard of ice",1,5);
        assertEquals("A shard of ice", spell.getDescription());
    }

    @Test
    @DisplayName("Test getting the casting time of a spell")
    public void testSpellCastingTime() {
        MagicAbility spell = new MagicAbility("Thunderbolt", 10, 3, "A powerful lightning spell",3,15);
        assertEquals(3, spell.getCastingTime());
    }

    @Test
    @DisplayName("Test getting the cool-down time of a spell")
    public void testSpellCoolDown() {
        MagicAbility spell = new MagicAbility("Lightning Strike", 10,2, "A fast lightning attack",2,8);
        assertEquals(8, spell.getCoolDown());
    }

    @Test
    @DisplayName("Test setting and getting the name of a spell")
    public void testSetAndGetSpellName() {
        MagicAbility spell = new MagicAbility("Earthquake", 10,5, "Shake the ground",5,20);
        assertEquals("Earthquake", spell.getName());
        spell.setName("Tornado");
        assertEquals("Tornado", spell.getName());
    }

    @Test
    @DisplayName("Test setting and getting the description of a spell")
    public void testSetAndGetSpellDescription() {
        MagicAbility spell = new MagicAbility("Healing Touch", 0,2, "Heal allies",2,10);
        assertEquals("Heal allies", spell.getDescription());
        spell.setDescription("Revive the fallen");
        assertEquals("Revive the fallen", spell.getDescription());
    }

    @Test
    @DisplayName("Test setting and getting the casting time of a spell")
    public void testSetAndGetSpellCastingTime() {
        MagicAbility spell = new MagicAbility("Firestorm", 10,4, "A blaze of fire",4,15);
        assertEquals(4, spell.getCastingTime());
        spell.setCastingTime(3);
        assertEquals(3, spell.getCastingTime());
    }

    @Test
    @DisplayName("Test setting and getting the cool-down time of a spell")
    public void testSetAndGetSpellCoolDown() {
        MagicAbility spell = new MagicAbility("Blizzard", 10,2, "Summon icy winds",2,10);
        assertEquals(10, spell.getCoolDown());
        spell.setCoolDown(5);
        assertEquals(5, spell.getCoolDown());
    }

    @Test
    @DisplayName("Test if magic ability is learnable")
    public void testLearnableMagicAbility() {
        MagicAbility ability = new MagicAbility("Fireball", 10,3, "A fiery ball",1,2);
        character.setLevel(3);
        assertTrue(ability.isLearnable(character));
    }

    @Test
    @DisplayName("Test if magic ability is not learnable")
    public void testNotLearnableMagicAbility() {
        MagicAbility ability = new MagicAbility("Fireball", 10,3, "A fiery ball",1,2);
        character.setLevel(0);
        assertFalse(ability.isLearnable(character));
    }

    @Test
    @DisplayName("Test equals method for Spell")
    public void testSpellEquals() {
        MagicAbility spell1 = new MagicAbility("Fireball", 10,2, "A powerful fire spell",2,3);
        MagicAbility spell2 = new MagicAbility("Fireball", 10,2, "A powerful fire spell",2,3);
        MagicAbility spell3 = new MagicAbility("Frostbolt", 7,3,"A freezing spell",3,4);
        assertEquals(spell1, spell1);
        assertEquals(spell1, spell2);
        assertNotSame(spell1, spell3);
    }

    @Test
    @DisplayName("Test hashCode method for Spell")
    public void testSpellHashCode() {
        MagicAbility spell1 = new MagicAbility("Fireball", 10,2, "A powerful fire spell",2,3);
        MagicAbility spell2 = new MagicAbility("Fireball", 10,2, "A powerful fire spell",2,3);
        MagicAbility spell3 = new MagicAbility("Frostbolt", 7,3,"A freezing spell", 3,4);
        assertEquals(spell1.hashCode(), spell1.hashCode());
        assertEquals(spell1.hashCode(), spell2.hashCode());
        assertNotSame(spell1, spell3);
    }
}
