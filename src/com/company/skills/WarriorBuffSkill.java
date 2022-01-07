package com.company.skills;

import com.company.player.Player;

public class WarriorBuffSkill extends Skill {
    private int shieldPowerIncrease;

    public WarriorBuffSkill(String name, int mpConsumption) {
        super(name, mpConsumption);
        this.shieldPowerIncrease = 2;
    }
    // 방어력 적용 정도 getter/setter
    public int getShieldPowerIncrease() { return shieldPowerIncrease; }
    public void setShieldPowerIncrease(int shieldPowerIncrease) { this.shieldPowerIncrease = shieldPowerIncrease; }

    public void ironBody(Player player) {
        if (player.getMP() < this.getMpConsumption()) { // 플레이어 MP 부족으로 스킬 사용 불가
            System.out.println("스킬을 사용하기에 MP 가 부족합니다.");
        } else { // 스킬 사용 가능
            // 스킬 1회 당 MP 8 소모
            player.setMP(player.getMP() - this.getMpConsumption());
            // 플레이어의 물리 방어력 + 2 증가
            player.setShieldPower(player.getShieldPower() + 2);

            System.out.println(this.getName() + " 스킬 사용!!!");
            System.out.println("공격 2턴 간 플레이어의 방어력이 " + shieldPowerIncrease + "만큼 증가합니다.");
            System.out.println("플레이어의 현재 방어력 : " + player.getShieldPower());
        }
    }
}
