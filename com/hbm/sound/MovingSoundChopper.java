package com.hbm.sound;

import com.hbm.entity.mob.EntityHunterChopper;
import com.hbm.items.ModItems;
import com.hbm.sound.MovingSoundPlayerLoop.EnumHbmSound;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class MovingSoundChopper extends MovingSoundPlayerLoop {

	public MovingSoundChopper(ResourceLocation p_i45104_1_, Entity player, EnumHbmSound type) {
		super(p_i45104_1_, player, type);
	}

	@Override
	public void update() {
		super.update();
		
		if(player instanceof EntityHunterChopper && ((EntityHunterChopper)player).getIsDying())
			this.stop();
	}
}
