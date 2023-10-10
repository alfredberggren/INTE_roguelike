import java.util.Set;

public class Equipment extends NonLivingEntity{
    private Effect effect;
    private int damage;
    private Ability ability;
    private double damageBar;

    public Equipment(String name, Set<Action> possibleActions, Effect effect, int damage, Ability ability) {
        super(name, possibleActions);
        this.effect = effect;
        this.damage = damage;
        this.ability = ability;
    }

    public String getName(){
        return name;
    }

    public Effect getEffect() {
        return effect;
    }

    public Set<Action> getPossibleActions() {
        return possibleActions;
    }

    public enum Effect {
        SPEED, HEALTH, DAMAGE
    }

    public String getAbility(){
         return ability.getTypeOfAbility();
    }

    public double damageModifier(double damageBar) {
        double decreaseBy = 10;
        if(10 <= damageBar || damageBar <= 100) {
            setDamageOnEquipment(damageBar - decreaseBy);
            return damageBar;
        }
        setDamageOnEquipment(damageBar);
        return damageBar;
    }

    public void setDamageOnEquipment(double damageBar){
        this.damageBar = damageBar;
    }

    public double getDamageOnEquipment(){
        return damageBar;
    }

    public String toString() {
        String s = name + " + " + damage + " + " + effect;
        return s;
    }
}
