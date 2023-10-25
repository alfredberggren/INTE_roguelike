import java.util.Objects;

public class NPC extends Character {

    public NPC(String name, int health, int speed, int level, Position pos, IO io) {
        //Kristian: removed use of experience points from constructor
        super(name, health, speed, level, pos, io);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NPC npc = (NPC) o;
        return Objects.equals(npc.getName(), getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
