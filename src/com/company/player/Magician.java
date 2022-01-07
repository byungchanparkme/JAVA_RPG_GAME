package com.company.player;

import com.company.monster.Monster;
import com.company.skills.MagicianAttackSkill;
import com.company.skills.MagicianBuffSkill;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Magician extends Player {
    private MagicianAttackSkill magicianAttackSkill = new MagicianAttackSkill("에너지 볼트", 6);
    private MagicianBuffSkill magicianBuffSkill = new MagicianBuffSkill("힐", 4);

    // Magician 클래스의 인스턴스 생성 시 인자 값이 초기화됨.
    public Magician(String name, String job, int HP, int MP, int attack_power, int shield_power) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        super(name, job, HP, MP, attack_power, shield_power);
    }

    // 기본 공격
    @Override
    public void attack() {
        super.attack();
    }
    // 공격 스킬 사용
    @Override
    public void energyBolt(Monster monster) {
        magicianAttackSkill.energyBolt(this, monster);
    }
    // 버프 스킬 사용
    @Override
    public void heal() {
        magicianBuffSkill.heal(this);
    }
    // 공격 스킬 레벨 업
    @Override
    public void increaseAttackSkillLevel() {
        // 스킬 레벨 증가
        magicianAttackSkill.setCurrentLevel(magicianAttackSkill.getCurrentLevel() + 1);
        // 데미지 퍼센트 증가
        magicianAttackSkill.setAttackDamagePercent(magicianAttackSkill.getAttackDamagePercent() + 0.06);
        // MP 소모량 증가
        magicianAttackSkill.setMpConsumption(magicianAttackSkill.getMpConsumption() + 1);
    }
    // 버프 스킬 레벨 업
    @Override
    public void increaseBuffSkillLevel() {
        // 스킬 레벨 증가
        magicianBuffSkill.setCurrentLevel(magicianBuffSkill.getCurrentLevel() + 1);
        // HP 회복율  증가
        magicianBuffSkill.setHealHpPercent(magicianBuffSkill.getHealHpPercent() + 0.05);
        // MP 소모량
        magicianBuffSkill.setMpConsumption(magicianBuffSkill.getMpConsumption() + 1);
    }
}
