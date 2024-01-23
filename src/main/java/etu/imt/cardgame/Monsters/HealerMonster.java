package etu.imt.cardgame.Monsters;

import etu.imt.cardgame.Unit;

public class HealerMonster extends Monster{
    public HealerMonster(String name, int health, int power) {
        super(name, health, power);
    }

    @Override
    public void targetUnit(Unit target){
        target.receiveDamage(-super.getPower());
    }
}
