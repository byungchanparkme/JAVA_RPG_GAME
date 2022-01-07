package com.company.player;


import com.company.monster.Monster;
import com.company.skills.WarriorAttackSkill;
import com.company.skills.WarriorBuffSkill;

public class Warrior extends Player {
    private WarriorAttackSkill warriorAttackSkill = new WarriorAttackSkill("파워 스트라이크", 4);
    private WarriorBuffSkill warriorBuffSkill = new WarriorBuffSkill("아이언 바디", 8);

    // Warrior 클래스의 인스턴스 생성 시 인자 값이 초기화됨.
    public Warrior(String name, String job, int HP, int MP, int attack_power, int shield_power) {
        super(name, job, HP, MP, attack_power, shield_power);
    }

    // 기본 공격
    @Override
    public void attack() {
        super.attack();
    }
    // 공격 스킬 사용
    @Override
    public void powerStrike(Monster monster) {
        warriorAttackSkill.powerStrike(this, monster);
    }
    // 버프 스킬 사용
    @Override
    public void ironBody() {
        warriorBuffSkill.ironBody(this);
    }
    // 공격 스킬 레벨 업
    @Override
    public void increaseAttackSkillLevel() {
        // 스킬 레벨 증가
        warriorAttackSkill.setCurrentLevel(warriorAttackSkill.getCurrentLevel() + 1);
        // 데미지 퍼센트 증가
        warriorAttackSkill.setAttackDamagePercent(warriorAttackSkill.getAttackDamagePercent() + 0.05);
        // MP 소모량 증가
        warriorAttackSkill.setMpConsumption(warriorAttackSkill.getMpConsumption() + 1);
    }
    // 버프 스킬 레벨 업
    @Override
    public void increaseBuffSkillLevel() {
        // 스킬 레벨 증가
        warriorBuffSkill.setCurrentLevel(warriorBuffSkill.getCurrentLevel() + 1);
        // 물리 방어력 증가
        warriorBuffSkill.setShieldPowerIncrease(warriorBuffSkill.getShieldPowerIncrease() + 2);
        // MP 소모량
        warriorBuffSkill.setMpConsumption(warriorBuffSkill.getMpConsumption() + 1);
    }
}
