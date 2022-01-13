package com.company.threads;

import com.company.Game;
import com.company.system.PlayerSystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.company.Game.*;

public class PlayerInputInBattle extends Thread {
    private static BufferedReader br;
    private String userInput;
    private final Music attackMusic;


    public PlayerInputInBattle() {
        InputStreamReader isr = new InputStreamReader(System.in);
        br = new BufferedReader(isr);
        attackMusic = new Music("Attack.mp3", false);
    }

    @Override
    public synchronized void run() {
        while((player.isLive()) && (monster.isLive())) {
            // 메뉴창 출력
            System.out.println("\n\n- - - - - - - - - - - - - - - - - - 메뉴 창 - - - - - - - - - - - - - - - - - - - - - - - -\n");
            System.out.println("                 (1) 기본공격           (2) 공격스킬 사용           (3) 버프스킬 사용");
            System.out.println("                 (4) 아이템 사용           (5) 마을로 돌아가기");
            System.out.println("\n- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -\n\n");

            // 3초 기다리기
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("제어권 넘겨진 후");
            try {
                if (br.ready()) { // 어떤 입력 값이 들어온다면
                    userInput = br.readLine();
                    System.out.println("userInput : " + userInput);

                    // 1, 2, 3, 4, 5 중의 하나라면
                    if (userInput.equals("1") || userInput.equals("2") || userInput.equals("3") || userInput.equals("4") || userInput.equals("5")) {
                        // 각 입력 값에 따라 분기
                        if (userInput.equals("1")) {
                            attackMusic.start(); // 공격 효과음 스레드의 run 메소드 호출 >> 공격 효과음 재생
                            attackMusic.join(); // 공격 효과음 스레드의 호출 보장
                            Game.player.attack(monster); // 플레이어가 몬스터에게 공격
                        }
                        // 플레이어의 공격스킬 사용
                        else if (userInput.equals("2")) {
                            player.useAttackSkill(monster);
                        }
                        // 플레이어의 버프스킬 사용
                        else if (userInput.equals("3")) {
                            player.useBuffSkill();
                        }
                        // 플레이어의 아이템 사용
                        else if (userInput.equals("4")) {
                            PlayerSystem.printPlayerInventory(player); // 플레이어의 현재 보유 아이템 목록 출력
                            String useItemInput = Game.getUseItemInput(scanner); // 사용할 아이템 이름을 입력해주세요.
                            if (useItemInput != null) System.out.println("아이템 이름 : " + useItemInput);
                            player.useItem(useItemInput); // 사용자의 입력값과 일치하는 아이템 사용
                        }
                        // 플레이어 여섯 갈래의 길로 돌아가기
                        else {
                            System.out.println("여섯 갈래의 길로 이동 중입니다...");
                        }
                    }
                    // 이외의 값이라면
                    else {
                        System.out.println("잘못된 선택입니다. 1, 2, 3, 4, 5 번 중 선택해주세요.");
                    }

                } else { // 어떤 입력 값이 들어오지 않는다면
                    Thread.yield(); // 몬스터 공격 스레드에게 제어권 넘김.
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeThread() throws IOException {
        br.close();
    }

    public String choosePlayerDoInBattle() {
        String userInput = null;

        System.out.println("\n\n- - - - - - - - - - - - - - - - - - 메뉴 창 - - - - - - - - - - - - - - - - - - - - - - - -\n");
        System.out.println("                 (1) 기본공격           (2) 공격스킬 사용           (3) 버프스킬 사용");
        System.out.println("                 (4) 아이템 사용           (5) 마을로 돌아가기");
        System.out.println("\n- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -\n\n");
        try {
            if (br.ready()) {
                userInput = br.readLine(); // I/O BLOCKING
            } else {
                Thread.yield();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        // TODO 에러 발생 : userInput 이 null 값이어서 String.equals() 를 호출할 수 없다.
        if(!((userInput.equals("1")) || (userInput.equals("2")) || (userInput.equals("3")) || (userInput.equals("4")) || (userInput.equals("5")))) {
            System.out.println("잘못된 선택입니다. 1, 2, 3, 4, 5 번 중 선택해주세요.");
            userInput = choosePlayerDoInBattle();
        }
        return userInput;
    }
}
