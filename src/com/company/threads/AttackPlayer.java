package com.company.threads;

import com.company.Game;
import com.company.system.PlayerSystem;
import com.company.utils.PrintUtil;
import java.util.Scanner;

// 몬스터가 플레이어 자동 공격하는 스레드
public class AttackPlayer extends Thread {
    private boolean stop = false; // 몬스터 자동 공격 루프 정지 결정 기준
    @Override
    public void run() {
        while((Game.player.isLive()) && (Game.monster.isLive()) && (!stop)) { // 몬스터와 플레이어 모두 살아 있고
            try {
                if (Game.player.getHP() == 0) {
                    System.out.println("플레이어가 죽었습니다.\n");
                    PrintUtil.printDelay("여섯 갈래의 길로 이동 중입니다...", 100, false);
                    Game.setLoadingEffect(); // 로딩바 적용
                } else { // 플레이어가 아직 살아 있으면,
                    Music punchMusicThread = new Music("Attack.mp3", false); // 버튼 클릭
                    punchMusicThread.start(); // 공격 효과음 적용
                    System.out.println("                                               [ " + Game.monster.getName() + "  ] 의 공격입니다.");
                    System.out.println("                                               [          펀치        ] 를 사용하였습니다");
                    System.out.println("                                                                                       파워는 " + Game.monster.getAttackPower() + "입니다.\n");
                    Game.player.setHP(Game.player.getHP() - Game.monster.getAttackPower());
                    System.out.println("\n                                                                                     플레이어의 HP : " + Game.player.getHP());
                }
                Thread.sleep(2000); // 몬스터 2초 주기로 플레이어 공격
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    public void close() { // 현재 스레드 중단
        this.stop = true;
        this.interrupt();
    }
}
