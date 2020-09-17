package com.hbm.items.special;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemAMSCore extends Item {

	long powerBase;
	int heatBase;
	int fuelBase;
	
	public ItemAMSCore(long powerBase, int heatBase, int fuelBase) {
		this.powerBase = powerBase;
		this.heatBase = heatBase;
		this.fuelBase = fuelBase;
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

		if (this == ModItems.ams_core_sing) {
			list.add("A modified undefined state of spacetime");
			list.add("used to aid in inter-gluon fusion and");
			list.add("spacetime annihilation. Yes, this destroys");
			list.add("the universe itself, slowly but steadily,");
			list.add("but at least you can power your toaster with");
			list.add("this, so it's all good.");
		}

		if (this == ModItems.ams_core_wormhole) {
			list.add("A cloud of billions of nano-wormholes which");
			list.add("deliberately fail at tunneling matter from");
			list.add("another dimension, rather it converts all");
			list.add("that matter into pure energy. That means");
			list.add("you're actively contributing to the destruction");
			list.add("of another dimension, sucking it dry like a");
			list.add("juicebox.");
			list.add("That dimension probably sucked, anyways. I");
			list.add("bet it was full of wasps or some crap, man,");
			list.add("I hate these things.");
		}

		if (this == ModItems.ams_core_eyeofharmony) {
			list.add("A star collapsing in on itself, mere nanoseconds");
			list.add("away from being turned into a black hole,");
			list.add("frozen in time. If I didn't know better I");
			list.add("would say this is some deep space magic");
			list.add("bullcrap some guy made up to sound intellectual.");
			list.add("Probably Steve from accounting. You still owe me");
			list.add("ten bucks.");
		}

		if (this == ModItems.ams_core_thingy) {
			if(MainRegistry.polaroidID == 11) {
				list.add("Yeah I'm not even gonna question that one.");
			} else {
				list.add("...");
				list.add("...");
				list.add("...am I even holding this right?");
				list.add("It's a small metal thing. I dunno where it's from");
				list.add("or what it does, maybe they found it on a");
				list.add("junkyard and sold it as some kind of antique");
				list.add("artifact. If it weren't for the fact that I can");
				list.add("actually stuff this into some great big laser");
				list.add("reactor thing, I'd probably bring it back to where");
				list.add("it belongs. In the trash.");
			}
		}
	}

    @Override
	public EnumRarity getRarity(ItemStack p_77613_1_) {

    	if(this == ModItems.ams_core_thingy)
    	{
    		return EnumRarity.epic;
    	}
    	
    	return EnumRarity.uncommon;
    }

    @Override
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack p_77636_1_)
    {
    	if(this == ModItems.ams_core_thingy && MainRegistry.polaroidID == 11)
    	{
    		return true;
    	}
    	
    	return false;
    }
    
    public static long getPowerBase(ItemStack stack) {
		if(stack == null || !(stack.getItem() instanceof ItemAMSCore))
			return 0;
		return ((ItemAMSCore)stack.getItem()).powerBase;
    }
    
    public static int getHeatBase(ItemStack stack) {
		if(stack == null || !(stack.getItem() instanceof ItemAMSCore))
			return 0;
		return ((ItemAMSCore)stack.getItem()).heatBase;
    }
    
    public static int getFuelBase(ItemStack stack) {
		if(stack == null || !(stack.getItem() instanceof ItemAMSCore))
			return 0;
		return ((ItemAMSCore)stack.getItem()).fuelBase;
    }
}
