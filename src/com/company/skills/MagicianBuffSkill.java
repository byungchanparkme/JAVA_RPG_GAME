package com.company.skills;

import com.company.player.Player;

public class MagicianBuffSkill extends Skill {
    private double healHpPercent;

    public MagicianBuffSkill(String name, int mpConsumption) {
        super(name, mpConsumption);
        this.healHpPercent = 2;
    }

    // hp 회복율 정도 getter/setter
    public double getHealHpPercent() { return healHpPercent; }
    public void setHealHpPercent(double healHpPercent) { this.healHpPercent = healHpPercent; }

    public void heal(Player player) {
        double healHp = player.getAttackPower() * healHpPercent;

        if (player.getMP() < this.getMpConsumption()) { // 플레이어 MP 부족으로 스킬 사용 불가
            System.out.println("스킬을 사용하기에 MP 가 부족합니다.");
        } else { // 스킬 사용 가능
            // 1회 시전 시 공격력의 200%만큼 HP 회복
            player.setHP(player.getHP() + healHp);
            // 1회 시전 시 MP 4 소모
            player.setMP(player.getMP() - 4);

            System.out.println(this.getName() + " 스킬 사용!!!");
            System.out.println("플레이어가 " + healHp + "만큼 체력을 회복합니다.");
            System.out.println("플레이어의 현재 체력 : " + player.getHP());
        }

    }
}
