package com.hbm.items.weapon.gununified;

import org.lwjgl.input.Keyboard;

import com.hbm.handler.HbmKeybinds;

import api.hbm.item.IButtonReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class GunRealoadable extends GunFrame implements IButtonReceiver {

	private IReloadBehavior reload;
	public static boolean lastReload = false;

	@Override
	@SideOnly(Side.CLIENT)
	public void handleKeyboardInput(ItemStack stack, EntityPlayer player) {
		
		boolean reload = Keyboard.isKeyDown(HbmKeybinds.reloadKey.getKeyCode());
		
		if(this.reload != null) {
			this.reload.tryStartReload(stack, player);
		}
		
		lastReload = reload;
	}
}
