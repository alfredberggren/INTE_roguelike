import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//Ska testa olika typer av consumables (potions, mat etc.)
public class ConsumableItemTest {
    private static final Set<Interactable.InteractableAction> DEFAULT_CONSUMABLE_INTERACTABLE_ACTIONS = new HashSet<>(Arrays.asList(
            Interactable.InteractableAction.USE,
            Interactable.InteractableAction.DROP,
            Interactable.InteractableAction.LOOT)
    );

    @Test
    @DisplayName("Testar att ett Food har korrekt healing-värde")
    public void testFoodHasCorrectHealValueTest() {
        int expectedHealValue = 5;
        FoodItem f1 = new FoodItem("Bread", expectedHealValue);
        assertEquals(expectedHealValue, f1.getHealValue());
    }

    @Test
    @DisplayName("Testar att en Potion har korrekt effekt och tidsgräns i antal drag")
    public void testPotionHasCorrectEffectAndTurnDuration() {
        int expectedTurnLimit = 10;
        Equipment.Effect expectedEffect = Equipment.Effect.SPEED;
        PotionItem p1 = new PotionItem("SpeedPotion", expectedEffect, expectedTurnLimit);
        assertEquals(expectedEffect, p1.getEffect());
        assertEquals(expectedTurnLimit, p1.getTurnLimit());
    }

    @Test
    @DisplayName("Ett namn som är null ska hanteras av FoodItem")
    public void testNullNameShouldBeHandledByFoodItemCtr() {
        assertThrows(NullPointerException.class, () -> {
            new FoodItem(null, 10);
        });
    }

    @Test
    @DisplayName("Ett namn som är null ska hanteras av PotionItem")
    public void testNullNameShouldBeHandledByPotionItemCtr() {
        assertThrows(NullPointerException.class, () -> {
            new PotionItem(null, Equipment.Effect.DAMAGE, 10);
        });
    }

    @Test
    @DisplayName("Ett tomt namn ska hanteras")

}
