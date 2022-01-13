package com.company.system;

import com.company.item.Item;
import com.company.player.Magician;
import com.company.player.Player;
import com.company.player.Warrior;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class PlayerSystem {
    private Rule rule = new Rule();
    private Player player;
    private int requiredExpForLevel;
    private int leftExpForLevelUp;

    // 각 레벨 별 요구 경험치를 기준으로 레벨 업 여부 판단
    public void judgeLevelUp(Player player) {
        // 플레이어 현재 레벨
        int currentLevel = player.getLevel();
        // 레벨 별 요구 경험치
        requiredExpForLevel = Rule.giveRequiredExpForLevel(currentLevel);
        leftExpForLevelUp = requiredExpForLevel - player.getCurrentExp();
        // 플레이어의 현재 경험치가 레벨 별 요구 경험치보다 크거나 같다면,
        if (player.getCurrentExp() >= requiredExpForLevel) {
            System.out.println("\n플레이어 레벨 업!!!!!!");
            // 레벨 증가
            player.setLevel(currentLevel + 1);
            // 플레이어 스탯 증가
            increasePlayerStatus(player);
            // 플레이어 스킬 레벨 업
            increasePlayerSkillLevel(player);

            showPlayerInfo(player);
            showJobDetailInfo(player);
        }
    }

    // 플레이어의 현재 경험치 출력
    public void showPlayerExp(Player player) {
        System.out.println("플레이어의 현재 경험치 : " + player.getCurrentExp());
    }
    // 플레이어의 정보 출력
    public static void showPlayerInfo(Player player) {
        System.out.println("\n===================== 플레이어 정보 =====================");
        System.out.println("이름 : " + player.getName());
        System.out.println("레벨 : " + player.getLevel());
        System.out.println("직업 : " + player.getJob());
        System.out.println("====================================================\n");
    }
    // 플레이어 선택 직업 및 능력치 출력
    public static void showJobDetailInfo(Player player) {
        System.out.println("\n===================== 직업 정보 =====================");
        System.out.println("선택한 직업 : " + player.getJob());
        System.out.println("체력 : " + player.getHP());
        System.out.println("마력 : " + player.getMP());
        System.out.println("공격력 : " + player.getAttackPower());
        System.out.println("방어력 : " + player.getShieldPower());
        System.out.println("====================================================\n");
    }
    // 레벨 업까지 남은 경험치 출력
    public void showLeftExpForLevelUp(Player player) {
        requiredExpForLevel = Rule.giveRequiredExpForLevel(player.getLevel());

        if (player.getCurrentExp() > requiredExpForLevel) {
            leftExpForLevelUp = player.getCurrentExp() - requiredExpForLevel;
        } else if (player.getCurrentExp() == requiredExpForLevel) {
            leftExpForLevelUp = 0;
        } else {
            leftExpForLevelUp = requiredExpForLevel - player.getCurrentExp();
        }
        System.out.println("레벨 업까지 남은 경험치 : " + leftExpForLevelUp);
    }

    // 플레이어 객체 생성
    public Player createPlayer(String playerName, String playerJob) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        // polymorphism => inheritance
        if (playerJob.equals("전사")) {
            player = new Warrior(playerName, playerJob, 200, 80, 40, 20);
        }
        else if (playerJob.equals("마법사")) {
            player = new Magician(playerName, playerJob, 130, 200, 30, 60);
        }

        return player;
    }
    // 플레이어 이름 설정
    public String setName(Scanner scanner) {
        String userName;
        while(true) {
            System.out.print("사용자 이름을 입력해주세요 : ");

            userName = scanner.nextLine();

            if (userName == null || userName.equals("")) {
                userName = setName(scanner);
            }
            // 한글 이외의 문자 입력 시 에러 처리
            if (userName.matches("^[ㄱ-ㅎ가-힣]*$")) break;
            System.out.println("사용자 이름이 올바르지 않습니다. 반드시 한글만으로 입력해주시기 바랍니다.");
        }
        return userName;
    }
    // 플레이어 직업 설정
    public String setJob(Scanner scanner) {
        int jobOption = 0;
        while(true) {
            System.out.println("사용자의 직업을 선택해주세요.(정수만 입력) ");
            System.out.println("1. 전사 2. 마법사");
            jobOption = Integer.parseInt(scanner.nextLine());

            // 주어진 메뉴 이외의 숫자 입력 시 에러 처리
            if (jobOption == 1 || jobOption == 2) {
                break;
            }
            System.out.println("잘못된 선택입니다. 1, 2 번 중 선택해주세요.");
        }
        return getJobName(jobOption);
    }
    public String getJobName(int option) {
        if (option == 1){
            return "전사";
        }
        else if (option == 2){
            return "마법사";
        }

        System.out.println("잘못된 선택입니다. 1, 2 번 중 선택해주세요.");
        return null;
    }
    // 레벨 업 시 플레이어 스탯 증가
    public void increasePlayerStatus(Player player) {
        // 플레이어 체력 20 증가
        player.setHP(player.getCurrentHpLimit() + 20);
        // 플레이어 마력 20 증가
        player.setMP(player.getCurrentMpLimit() + 20);
        // 플레이어 공격력 10 증가
        player.setAttackPower(player.getAttackPower() + 10);
        // 플레이어 방어력 10 증가
        player.setShieldPower(player.getShieldPower() + 10);
    }
    // 레벨 업 시 플레이어 스킬 증가
    public void increasePlayerSkillLevel(Player player) {
        // 공격 스킬 레벨 업
        player.increaseAttackSkillLevel();
        // 버프 스킬 레벨 업
        player.increaseBuffSkillLevel();
    }
    // 마을에서 플레이어의 행위 선택
    public static int choosePlayerToDoInVillage(Scanner scanner) {
        // 메뉴 선택
        int action = 0;

        System.out.println("마을에 오신 것을 환영합니다.");
        System.out.println("1. 마을 걸어다니기");
        System.out.println("2. 회복실로 이동");
        System.out.println("3. 여섯 갈래 길로 돌아가기 ");
        // 사용자로부터 행위 선택 메뉴 값 입력받기
        action = Integer.parseInt(scanner.nextLine());;

        if (!((action == 1) || (action == 2) || (action == 3))) {
            System.out.println("잘못된 선택입니다. 1, 2, 3 번 중 선택해주세요.");
            action = choosePlayerToDoInVillage(scanner);
        }
        return action;
    }
    public static void printPlayerInventory(Player player) {
        ArrayList<ArrayList<Item>> currentInventoryState = player.getInventory();
        Item item = null;

        System.out.println("=========== 보유 아이템 목록 ===========");
        inventoryLine:
        for (int i = 0; i < currentInventoryState.size(); i++) {
            itemSlot:
            for (int j = 0; j < currentInventoryState.get(i).size(); j++) {
                item = currentInventoryState.get(i).get(j);
                if (item != null) {
                    System.out.print("[" + item.getName() + " 1개]");
                } else {
                    System.out.print("[   ]");
                }
                // 아이템 슬롯 간 한 칸 공백
                System.out.print(" ");
            }
            // 한 줄 공백
            System.out.println("");
        }
    }
    // 플레이어의 체력 회복 여부 선택
    public static int choosePlayerCure(Scanner scanner) {
        int action = 0;
        System.out.println("체력을 회복하시겠습니까?");
        System.out.println("1. 네 2. 아니오");
        // 사용자로부터 체력 회복 여부 입력받기
        action = Integer.parseInt(scanner.nextLine());;
        // 잘못된 값 입력 시 사용자로부터 체력 회복 여부 재입력받기
        if (!(action == 1 || action == 2)) {
            System.out.println("잘못된 선택입니다. 1, 2 번 중 선택해주세요.");
            action = choosePlayerCure(scanner);
        }
        // 사용자 입력값 반환
        return action;
    }
    // 플레이어의 회복실 탈출 여부 선택
    public static int stopPlayerCure(Scanner scanner) {
        int action = 0;
        System.out.println("회복실 바깥으로 나가시겠습니까?");
        System.out.println("1. 네 2. 아니오");
        // 사용자로부터 회복실 탈출 여부 입력받기
        action = Integer.parseInt(scanner.nextLine());;
        // 잘못된 값 입력 시 플레이어의 회복실 탈출 여부 재입력받기
        if (!((action == 1) || (action == 2))) {
            System.out.println("잘못된 선택입니다. 1, 2 번 중 선택해주세요.");
            action = stopPlayerCure(scanner);
        }
        return action;
    }
    // 플레이어의 던전 선택
    public static String chooseDungeon(Scanner scanner) {
        String action = null;
        System.out.println("원하시는 사냥터를 선택해주세요!");
        System.out.println("===================== 사냥터 =====================");
        System.out.println("1. 초심자의 사냥터1");
        System.out.println("2. 초심자의 사냥터2");
        System.out.println("3. 중급자의 사냥터1");
        System.out.println("4. 중급자의 사냥터2");
        // 사냥터 선택
        action = scanner.nextLine();
        if (!((action.equals("1")) || (action.equals("2")) || (action.equals("3")) || (action.equals("4")))) {
            System.out.println("잘못된 선택입니다. 1, 2, 3, 4 번 중 선택해주세요.");
            action = chooseDungeon(scanner);
        }
        return action;
    }
    // 몬스터와의 전투 여부 입력 받기
    public static String chooseFightWithMonster(Scanner scanner) {
        String action = null;
        System.out.println("\n싸우시겠습니까?");
        System.out.println("1. 네 2. 아니오");
        action = scanner.nextLine();
        if (!((action.equals("1")) || (action.equals("2")))) {
            System.out.println("잘못된 선택입니다. 1, 2 번 중 선택해주세요.");
            action = chooseFightWithMonster(scanner);
        }
        return action;
    }
    public static int choosePlayerDoInBattle(Scanner scanner) {
        int action = 0;
        System.out.println("\n\n- - - - - - - - - - - - - - - - - - 메뉴 창 - - - - - - - - - - - - - - - - - - - - - - - -\n");
        System.out.println("                 (1) 기본공격           (2) 공격스킬 사용           (3) 버프스킬 사용");
        System.out.println("                 (4) 아이템 사용           (5) 마을로 돌아가기");
        System.out.println("\n- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -\n\n");
        action = scanner.nextInt(); // 사용자로부터 메뉴 입력받기
        if(!((action == 1) || (action == 2) || (action == 3) || (action == 4) || (action == 5))) {
            System.out.println("잘못된 선택입니다. 1, 2, 3, 4, 5 번 중 선택해주세요.");
            action = choosePlayerDoInBattle(scanner);
        }
        return action;
    }
    public static int searchPlayerInfo(Scanner scanner) {
        int action = 0;
        System.out.println("내 상태를 확인하겠습니다.");
        System.out.println("1. 나의 상태");
        System.out.println("2. 보유 아이템");
        action = Integer.parseInt(scanner.nextLine());;
        if (!((action == 1) || (action == 2))) {
            System.out.println("잘못된 선택입니다. 1, 2 번 중 선택해주세요.");
            action = searchPlayerInfo(scanner);
        }
        return action;
    }

    public static void resetPlayerAttackPowerBeforeItemUse(Player player) {
        player.setAttackPower(player.getCurrentLevelAttackPowerLimit());
    }
}
