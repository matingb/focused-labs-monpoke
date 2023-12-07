package com.monpoke;

public class Monpoke {
    private final String name;
    private int currentHealth;
    private final int maxHealth;
    private final int attack;

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
    public int getMaxHealth() { return maxHealth; }

    public int getAttack() {
        return attack;
    }

    public int receiveAttack(Monpoke attackingMonpoke) {
        currentHealth -= attackingMonpoke.getAttack();
        return Math.max(currentHealth, 0);
    }

    public int receiveHeal(int healAmount) {
        int health = currentHealth + healAmount;
        int amountHealed = health > maxHealth ? maxHealth - currentHealth : healAmount;

        currentHealth += amountHealed;

        return amountHealed;
    }
}
