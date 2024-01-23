package etu.imt.cardgame.Monsters;

public class NecroMonster extends Monster {

    public NecroMonster(String name, int health, int power) {
        super(name, health, power);
    }

    public Monster resurrect(Monster target) throws Exception {
        if (target.getName().startsWith("Zombified_")){
            throw new Exception("Target is already dead, let some rest to the dead");
        }
        if (target.getHealth()!=0) {
            throw new Exception("Target is not dead, you can't corrupt the living");
        }
        // TODO: maybe clone the target instead of overriding value
        target.setPower((int) (target.getPower() * 0.80));
        target.setHealth(7);
        target.setName("Zombified_"+target.getName());
        super.setPower(super.getPower()-1);
        return target;
    }
}
