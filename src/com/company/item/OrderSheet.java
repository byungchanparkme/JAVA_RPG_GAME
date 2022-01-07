package com.company.item;

import com.company.player.Player;

public class OrderSheet extends Item {
    public OrderSheet(String name, String color) {
        super(name, color);
    }

    // 주문서 효과
    @Override
    public void effect(Player player) {
        // 플레이어의 현재 공격력
        double attackPower = player.getAttackPower();
        // 빨강 주문서 효과 : 공격력 10% 추가 증가
        if (this.getColor() == "RED") {
            player.setAttackPower(attackPower*1.1);
        }
        // 주황 주문서 효과 : 공격력 20% 추가 증가
        else if (this.getColor() == "ORANGE") {
            player.setAttackPower(attackPower*1.2);
        }
        // 하얀 주문서 효과 : 공격력 50% 추가 증가
        else if (this.getColor() == "WHITE") {
            player.setAttackPower(attackPower*1.5);
        }
        else {
            return;
        }
    }
}
