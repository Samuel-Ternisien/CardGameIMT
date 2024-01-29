package etu.imt.cardgame;

import etu.imt.cardgame.Monsters.Monster;
import etu.imt.cardgame.Monsters.ShieldMonster;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Champion implements Unit{
    private static int count = 0;
    private final int id;
    private String name;
    private int health;
    private Abilities ability;
    private ArrayList<Monster> deck;
    private ArrayList<Monster> onBoard;

    public Champion(String name, int health, Abilities ability) {
        this.id = ++count;
        this.name = name;
        this.health = health;
        this.ability = ability;
        this.deck = new ArrayList<Monster>();
        this.onBoard = new ArrayList<Monster>();
    }



    @Override
    public void receiveDamage(int amount) {
        this.health -= amount;
    }

    @Override
    public void receiveBoost(int amount) {
        return;
    }

    @Override
    public int getHealth() {
        return this.health;
    }
    public String getName(){
        return this.name;
    }

    public void useAbility(Unit target){
        ability.useAbility(target);
    }

    // Ajout de la fonction jouerCarte que j'utilise dans PlateaudeJeu
    public void jouerCarte(int id) {
        // On récupére le monstre du deck grâce à son id
        Monster m = getMonsterById(id);
        // On le rajoute sur le plateau
        onBoard.add(m);
        // Puis on le supprime du deck
        deck.remove(m);
    }

    @Override
    public String toString() {
        return "Champion{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", health=" + health +
                ", ability=" + ability +
                '}';
    }

    public void addMonster(Monster monster) {
        this.deck.add(monster);
    }

    public ArrayList<Monster> getDeck(){
        return this.deck;
    }

    public ArrayList<Monster> getOnBoard(){
        return onBoard;
    }

    public Monster getMonsterById(int id){
        for (Monster monster : deck) {
            if (monster.getId()==id) {
                return monster;
            }
        }
        return null;
    }

    public Monster getShield(){
        // Filter every monster to only get shield monster because they protect the champion
        List<ShieldMonster> shieldMonsters = onBoard.stream()
                .filter(ShieldMonster.class::isInstance)
                .map(ShieldMonster.class::cast)
                .toList();
        // return first shield monster or null if not found
        return shieldMonsters.isEmpty() ? null : shieldMonsters.get(0);
    }
}
