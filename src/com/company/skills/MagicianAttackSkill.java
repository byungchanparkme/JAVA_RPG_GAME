package com.company.skills;

import com.company.monster.Monster;
import com.company.player.Player;

public class MagicianAttackSkill extends Skill {
    private double attackDamagePercent;

    public MagicianAttackSkill(String name, int mpConsumption) {
        super(name, mpConsumption);
        this.attackDamagePercent = 1.96;
    }

    // 공격 데미지 적용 정도 getter/setter
    public double getAttackDamagePercent() { return attackDamagePercent; }
    public void setAttackDamagePercent(double attackDamagePercent) { this.attackDamagePercent = attackDamagePercent; }

    // 공격 스킬
    public void energyBolt(Player player, Monster monster) {
        double damage = player.getAttackPower() * attackDamagePercent - (monster.getShieldPower() / 2.0);

        if (player.getMP() < this.getMpConsumption()) { // 스킬 사용하기에 플레이어의 MP 부족
            System.out.println("스킬을 사용하기에 MP 가 부족합니다.");
        } else { // 플레이어 스킬 사용 가능
            // 스킬 1회 당 MP 6 소모
            player.setMP(player.getMP() - this.getMpConsumption());
            // 1회 시전 시 데미지 196% 가함.
            monster.setHP(monster.getHP() - damage);

            System.out.println(this.getName() + " 스킬 사용!!!");
            System.out.println("플레이어의 MP 가 " + this.getMpConsumption() + "만큼 사용됩니다.");
            System.out.println("플레이어의 현재 MP : " + player.getMP());

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) { return; }

            System.out.println(monster.getName() + "가 " + damage + "의 데미지를 받습니다.");
            System.out.println(monster.getName() + "의 현재 체력 : " + monster.getHP());
        }
    }
}
