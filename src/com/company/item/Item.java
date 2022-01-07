package com.company.item;

import com.company.player.Player;

public class Item {
    // 아이템 이름
    private String name;
    // 자식 클래스인 포션, 주문서 각각 색깔에 따라 아이템 구분
    private String color;

    public Item(String name, String color) {
        this.name = name;
        this.color = color;
    }
    public String getName() { return name; }
    public String getColor() { return color; }

    public void effect(Player player) {
        System.out.println("Super Method");
    }
}
