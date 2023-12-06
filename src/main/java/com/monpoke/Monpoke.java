package com.monpoke;

public class Monpoke {
    private String name;
    private int currentHealth;
    private int maxHealth;
    private int attack;

    public Monpoke(String name, int health, int attack) {
        this.name = name;
        if (health < 1 || attack < 1) {
            throw new IllegalArgumentException("Rule violated - health or attack is less than 1");
        }
        this.maxHealth = health;
        this.currentHealth = health;
        this.attack = attack;
    }

    public String getName() {
        return name;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getAttack() {
        return attack;
    }

    public int receiveAttack(Monpoke attackingMonpoke) {
        currentHealth -= attackingMonpoke.getAttack();
        return currentHealth;
    }

    public int receiveHeal(int healAmount) {
        int health = currentHealth + healAmount;
        int amountHealed = health > maxHealth ? maxHealth - currentHealth : healAmount;

        currentHealth += amountHealed;

        return amountHealed;
    }
}
