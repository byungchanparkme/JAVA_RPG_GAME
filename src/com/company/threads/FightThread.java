package com.company.threads;

import com.company.Game;
import com.company.monster.Monster;
import com.company.player.Player;
import com.company.system.PlayerSystem;
import com.company.utils.PrintUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class FightThread {
    Player player;
    Monster monster;
    private final BufferedReader br;
    Scanner scanner;

    public FightThread(Player player, Monster monster) {
        this.player = player;
        this.monster = monster;
        InputStreamReader isr = new InputStreamReader(System.in);
        br = new BufferedReader(isr);
        scanner = new Scanner(System.in);
    }

    public synchronized void hitMonster() {
        if (Game.isFighting) {
            String userInput = null;
            // 메뉴창 출력
            System.out.println("\n\n- - - - - - - - - - - - - - - - - - 메뉴 창 - - - - - - - - - - - - - - - - - - - - - - - -\n");
            System.out.println("                 (1) 기본공격           (2) 공격스킬 사용           (3) 버프스킬 사용");
            System.out.println("                 (4) 아이템 사용           (5) 마을로 돌아가기");
            System.out.println("\n- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -\n\n");
            // 3초 동안 플레이어가 입력할 시간을 준다.
            try {
                // 3번 동작함.
                for (int i = 4; i > 0; i--) {
                    if (br.ready()) { // 논-블록킹으로 사용자 입력 받기
                        userInput = br.readLine();
                    }
                    if (userInput != null) break; // 사용자로부터 입력값 받는 즉시 스레드 중지하는 동작 차단
                    Thread.sleep(1000);
                }
            } catch (Exception ignored) { }
            if (userInput == null) {
                System.out.println("아무 값도 입력하지 않았습니다.");
                return;
            }
            // 만약 입력값이 있으면 그 입력값에 따라 플레이어는 몬스터 공격
            else {
                // 기본 공격
                switch (userInput) {
                    case "1": {
                        Music punchMusic = new Music("Attack.mp3", false);
                        punchMusic.start(); // 공격 효과음 적용

                        try {
                            punchMusic.join(); // 공격 효과음 실행 보장
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        player.attack(monster);
                        break;
                    }
                    // 공격 스킬 사용
                    case "2": {
                        player.useAttackSkill(monster);
                        break;
                    }
                    // 버프 스킬 사용
                    case "3":
                        player.useBuffSkill();
                        break;
                    // 아이템 사용
                    case "4":
                        PlayerSystem.printPlayerInventory(player); // 플레이어의 현재 보유 아이템 목록 출력

                        String useItemInput = Game.getUseItemInput(scanner); // 사용할 아이템 이름을 입력해주세요.

                        if (useItemInput != null) System.out.println("아이템 이름 : " + useItemInput);
                        player.useItem(useItemInput); // 사용자의 입력값과 일치하는 아이템 사용

                        break;
                    // 여섯 갈래의 길로 이동
                    case "5":
                        try {
                            PrintUtil.printDelay("여섯 갈래의 길로 이동 중입니다......", 200, true);
                        } catch (Exception ignored) {
                        }
                        break;
                }
            }
            Game.isFighting = monster.getHP() > 0 && player.getHP() > 0;
            if (!Game.isFighting) return;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            notify(); // 몬스터 공격 스레드 실행 대기 상태로
        }

    }

    public synchronized void hitPlayer() {
        if (Game.isFighting) {
            Music punchMusic = new Music("Attack.mp3", false);
            punchMusic.start(); // 공격 효과음 적용
            try {
                punchMusic.join(); // 공격 효과음 실행 보장
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("                                                                      [ " + Game.monster.getName() + "  ] 의 공격입니다.");
            System.out.println("                                                                          [  펀치  ] 를 사용하였습니다");
            System.out.println("                                                                                       파워는 " + Game.monster.getAttackPower() + "입니다.\n");
            Game.player.setHP(Game.player.getHP() - Game.monster.getAttackPower());
            System.out.println("\n                                                                                     플레이어의 HP : " + Game.player.getHP());
            Game.isFighting = player.getHP() > 0 && monster.getHP() > 0;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
            try{
                wait();
            } catch (InterruptedException ignored) {}
            notify();
        }
    }
}
