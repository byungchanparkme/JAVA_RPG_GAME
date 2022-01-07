package com.company;

import com.company.item.Item;
import com.company.monster.Monster;
import com.company.player.Player;
import com.company.system.DungeonSystem;
import com.company.system.ItemSystem;
import com.company.system.PlayerSystem;
import com.company.threads.Music;
import com.company.utils.PrintUtil;

import java.util.*;

public class Game {
    private final DungeonSystem dungeonSystem = new DungeonSystem();
    private final PlayerSystem playerSystem = new PlayerSystem();
    private Music backgroundMusic;
    private Music userCreateMusic;
    private Music typingMusic;
    public static Player player;
    public static Monster monster;
    public static Scanner scanner;

    public void play() throws Exception {
        backgroundMusic = new Music("GameIntro.mp3", true); // 배경음악, 무한 반복 재생
        userCreateMusic = new Music("UserCreate.mp3", false); // 유저 생성 시 효과음, 한 번만 재생
        typingMusic = new Music("Typing.mp3", true); // 타이핑 효과 음악, 무한 반복 재생

        scanner = new Scanner(System.in);
        backgroundMusic.setDaemon(true); // 배경 음악 스레드는 데몬 스레드롲 ㅣ정
        backgroundMusic.start(); // 게임 시작 시 배경 음악 재생
        showGameStartScreen(); // 시작 화면 출력

        // # 게임 캐릭터 생성 #
        String playerName = playerSystem.setName(scanner); // 플레이어 이름 입력받기
        System.out.println("이름이 입력되었습니다.\n");
        String playerJob = playerSystem.setJob(scanner); // 플레이어 직업 선택
        System.out.println("[" + playerJob + "] 가 선택되었습니다.\n");
        player = playerSystem.createPlayer(playerName, playerJob); // 플레이어 객체 생성
        Thread.sleep(1000); // 메인 스레드 1초 간 정지 후 실행
        System.out.println("캐릭터를 생성합니다.");
        userCreateMusic.start(); // 유저 생성 음악 재생
        Thread.sleep(1000); // 메인 스레드 1초 간 정지 후 실행

        // 로딩 효과 적용
        setLoadingEffect();

        // # 플레이어 정보 출력 #
        // 플레이어 이름 및 직업 출력
        playerSystem.showPlayerInfo(player);
        // 선택한 직업의 능력치 출력
        playerSystem.showJobDetailInfo(player);
        // 유저 생성 음악 종료
        userCreateMusic.close();
        Thread.sleep(2000); // 메인 스레드 2초 간 정지 후 실행

        // # 게임 루프 #
        gameLoop(scanner, player, playerSystem);
    }

    // 게임 시작 시 메세지 출력
    public void showGameStartScreen() throws Exception {
        greeting();
        typingMusic.start(); // 시작 화면 출력 시 타이핑 효과음 재생
        introduceGameJob();
        typingMusic.close(); // 시작 화면 출력 시 타이핑 효과음 종료
        typingMusic = new Music("Typing.mp3", true); // start 메소드 두 번 사용불가하므로 MusicThread 객체 재할당
        typingMusic.start(); // 시작 화면 출력 시 타이핑 효과음 재생
        introduceGameStory();
        typingMusic.close(); // 시작 화면 출력 시 타이핑 효과음 종료

    }

    // 환영 인사 메세지 출력
    private void greeting() throws Exception {
        PrintUtil.printDelay("\uD83C\uDF41 메이플 스토리에 오신 것을 환영합니다. \uD83C\uDF41 \n\n", 100, true); // 게임 환영 메세지 타이핑 효과
        Thread.sleep(1000); // 메인 스레드 1초 간 정지 후 실행
        printGameLogo(); // 게임 로고 출력
        Thread.sleep(2000); // 메인 스레드 2초 간 정지 후 실행
    }
    // 게임 로고 출력
    private void printGameLogo() {
        System.out.println("/$$      /$$                     /$$                  /$$$$$$   /$$");
        System.out.println("| $$$    /$$$                    | $$                 /$$__  $$ | $$");
        System.out.println("| $$$$  /$$$$  /$$$$$$   /$$$$$$ | $$  /$$$$$$       | $$  \\__//$$$$$$    /$$$$$$   /$$$$$$  /$$   /$$");
        System.out.println("| $$ $$/$$ $$ |____  $$ /$$__  $$| $$ /$$__  $$      |  $$$$$$|_  $$_/   /$$__  $$ /$$__  $$| $$  | $$");
        System.out.println("| $$  $$$| $$  /$$$$$$$| $$  \\ $$| $$| $$$$$$$$       \\____  $$ | $$    | $$  \\ $$| $$  \\__/| $$  | $$");
        System.out.println("| $$\\  $ | $$ /$$__  $$| $$  | $$| $$| $$_____/       /$$  \\ $$ | $$ /$$| $$  | $$| $$      | $$  | $$");
        System.out.println("| $$ \\/  | $$|  $$$$$$$| $$$$$$$/| $$|  $$$$$$$      |  $$$$$$/ |  $$$$/|  $$$$$$/| $$      |  $$$$$$$");
        System.out.println("|__/     |__/ \\_______/| $$____/ |__/ \\_______/       \\______/   \\___/   \\______/ |__/       \\____  $$");
        System.out.println("                        | $$                                                                  /$$  | $$");
        System.out.println("                        | $$                                                                 |  $$$$$$/");
        System.out.println("                        |__/                                                                  \\______/");
        System.out.println("\n\n\n");
    }
    // 게임 소개 메세지 출력
    private void introduceGameJob() {
        try {
            System.out.println("===================== 게임 소개 =====================\n\n");
            String s = "게임을 시작하시게 되면 원하시는 직업의 캐릭터를 고르시면 됩니다.\n\n"
                    + "직업은 각각 전사, 마법사 총 2개입니다.\n\n"
                    + "직업에 따라 초기 능력치가 다르게 설정됩니다.\n\n";
            for (int i = 0; i < s.length(); i++) { // 타이핑 효과
                Thread.sleep(25); // 각 문자 별로 25ms 간 정지 후 실행
                System.out.print(s.charAt(i)); // 문자 하나하나씩 접근하여 출력
            }
            Thread.sleep(300); // 메인 스레드 0.3s 간 정지 후 실행
        } catch (Exception e) { // 예외 처리
            e.printStackTrace();
        }
    }
    // 게임 스토리 출력
    private void introduceGameStory() {
        try {
            System.out.println("====================== 줄거리 ======================\n\n");
            String s = "서기 1500년 나라 자체는 부유하고 평화로웠다.\n\n"
                    + "그런데 어느 날 운석이 떨어졌다.\n\n"
                    + "운석 안에는 지구를 정복하려는 몬스터들이 나오기 시작한다.\n\n"
                    + "하지만 그 몬스터들과 몬스터들을 이끄는 머쉬맘은 실로 막강했다.\n\n"
                    + "이때 이것을 저지하기 위한 모험가가 나타난다.\n\n";
            for (int i = 0; i < s.length(); i++) { // 타이핑 효과
                Thread.sleep(25);
                System.out.print(s.charAt(i));
            }
            Thread.sleep(300);
        } catch (Exception e) { // 예외 처리
            e.printStackTrace();
        }
    }
    // 메인 게임 루프
    private void gameLoop(Scanner scanner, Player player, PlayerSystem playerSystem) throws Exception {
        int firstLoopAction = 0;
        boolean run = true;
        while(run) {
            // 사용자로부터 여섯 갈래의 길 중 하나의 메뉴 번호 입력받기
            firstLoopAction = sixRoadSelectionPage(scanner);
            // 로딩 효과 적용
            setLoadingEffect();
            // 사용자의 메뉴 선택에 따라 각각의 로직 진행
            // 1. 마을로 이동(체력 회복)
            if (firstLoopAction == 1) {
                boolean villageRun = true;
                int secondLoopAction = 0;
                // 마을 루프
                while(villageRun) {
                    // 마을에서 플레이어의 행위 선택
                    secondLoopAction = PlayerSystem.choosePlayerToDoInVillage(scanner);
                    // 사용자의 메뉴 선택에 따라 각각의 로직 진행
                    // 마을 걸어다니기
                    if (secondLoopAction == 1) {
                        System.out.println("마을 구석구석 이리저리 돌아다니는 중.........");
                        villageRun = true;
                    }
                    // 회복실로 이동
                    else if (secondLoopAction == 2) {
                        // 회복실 입장 메세지 출력
                        printEnterHospitalMessage();

                        boolean cureRun = true;
                        int thirdLoopAction = 0;
                        int fourthLoopAction = 0;
                        while(cureRun) {
                            // 플레이어의 체력 회복 여부 선택
                            thirdLoopAction = PlayerSystem.choosePlayerCure(scanner);
                            // 플레이어가 체력 회복 원할 시
                            if (thirdLoopAction == 1) {
                                PrintUtil.printDelay("휴식중...............", 200, true);
                                System.out.println("플레이어의 체력과 마력이 전부 회복되었습니다.\n");
                                // 플레이어의 회복실 탈출 여부 선택
                                fourthLoopAction = PlayerSystem.stopPlayerCure(scanner);
                                // 플레이어가 회복실 나가고 싶을 시
                                if (fourthLoopAction == 1) {
                                    System.out.println("마을로 이동 중입니다. !!!!!!!!!!!!!!!!!!!!");
                                    // 회복실 루프 종료
                                    cureRun = false;
                                }
                                // 플레이어가 회복실에 머물길 원할 시
                                else if (fourthLoopAction == 2) {
                                    // 회복실 루프 진행
                                    cureRun = true;
                                }
                            }
                            // 플레이어가 체력 회복 원하지 않을 시
                            else if (thirdLoopAction == 2) {
                                System.out.println("마을로 이동 중입니다. !!!!!!!!!!!!!!!!!!!!");
                                // 회복실 루프 종료
                                cureRun = false;
                                // 마을 루프 진행
                                villageRun = true;
                            }
                        }
                    }
                    // 여섯 갈래 길로 돌아가기
                    else if (secondLoopAction == 3) {
                        villageRun = false;
                        // 로딩 효과 적용
                        setLoadingEffect();
                    }
                }
            }
            // 사냥터로 이동
            else if (firstLoopAction == 2) {
                // 던전 선택 로직 진행 여부
                boolean dungeonChooseRun = true;
                int secondLoopAction = 0;
                String dungeonName = null;
                while(dungeonChooseRun) {
                    // 사용자로부터 4개의 사냥터 중 하나의 값 입력받기
                    secondLoopAction = PlayerSystem.chooseDungeon(scanner);
                    // 로딩 효과 적용
                    setLoadingEffect();
                    // 사용자 입력값에 따라 던전 이름 저장
                    dungeonName = DungeonSystem.getDungeonName(secondLoopAction);
                    // 몬스터와의 전투 진행 여부
                    boolean dungeonRun = true;
                    int thirdLoopAction = 0;
                    while(dungeonRun) {
                        // 출몰하는 몬스터 랜덤 생성 및 정보 출력
                        monster = DungeonSystem.makeMonster(dungeonName);
                        // 몬스터와 전투 여부 입력받기
                        thirdLoopAction = PlayerSystem.chooseFightWithMonster(scanner);
                        // 전투 진행 여부에 대한 입력 값에 따라 분기
                        // 전투 진행하겠다
                        if (thirdLoopAction == 1) {
                            System.out.println("플레이어와 몬스터 간 전투를 시작합니다. !!!!!!!");
                            DungeonSystem.fight(player, monster);

                            // 플레이어 공격력 원 상태로 리셋
                            PlayerSystem.resetPlayerAttackPowerBeforeItemUse(player);

                            // if (player.isLive()) 플레이어의 승리
                            if (player.isLive()) {
                                Music achieveMusicThread = new Music("Achieve.mp3", false);
                                System.out.println(monster.getName() + "가 사망하였습니다.");
                                Thread.sleep(1000);
                                // 몬스터가 드랍한 아이템 획득 (안 나올 수도 있음.) player.getDropItemFromMonster
                                Item monsterDropItem = DungeonSystem.dropItem(monster);
                                System.out.println(monster.getName() + "가 " + monsterDropItem.getName() + "(을/를) 드랍하였습니다.");
                                Thread.sleep(1000); // 메인 스레드 1초 간 정지
                                achieveMusicThread.start(); // 획득 효과음 실행
                                achieveMusicThread.join(); // 획득 효과음 실행 보장
                                achieveMusicThread = new Music("Achieve.mp3", false);
                                player.getDropItem(monsterDropItem);
                                achieveMusicThread.start(); // 획득 효과음 실행
                                achieveMusicThread.join(); // 획득 효과음 실행 보장
                                achieveMusicThread = new Music("Achieve.mp3", false);
                                System.out.println("플레이어 경험치 +" + monster.getExp());
                                Thread.sleep(1000); // 메인 스레드 1초 간 정지
                                achieveMusicThread.start(); // 획득 효과음 실행
                                achieveMusicThread.join();
                                System.out.println("플레이어 보유 메소 + " + monster.getDropCoin());
                                // 몬스터가 드랍한 코인 획득 player.getCoinFromMonster
                                DungeonSystem.giveMonsterDropCoinToPlayer(player, monster);
                                // 던전 시스템이 플레이어에게 경험치 제공 dungeonSystem.setPlayerExp(monster.exp) { player.setExp(currentExp + monster.exp) }
                                DungeonSystem.giveMonsterExpToPlayer(player, monster);
                                // 플레이어의 레벨업 여부 판단
                                playerSystem.judgeLevelUp(player);

                                // 몬스터 사냥 후의 플레이어 보유 코인 및 경험치 출력
                                System.out.println("==========================");
                                // 플레이어 현재 보유 코인 출력
                                System.out.println("플레이어의 현재 보유 코인 : " + player.getCoin());
                                // 플레이어의 현재 경험치 출력
                                playerSystem.showPlayerExp(player);
                                // 플레이어의 레벨 업까지 남은 경험치 출력
                                playerSystem.showLeftExpForLevelUp(player);
                                System.out.println("==========================");
                                Thread.sleep(1000);

                                int fifthLoopAction = chooseContinueBattle(scanner);

                                // 마을로
                                if (fifthLoopAction == 2) {
                                    dungeonRun = false;
                                    dungeonChooseRun = false;
                                    // 로딩 효고 적용
                                    setLoadingEffect();
                                }
                            }
                            // if (monster.isLive()) 몬스터의 승리 => 마을에서 소생
                            else if (monster.isLive()) {
                                System.out.println("플레이어가 사망하였습니다.");
                                System.out.println("여섯 갈래의 길에서 다시 부활합니다.");
                                System.out.println("여섯 갈래의 길로 이동 중.........");
                                // 로딩 효과 적용
                                setLoadingEffect();
                            }
                        }
                        // 전투 진행 안하겠다 => 마을로 이동
                        else if (thirdLoopAction == 2) {
                            dungeonRun = false;
                            dungeonChooseRun = false;
                            System.out.println("여섯 갈래의 길로 이동 중입니다. !!!!!!!!!!!!!!!!!!!!");
                            // 로딩 효과 적용
                            setLoadingEffect();
                        }
                    }
                }
            }
            // 보스 몬스터 사냥터로 이동(클리어하면 완료)
            else if (firstLoopAction == 3) {
                Thread.sleep(1000); // 스레드 1초 간 정지
                DungeonSystem.appearBossMonster(); // 보스 몹 ASCII 코드로 등장
                monster = DungeonSystem.makeBossMonster(); // 보스 몬스터 생성
                int thirdLoopAction = 0;
                // 몬스터와 전투 여부 입력받기
                thirdLoopAction = PlayerSystem.chooseFightWithMonster(scanner);
                // 전투 진행 여부에 대한 입력 값에 따라 분기
                // 전투 진행하겠다
                if (thirdLoopAction == 1) {
                    System.out.println("플레이어와 몬스터 간 전투를 시작합니다. !!!!!!!");
                    DungeonSystem.fight(player, monster);

                    // 플레이어 공격력 원 상태로 리셋
                    PlayerSystem.resetPlayerAttackPowerBeforeItemUse(player);

                    // if (player.isLive()) 플레이어의 승리
                    if (player.isLive()) {
                        Music achieveMusicThread = new Music("Achieve.mp3", false);
                        System.out.println(monster.getName() + "가 사망하였습니다.");
                        Thread.sleep(1000);
                        // 몬스터가 드랍한 아이템 획득 (안 나올 수도 있음.) player.getDropItemFromMonster
                        Item monsterDropItem = DungeonSystem.dropItem(monster);
                        System.out.println(monster.getName() + "가 " + monsterDropItem.getName() + "(을/를) 드랍하였습니다.");
                        Thread.sleep(1000); // 메인 스레드 1초 간 정지
                        achieveMusicThread.start(); // 획득 효과음 실행
                        achieveMusicThread.join();
                        achieveMusicThread = new Music("Achieve.mp3", false);
                        player.getDropItem(monsterDropItem);
                        achieveMusicThread.start(); // 획득 효과음 실행
                        achieveMusicThread.join();
                        achieveMusicThread = new Music("Achieve.mp3", false);
                        System.out.println("플레이어 경험치 +" + monster.getExp());
                        Thread.sleep(1000); // 메인 스레드 1초 간 정지
                        achieveMusicThread.start(); // 획득 효과음 실행
                        achieveMusicThread.join();
                        System.out.println("플레이어 보유 메소 + " + monster.getDropCoin());
                        // 몬스터가 드랍한 코인 획득 player.getCoinFromMonster
                        DungeonSystem.giveMonsterDropCoinToPlayer(player, monster);
                        // 던전 시스템이 플레이어에게 경험치 제공 dungeonSystem.setPlayerExp(monster.exp) { player.setExp(currentExp + monster.exp) }
                        DungeonSystem.giveMonsterExpToPlayer(player, monster);
                        // 플레이어의 레벨업 여부 판단
                        playerSystem.judgeLevelUp(player);

                        // 몬스터 사냥 후의 플레이어 보유 코인 및 경험치 출력
                        System.out.println("==========================");
                        // 플레이어 현재 보유 코인 출력
                        System.out.println("플레이어의 현재 보유 코인 : " + player.getCoin());
                        // 플레이어의 현재 경험치 출력
                        playerSystem.showPlayerExp(player);
                        // 플레이어의 레벨 업까지 남은 경험치 출력
                        playerSystem.showLeftExpForLevelUp(player);
                        System.out.println("==========================");
                        Thread.sleep(1000);

                        int fifthLoopAction = chooseContinueBattle(scanner);

                        // 마을로
                        if (fifthLoopAction == 2) {
                            // 로딩 효과 적용
                            setLoadingEffect();
                        }
                    }
                    // if (monster.isLive()) 몬스터의 승리 => 여섯 갈래의 길에서 소생
                    else if (monster.isLive()) {
                        System.out.println("플레이어가 사망하였습니다.");
                        System.out.println("여섯 갈래의 길에서 다시 시작합니다.");
                        System.out.println("여섯 갈래의 길로 이동 중.........");
                        // 로딩 효과 적용
                        setLoadingEffect();
                    }
                }
                // 전투 진행 안하겠다 => 마을로 이동
                else if (thirdLoopAction == 2) {
                    System.out.println("어섯 갈래의 길로 이동 중입니다. !!!!!!!!!!!!!!!!!!!!");
                    // 로딩 효과 적용
                    setLoadingEffect();
                }
            }
            // 몬스터 및 아이템 도감
            else if (firstLoopAction == 4) {
                int secondLoopAction = 0;
                boolean gameInfoSearchRun = true;
                secondLoopAction = searchMonsterOrItem(scanner);
                // 사용자의 입력값(1. 사냥터 별 몬스터 정보 조회 2. 아이템 정보 조회) 에 따라 분기 및 로직 처리
                while (gameInfoSearchRun) {
                    int thirdLoopAction = 0;
                    // 사냥터 별 몬스터 정보 조회
                    if (secondLoopAction == 1) {
                        System.out.println("사냥터 별 몬스터 정보 조회");
                        boolean backToPreviousMenuRun = true;
                        thirdLoopAction = backToPreviousMenu(scanner);
                        // 이전 메뉴로 돌아가시겠습니까?
                        while (backToPreviousMenuRun) {
                            // 1. 네
                            if (thirdLoopAction == 1) {
                                backToPreviousMenuRun = false;
                                gameInfoSearchRun = false;
                                setLoadingEffect(); // 로딩 효과 적용
                            }
                            // 2. 아니오
                            else if (thirdLoopAction == 2) {
                                // 아이템 정보 출력
                                ItemSystem.printItemInfo();
                            }
                        }
                    }
                    // 아이템 정보 조회
                    else if (secondLoopAction == 2) {
                        // 아이템 정보 출력
                        ItemSystem.printItemInfo();
                        boolean backToPreviousMenuRun = true;
                        thirdLoopAction = backToPreviousMenu(scanner);
                        // 이전 메뉴로 돌아가시겠습니까?
                        while (backToPreviousMenuRun) {
                            // 1. 네
                            if (thirdLoopAction == 1) {
                                backToPreviousMenuRun = false;
                                gameInfoSearchRun = false;
                                setLoadingEffect(); // 로딩 효과 적용
                            }
                            // 2. 아니오
                            else if (thirdLoopAction == 2) {
                                // 아이템 정보 출력
                                ItemSystem.printItemInfo();
                            }
                        }
                    }
                }
            }
            // 플레이어 정보 조회
            else if (firstLoopAction == 5) {
                boolean playerInfoSearchRun = true;
                int secondLoopAction = 0;

                while (playerInfoSearchRun) {
                    secondLoopAction = PlayerSystem.searchPlayerInfo(scanner);
                    int thirdLoopAction = 0;
                    // 플레이어의 상태 조회
                    if (secondLoopAction == 1) {
                        // # 플레이어 정보 출력 #
                        // 플레이어 이름 및 직업 출력
                        PlayerSystem.showPlayerInfo(player);
                        // 선택한 직업의 능력치 출력
                        PlayerSystem.showJobDetailInfo(player);

                        boolean backToPreviousMenuRun = true;
                        thirdLoopAction = backToPreviousMenu(scanner);
                        // 이전 메뉴로 돌아가시겠습니까?
                        while (backToPreviousMenuRun) {
                            // 1. 네
                            if (thirdLoopAction == 1) {
                                backToPreviousMenuRun = false;
                                playerInfoSearchRun = false;
                                setLoadingEffect(); // 로딩 효과 적용
                            }
                            // 2. 아니오
                            else if (thirdLoopAction == 2) {
                                // # 플레이어 정보 출력 #
                                // 플레이어 이름 및 직업 출력
                                PlayerSystem.showPlayerInfo(player);
                                // 선택한 직업의 능력치 출력
                                PlayerSystem.showJobDetailInfo(player);
                            }
                        }
                    }
                    // 플레이어의 보유 아이템 조회
                    else if (secondLoopAction == 2) {
                        // 플레이어 보유 아이템 목록 출력
                        PlayerSystem.printPlayerInventory(player);

                        boolean backToPreviousMenuRun = true;
                        thirdLoopAction = backToPreviousMenu(scanner);
                        // 이전 메뉴로 돌아가시겠습니까?
                        while (backToPreviousMenuRun) {
                            // 1. 네
                            if (thirdLoopAction == 1) {
                                backToPreviousMenuRun = false;
                                playerInfoSearchRun = false;
                                setLoadingEffect(); // 로딩 효과 적용
                            }
                            // 2. 아니오
                            else if (thirdLoopAction == 2) {
                                // 플레이어 보유 아이템 목록 출력
                                PlayerSystem.printPlayerInventory(player);
                            }
                        }
                    }
                }
            }
            // 게임 종료
            else if (firstLoopAction == 6) {
                gameEnd();
                break;
            }
        }
    }


    // 여섯 갈래의 길 선택
    private int sixRoadSelectionPage(Scanner scanner) {
        // 메뉴 선택
        int action = 0;

        System.out.println("현재 여섯 갈래 길에 위치하고 있습니다.");
        System.out.println("가고 싶은 장소를 선택해주세요.");
        System.out.println("1. 마을로 이동(체력 회복)");
        System.out.println("2. 사냥터로 이동");
        System.out.println("3. 보스 몬스터 사냥터로 이동(클리어하면 완료)");
        System.out.println("4. 몬스터 및 아이템 도감");
        System.out.println("5. 나의 상태 확인");
        System.out.println("6. 게임 종료");
        // 사용자로부터 여섯 갈래의 길 중 선택 메뉴 입력받기
        action = Integer.parseInt(scanner.nextLine());;

        // 메뉴 선택 시 잘못된 문자 입력 시 여섯 갈래의 길 중 메뉴 선택 페이지 재호출
        if (!((action == 1) || (action == 2) || (action == 3) || (action == 4) || (action == 5) || (action == 6))) {
            System.out.println("잘못된 선택입니다. 1, 2, 3, 4, 5, 6 번 중 선택해주세요.");
            action = sixRoadSelectionPage(scanner);
        }
        // 1, 2, 3, 4, 5, 6 중 하나의 숫자 출력
        return action;
    }

    // 회복실 입장 메세지 출력
    public static void printEnterHospitalMessage() {
        System.out.println("회복실로 이동 중입니다. !!!!!!!!!!!!!!!!!!!!");
        System.out.println("회복실로 들어오셨습니다.");
    }

    public static void villageLoop(Scanner scanner) throws Exception {
        boolean villageRun = true;
        int secondLoopAction = 0;
        // 마을 루프
        while(villageRun) {
            // 마을에서 플레이어의 행위 선택
            secondLoopAction = PlayerSystem.choosePlayerToDoInVillage(scanner);
            // 사용자의 메뉴 선택에 따라 각각의 로직 진행
            // 마을 걸어다니기
            if (secondLoopAction == 1) {
                System.out.println("마을 구석구석 이리저리 돌아다니는 중.........");
                villageRun = true;
            }
            // 회복실로 이동
            else if (secondLoopAction == 2) {
                // 회복실 입장 메세지 출력
                printEnterHospitalMessage();

                boolean cureRun = true;
                int thirdLoopAction = 0;
                int fourthLoopAction = 0;
                while(cureRun) {
                    // 플레이어의 체력 회복 여부 선택
                    thirdLoopAction = PlayerSystem.choosePlayerCure(scanner);
                    // 플레이어가 체력 회복 원할 시
                    if (thirdLoopAction == 1) {
                        PrintUtil.printDelay("휴식중...............", 200, true);
                        System.out.println("플레이어의 체력과 마력이 전부 회복되었습니다.\n");
                        // 플레이어의 회복실 탈출 여부 선택
                        fourthLoopAction = PlayerSystem.stopPlayerCure(scanner);
                        // 플레이어가 회복실 나가고 싶을 시
                        if (fourthLoopAction == 1) {
                            System.out.println("마을로 이동 중입니다. !!!!!!!!!!!!!!!!!!!!");
                            // 회복실 루프 종료
                            cureRun = false;
                        }
                        // 플레이어가 회복실에 머물길 원할 시
                        else if (fourthLoopAction == 2) {
                            // 회복실 루프 진행
                            cureRun = true;
                        }
                    }
                    // 플레이어가 체력 회복 원하지 않을 시
                    else if (thirdLoopAction == 2) {
                        System.out.println("마을로 이동 중입니다. !!!!!!!!!!!!!!!!!!!!");
                        // 회복실 루프 종료
                        cureRun = false;
                        // 마을 루프 진행
                        villageRun = true;
                    }
                }
            }
            // 여섯 갈래 길로 돌아가기
            else if (secondLoopAction == 3) {
                villageRun = false;
            }
        }
    }
    // 사용자의 선택 (전투 계속 할 지 안 할 지)
    public int chooseContinueBattle(Scanner scanner) {
        int action = 0;
        System.out.println("사냥을 계속 하시겠습니까?");
        System.out.println("1. 예 2. 아니오");
        action = Integer.parseInt(scanner.nextLine());;
        if (!((action == 1) || (action == 2))) {
            System.out.println("잘못된 선택입니다. 1, 2 번 중 선택해주세요.");
            action = chooseContinueBattle(scanner);
        }
        return action;
    }
    // 사용자의 선택 (사냥터 별 몬스터 정보 혹은 아이템 정보 조회)
    public int searchMonsterOrItem(Scanner scanner) {
        int action = 0;
        System.out.println("보고 싶은 정보를 선택해주세요.");
        System.out.println("1. 사냥터 별 몬스터 정보");
        System.out.println("2. 아이템 정보");
        action = Integer.parseInt(scanner.nextLine());;
        if (!((action == 1) || (action == 2))) {
            System.out.println("잘못된 선택입니다. 1, 2 번 중 선택해주세요.");
            action = searchMonsterOrItem(scanner);
        }
        return action;
    }
    // 여섯 갈래의 길로 돌아가기 VS 현재 상태 머무르기 선택
    public int backToPreviousMenu(Scanner scanner) {
        int action = 0;
        System.out.println("돌아가시겠습니까?");
        System.out.println("1. 예 2. 아니오");
        action = Integer.parseInt(scanner.nextLine());;
        if (!((action == 1) || (action == 2))) {
            System.out.println("잘못된 선택입니다. 1, 2 번 중 선택해주세요.");
            action = backToPreviousMenu(scanner);
        }
        return action;
    }
    // 사용자로부터 사용할 아이템 입력받기
    public static String getUseItemInput(Scanner scanner) {
        String action;
        if (Game.player.getInventory().size() == 0) { // 만약 인벤토리가 비어 있다면,
            System.out.println("현재 인벤토리에 사용할 수 있는 아이템이 존재하지 않습니다.");
            return null;
        }
        System.out.println("사용하실 아이템 이름을 입력해주세요.");
        action = scanner.nextLine();
        return action;
    }
    // 로딩바 적용
    public static void setLoadingEffect() throws InterruptedException {
        String loadingBar ="██████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████\n";

        for (int i = 0; i < loadingBar.length(); i++) { // 로딩 효과 스레드
            Thread.sleep(10);
            System.out.print(loadingBar.charAt(i) );
        }
    }
    // 게임 종료
    public void gameEnd() {
        backgroundMusic.close();
        System.out.println("게임이 종료됩니다.");
    }
}
