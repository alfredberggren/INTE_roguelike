import java.util.*;

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

    private void setBaseProbabilities() {
        matProbabilities = new LinkedHashMap<>(){{
            put("Steel", 10);
            put("Iron", 15);
            put("Stone", 30);
            put("Wood", 50);
        }};
        probabilityMaps.add(matProbabilities);
        posNpcProbabilities = new LinkedHashMap<>(){{
            put("Wizard", 10);
            put("Merchant", 15);
            put("Traveller", 75);
        }};
        probabilityMaps.add(posNpcProbabilities);
        posFoodProbabilities = new LinkedHashMap<>(){{
            put("Steak", 10);
            put("Porkchop", 15);
            put("Soup", 15);
            put("Mushroom", 20);
            put("Bread", 40);
        }};
        probabilityMaps.add(posFoodProbabilities);
        posPotionProbabilities = new LinkedHashMap<>(){{
            put(Equipment.Effect.HEALTH, 25);
            put(Equipment.Effect.SPEED, 25);
            put(Equipment.Effect.DAMAGE, 25);
            put(Equipment.Effect.ARMOR, 25);
        }};
        probabilityMaps.add(posPotionProbabilities);
        negativeNpcProbabilities = new LinkedHashMap<>(){{
            put("Wolf", 30);
            put("Troll", 20);
            put("Giant", 25);
            put("Ghost", 15);
            put("Dragon", 10);
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
