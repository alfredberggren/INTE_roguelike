import java.util.Objects;
import java.util.Set;

public class NPC extends Character {
    private Set<InteractableAction> possibleInteractableActions;
    private String name;

    public NPC(String name, int health, int speed, Set<InteractableAction> possibleInteractableActions) {
        super(health, speed);
        this.name = name;
        this.possibleInteractableActions = possibleInteractableActions;
    }

    public Set<InteractableAction> getPossibleActions() {
        return possibleInteractableActions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NPC npc = (NPC) o;
        return Objects.equals(npc.getName(), name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
