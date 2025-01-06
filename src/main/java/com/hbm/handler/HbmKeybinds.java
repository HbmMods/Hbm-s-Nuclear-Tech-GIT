package com.hbm.handler;

import com.hbm.inventory.gui.GUICalculator;

import cpw.mods.fml.common.FMLCommonHandler;
import org.lwjgl.input.Keyboard;

import com.hbm.extprop.HbmPlayerProps;
import com.hbm.main.MainRegistry;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.KeybindPacket;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.common.gameevent.InputEvent.MouseInputEvent;
import net.minecraft.client.settings.KeyBinding;

public class HbmKeybinds {

	public static final String category = "hbm.key";

	public static KeyBinding calculatorKey = new KeyBinding(category + ".calculator", Keyboard.KEY_N, category);
	public static KeyBinding jetpackKey = new KeyBinding(category + ".toggleBack", Keyboard.KEY_C, category);
	public static KeyBinding hudKey = new KeyBinding(category + ".toggleHUD", Keyboard.KEY_V, category);
	public static KeyBinding dashKey = new KeyBinding(category + ".dash", Keyboard.KEY_LSHIFT, category);
	public static KeyBinding trainKey = new KeyBinding(category + ".trainInv", Keyboard.KEY_R, category);
	public static KeyBinding copyToolAlt = new KeyBinding(category + ".copyToolAlt", Keyboard.KEY_LMENU, category);

	public static KeyBinding copyToolCtrl = new KeyBinding(category + ".copyToolCtrl", Keyboard.KEY_LCONTROL, category);

	public static KeyBinding reloadKey = new KeyBinding(category + ".reload", Keyboard.KEY_R, category);
	public static KeyBinding gunPrimaryKey = new KeyBinding(category + ".gunPrimary", -100, category);
	public static KeyBinding gunSecondaryKey = new KeyBinding(category + ".gunSecondary", -99, category);
	public static KeyBinding gunTertiaryKey = new KeyBinding(category + ".gunTertitary", -98, category);

	public static KeyBinding craneUpKey = new KeyBinding(category + ".craneMoveUp", Keyboard.KEY_UP, category);
	public static KeyBinding craneDownKey = new KeyBinding(category + ".craneMoveDown", Keyboard.KEY_DOWN, category);
	public static KeyBinding craneLeftKey = new KeyBinding(category + ".craneMoveLeft", Keyboard.KEY_LEFT, category);
	public static KeyBinding craneRightKey = new KeyBinding(category + ".craneMoveRight", Keyboard.KEY_RIGHT, category);
	public static KeyBinding craneLoadKey = new KeyBinding(category + ".craneLoad", Keyboard.KEY_RETURN, category);

	public static void register() {
		ClientRegistry.registerKeyBinding(calculatorKey);
		ClientRegistry.registerKeyBinding(jetpackKey);
		ClientRegistry.registerKeyBinding(hudKey);
		ClientRegistry.registerKeyBinding(dashKey);
		ClientRegistry.registerKeyBinding(trainKey);

		ClientRegistry.registerKeyBinding(reloadKey);
		ClientRegistry.registerKeyBinding(gunPrimaryKey);
		ClientRegistry.registerKeyBinding(gunSecondaryKey);
		ClientRegistry.registerKeyBinding(gunTertiaryKey);

		ClientRegistry.registerKeyBinding(craneUpKey);
		ClientRegistry.registerKeyBinding(craneDownKey);
		ClientRegistry.registerKeyBinding(craneLeftKey);
		ClientRegistry.registerKeyBinding(craneRightKey);
		ClientRegistry.registerKeyBinding(craneLoadKey);
		ClientRegistry.registerKeyBinding(copyToolAlt);
		ClientRegistry.registerKeyBinding(copyToolCtrl);
	}
	
	@SubscribeEvent
	public void mouseEvent(MouseInputEvent event) {
		HbmPlayerProps props = HbmPlayerProps.getData(MainRegistry.proxy.me());
		
		for(EnumKeybind key : EnumKeybind.values()) {
			boolean last = props.getKeyPressed(key);
			boolean current = MainRegistry.proxy.getIsKeyPressed(key);
			
			if(last != current) {
				PacketDispatcher.wrapper.sendToServer(new KeybindPacket(key, current));
				props.setKeyPressed(key, current);
			}
		}
	}
	
	@SubscribeEvent
	public void keyEvent(KeyInputEvent event) {
		if (calculatorKey.getIsKeyPressed()) { // handle the calculator client-side only
			FMLCommonHandler.instance().showGuiScreen(new GUICalculator());
		}
		
		HbmPlayerProps props = HbmPlayerProps.getData(MainRegistry.proxy.me());
		
		for(EnumKeybind key : EnumKeybind.values()) {
			boolean last = props.getKeyPressed(key);
			boolean current = MainRegistry.proxy.getIsKeyPressed(key);
			
			if(last != current) {
				PacketDispatcher.wrapper.sendToServer(new KeybindPacket(key, current));
				props.setKeyPressed(key, current);
			}
		}
	}

	public static enum EnumKeybind {
		JETPACK,
		TOGGLE_JETPACK,
		TOGGLE_HEAD,
		DASH,
		TRAIN,
		CRANE_UP,
		CRANE_DOWN,
		CRANE_LEFT,
		CRANE_RIGHT,
		CRANE_LOAD,
		TOOL_ALT,
		TOOL_CTRL,
		GUN_PRIMARY,
		GUN_SECONDARY,
		GUN_TERTIARY,
		RELOAD
	}
}
