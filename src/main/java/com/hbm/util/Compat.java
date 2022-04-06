package com.hbm.util;

import java.util.ArrayList;
import java.util.List;

import com.hbm.hazard.HazardRegistry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public class Compat {
	
	public static final String MOD_GT6 = "gregtech";
	public static final String MOD_GCC = "GalacticraftCore";
	public static final String MOD_AR = "advancedrocketry";
	public static final String MOD_EF = "etfuturum";
	public static final String MOD_REC = "ReactorCraft";

	public static Item tryLoadItem(String domain, String name) {
		String reg = domain + ":" + name;
		return (Item) Item.itemRegistry.getObject(reg);
	}
	
	public static enum ReikaIsotope {
		C14(HazardRegistry.gen_10K),
		U235(HazardRegistry.u235),
		U238(HazardRegistry.u238),
		Pu239(HazardRegistry.pu239),
		Pu244(HazardRegistry.gen_100M),
		Th232(HazardRegistry.th232),
		Rn222(HazardRegistry.gen_10D),
		Ra226(HazardRegistry.ra226),
		Sr90(HazardRegistry.gen_10Y),
		Po210(HazardRegistry.po210),
		Cs134(HazardRegistry.gen_1Y),
		Xe135(HazardRegistry.xe135),
		Zr93(HazardRegistry.gen_1M),
		Mo99(HazardRegistry.gen_10D),
		Cs137(HazardRegistry.cs137),
		Tc99(HazardRegistry.tc99),
		I131(HazardRegistry.i131),
		Pm147(HazardRegistry.gen_1Y),
		I129(HazardRegistry.gen_10M),
		Sm151(HazardRegistry.gen_100Y),
		Ru106(HazardRegistry.gen_1Y),
		Kr85(HazardRegistry.gen_10Y),
		Pd107(HazardRegistry.gen_10M),
		Se79(HazardRegistry.gen_100K),
		Gd155(HazardRegistry.gen_1Y),
		Sb125(HazardRegistry.gen_1Y),
		Sn126(HazardRegistry.gen_100K),
		Xe136(0),
		I135(HazardRegistry.gen_H),
		Xe131(HazardRegistry.gen_10D),
		Ru103(HazardRegistry.gen_S),
		Pm149(HazardRegistry.gen_10D),
		Rh105(HazardRegistry.gen_H);
		
		private float rads;
		
		private ReikaIsotope(float rads) {
			this.rads = rads;
		}
		
		public float getRad() {
			return this.rads;
		}
	}
	
	public static List<ItemStack> scrapeItemFromME(ItemStack meDrive) {
		List<ItemStack> stacks = new ArrayList();
		
		if(meDrive != null && meDrive.hasTagCompound()) {
			NBTTagCompound nbt = meDrive.getTagCompound();
			int types = nbt.getShort("it"); //ITEM_TYPE_TAG
			
			for(int i = 0; i < types; i++) {
				NBTBase stackTag = nbt.getTag("#" + i);
				
				if(stackTag instanceof NBTTagCompound) {
					NBTTagCompound compound = (NBTTagCompound) stackTag;
					ItemStack stack = ItemStack.loadItemStackFromNBT(compound);
					
					int count = nbt.getInteger("@" + i);
					stack.stackSize = count;
					
					stacks.add(stack);
				}
			}
		}
		
		return stacks;
	}
}
