package com.company.monster;

import com.company.item.Item;
import com.company.player.Player;

import java.util.ArrayList;

public class BossMonster extends Monster{
    public BossMonster(String name, double HP, int MP, double attackPower, int shieldPower, int exp, ArrayList<Item> dropItems, int dropCoin) {
        super(name, HP, MP, attackPower, shieldPower, exp, dropItems, dropCoin);
    }

    public void doubleAttack(Player player) {
        System.out.println("플레이어 현재 체력 : " + player.getHP());
        System.out.println(this.getName() + "이 더블어택을 시전했습니다.");
        // 몬스터 공격력 2배의 데미지
        player.setHP(player.getHP() -  2*this.getAttackPower());
    }
}
