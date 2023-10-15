import java.util.Objects;

/**The Spell class represents a magical spell with name, description, casting time and cool-down*/
public class Spell {

    private String name;
    private String description;
    private int castingTime;
    private int coolDown;

    public Spell(String name, String description, int castingTime, int coolDown) {
        this.name = name;
        this.description = description;
        this.castingTime = castingTime;
        this.coolDown = coolDown;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCastingTime() {
        return castingTime;
    }

    public void setCastingTime(int castingTime){
        this.castingTime = castingTime;
    }

    public int getCoolDown() {
        return coolDown;
    }

    public void setCoolDown(int coolDown) {
        this.coolDown = coolDown;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        Spell spell = (Spell) o;
        return Objects.equals(name, spell.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
