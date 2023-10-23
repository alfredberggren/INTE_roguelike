import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class CharacterTest {
    static Character character;
    private static MagicAbility magicAbility = new MagicAbility("Fireball",10,1,"A fiery projectile",1,2,5);
    static Character characterWithPos = new Character("Rudolf", 100, 1,1,  new Position(1, 2), new TextIO());
    static Equipment equipment = new Equipment("Sword", EquipmentSlot.LEFT_HAND, Equipment.Effect.DAMAGE, 50, new PhysicalAbility("Slash", 10,1, "Test"));

    @BeforeEach
    void setUp() {
        character = new Character("Ragnar", 80, 20,1, new TextIO());

    }


    //name tests
    @Test
    public void testCorrectName() {
        character.setName("Rudolf");
        assertEquals("Rudolf", character.getName());
    }

    @Test
    public void whenNameIsNullThrowsException() {
        assertThrows(NullPointerException.class, () -> {
            character.setName(null);
        });
    }

    @Test
    public void whenNameIsEmptyThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            character.setName("");
        });
    }

    @Test
    public void whenNameIsTooShortThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            character.setName("A");
        });
    }

    @Test
    public void whenNameBeginsWithDigitThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            character.setName("1Ab");
        });
    }

    @Test
    public void whenNameIsTooLongThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            character.setName("tooLongNameToBeAccepted");
        });
    }

    @Test
    public void whenNameContainsNotOnlyAlphanumericCharactersAndUnderscores_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            character.setName("Aabba&&");
        });
    }
//name tests


    @Test
    @DisplayName("Test to get character's health")
    public void testToGetCharactersHealth() {
        assertEquals(80, character.getHealth());
    }

    @Test
    @DisplayName("Test to get character's position")
    public void testToGetCharactersPosition() {
        assertEquals(new Position(1, 2), characterWithPos.getPosition());
        assertEquals(1, characterWithPos.getPosition().getX());
        assertEquals(2, characterWithPos.getPosition().getY());
    }

    @Test
    @DisplayName("Test to get character's speed")
    public void testToGetCharactersSpeed() {
        assertEquals(20, character.getSpeed());
    }

    @Test
    @DisplayName("Test to set negative health")
    public void testToSetNegativeHealth_ThrowsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Character("Ragnar", -100, 0, 1, new TextIO());
        });
    }

    @Test
    @DisplayName("Test to set health larger than max health")
    public void testToSetHealthLargeThanMaxHealth_ShouldSetMaxHealth() {
        character.setHealth(110);
        assertEquals(100, character.getHealth());
    }

    @Test
    @DisplayName("Test to set health equal to zero")
    public void testIfCharacterIsDeadWhenHealthIsZero() {
        character.setHealth(0);
        assertTrue(character.isDead());
    }

    @Test
    @DisplayName("Test to set correct health")
    public void testToSetCorrectHealth() {
        character.setHealth(100);
        assertEquals(100, character.getHealth());
    }

    @Test
    @DisplayName("Test to set correct position")
    public void testToSetCorrectPosition() {
        character.setPos(new Position(2,2));
        assertEquals(new Position(2,2).toString(), character.getPosition().toString());
    }

    @Test
    @DisplayName("Test to set position equal to null")
    public void testToSetPositionEqualToNull_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
           character.setPos(null);
        });
    }

    @Test
    @DisplayName("Test to set i constructor negative speed")
    public void testConstructorWithNegativeSpeed_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Character("Ragnar", 100, -10,1, new TextIO());
        });
    }

    @Test
    @DisplayName("Test to set i constructor IO equal to null")
    public void testConstructorWithNIOEqualToNull_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Character("Ragnar", 100, -10,1, null);
        });
    }

    @Test
    @DisplayName("Test to set negative mana")
    public void testToSetNegativeMana_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            character.setMana(-10);
        });
    }

    @Test
    @DisplayName("Test to set mana larger than max mana")
    public void testToSetManaLargeThanMaxMana_ShouldSetMaxMana() {
        character.setMana(110);
        assertEquals(100, character.getMana());
    }

    @Test
    @DisplayName("Test to set mana equal to zero")
    public void testToSetManaEqualToZero() {
        character.setHealth(0);
        assertEquals(0, character.getMana());
        assertFalse(character.canUseMagic());
    }

    @Test
    @DisplayName("Test to set correct mana")
    public void testToSetCorrectMana() {
        character.setMana(100);
        assertEquals(100, character.getMana());
    }

    @Test
    @DisplayName("Test to set correct level")
    public void testToSetCorrectLevel() {
        character.setLevel(1);
        assertEquals(1, character.getLevel());
    }

    @Test
    @DisplayName("Test to set level equal to zero")
    public void testToSetLevelEqualToZero() {
        character.setLevel(0);
        assertEquals(0, character.getLevel());
    }

    @Test
    @DisplayName("Test to set negative level")
    public void testToSetNegativeLevel_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            character.setLevel(-10);
        });
    }

    @Test
    @DisplayName("Test to set level larger than max level")
    public void testToSetLevelLargeThanMaxLevel_ShouldSetMaxLevel() {
        character.setLevel(80);
        assertEquals(10, character.getLevel());
    }

    @Test
    @DisplayName("Test to increase mana with correct value")
    public void testToIncreaseManaWithCorrectValue() {
        character.increaseMana(10);
        assertEquals(10, character.getMana());
        assertTrue(character.canUseMagic());
    }

    @Test
    @DisplayName("Test to increase mana with negative value")
    public void testToIncreaseManaWithNegativeValue_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            character.increaseMana(-10);
        });
    }

    @Test
    @DisplayName("Test to decrease mana with correct value to zero")
    public void testToDecreaseManaWithCorrectValueToZero() {
        character.increaseMana(100);
        character.decreaseMana(100);
        assertEquals(0, character.getMana());
        assertFalse(character.canUseMagic());
    }

    @Test
    @DisplayName("Test to decrease mana with negative value")
    public void testToDecreaseManaWithNegativeValue_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            character.decreaseMana(-10);
        });
    }

    @Test
    @DisplayName("Test to increase health with correct value")
    public void testToIncreaseHealthWithCorrectValue() {
        character.increaseHealth(10);
        assertEquals(90, character.getHealth());
    }

    @Test
    @DisplayName("Test to increase health with negative value")
    public void testToIncreaseHealthWithNegativeValue_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            character.increaseHealth(-10);
        });
    }

    @Test
    @DisplayName("Test to decrease health with correct value to zero")
    public void testToDecreaseHealthWithCorrectValueToZero() {
        character.increaseHealth(100);
        character.decreaseHealth(100);
        assertEquals(0, character.getHealth());
        assertTrue(character.isDead());
    }

    @Test
    @DisplayName("Test to decrease health with negative value")
    public void testToDecreaseHealthWithNegativeValue_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            character.decreaseHealth(-10);
        });
    }

    @Test
    @DisplayName("Test to decrease health to negative value")
    public void testToDecreaseHealthToNegativeValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            character.decreaseHealth(90);
        });
    }

    @Test
    @DisplayName("Test that character get Magic Ability")
    public void testCharacterMagicAbility() {
        character.addAbility(magicAbility);
        assertEquals("Fireball", magicAbility.getName());
    }

    @Test
    @DisplayName("Test setting and getting Magic Ability")
    public void testCharacterSetAndGetMagicAbility() {
        character.addAbility(magicAbility);
        assertTrue(character.getAbilities().contains(magicAbility));
    }

    @Test
    @DisplayName("Test setting and getting Physical Ability")
    public void testCharacterSetAndGetPhysicalAbility() {
        PhysicalAbility slash = new PhysicalAbility("Slash", 5, 0,"Physical Attack");
        character.addAbility(slash);
        assertTrue(character.getAbilities().contains(slash));
    }

    @Test
    @DisplayName("Test adding and forgetting spells")
    public void testCharacterSpellHandling() {
        character.addAbility(magicAbility);
        assertTrue(character.getAbilities().contains(magicAbility));
        character.removeAbility(magicAbility);
        assertFalse(character.getAbilities().contains(magicAbility));
        assertDoesNotThrow(() -> character.removeAbility(magicAbility));
    }


    @Test
    @DisplayName("Test to equip a character")
    public void testToEquipCharacter() {
        character.getInventory().add(equipment);
        character.equip(equipment);
        assertEquals("LEFT_HAND: Sword +50% DAMAGE", character.getEquipmentOnBody().toString());
    }

    @Test
    @DisplayName("Test to equip a character when Equipment is null")
    public void testToEquipCharacterWhenEquipmentIsNull() {
        character.getInventory().add(equipment);
        character.equip(null);
        assertEquals("", character.getEquipmentOnBody().toString());
    }

    @Test
    @DisplayName("Test to unequip a character")
    public void testToUnEquipCharacter() {
        character.getInventory().add(equipment);
        character.equip(equipment);
        character.unEquip(equipment);
        assertEquals("", character.getEquipmentOnBody().toString());
    }

    @Test
    @DisplayName("Test to unequip a character when Equipment is null")
    public void testToUnEquipCharacterWhenEquipmentIsNull() {
        character.getInventory().add(equipment);
        character.equip(equipment);
        character.unEquip(null);
        assertEquals("LEFT_HAND: Sword +50% DAMAGE", character.getEquipmentOnBody().toString());
    }

    @Test
    @DisplayName("Test that equipment which was uneqiped was added to Inventory")
    public void testThatDiscardedEquipmentWasAddedToInventory() {
        character.getInventory().add(equipment);
        character.equip(equipment);
        character.unEquip(equipment);
        assertTrue(character.getInventory().contains(equipment));
    }

    @Test
    @DisplayName("Test that equipment which was selected to equip was removed from Inventory")
    public void testThatChosenEquipmentWasRemovedFromInventory() {
        character.equip(equipment);
        assertFalse(character.getInventory().contains(equipment));
    }

    @Test
    @DisplayName("Test that character get the ability from the equipment after equip()")
    public void testThatCharacterGetAbilityAfterEquip() {
        character.getInventory().add(equipment);
        character.equip(equipment);
        assertTrue(character.getAbilities().contains(new PhysicalAbility("Slash", 10, 1, "Test")));
    }

    @Test
    @DisplayName("Test to remove ability from character after unEquip()")
    public void testRemoveAbilityFromCharacterAfterUnEquip() {
        character.getInventory().add(equipment);
        character.equip(equipment);
        character.unEquip(equipment);
        assertFalse(character.getAbilities().contains(new PhysicalAbility("Slash", 10, 1, "Test")));
    }

    @Test
    @DisplayName("Testing that magic ability is affected")
    public void testMagicAbilityAffected() {
        character.addAbility(magicAbility);
        character.setLevel(0);
        character.removeAbility(magicAbility);
        assertFalse(character.getAbilities().contains(magicAbility));
    }

    @Test
    @DisplayName("Testing that magic ability is not affected")
    public void whenCharacterHaveMagicAbilityAndLevelIsMinimumRequired_thenNotRemoveAbility() {
        character.addAbility(magicAbility);
        character.setLevel(1);
        assertTrue(character.getAbilities().contains(magicAbility));
    }

}
