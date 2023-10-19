
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class CharacterTest {
    static Character character;
    static Character characterWithPos = new Character("Rudolf", 100, 1,1,  new Position(1, 2), new TextIO());

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
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
           character.setPos(null);
        });
    }





    @Test
    @DisplayName("Test to set i constructor negative speed")
    public void testConstructorWithNegativeSpeed_ThrowsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Character("Ragnar", 100, -10,1, new TextIO());
        });
    }

    @Test
    @DisplayName("Test to set i constructor IO equal to null")
    public void testConstructorWithNIOEqualToNull_ThrowsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Character("Ragnar", 100, -10,1, null);
        });
    }


    @Test
    @DisplayName("Test to decrease health to 0")
    public void testToDecreaseHealthToZero() {
        character.decreaseHealth(80);
        assertEquals(0, character.getHealth());
        assertTrue(character.isDead());
    }

    @Test
    @DisplayName("Test to decrease health to negative value")
    public void testToDecreaseHealthToNegativeValue() {
        character.decreaseHealth(100);
        assertEquals(0, character.getHealth());
        assertTrue(character.isDead());
    }

    @Test
    @DisplayName("Test to increase health")
    public void testToIncreaseHealth() {
        character.increaseHealth(20);
        assertEquals(100, character.getHealth());
    }

    @Test
    @DisplayName("Test to increase mana and ability to use magic if mana>0")
    public void testToIncreaseMana() {
        character.increaseMana(100);
        assertEquals(100, character.getMana());
        assertTrue(character.canUseMagic());
    }

    @Test
    @DisplayName("Test to decrease mana and ability to use magic if mana=0")
    public void testToDecreaseMana() {
        character.increaseMana(100);
        character.decreaseMana(100);
        assertEquals(0, character.getMana());
        assertFalse(character.canUseMagic());
    }


    @Test
    @DisplayName("Test that character get Magic Ability")
    public void testCharacterMagicAbility() {
        Character c = new Character("Rudolf", 10, 10, new Position(0, 0), new TextIO());
        MagicAbility fireMagic = new MagicAbility("Fireball", 20, 1, "Shoots fire", 2, 1);
        c.addAbility(fireMagic);
        assertEquals("Fireball", fireMagic.name);
    }

    @Test
    @DisplayName("Test setting and getting Magic Ability")
    public void testCharacterSetAndGetMagicAbility() {
        MagicAbility fireMagic = new MagicAbility("Fireball", 20, 1, "Shoots fire", 2, 1);
        character.addAbility(fireMagic);
        assertTrue(character.getAbilities().contains(fireMagic));
    }

    @Test
    @DisplayName("Test setting and getting Physical Ability")
    public void testCharacterSetAndGetPhysicalAbility() {
        PhysicalAbility sword = new PhysicalAbility("Sword", 10, 1);
        character.addAbility(sword);
        assertTrue(character.getAbilities().contains(sword));
    }

    @Test
    @DisplayName("Test adding and forgetting spells")
    public void testCharacterSpellHandling() {
        MagicAbility fireSpell = new MagicAbility("Fire", 10, 1, "Shoots fire", 1, 2);
        MagicAbility iceSpell = new MagicAbility("Ice", 12, 1, "Shoots ice", 1, 3);
        character.addAbility(fireSpell);
        assertTrue(character.getAbilities().contains(fireSpell));
        character.removeAbility(fireSpell);
        assertFalse(character.getAbilities().contains(fireSpell));
        assertDoesNotThrow(() -> character.removeAbility(iceSpell));
    }

    @Test
    @DisplayName("Test set-method at health=0")
    public void testIfCharacterIsDeadWhenHealthIsZero() {
        character.setHealth(0);
        assertTrue(character.isDead());
    }

    @Test
    @DisplayName("Test throws Exception at negative health")
    public void testThrowsExceptionWithNegativeHealth() {
        assertThrows(IllegalArgumentException.class, () -> {
            character.setHealth(-10);
        });
    }

    @Test
    @DisplayName("Test to equip a character")
    public void testToEquipCharacter() {
        character.getInventory().add(new Equipment("Sword", EquipmentSlot.LEFT_HAND, Equipment.Effect.DAMAGE, 50, new PhysicalAbility("Sword", 10, 1)));
        character.equip(new Equipment("Sword", EquipmentSlot.LEFT_HAND, Equipment.Effect.DAMAGE, 50, new PhysicalAbility("Sword", 10, 1)));
        assertEquals("LEFT_HAND: Sword +50% DAMAGE", character.getEquipmentOnBody().toString());
    }

    @Test
    @DisplayName("Test to equip a character when Equipment is null")
    public void testToEquipCharacterWhenEquipmentIsNull() {
        character.getInventory().add(new Equipment("Sword", EquipmentSlot.LEFT_HAND, Equipment.Effect.DAMAGE, 50, new PhysicalAbility("Sword", 10, 1)));
        character.equip(null);
        assertEquals("", character.getEquipmentOnBody().toString());
    }

    @Test
    @DisplayName("Test to unequip a character")
    public void testToUnEquipCharacter() {
        Equipment testEquipment = new Equipment("Sword", EquipmentSlot.LEFT_HAND, Equipment.Effect.DAMAGE, 50, new PhysicalAbility("Sword", 10, 1));
        character.getInventory().add(new Equipment("Sword", EquipmentSlot.LEFT_HAND, new HashSet<>(Arrays.asList(Interactable.InteractableAction.LOOT, Interactable.InteractableAction.UNEQUIP)), Equipment.Effect.DAMAGE, 50, new PhysicalAbility("Sword", 10, 1)));
        character.equip(testEquipment);
        character.unEquip(testEquipment);
        assertEquals("", character.getEquipmentOnBody().toString());
    }

    @Test
    @DisplayName("Test to unequip a character when Equipment is null")
    public void testToUnEquipCharacterWhenEquipmentIsNull() {
        Equipment equipment = new Equipment("Sword", EquipmentSlot.LEFT_HAND, new HashSet<>(Arrays.asList(Interactable.InteractableAction.LOOT, Interactable.InteractableAction.DROP)), Equipment.Effect.DAMAGE, 50, new PhysicalAbility("Sword", 10, 1));
        character.getInventory().add(equipment);
        character.equip(equipment);
        character.unEquip(null);
        assertEquals("LEFT_HAND: Sword +50% DAMAGE", character.getEquipmentOnBody().toString());
    }

    @Test
    @DisplayName("Test that equipment which was Uneqiped was added to Inventory")
    public void testThatDiscardedEquipmentWasAddedToInventory() {
        Equipment equipment = new Equipment("Sword", EquipmentSlot.LEFT_HAND, new HashSet<>(Arrays.asList(Interactable.InteractableAction.LOOT, Interactable.InteractableAction.UNEQUIP)), Equipment.Effect.DAMAGE, 50, new PhysicalAbility("Sword", 10, 1));
        character.getInventory().add(equipment);
        character.equip(equipment);
        character.unEquip(equipment);
        assertTrue(character.getInventory().contains(equipment));
    }

    @Test
    @DisplayName("Test that equipment which was selected to equip was removed from Inventory")
    public void testThatChosenEquipmentWasRemovedFromInventory() {
        Equipment equipment = new Equipment("Sword", EquipmentSlot.LEFT_HAND, new HashSet<>(Arrays.asList(Interactable.InteractableAction.LOOT, Interactable.InteractableAction.DROP)), Equipment.Effect.DAMAGE, 50, new PhysicalAbility("Sword", 10, 1));
        character.equip(equipment);
        assertFalse(character.getInventory().contains(equipment));
    }

    @Test
    @DisplayName("Test that character get the ability from the equipment after equip()")
    public void testThatCharacterGetAbilityAfterEquip() {
        Equipment equipment = new Equipment("Sword", EquipmentSlot.LEFT_HAND, new HashSet<>(Arrays.asList(Interactable.InteractableAction.LOOT, Interactable.InteractableAction.DROP)), Equipment.Effect.DAMAGE, 50, new PhysicalAbility("Sword", 10, 1));
        character.equip(equipment);
        assertTrue(character.getAbilities().contains(new PhysicalAbility("Sword", 10, 1)));
    }

    @Test
    @DisplayName("Test to remove ability from character after unEquip()")
    public void testRemoveAbilityFromCharacterAfterUnEquip() {
        Equipment equipment = new Equipment("Sword", EquipmentSlot.LEFT_HAND, new HashSet<>(Arrays.asList(Interactable.InteractableAction.LOOT, Interactable.InteractableAction.DROP)), Equipment.Effect.DAMAGE, 50, new PhysicalAbility("Sword", 10, 1));
        character.getInventory().add(equipment);
        character.equip(equipment);
        character.unEquip(equipment);
        assertFalse(character.getAbilities().contains(new PhysicalAbility("Sword", 10, 1)));
    }


    @Test
    @DisplayName("Test that player cannot be in a level higher than 10")
    public void testPlayerLevelDoNotExceedAcceptedLevel() {
        character.setLevel(11);
        assertEquals(10, character.getLevel());
    }

}
