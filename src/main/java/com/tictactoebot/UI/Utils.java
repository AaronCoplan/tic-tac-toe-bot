package com.tictactoebot.UI;

/**
 * Created by Devin on 2/12/2017.
 */
public class Utils {
    public static void sleep(int millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public static void join (Thread t){
        try {
            t.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
