package com.company.item;

import com.company.player.Player;

public class Potion extends Item {
    public Potion(String name, String color) {
        super(name, color);
    }

    @Override
    public void effect(Player player) {
        double hp = player.getHP();
        // 빨간 포션 : HP 50 회복
        if (this.getColor() == "RED") {
            player.setHP(player, hp + 50);
            System.out.println("플레이어의 HP + 50");
        }
        // 오렌지 포션 : HP 150 회복
        else if (this.getColor() == "ORANGE") {
            player.setHP(player, hp + 150);
            System.out.println("플레이어의 HP + 150");
        }
        // 하얀 포션 : HP 300 회복
        else if (this.getColor() == "WHITE") {
            player.setHP(player, hp + 300);
            System.out.println("플레이어의 HP + 300");
        }
        // 엘릭서 : HP 전체 HP 의 50%만큼 회복
        else if (this.getColor() == "PURPLE") {
            player.setHP(player, hp + player.getCurrentHpLimit()/2);
            System.out.println("플레이어의 HP +" + (player.getCurrentHpLimit()/2));
        }
        System.out.println("플레이어의 현재 HP : " + player.getHP());
    }
}
