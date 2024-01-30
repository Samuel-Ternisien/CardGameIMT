package etu.imt.cardgame;

//>> Capacités spéciales des champions
public enum Abilities {
    Soin{
        @Override
        void useAbility(Unit target){
            target.receiveDamage(-20);
        }
    },
    Attaque{
        @Override
        void useAbility(Unit target){
            target.receiveDamage(10);
        }
    };
    abstract void useAbility(Unit target);
}
