package com.company.player;

import com.company.Character;
import com.company.item.Item;
import com.company.item.OrderSheet;
import com.company.item.Potion;
import com.company.monster.Monster;

import java.util.ArrayList;
import java.util.Arrays;

public class Player extends Character {
    // 멤버 변수
    private double currentHpLimit;
    private int currentMpLimit;
    private final double currentLevelAttackPowerLimit;
    private String job;
    private int level = 1;
    private int currentExp;
    private int coin = 0;
    // 사용자 보유 아이템 목록 선언
    private ArrayList<ArrayList<Item>> inventory = new ArrayList<ArrayList<Item>>();

    public Player(String name, String job, double HP, int MP, double attack_power, int shield_power) {
        super(name, HP, MP, attack_power, shield_power);
        this.job = job;
        this.currentHpLimit = HP;
        this.currentMpLimit = MP;
        this.currentExp = 0;
        this.currentLevelAttackPowerLimit = attack_power;

        // 사용자 보유 아이템 목록 초기화
        for (int i=0; i<5; i++) {
            // row 의 크기는 5이며, 모든 요소는 null 로 초기화하고자 함.
            ArrayList<Item> row = new ArrayList<>(Arrays.asList(null, null, null, null, null));
            inventory.add(row);
        }
    }

    public double getCurrentLevelAttackPowerLimit() {
        return currentLevelAttackPowerLimit;
    }

    public double getCurrentHpLimit() { return currentHpLimit; }
    public void setCurrentHpLimit(double currentHpLimit) { this.currentHpLimit = currentHpLimit; }
    public int getCurrentMpLimit() { return currentMpLimit; }
    public void setCurrentMpLimit(int currentMpLimit) { this.currentMpLimit = currentMpLimit; }
    public String getJob() {
        return job;
    }
    public void setJob(String job) {
        this.job = job;
    }
    public int getCurrentExp() {
        return currentExp;
    }
    public void setCurrentExp(int currentExp) {
        this.currentExp = currentExp;
    }
    public int getCoin() {
        return coin;
    }
    public void setCoin(int coin) {
        this.coin = coin;
    }
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }

    // 아이템 사용
    public void useItem(String itemName) {
        Item targetItem = null;
        OrderSheet orderSheet = null;
        Potion potion = null;

        if (itemName == null) return;
        // 인벤토리에 접근
        inventory:
        for (int i=0; i<inventory.size(); i++) {
            for (int j=0; j<inventory.get(i).size(); j++) {
                targetItem = inventory.get(i).get(j);

                if (targetItem == null) continue;
                // 요소 중 사용자의 입력값을 포함하는 아이템 이름이 존재하면
                if (targetItem.getName().contains(itemName)) {
                    // 아이템 사용
                    targetItem.effect(this);
                    // 아이템 사용하므로 아이템 요소 제거 후 null 값으로 변경
                    inventory.get(i).set(j, null);
                    break inventory;
                }
            }
        }
    }
    public void setHP(Player player, double HP) {
        // 아이템 사용 시 회복한 HP 가 현재 레벨 최대 HP 량을 넘어설 경우 예외 처리
        // HP 는 항상 0 이상의 값을 가진다.
        double currentHpState = HP > currentHpLimit ? (int) Math.min(currentHpLimit, HP) : (int) Math.max(0, HP);
        player.setHP(currentHpState);
    }


    // 플레이어의 기본 공격 및 스킬 공격
    public void attack() {} // 기본 공격
    public void powerStrike(Monster monster) { return; } // 전사 공격 스킬 : 파워 스트라이크
    public void ironBody() { return; } // 전사 버프 스킬 : 아이언 바디
    public void energyBolt(Monster monster) { return; } // 마법사 공격 스킬 : 에너지 볼트
    public void heal() { return; } // 마법사 버프 스킬 : 힐

    public void increaseAttackSkillLevel() { return; } // (전사, 마법사 공통) 공격 스킬 레벨 업
    public void increaseBuffSkillLevel() { return; } // (전사, 마법사 공통) 버프 스킬 레벨 업

    public void getDropItem(Item item) {
        // 인벤토리에 드랍한 아이템 추가
        row:
        for(int i=0; i<inventory.size(); i++) {
            column:
            for (int j=0; j<inventory.get(i).size(); j++) {
                if (inventory.get(i).get(j) == null) {
                    inventory.get(i).set(j, item);
                    // 아이템이 인벤토리에 1번 추가되면 즉시 반복문 탈출
                    break row;
                }
            }
        }
        System.out.println("플레이어 " + item.getName() + " 획득!!!");
    }

    public ArrayList<ArrayList<Item>> getInventory() {
        return inventory;
    }

    public void useAttackSkill(Monster monster) {
        if (this.getJob() == "전사") {
            // 전사 공격 스킬 사용
            this.powerStrike(monster);
        }
        else if (this.getJob() == "마법사") {
            // 마법 사 공격 스킬 사용
            this.energyBolt(monster);
        }
    }

    public void useBuffSkill() {
        if (this.getJob() == "전사") {
            // 전사 버프 스킬 사용
            this.ironBody();
        }
        else if (this.getJob() == "마법사") {
            // 마법사 버프 스킬 사용
            this.heal();
        }
    }
}
