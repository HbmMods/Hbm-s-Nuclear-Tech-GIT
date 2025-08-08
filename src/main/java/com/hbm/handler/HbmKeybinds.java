package com.hbm.handler;

import com.hbm.inventory.gui.GUICalculator;
import com.hbm.items.IKeybindReceiver;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;

import cpw.mods.fml.common.FMLCommonHandler;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.hbm.config.GeneralConfig;
import com.hbm.extprop.HbmPlayerProps;
import com.hbm.main.MainRegistry;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.KeybindPacket;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.common.gameevent.InputEvent.MouseInputEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class HbmKeybinds {

	public static final String category = "hbm.key";

	public static KeyBinding calculatorKey = new KeyBinding(category + ".calculator", Keyboard.KEY_N, category);
	public static KeyBinding jetpackKey = new KeyBinding(category + ".toggleBack", Keyboard.KEY_C, category);
	public static KeyBinding magnetKey = new KeyBinding(category + ".toggleMagnet", Keyboard.KEY_Z, category);
	public static KeyBinding hudKey = new KeyBinding(category + ".toggleHUD", Keyboard.KEY_V, category);
	public static KeyBinding dashKey = new KeyBinding(category + ".dash", Keyboard.KEY_LSHIFT, category);
	public static KeyBinding trainKey = new KeyBinding(category + ".trainInv", Keyboard.KEY_R, category);

	public static KeyBinding qmaw = new KeyBinding(category + ".qmaw", Keyboard.KEY_F1, category);
	
	public static KeyBinding abilityCycle = new KeyBinding(category + ".ability", -99, category);
	public static KeyBinding abilityAlt = new KeyBinding(category + ".abilityAlt", Keyboard.KEY_LMENU, category);
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
		ClientRegistry.registerKeyBinding(magnetKey);
		ClientRegistry.registerKeyBinding(hudKey);
		ClientRegistry.registerKeyBinding(dashKey);
		ClientRegistry.registerKeyBinding(trainKey);
		
		ClientRegistry.registerKeyBinding(qmaw);

		ClientRegistry.registerKeyBinding(reloadKey);
		ClientRegistry.registerKeyBinding(gunPrimaryKey);
		ClientRegistry.registerKeyBinding(gunSecondaryKey);
		ClientRegistry.registerKeyBinding(gunTertiaryKey);

		ClientRegistry.registerKeyBinding(craneUpKey);
		ClientRegistry.registerKeyBinding(craneDownKey);
		ClientRegistry.registerKeyBinding(craneLeftKey);
		ClientRegistry.registerKeyBinding(craneRightKey);
		ClientRegistry.registerKeyBinding(craneLoadKey);
		ClientRegistry.registerKeyBinding(abilityCycle);
		ClientRegistry.registerKeyBinding(abilityAlt);
		ClientRegistry.registerKeyBinding(copyToolAlt);
		ClientRegistry.registerKeyBinding(copyToolCtrl);
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.LOW)
	public void mouseEvent(MouseInputEvent event) {

		/// OVERLAP HANDLING ///
		handleOverlap(Mouse.getEventButtonState(), Mouse.getEventButton() - 100);
		
		/// KEYBIND PROPS ///
		handleProps(Mouse.getEventButtonState(), Mouse.getEventButton() - 100);
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.LOW)
	public void keyEvent(KeyInputEvent event) {

		/// OVERLAP HANDLING ///
		handleOverlap(Keyboard.getEventKeyState(), Keyboard.getEventKey());
		
		/// KEYBIND PROPS ///
		handleProps(Keyboard.getEventKeyState(), Keyboard.getEventKey());
		
		/// CALCULATOR ///
		if(calculatorKey.getIsKeyPressed()) {
			MainRegistry.proxy.me().closeScreen();
			FMLCommonHandler.instance().showGuiScreen(new GUICalculator());
		}
	}
	
	/**
	 * Shitty hack: Keybinds fire before minecraft checks right click on block, which means the tool cycle keybind would fire too.
	 * If cycle collides with right click and a block is being used, cancel the keybind.
	 * @param event
	 */
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void postClientTick(ClientTickEvent event) {
		if(event.phase != event.phase.END) return;
		EntityPlayer player = MainRegistry.proxy.me();
		if(player == null) return;
		if(player.worldObj == null) return;
		
		HbmPlayerProps props = HbmPlayerProps.getData(player);
		
		// in theory, this should do the same keybind crap as the main one, but at the end of the client tick, fixing the issue
		// of detecting when a block is being interacted with
		// in practice, this shit doesn't fucking work. detection fails when the click is sub one tick long
		if(Minecraft.getMinecraft().gameSettings.keyBindUseItem.getKeyCode() == abilityCycle.getKeyCode()) {
			boolean last = props.getKeyPressed(EnumKeybind.ABILITY_CYCLE);
			boolean current = abilityCycle.pressed;
			
			if(last != current) {
				PacketDispatcher.wrapper.sendToServer(new KeybindPacket(EnumKeybind.ABILITY_CYCLE, current));
				props.setKeyPressed(EnumKeybind.ABILITY_CYCLE, current);
				onPressedClient(player, EnumKeybind.ABILITY_CYCLE, current);
			}
		}
	}
	
	/** Handles keybind overlap. Make sure this runs first before referencing the keybinds set by the extprops */
	public static void handleOverlap(boolean state, int keyCode) {
		Minecraft mc = Minecraft.getMinecraft();
		if(GeneralConfig.enableKeybindOverlap && (mc.currentScreen == null || mc.currentScreen.allowUserInput)) {

			//if anything errors here, run ./gradlew clean setupDecompWorkSpace
			for(Object o : KeyBinding.keybindArray) {
				KeyBinding key = (KeyBinding) o;

				if(keyCode != 0 && key.getKeyCode() == keyCode && KeyBinding.hash.lookup(key.getKeyCode()) != key) {

					key.pressed = state;
					if(state && key.pressTime == 0) {
						key.pressTime = 1;
					}
				}
			}

			/// GUN HANDLING ///
			boolean gunKey = keyCode == HbmKeybinds.gunPrimaryKey.getKeyCode() || keyCode == HbmKeybinds.gunSecondaryKey.getKeyCode() ||
					keyCode == HbmKeybinds.gunTertiaryKey.getKeyCode() || keyCode == HbmKeybinds.reloadKey.getKeyCode();

			EntityPlayer player = mc.thePlayer;

			if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemGunBaseNT) {

				/* Shoot in favor of attacking */
				if(gunKey && keyCode == mc.gameSettings.keyBindAttack.getKeyCode()) {
					mc.gameSettings.keyBindAttack.pressed = false;
					mc.gameSettings.keyBindAttack.pressTime = 0;
				}

				/* Shoot in favor of interacting */
				/*if(gunKey && keyCode == mc.gameSettings.keyBindUseItem.getKeyCode()) {
					mc.gameSettings.keyBindUseItem.pressed = false;
					mc.gameSettings.keyBindUseItem.pressTime = 0;
				}*/

				/* Scope in favor of picking */
				if(gunKey && keyCode == mc.gameSettings.keyBindPickBlock.getKeyCode()) {
					mc.gameSettings.keyBindPickBlock.pressed = false;
					mc.gameSettings.keyBindPickBlock.pressTime = 0;
				}
			}
		}
	}
	
	public static void handleProps(boolean state, int keyCode) {

		/// KEYBIND PROPS ///
		EntityPlayer player = MainRegistry.proxy.me();
		HbmPlayerProps props = HbmPlayerProps.getData(player);
		
		for(EnumKeybind key : EnumKeybind.values()) {
			boolean last = props.getKeyPressed(key);
			boolean current = MainRegistry.proxy.getIsKeyPressed(key);
			
			if(last != current) {
				
				/// ABILITY HANDLING ///
				if(key == EnumKeybind.ABILITY_CYCLE && Minecraft.getMinecraft().gameSettings.keyBindUseItem.getKeyCode() == abilityCycle.getKeyCode()) continue;
				
				props.setKeyPressed(key, current);
				PacketDispatcher.wrapper.sendToServer(new KeybindPacket(key, current));
				onPressedClient(player, key, current);
			}
		}
	}
	
	public static void onPressedClient(EntityPlayer player, EnumKeybind key, boolean state) {
		// ITEM HANDLING
		ItemStack held = player.getHeldItem();
		if(held != null && held.getItem() instanceof IKeybindReceiver) {
			IKeybindReceiver rec = (IKeybindReceiver) held.getItem();
			if(rec.canHandleKeybind(player, held, key)) rec.handleKeybindClient(player, held, key, state);
		}
	}

	public static enum EnumKeybind {
		JETPACK,
		TOGGLE_JETPACK,
		TOGGLE_MAGNET,
		TOGGLE_HEAD,
		DASH,
		TRAIN,
		CRANE_UP,
		CRANE_DOWN,
		CRANE_LEFT,
		CRANE_RIGHT,
		CRANE_LOAD,
		ABILITY_CYCLE,
		ABILITY_ALT,
		TOOL_ALT,
		TOOL_CTRL,
		GUN_PRIMARY,
		GUN_SECONDARY,
		GUN_TERTIARY,
		RELOAD
	}
}
