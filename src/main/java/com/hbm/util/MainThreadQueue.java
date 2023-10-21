package com.hbm.util;

import java.util.LinkedList;
import java.util.Queue;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class MainThreadQueue {
    private static Queue<Runnable> queue = new LinkedList<>();

    public static void enqueue(Runnable runnable) {
        queue.add(runnable);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        while (!queue.isEmpty()) {
            queue.poll().run();
        }
    }
}