package com.company.skills;

public class Skill {
    private final String name;
    private int mpConsumption;
    private int currentLevel;

    Skill(String name, int mpConsumption) {
        this.name = name;
        this.mpConsumption = mpConsumption;
        this.currentLevel = 1;
    }

    // 스킬명 getter
    public String getName() { return name; }
    // 스킬 사용 시 MP 소모량 getter/setter
    public int getMpConsumption() { return mpConsumption; }
    public void setMpConsumption(int mpConsumption) { this.mpConsumption = mpConsumption; }
    // 스킬 레벨 getter/setter
    public int getCurrentLevel() { return currentLevel; }
    public void setCurrentLevel(int currentLevel) { this.currentLevel = currentLevel; }
}
