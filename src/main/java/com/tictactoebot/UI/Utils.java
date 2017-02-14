package com.tictactoebot.UI;

/**
 * Created by Devin on 2/12/2017.
 */
public class Utils {
    public static volatile int dummy;
    public static void sleep(int millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }


    public static void smallSleep(){
        dummy = 0;
        for(int i = 0; i < 6000; i++){
            dummy++;
        }
    }
}
