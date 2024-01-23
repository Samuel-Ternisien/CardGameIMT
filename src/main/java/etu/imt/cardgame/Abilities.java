package etu.imt.cardgame;

public enum Abilities {
    Soin{
        @Override
        void useAbility(Unit target){
            target.receiveDamage(-5);
        }
    };
    abstract void useAbility(Unit target);
}
