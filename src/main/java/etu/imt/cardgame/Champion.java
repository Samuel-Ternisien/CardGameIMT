package etu.imt.cardgame;

import etu.imt.cardgame.Monsters.Monster;
import etu.imt.cardgame.Monsters.ShieldMonster;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//>> Définition des joueurs
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

    public ArrayList<Monster> getDeck(){
        return this.deck;
    }

    public ArrayList<Monster> getOnBoard(){
        return this.onBoard;
    }

    public Abilities getAbility() {
        return this.ability;
    }

    public void useAbility(Unit target){
        ability.useAbility(target);
    }

    /**
     * When the player plays a card, we remove it from the deck and add it to the board
     * @param id of the monsters to be played
     */
    public void jouerCarte(int id) {
        // On récupére le monstre du deck grâce à son id
        Monster m = getDeckMonsterById(id);
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

    /**
     * Allow the gathering of a monsters in the DECK of a player using the monster id
     * @param id id to find
     * @return The monster or null if not found
     */
    public Monster getDeckMonsterById(int id){
        return deck.stream().filter(monster -> monster.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Same as previous function but for the board
     * @param id id to find
     * @return The monster or null if not found
     */
    public Monster getBoardMonsterById(int id){
        return onBoard.stream()
                .filter(monster -> monster.getId() == id)
                .findFirst()
                .orElse(null);

    }

    /**
     * Function used to check if the champion have a shield monster on its board
     * @return The first shield monster found or null
     */
    public Monster getShield(){
        // Filter every monster to only get shield monster because they protect the champion
        List<ShieldMonster> shieldMonsters = onBoard.stream()
                .filter(ShieldMonster.class::isInstance)
                .map(ShieldMonster.class::cast)
                .collect(Collectors.toList());
        // return first shield monster or null if not found
        return shieldMonsters.isEmpty() ? null : shieldMonsters.get(0);
    }

    /**
     * Function used to check if the champion have a dead monster on its board
     * @return List of dead monster or null
     */
    public List<Monster> getDeadMonsters(){
        List<Monster> dead = onBoard.stream()
                .filter(monster -> monster.getHealth()==0)
                .map(Monster.class::cast)
                .collect(Collectors.toList());
        onBoard.removeAll(dead);
        return dead;
    }
}
