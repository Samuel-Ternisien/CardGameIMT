package etu.imt.cardgame.Monsters;

//>> Constructeur des monstres faisant appel à la classe monstre
public class MonsterBuilder {
    public static Monster buildMonster(String type){
        Monster m = null;
        switch (type){
            case "Simple":
                m = new SimpleMonster("Simplet", 45, 15);
                break;
            case "Shield":
                m = new ShieldMonster("Protecteur", 40, 10);
                break;
            case "Support":
                m = new SupportMonster("Mascotte", 40, 10);
                break;
            case "Healer":
                m = new HealerMonster("Soigneur", 35, 15);
                break;
            case "Necro":
                m = new NecroMonster("Necromancien", 15, 3);
                break;
            default:
                break;
        }
        return m;
    }
}
