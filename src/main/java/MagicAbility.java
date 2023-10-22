import java.util.Objects;

/**The MagicAbility class extends the abstract Ability class and represents a specific type of magical ability*/
public class MagicAbility extends Ability{

    private static final int BASE_DAMAGE_FOR_MAGIC = 10;
    private static final int LEVEL_BONUS_FOR_MAGIC = 5;
    private static final int XP_BONUS_FOR_MAGIC = 10;
    private static final int DEFAULT_XP = 0;
    private static final int DEFAULT_MANA_COST_TO_CAST_SPELL = 5;
    private int requiredTimeToCast;
    private int coolDownTime;
    private final int manaCost;

    /**Constructs an Ability object with the specified characteristics*/
    MagicAbility(String name, int baseDamage, int requiredLevel, String description, int requiredTimeToCast, int coolDownTime, int manaCost) {
        super(name, baseDamage, AbilityType.MAGICAL, requiredLevel, description);
        this.setRequiredTimeToCast(requiredTimeToCast);
        this.setCoolDownTime(coolDownTime);
        this.manaCost = DEFAULT_MANA_COST_TO_CAST_SPELL;
    }

    public int getRequiredTimeToCast() {
        return requiredTimeToCast;
    }

    public void setRequiredTimeToCast(int requiredTimeToCast){
        if (requiredTimeToCast < 0) {
            throw new IllegalArgumentException("Casting time needs to be 1 or more");
        } else {
            this.requiredTimeToCast = requiredTimeToCast;
        }
    }

    public int getCoolDownTime() {
        return coolDownTime;
    }

    public void setCoolDownTime(int coolDownTime) {
        if (coolDownTime < 0) {
            throw new IllegalArgumentException("Cool-down needs to be 1 or more");
        } else {
            this.coolDownTime = coolDownTime;
        }
    }

    public void manaCostForMagic(Character character) {
        int mana = character.getMana();
        if (manaCost < 0){
            throw new IllegalArgumentException("Mana cost cannot be negative");
        }
        if (mana < manaCost) {
            throw new IllegalArgumentException("Not enough mana to cast the spell");
        }
        character.decreaseMana(manaCost);
    }

    /**Checks whether the ability is learnable by a character based on their level*/
    protected boolean isLearnable(Character character) {
        return character.getLevel() >= getRequiredLevel();
    }

    /** {@inheritDoc} Calculates the damage inflicted by this specific magical ability*/
    protected int calculateDamageOfMagicalAbility(Character character) {
        int baseDamage = BASE_DAMAGE_FOR_MAGIC;
        int levelBonus = character.getLevel() * LEVEL_BONUS_FOR_MAGIC;
        int experienceBonus = DEFAULT_XP;
        if(character instanceof Player player) {
            experienceBonus = player.getAmountOfExperience() / XP_BONUS_FOR_MAGIC;
        }
        return baseDamage + levelBonus + experienceBonus;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        if(!super.equals(o)) {
            return false;
        }
        MagicAbility ability = (MagicAbility) o;
        return Objects.equals(getName(), ability.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    /**{@inheritDoc}*/
    @Override
    public String toString() {
        return String.valueOf(AbilityType.MAGICAL);
    }

}
