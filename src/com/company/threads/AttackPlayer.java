package com.company.threads;

import com.company.Game;
import com.company.utils.PrintUtil;

import java.io.IOException;

import static com.company.Game.monster;
import static com.company.Game.player;

// 몬스터가 플레이어 자동 공격하는 스레드
public class AttackPlayer extends Thread {

    @Override
    public synchronized void run() {
        while((player.isLive()) && (monster.isLive())) {
            Music punchMusic = new Music("Attack.mp3", false);
            punchMusic.start(); // 공격 효과음 적용
            try {
                punchMusic.join(); // 공격 효과음 실행 보장
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("                                               [ " + Game.monster.getName() + "  ] 의 공격입니다.");
            System.out.println("                                               [          펀치        ] 를 사용하였습니다");
            System.out.println("                                                                                       파워는 " + Game.monster.getAttackPower() + "입니다.\n");
            Game.player.setHP(Game.player.getHP() - Game.monster.getAttackPower());
            System.out.println("\n                                                                                     플레이어의 HP : " + Game.player.getHP());
            Thread.yield();

            if (Game.player.getHP() == 0) {
                close(); // 몬스터 자동 공격 스레드 중단
                try {
                    PlayerInputInBattle.closeThread();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("플레이어가 죽었습니다.\n");
                try {
                    PrintUtil.printDelay("여섯 갈래의 길로 이동 중입니다...", 100, false);
                    Game.setLoadingEffect(); // 로딩바 적용
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public void close() { // 현재 스레드 중단
        this.interrupt();
    }
}
