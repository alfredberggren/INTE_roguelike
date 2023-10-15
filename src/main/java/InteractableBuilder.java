import java.util.*;

//TODO: kommentera skiten så man fattar vad detta är!
public class InteractableBuilder {
    private static final String[] WEAPON_ARMOR_MATS = {
            "Steel",
            "Iron",
            "Stone",
            "Wood"
    };
    // Base probabilities
    private List<HashMap<?, Integer>> probabilityMaps;
    private HashMap<String, Integer> matProbabilities;
    private HashMap<String, Integer> posNpcProbabilities;
    private HashMap<String, Integer> posFoodProbabilities;
    private HashMap<Equipment.Effect, Integer> posPotionProbabilities;
    private HashMap<String, Integer> negativeNpcProbabilities;
    Random r = new Random();
    private int diffScale;
    public InteractableBuilder(int diffScale) {
        this.diffScale = diffScale;
        setBaseProbabilities();
        determineProbabilities();
    }


    /**
     * Sets hardcoded base probabilities (ATM). The probabilities work in such a way, that when a random number between
     * 1-100 is generated, the number is compared to the key's value. If the number is less than than the value, that
     * key is chosen. Therefore, the higher the number, the higher the chance of that "thing" generating.
     */
    private void setBaseProbabilities() {
        probabilityMaps = new ArrayList<>();
        matProbabilities = new LinkedHashMap<>(){{
            put("Steel", 10);
            put("Iron", 25);
            put("Stone", 55);
            put("Wood", 100);
        }};
        probabilityMaps.add(matProbabilities);
        posNpcProbabilities = new LinkedHashMap<>(){{
            put("Wizard", 10);
            put("Merchant", 25);
            put("Traveller", 100);
        }};
        probabilityMaps.add(posNpcProbabilities);
        posFoodProbabilities = new LinkedHashMap<>(){{
            put("Steak", 10);
            put("Porkchop", 25);
            put("Soup", 40);
            put("Mushroom", 60);
            put("Bread", 100);
        }};
        probabilityMaps.add(posFoodProbabilities);
        posPotionProbabilities = new LinkedHashMap<>(){{
            put(Equipment.Effect.HEALTH, 25);
            put(Equipment.Effect.SPEED, 50);
            put(Equipment.Effect.DAMAGE, 75);
            put(Equipment.Effect.ARMOR, 100);
        }};
        probabilityMaps.add(posPotionProbabilities);
        negativeNpcProbabilities = new LinkedHashMap<>(){{
            put("Wolf", 30);
            put("Troll", 50);
            put("Giant", 75);
            put("Ghost", 90);
            put("Dragon", 100);
        }};
        probabilityMaps.add(negativeNpcProbabilities);
    }

    public Interactable getPositiveInteractable() {
        if (r.nextInt(10) == 0) {
            return getPositiveNPC();
        } else {
            return getPositiveConsumable();
        }
    }


    private Interactable getPositiveConsumable() {
        boolean food = r.nextBoolean();
        if (food) {
            return getPositiveFoodItem();
        } else {
            return getPositivePotionItem();
        }
    }

    private Interactable getPositivePotionItem() {
        int determinator = r.nextInt(100) + 1;
        for (Equipment.Effect e : posPotionProbabilities.keySet()) {
            if (determinator < posPotionProbabilities.get(e)) {
                return new PotionItem(e.name(), e, 5);
            }
        }
        return null;
    }

    private Interactable getPositiveFoodItem() {
        int determinator = r.nextInt(100) + 1;
        for (String s : posFoodProbabilities.keySet()) {
            if (determinator < posFoodProbabilities.get(s)) {
                return new FoodItem(s, 10);
            }
        }
        return null;
    }

    private Interactable getPositiveNPC() {
        int determinator = r.nextInt(100) + 1;
        for (String s : posNpcProbabilities.keySet()) {
            if (determinator < posNpcProbabilities.get(s)) {
                return new NPC(s, 100, 10);
            }
        }
        return null;
    }

    public Interactable getNegativeInteractable() {
        int determinator = r.nextInt(100) + 1;
        for (String s : negativeNpcProbabilities.keySet()) {
            if (determinator < negativeNpcProbabilities.get(s)) {
                return new NPC(s, 100, 10);
            }
        }
        return null;
    }

    private void determineProbabilities() {
        for (HashMap<?, Integer> m : probabilityMaps) {
            int tempDiffScale = diffScale;
            int changeFactor = tempDiffScale * 2 / m.keySet().size();
            putChangeFactor(m, changeFactor, tempDiffScale);
        }
    }

    private static <T> void putChangeFactor(HashMap<T, Integer> m, int changeFactor, int tempDiffScale) {
        for (T t : m.keySet()) {
            m.put(t, m.get(t) - tempDiffScale);
            tempDiffScale = tempDiffScale - changeFactor;
        }
    }

}
