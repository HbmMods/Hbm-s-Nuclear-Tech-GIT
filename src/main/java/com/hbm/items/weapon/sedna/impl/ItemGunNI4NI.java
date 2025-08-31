package com.hbm.items.weapon.sedna.impl;

import java.util.List;

import com.hbm.items.ICustomizable;
import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.mods.WeaponModManager;
import com.hbm.util.ChatBuilder;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemGunNI4NI extends ItemGunBaseNT implements ICustomizable {

	public ItemGunNI4NI(WeaponQuality quality, GunConfig... cfg) {
		super(quality, cfg);
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isHeld) {
		super.onUpdate(stack, world, entity, slot, isHeld);
		
		if(!world.isRemote) {
			
			int maxCoin = 4;
			if(WeaponModManager.hasUpgrade(stack, 0, WeaponModManager.ID_NI4NI_NICKEL)) maxCoin += 2;
			if(WeaponModManager.hasUpgrade(stack, 0, WeaponModManager.ID_NI4NI_DOUBLOONS)) maxCoin += 2;
			
			if(this.getCoinCount(stack) < maxCoin) {
				this.setCoinCharge(stack, this.getCoinCharge(stack) + 1);
				
				if(this.getCoinCharge(stack) >= 80) {
					this.setCoinCharge(stack, 0);
					int newCount = this.getCoinCount(stack) + 1;
					this.setCoinCount(stack, newCount);
					
					if(isHeld) {
						world.playSoundAtEntity(entity, "hbm:item.techBoop", 1.0F, 1F + newCount / (float) maxCoin);
					}
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		list.add("Now, don't get the wrong idea.");
		list.add("I " + EnumChatFormatting.RED + "fucking hate " + EnumChatFormatting.GRAY + "this game.");
		list.add("I didn't do this for you, I did it for sea.");
		super.addInformation(stack, player, list, ext);
	}

	@Override
	public void customize(EntityPlayer player, ItemStack stack, String... args) {
		
		if(args.length == 0) {
			resetColors(stack);
			player.addChatComponentMessage(ChatBuilder.start("Colors reset!").color(EnumChatFormatting.GREEN).flush());
			return;
		}
		
		if(args.length != 3) {
			resetColors(stack);
			player.addChatComponentMessage(ChatBuilder.start("Requires three hexadecimal colors!").color(EnumChatFormatting.RED).flush());
			return;
		}
		
		try {
			int dark = Integer.parseInt(args[0], 16);
			int light = Integer.parseInt(args[1], 16);
			int grip = Integer.parseInt(args[2], 16);
			
			if(dark < 0 || dark > 0xffffff || light < 0 || light > 0xffffff || grip < 0 || grip > 0xffffff) {
				player.addChatComponentMessage(ChatBuilder.start("Colors must range from 0 to FFFFFF!").color(EnumChatFormatting.RED).flush());
				return;
			}
			
			setColors(stack, dark, light, grip);
			player.addChatComponentMessage(ChatBuilder.start("Colors set!").color(EnumChatFormatting.GREEN).flush());
			
		} catch(Throwable ex) {
			player.addChatComponentMessage(ChatBuilder.start(ex.getLocalizedMessage()).color(EnumChatFormatting.RED).flush());
		}
	}
	
	public static void resetColors(ItemStack stack) {
		if(!stack.hasTagCompound()) return;
		stack.stackTagCompound.removeTag("colors");
	}
	
	public static void setColors(ItemStack stack, int dark, int light, int grip) {
		if(!stack.hasTagCompound()) stack.stackTagCompound = new NBTTagCompound();
		stack.stackTagCompound.setIntArray("colors", new int[] {dark, light, grip});
	}
	
	public static int[] getColors(ItemStack stack) {
		if(!stack.hasTagCompound() || !stack.stackTagCompound.hasKey("colors")) return null;
		int[] colors = stack.stackTagCompound.getIntArray("colors");
		if(colors.length != 3) return null;
		return colors;
	}
	
	public static final String KEY_COIN_COUNT = "coincount";
	public static final String KEY_COIN_CHARGE = "coincharge";
	public static int getCoinCount(ItemStack stack) { return getValueInt(stack, KEY_COIN_COUNT); }
	public static void setCoinCount(ItemStack stack, int value) { setValueInt(stack, KEY_COIN_COUNT, value); }
	public static int getCoinCharge(ItemStack stack) { return getValueInt(stack, KEY_COIN_CHARGE); }
	public static void setCoinCharge(ItemStack stack, int value) { setValueInt(stack, KEY_COIN_CHARGE, value); }
}
