package com.company.system;

import java.util.ArrayList;

public class Rule {
    // 각 레벨 별 요구 경험치
    private static final ArrayList<Integer> EXP_STANDARD_FOR_LEVEL = new ArrayList<Integer>();

    Rule() {
        // 레벨 1 ~ 30 레벨업을 위한 요구 경험치 저장
        for (int i = 0; i < 30; i++) {
            if (i == 0) EXP_STANDARD_FOR_LEVEL.add(15);
            else {
                EXP_STANDARD_FOR_LEVEL.add(EXP_STANDARD_FOR_LEVEL.get(i-1)*2);
            }
        }
    }
    // 현재 레벨에 다른 레벨업을 위한 경험치 수치 리턴
    public static int giveRequiredExpForLevel(int currentLevel) {
        return EXP_STANDARD_FOR_LEVEL.get(currentLevel - 1);
    }

}
