package com.hbm.sound;

import com.hbm.items.ModItems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class MovingSoundXVL1456 extends MovingSoundPlayerLoop {

	public MovingSoundXVL1456(ResourceLocation p_i45104_1_, Entity player, EnumHbmSound type) {
		super(p_i45104_1_, player, type);
		this.setPitch(0.5F);
	}

	@Override
	public void update() {
		super.update();
		
		ItemStack i = null;
		
		if(this.player != null) {
			i = ((EntityPlayer)this.player).getItemInUse();
		}
		
		//this.setPitch(this.getPitch() + 0.1F);
		
		if(i == null || (i != null && i.getItem() != ModItems.gun_xvl1456) || !((EntityPlayer)this.player).isSneaking() || ((EntityPlayer)this.player).getItemInUseDuration() <= 0)
			this.stop();
	}
}