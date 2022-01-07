package com.company.utils;

public class PrintUtil {
    public static void printDelay(String message, int ms, boolean println) throws Exception {
        try {
            for (int i = 0; i < message.length(); i++) {
                System.out.print(message.charAt(i));
                Thread.sleep(ms);
            }

            if (println) {
                System.out.println("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
