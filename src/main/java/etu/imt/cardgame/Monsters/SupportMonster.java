package etu.imt.cardgame.Monsters;

import etu.imt.cardgame.Unit;

public class SupportMonster extends Monster {
    public SupportMonster(String name, int health, int power) {
        super(name, health, power);
    }

    @Override
    public void targetUnit(Unit target) {
        int power = super.getPower();
        target.receiveBoost(power);
        if ((power == 0)) {
            super.setPower(7);
        } else {
            super.setPower(--power);
        }
    }
}
