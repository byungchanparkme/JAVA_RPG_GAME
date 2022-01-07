package com.company;

import com.company.monster.Monster;

public class Character {
    // 멤버 변수 구성
    private String name; // 이름
    private double HP; // 체력
    private int MP; // 마력
    private double attackPower; // 공격력
    private int shieldPower; // 방어력

    public Character(String name, double HP, int MP, double attackPower, int shieldPower) {
        this.name = name;
        this.HP = HP;
        this.MP = MP;
        this.attackPower = attackPower;
        this.shieldPower = shieldPower;
    }

    public String getName() {
        return name;
    }

    public int getHP() {
        if (HP <= 0) return 0;
        return (int) HP;
    }
    public void setHP(double HP) {
        this.HP = (int) Math.max(0, HP);
    }
    public int getMP() {
        if (MP <= 0) return 0;
        return MP;
    }
    public void setMP(int MP) {
        this.MP = Math.max(0, MP);
    }
    public int getAttackPower() {
        return (int) attackPower;
    }
    public void setAttackPower(double attackPower) {
        this.attackPower = attackPower;
    }
    public int getShieldPower() {
        return shieldPower;
    }
    public void setShieldPower(int shieldPower) {
        this.shieldPower = shieldPower;
    }

    public void attack(Character enemy) {
        System.out.println("=============================");
        System.out.println(this.getName() + "이 1번의 " + this.getAttackPower() + "의 공격을 가합니다.");
        System.out.println("=============================");

        enemy.setHP(enemy.getHP() - this.getAttackPower());

        String who = (enemy instanceof Monster) ? "몬스터" : "플레이어";
        System.out.println(who + " 이름 : " + enemy.getName() + ", 남은 HP : " + enemy.getHP());
    }

    public boolean isLive() {
        if (this.getHP() <= 0) {
            return false;
        }
        return true;
    }
}
