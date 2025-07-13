package com.hbm.util;

public class Clock {
    private static long time_ms;
    public static void update(){
	time_ms = System.currentTimeMillis();
    }
    public static long get_ms(){
	return time_ms;
    }
}
