package com.hbm.handler.imc;

import java.util.HashMap;

import com.hbm.main.MainRegistry;

import cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;

/**
 * I'm not aware of anyone even using these, and for proper addon mods it's way easier to use direct calls instead of messages.
 * Too cumbersome to implement and maintain, especially since the recipe register listeners exist now. Current implementation will break on recipe reload anyway.
 * 
 * @author hbm
 */
@Deprecated
public abstract class IMCHandler {
	
	private static final HashMap<String, IMCHandler> handlers = new HashMap();
	
	public static void registerHandler(String name, IMCHandler handler) {
		handlers.put(name, handler);
	}
	
	public static IMCHandler getHandler(String name) {
		return handlers.get(name);
	}

	public abstract void process(IMCMessage message);
	
	public void printError(IMCMessage message, String error) {
		MainRegistry.logger.error("[" + this.getClass().getSimpleName() + "] Error reading IMC message from " + message.getSender() + ": " + error);
	}
}