package etu.imt.cardgame.Monsters;

//>> Constructeur des monstres faisant appel à la classe monstre
public class MonsterBuilder {
    public static Monster buildMonster(String type){
        Monster m = null;
        switch (type){
            case "Simple":
                m = new SimpleMonster("Simplet", 30, 15);
                break;
            case "Shield":
                m = new ShieldMonster("Protecteur", 20, 0);
                break;
            case "Support":
                m = new SupportMonster("Mascotte", 40, 15);
                break;
            case "Healer":
                m = new HealerMonster("Soigneur", 20, 15);
                break;
            case "Necro":
                m = new NecroMonster("Necromancien", 15, 3);
                break;
            case "Kamikaz":
                m = new KamikazMonster("Kamikaz", 10, 100);
                break;
            default:
                break;
        }
        return m;
    }
}
