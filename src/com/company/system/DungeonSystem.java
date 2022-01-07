package com.company.system;

import com.company.Game;
import com.company.item.Item;
import com.company.monster.BossMonster;
import com.company.monster.GeneralMonster;
import com.company.monster.Monster;
import com.company.player.Player;
import com.company.threads.AttackPlayer;
import com.company.threads.Music;
import com.company.types.MonsterTypes;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class DungeonSystem {
    private static final Random rand = new Random();
    private static Monster monster;
    private static final Scanner scanner = new Scanner(System.in);

    // 몬스터 객체화 담당 메소드
    public static Monster createMonster(String monsterName) throws IllegalAccessException {
        ArrayList<Item> dropItemList = ItemSystem.createMonsterDropItemList(monsterName);
        // 각 몬스터의 능력치 정의
        if (Objects.equals(monsterName, MonsterTypes.GREEN_SNAIL.name())) {
            // 초록 달팽이 => HP : 15, MP : 0
            monster = new GeneralMonster("초록 달팽이", 15, 0, 2, 0, 3, dropItemList, 5);
        }
        else if (Objects.equals(monsterName, MonsterTypes.BLUE_SNAIL.name())) {
            // 파란 달팽이 => HP : 50, MP : 0
            monster = new GeneralMonster("파란 달팽이", 20, 0, 3, 0, 4, dropItemList, 10);
        }
        else if (Objects.equals(monsterName, MonsterTypes.RED_SNAIL.name())) {
            // 빨간 달팽이 => HP : 50, MP : 0
            monster = new GeneralMonster("빨간 달팽이", 50, 0, 15, 3,8, dropItemList, 15);
        }
        else if (Objects.equals(monsterName, MonsterTypes.PIG.name())) {
            // 빨간 달팽이 => HP : 50, MP : 0
            monster = new GeneralMonster("돼지", 80, 0, 25, 5,12, dropItemList, 18);
        }
        else if (Objects.equals(monsterName, MonsterTypes.SLIME.name())) {
            monster = new GeneralMonster("슬라임", 100, 0, 30, 10, 12, dropItemList, 15);
        }
        else if (Objects.equals(monsterName, MonsterTypes.STUMP.name())) {
            monster = new GeneralMonster("스텀프", 95, 0, 30, 0, 13, dropItemList, 15);
        }
        else if (Objects.equals(monsterName, MonsterTypes.RIBBON_PIG.name())) {
            monster = new GeneralMonster("리본 돼지", 125, 0, 38, 10, 17, dropItemList, 25);
        }
        else if (Objects.equals(monsterName, MonsterTypes.CYNICAL_ORANGE_MUSHROOM.name())) {
            monster = new GeneralMonster("시니컬한 주황버섯", 150, 0, 43, 0, 19, dropItemList, 30);
        }
        else if (Objects.equals(monsterName, MonsterTypes.STRANGE_PIG.name())) {
            monster = new GeneralMonster("이상한 돼지", 150, 0, 51, 10, 19, dropItemList, 25);
        }
        else if (Objects.equals(monsterName, MonsterTypes.GREEN_MUSHROOM.name())) {
            monster = new GeneralMonster("초록버섯", 175, 0, 58, 12, 21, dropItemList, 40);
        }
        else if (Objects.equals(monsterName, MonsterTypes.HORN_MUSHROOM.name())) {
            monster = new GeneralMonster("뿔버섯", 225, 0, 57, 30, 24, dropItemList, 40);
        }
        else if (Objects.equals(monsterName, MonsterTypes.LACE.name())) {
            monster = new GeneralMonster("레이스", 250, 0, 62, 180, 26, dropItemList, 35);
        }
        else if (Objects.equals(monsterName, MonsterTypes.BLUE_MUSHROOM.name())) {
            monster = new GeneralMonster("파란버섯", 275, 0, 69, 10, 28, dropItemList, 45);
        }
        else if (Objects.equals(monsterName, MonsterTypes.ZOMBIE_MUSHROOM.name())) {
            monster = new GeneralMonster("좀비버섯", 325, 0, 77, 20, 32, dropItemList, 55);
        }
        else if (Objects.equals(monsterName, MonsterTypes.EVIL_EYE.name())) {
            monster = new GeneralMonster("이블아이", 325, 0, 79, 35, 32, dropItemList, 60);
        }
        else if (Objects.equals(monsterName, MonsterTypes.WILD_BOER.name())) {
            monster = new GeneralMonster("와일드보어", 350, 0, 100, 20, 33, dropItemList, 55);
        }
        else {
            throw new IllegalAccessException("잘못된 몬스터입니다.");
        }
        return monster;
    }

    // 던전 내 출몰 몬스터 랜덤하게 생성
    public static Monster createRandomMonster(String dungeonName) throws IllegalAccessException {
        ArrayList<String> inhabitMonstersName = getMonstersName(dungeonName);

        int monsterIndex = rand.nextInt(inhabitMonstersName.size());
        monster = createMonster(inhabitMonstersName.get(monsterIndex));
        return monster;
    }

    // 던전 별 출몰 몬스터 이름 저장
    public static ArrayList<String> getMonstersName(String dungeonName) {
        ArrayList<String> monstersName = new ArrayList<>();

        switch (dungeonName) {
            case "BEGINNER_DUNGEON_1" -> {
                monstersName.add("GREEN_SNAIL");
                monstersName.add("BLUE_SNAIL");
                monstersName.add("RED_SNAIL");
            }
            case "BEGINNER_DUNGEON_2" -> {
                monstersName.add("PIG");
                monstersName.add("SLIME");
                monstersName.add("STUMP");
                monstersName.add("RIBBON_PIG");
                monstersName.add("ORANGE_MUSHROOM");
            }
            case "INTERMEDIATE_DUNGEON_1" -> {
                monstersName.add("CYNICAL_MUSHROOM");
                monstersName.add("STRANGE_PIG");
                monstersName.add("GREEN_MUSHROOM");
                monstersName.add("HORN_MUSHROOM");
                monstersName.add("LACE");
            }
            case "INTERMEDIATE_DUNGEON_2" -> {
                monstersName.add("BLUE_MUSHROOM");
                monstersName.add("ZOMBIE_MUSHROOM");
                monstersName.add("EVIL_EYE");
                monstersName.add("WILD_BOER");
            }
        }
        return monstersName;
    }
    // 몬스터 사냥 후 플레이어 경험치 증가
    public static void giveMonsterExpToPlayer(Player player, Monster monster) {
        player.setCurrentExp(player.getCurrentExp() + monster.getExp());
    }
    // 몬스터 사냥 후 플레이어 코인 증가
    public static void giveMonsterDropCoinToPlayer(Player player, Monster monster) {
        player.setCoin(player.getCoin() + monster.getDropCoin());
    }
    // 입력값에 따라 던전 이름 출력
    public static String getDungeonName(int action) {
        // 초록 달팽이, 파란 달팽이, 빨간 달팽이 랜덤하게 출몰
        if (action == 1) { return "BEGINNER_DUNGEON_1"; }
        // 돼지, 슬라임, 스텀프, 리본 돼지, 주황 버섯 랜덤하게 출몰
        else if (action == 2) { return "BEGINNER_DUNGEON_2"; }
        // 시니컬한 주황버섯, 이상한 돼지, 초록버섯, 뿔버섯, 레이스 랜덤하게 출몰
        else if (action == 3) { return "INTERMEDIATE_DUNGEON_1"; }
        // 파란버섯, 좀비버섯, 이블아이, 와일드보어 랜덤하게 출몰
        else if (action == 4) { return "INTERMEDIATE_DUNGEON_2"; }
        return null;
    }
    // 일반 몬스터 생성
    public static Monster makeMonster(String dungeonName) throws IllegalAccessException {
        Monster monster = createRandomMonster(dungeonName);
        System.out.println(monster.getName() + "가 출몰하였습니다!!!");
        System.out.println("===== 출몰한 몬스터의 정보 =====");
        System.out.println("몬스터 이름 : " + monster.getName());
        System.out.println("몬스터 체력 : " + monster.getHP());
        System.out.println("몬스터 공격력 : " + monster.getAttackPower());
        return monster;
    }
    // 몬스터가 가지고 있는 아이템 중 랜덤으로 1개 드랍
    public static Item dropItem(Monster monster) {
        ArrayList<Item> dropItemList = monster.getDropItems();
        Random random = new Random();
        int dropItemIndex = random.nextInt(dropItemList.size());
        return dropItemList.get(dropItemIndex);
    }
    // 보스 몬스터 생성
    private static BossMonster createBossMonster() {
        ArrayList<Item> dropItemList = ItemSystem.createMonsterDropItemList(MonsterTypes.MUSHMOM.name());
        return new BossMonster("머쉬맘", 17500, 0, 126, 320, 1650, dropItemList, 1000);
    }
    // 보스 몬스터 정보 출력
    public static BossMonster makeBossMonster() {
        BossMonster monster = DungeonSystem.createBossMonster();
        System.out.println(monster.getName() + "이 출몰하였습니다!!!");
        System.out.println("===== 출몰한 몬스터의 정보 =====");
        System.out.println("몬스터 이름 : " + monster.getName());
        System.out.println("몬스터 체력 : " + monster.getHP());
        System.out.println("몬스터 공격력 : " + monster.getAttackPower());
        return monster;
    }
    // 보스 몬스터 형태 콘솔에 출력
    public static void appearBossMonster() {
        Music bossMonsterBGM = new Music("BossMonsterBGM.mp3", false); // 보스 몬스터 등장 음악, 한 번 재생
        // 몬스터 모양 출력하는 데 필요한 문자열 저장
        ArrayList<String> bossMonsterShapeLine = new ArrayList<>(){
            {
                add("  ., ,.  .  ,. .,  .  .. ,. .,  .  ,, ,.  .  ., ..  .  .. ,, ..  .  ., ,.  .  ,.\n");
                add(".,  ,  ,, ,,  ,  ,, ,,  ,  ,  ,. ,,  ,  ,, ,,  ,  ,, ,, .,  ,  ,, ,,  ,  ,, ,,  \n");
                add(",,  ,  ,, ,,  ,  ,, .,  ,  ,  ,. ,,  , ,&@@@@@&*  ., ,, .,  ,  ,, ,,  ,  ,, ,,\n");
                add("  ,, ,,  ,  ,, ,,  ,  ,. ,, ., .%@&******,,,*//////(/@@,. ,, .,  ,  ,, ,,  ,  ,,\n");
                add("  ,, ,,  ,  ., ,,  ,  ,. ,,./@///,,,,,,,,*/////////(((//*@@. .,  ,  ,, ,,  ,  ,,\n");
                add(",,  ,  ,. ,,  ,  ,, .,  ,*@/////*,,,,,****//////////((((/**,@  ,, ,,  ,  ,. ,,\n");
                add(",,  ,  ,, ,,  ,  ,, ,,.#@///***************//////*,*****/////*@., ,,  ,  ,, ,,\n");
                add("  ,, ,,  ,  ,, ,,  ,%@*********************/////*,,********///*@ .  ,, ,,  ,  ,,\n");
                add("  ,, ,,  ,  ,, ,/@&,,,,,,********************/////*,*****/**////@,  ,, .,  ,  ,,\n");
                add(".,  ,  ,, ,(@@,,,,,,,,.,*************************//////(((((/////#@,  ,  ,, ,,\n");
                add(",,  ,  /@@******************************,,,,,,*****/////((((((////*@  ,  ,, ,,\n");
                add("  ,, /@%/(((((((((((((((///////*******,,,,,,,,,,**//////(((((((//***,@(,,  ,  ,,\n");
                add("  ,,*%@(((#####(((((///((((((((//////,,,,,,,,,,,,///////((((((((((////*@@  ,  ,,\n");
                add(",.  #&@(#((%&(###&###@@&/((((((((((((/*****,,,,,,////(((((((((((((((////*@( .,\n");
                add(".,  (@@////##@((####(**////%@//(((((((((///////(((((((((((####((/((((////*@ .,\n");
                add("  .. (@@///(@(/(((((**///////////*@@///(((((((###############((///((((////*@  ..\n");
                add("  ,, ,*%@@@(#((%//@**@ @@****////////////*#@@%*////////(((//***,@(((((////*@  ,,\n");
                add("  .. .. ./#&@@@@/....@@@@,. ........,**/////.@@@////////****,,@(((((/*****@.  ..\n");
                add(",,  ,  ,, ,,/@ .,*,,, ..... .. ...,......*/%@@@@////////*****,,@///***,,.@, ,,\n");
                add(",.  ,  ,, *&@....,*,,,,.    ... *#..........,//////////******,,,,**,./@@.,, ,,\n");
                add("  ,, ,,  (@&.......... ..................,,,***////////******,,,@%#(/* ,,  ,  ,,\n");
                add("  ,. ,, ,&@................................,****,***********,,,,#%  ,, ,,  ,  ,,\n");
                add(".,  ,  ,(@@...,,..................................,*******,,,,,,@#,,  ,  ,, ,,\n");
                add(",,  ,  ,(@@...,,,,,,............................,,,,,****,,,,,,,@ ,,  ,  ,, ,,\n");
                add("  ,, ,, .%%@..,,,,,,,,,,,,.............,,,,,,,,,,,,,***,,,,,,,,@*,  ,, ,,  ,  ,,\n");
                add("  ,, ,,  *#@@ .,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,*******,,,,,...@  ,  ,, ,,  ,  ,,\n");
                add(",,  ,  ,, ,*%@@@.,,,,,,*****************************,,,,,...@@ ., ,,  ,  ,, ,,\n");
                add(",,  ,  ,, ,, .(&@@@,,,,,,,,**********************,,,,,,...@@*  ,, ,,  ,  ,, ,,\n");
                add("  ,, ,,  ,  ,, ,,*#&@@@@.,.,,,,,,,,,,,,,,,,,,,,,,,....%@@*,. .,  ,  ,, ,,  ,  ,,\n");
                add("  ,, ,,  ,  ,, ,,  ,  */#&@&@@@@%............. /@@@@%/.,. ,, ,,  .  ,, .,  ,  ,,\n");
                add(".,  ,  ,, ,,  ,  ,, .,  ,  ,  ***(((#%%%%%###/**. ,, ,, .,  ,  ,. ,,  ,  ,, ,,\n");
            }
        };

        try {
            bossMonsterBGM.start();
            System.out.println("두둥!!! 보스 몬스터 등장!!!\n\n");
            for (String s : bossMonsterShapeLine) {
                System.out.print(s);
                Thread.sleep(200);
            }
            System.out.println("\n\n");
            bossMonsterBGM.close();
            bossMonsterBGM.join();
        } catch(Exception e) { // 예외 처리
            e.printStackTrace();
        }
    }
    public static void fight(Player player, Monster monster) throws Exception {
        int fourthLoopAction;
        // 플레이어와 몬스터 간 전투

        // 몬스터 혹은 플레이어 모두 살아 있다면 전투 진행
        while((player.isLive()) && (monster.isLive())) {
            Music attackMusic = new Music("Attack.mp3", false); // 공격 효과음 재생 쓰레드 생성
            AttackPlayer attackPlayer = new AttackPlayer(); // 몬스터의 플레이어 자동 공격 스레드 생성
            attackPlayer.start(); // // 몬스터의 플레이어 자동 공격 스레드 실행
            fourthLoopAction = PlayerSystem.choosePlayerDoInBattle(scanner); // 전투 중 플레이어의 행위 입력받기
            // 플레이어의 기본 공격
            if (fourthLoopAction == 1) {
                attackMusic.start(); // 공격 효과음 스레드의 run 메소드 호출 >> 공격 효과음 재생
                attackMusic.join(); // 공격 효과음 스레드의 호출 보장
                player.attack(monster); // 플레이어가 몬스터에게 공격
            }
            // 플레이어의 공격스킬 사용
            else if (fourthLoopAction == 2) {
                player.useAttackSkill(monster);
            }
            // 플레이어의 버프스킬 사용
            else if (fourthLoopAction == 3) {
                player.useBuffSkill();
            }
            // 플레이어의 아이템 사용
            else if (fourthLoopAction == 4) {
                PlayerSystem.printPlayerInventory(player); // 플레이어의 현재 보유 아이템 목록 출력
                String useItemInput = Game.getUseItemInput(scanner); // 사용할 아이템 이름을 입력해주세요.
                if (useItemInput != null) System.out.println("아이템 이름 : " + useItemInput);
                player.useItem(useItemInput); // 사용자의 입력값과 일치하는 아이템 사용
                continue;
            }
            // 플레이어 여섯 갈래의 길로 돌아가기
            else if (fourthLoopAction == 5) {
                return;
            }
            attackPlayer.close(); // 플레이어로부터 입력을 받고 나면 몬스터의 자동 공격 종료

            System.out.println(monster.getName() + "의 남은 HP : " + monster.getHP());
        }
    }
}
