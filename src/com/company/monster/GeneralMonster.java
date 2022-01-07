package com.company.monster;

import com.company.item.Item;

import java.util.ArrayList;

public class GeneralMonster extends Monster {
    public GeneralMonster(String name, double HP, int MP, double attackPower, int shieldPower, int exp, ArrayList<Item> dropItems, int dropCoin) {
        super(name, HP, MP, attackPower, shieldPower, exp, dropItems, dropCoin);
    }
}
