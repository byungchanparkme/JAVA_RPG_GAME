package com.company.threads;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import javazoom.jl.player.Player;
import com.company.Application;

public class Music extends Thread {
    private Player player;
    private boolean isLoop; // 곡이 무한 반복인지 한 번만 재생되는지 설정
    private File file;
    private FileInputStream fis;
    private BufferedInputStream bis;

    // 생성자
    public Music(String name, boolean isLoop) {
        // 곡의 제목과 무한반복인지의 유무
        try {
            this.isLoop = isLoop;
            // 음악 파일에 Application 의 절대경로 기준으로 접근
            file = new File(Application.class.getResource("./music/" + name).toURI());
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            player = new Player(bis);
        } catch (Exception e) { // 오류 발생 시
            e.printStackTrace();
        }
    }

    // 쓰레드 실행
    @Override
    public void run() {
        try {
            do {
                player.play(); // 곡 재생
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                player = new Player(bis);
            } while(isLoop); // isLoop 가 true 이면 무한반복
        } catch (Exception e) { // 오류 발생 시
            e.printStackTrace();
        }
    }
    public void close() { // 음악 종료
        isLoop = false;
        player.close();
        this.interrupt();
    }
}
