package com.company.monster;

import com.company.Character;
import com.company.item.Item;

import java.util.ArrayList;

public class Monster extends Character {
    private final ArrayList<Item> dropItems;
    private final int dropCoin;
    private final int exp;

    public Monster(String name, double HP, int MP, double attackPower, int shieldPower, int exp, ArrayList<Item> dropItems, int dropCoin) {
        super(name, HP, MP, attackPower, shieldPower);
        this.dropCoin = dropCoin;
        this.exp = exp;
        this.dropItems = dropItems;
    }

    public ArrayList<Item> getDropItems() {
        return dropItems;
    }
    public int getDropCoin() {
        return dropCoin;
    }
    public int getExp() {
        return exp;
    }
}
