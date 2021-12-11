package com.hbm.items.special;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemInstrument extends Item {
	
	public String sound;
	
	public ItemInstrument(String soundname) {
		
		this.setMaxStackSize(1);
		this.setCreativeTab(MainRegistry.controlTab);
		
		this.setSoundName(soundname);
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		if(this == ModItems.gabriel_horn) {
			if(MainRegistry.polaroidID == 11) {
				list.add("Blow on the end to bring forth Judgement Day.");
			} else {
				list.add("Blow on the end to make a funny noise.");
			}
			list.add("That is, if you can find the end.");
			list.add("Man, Gabriel must have a really small mouth.");
		}
	}
	
	public ItemInstrument setSoundName(String soundname) {
		this.sound = soundname;
		return this;
	}
	
	public String getSoundName() {
		return this.sound;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
	
		if(!player.isSneaking()) {
			
			world.playSoundAtEntity(player, sound, 1.0F, ((itemRand.nextInt(100)) / 100F) + 0.5F);
		}
		
		return stack;
	}
}