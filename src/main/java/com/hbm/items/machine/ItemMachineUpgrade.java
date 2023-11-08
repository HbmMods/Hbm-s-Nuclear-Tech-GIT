package com.hbm.items.machine;

import java.util.List;

import com.hbm.items.ModItems;

import com.hbm.util.I18nUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemMachineUpgrade extends Item {
	
	public UpgradeType type;
	public int tier = 0;
	
	public ItemMachineUpgrade() {
		this.setMaxStackSize(1);
		this.type = UpgradeType.SPECIAL;
	}
	
	public ItemMachineUpgrade(UpgradeType type) {
		this.setMaxStackSize(1);
		this.type = type;
	}
	
	public ItemMachineUpgrade(UpgradeType type, int tier) {
		this(type);
		this.tier = tier;
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		
		if(this.type == UpgradeType.SPEED) {
			list.add(EnumChatFormatting.RED + I18nUtil.resolveKeyArray("desc.item.upgrade.machine")[0]);
			list.add(I18nUtil.resolveKeyArray("desc.item.upgrade.speed",(15 * this.tier),(300 * this.tier),"","","","","","")[0]);
			list.add(EnumChatFormatting.RED + I18nUtil.resolveKeyArray("desc.item.upgrade.machine")[1]);
			list.add(I18nUtil.resolveKeyArray("desc.item.upgrade.speed","","",(1 + this.tier),(625 * this.tier),"","","","")[1]);
			list.add(EnumChatFormatting.RED + I18nUtil.resolveKeyArray("desc.item.upgrade.machine")[2]);
			list.add(I18nUtil.resolveKeyArray("desc.item.upgrade.speed",(25 * this.tier),(50 * this.tier),"","","","","","")[0]);
			list.add(EnumChatFormatting.RED + I18nUtil.resolveKeyArray("desc.item.upgrade.machine")[3]);
			list.add(I18nUtil.resolveKeyArray("desc.item.upgrade.speed",(25 * this.tier),(300 * this.tier),"","","","","","")[0]);
			list.add(EnumChatFormatting.RED + I18nUtil.resolveKeyArray("desc.item.upgrade.machine")[4]);
			list.add(I18nUtil.resolveKeyArray("desc.item.upgrade.speed",(25 * this.tier),(300 * this.tier),"","","","","","")[0]);
			list.add(EnumChatFormatting.RED + I18nUtil.resolveKeyArray("desc.item.upgrade.machine")[5]);
			list.add(I18nUtil.resolveKeyArray("desc.item.upgrade.speed","","","","",(25 * this.tier),(25 * this.tier),"","")[2]);
			list.add(EnumChatFormatting.RED + I18nUtil.resolveKeyArray("desc.item.upgrade.machine")[6]);
			list.add(I18nUtil.resolveKeyArray("desc.item.upgrade.speed",(20 * this.tier),(1000 * this.tier),"","","","","","")[0]);
			list.add(EnumChatFormatting.RED + I18nUtil.resolveKeyArray("desc.item.upgrade.machine")[7]);
			list.add(I18nUtil.resolveKeyArray("desc.item.upgrade.speed","","","","","","",(1 + this.tier),"")[3]);
			list.add(EnumChatFormatting.RED + I18nUtil.resolveKeyArray("desc.item.upgrade.machine")[8]);
			list.add(I18nUtil.resolveKeyArray("desc.item.upgrade.speed","","","","","","",(1 + this.tier),"")[3]);
			list.add(EnumChatFormatting.RED + I18nUtil.resolveKeyArray("desc.item.upgrade.machine")[9]);
			list.add(I18nUtil.resolveKeyArray("desc.item.upgrade.speed","","","","","","","",(0.25 * (double)this.tier))[4]);
		}
		
		if(this.type == UpgradeType.EFFECT) {
			list.add(EnumChatFormatting.RED + I18nUtil.resolveKeyArray("desc.item.upgrade.machine")[0]);
			list.add(I18nUtil.resolveKeyArray("desc.item.upgrade.effect",this.tier,(80 * this.tier),"","","","","")[0]);
			list.add(EnumChatFormatting.RED + I18nUtil.resolveKeyArray("desc.item.upgrade.machine")[6]);
			list.add(I18nUtil.resolveKeyArray("desc.item.upgrade.effect","","",(5 * this.tier),(1000 * this.tier),"","","")[1]);
			list.add(EnumChatFormatting.RED + I18nUtil.resolveKeyArray("desc.item.upgrade.machine")[7]);
			list.add(I18nUtil.resolveKeyArray("desc.item.upgrade.effect","","","","",(100 - 100 / (this.tier + 1)),"","")[2]);
			list.add(EnumChatFormatting.RED + I18nUtil.resolveKeyArray("desc.item.upgrade.machine")[8]);
			list.add(I18nUtil.resolveKeyArray("desc.item.upgrade.effect","","","","","",(100 * this.tier / 3),"")[3]);
			list.add(EnumChatFormatting.RED + I18nUtil.resolveKeyArray("desc.item.upgrade.machine")[9]);
			list.add(I18nUtil.resolveKeyArray("desc.item.upgrade.effect","","","","","","",(3 * this.tier))[4]);
		}
		
		if(this.type == UpgradeType.POWER) {
			list.add(EnumChatFormatting.RED + I18nUtil.resolveKeyArray("desc.item.upgrade.machine")[0]);
			list.add(I18nUtil.resolveKeyArray("desc.item.upgrade.power",(30 * this.tier),(5 * this.tier),"","","","","")[0]);
			list.add(EnumChatFormatting.RED + I18nUtil.resolveKeyArray("desc.item.upgrade.machine")[2]);
			list.add(I18nUtil.resolveKeyArray("desc.item.upgrade.power",(15 * this.tier),(10 * this.tier),"","","","","")[0]);
			list.add(EnumChatFormatting.RED + I18nUtil.resolveKeyArray("desc.item.upgrade.machine")[3]);
			list.add(I18nUtil.resolveKeyArray("desc.item.upgrade.power",(30 * this.tier),(5 * this.tier),"","","","","")[0]);
			list.add(EnumChatFormatting.RED + I18nUtil.resolveKeyArray("desc.item.upgrade.machine")[4]);
			list.add(I18nUtil.resolveKeyArray("desc.item.upgrade.power",(30 * this.tier),(5 * this.tier),"","","","","")[0]);
			list.add(EnumChatFormatting.RED + I18nUtil.resolveKeyArray("desc.item.upgrade.machine")[5]);
			list.add(I18nUtil.resolveKeyArray("desc.item.upgrade.power","","",(25 * this.tier),(10 * this.tier),"","","")[1]);
			list.add(EnumChatFormatting.RED + I18nUtil.resolveKeyArray("desc.item.upgrade.machine")[7]);
			list.add(I18nUtil.resolveKeyArray("desc.item.upgrade.power","","","","",(100 * this.tier),"","")[2]);
			list.add(EnumChatFormatting.RED + I18nUtil.resolveKeyArray("desc.item.upgrade.machine")[9]);
			list.add(I18nUtil.resolveKeyArray("desc.item.upgrade.power","","","","","",(150 * this.tier),"")[3]);
			list.add(I18nUtil.resolveKeyArray("desc.item.upgrade.power","","","","","","",(1500 * this.tier))[4]);
		}
		
		if(this == ModItems.upgrade_fortune_1) {
			list.add(EnumChatFormatting.RED + I18nUtil.resolveKeyArray("desc.item.upgrade.machine")[0]);
			list.add(I18nUtil.resolveKey("desc.item.upgrade.fortune","1","15"));
		}
		
		if(this == ModItems.upgrade_fortune_2) {
			list.add(EnumChatFormatting.RED + I18nUtil.resolveKeyArray("desc.item.upgrade.machine")[0]);
			list.add(I18nUtil.resolveKey("desc.item.upgrade.fortune","2","30"));
		}
		
		if(this == ModItems.upgrade_fortune_3) {
			list.add(EnumChatFormatting.RED + I18nUtil.resolveKeyArray("desc.item.upgrade.machine")[0]);
			list.add(I18nUtil.resolveKey("desc.item.upgrade.fortune","3","45"));
		}
		
		if(this.type == UpgradeType.AFTERBURN) {
			list.add(EnumChatFormatting.RED + I18nUtil.resolveKeyArray("desc.item.upgrade.machine")[10]);
			list.add(I18nUtil.resolveKeyArray("desc.item.upgrade.afterburn",(this.tier + 1),(this.tier + 2),"","","")[0]);
			list.add(EnumChatFormatting.RED + I18nUtil.resolveKeyArray("desc.item.upgrade.machine")[9]);
			list.add(I18nUtil.resolveKeyArray("desc.item.upgrade.afterburn","","",(this.tier * 3),"","")[1]);
			list.add(EnumChatFormatting.RED + I18nUtil.resolveKeyArray("desc.item.upgrade.machine")[5]);
			list.add(I18nUtil.resolveKeyArray("desc.item.upgrade.afterburn","","","",(this.tier * 10),(this.tier * 50))[2]);
		}
		
		if(this == ModItems.upgrade_radius) {
			list.add(EnumChatFormatting.RED + I18nUtil.resolveKeyArray("desc.item.upgrade.machine")[11]);
			list.add(I18nUtil.resolveKeyArray("desc.item.upgrade.forcefield","16","500")[0]);
			list.add(I18nUtil.resolveKeyArray("desc.item.upgrade.forcefield")[2]);
		}
		
		if(this == ModItems.upgrade_health) {
			list.add(EnumChatFormatting.RED + I18nUtil.resolveKeyArray("desc.item.upgrade.machine")[12]);
			list.add(I18nUtil.resolveKeyArray("desc.item.upgrade.forcefield","50","250")[1]);
			list.add(I18nUtil.resolveKeyArray("desc.item.upgrade.forcefield")[2]);
		}
		
		if(this == ModItems.upgrade_smelter) {
			list.add(EnumChatFormatting.RED + I18nUtil.resolveKeyArray("desc.item.upgrade.machine")[13]);
			list.add(I18nUtil.resolveKey("desc.item.upgrade.smelter"));
		}
		
		if(this == ModItems.upgrade_shredder) {
			list.add(EnumChatFormatting.RED + I18nUtil.resolveKeyArray("desc.item.upgrade.machine")[13]);
			list.add(I18nUtil.resolveKey("desc.item.upgrade.shredder"));
		}
		
		if(this == ModItems.upgrade_centrifuge) {
			list.add(EnumChatFormatting.RED + I18nUtil.resolveKeyArray("desc.item.upgrade.machine")[13]);
			list.add(I18nUtil.resolveKey("desc.item.upgrade.centrifuge"));
		}
		
		if(this == ModItems.upgrade_crystallizer) {
			list.add(EnumChatFormatting.RED + I18nUtil.resolveKeyArray("desc.item.upgrade.machine")[13]);
			list.add(I18nUtil.resolveKey("desc.item.upgrade.crystallizer"));
		}
		
		if(this == ModItems.upgrade_screm) {
			list.add(EnumChatFormatting.RED + I18nUtil.resolveKeyArray("desc.item.upgrade.machine")[13]);
			for(String s : I18nUtil.resolveKeyArray("desc.item.upgrade.screm"))
				list.add(s);
		}
		
		if(this == ModItems.upgrade_nullifier) {
			list.add(EnumChatFormatting.RED + I18nUtil.resolveKeyArray("desc.item.upgrade.machine")[13]);
			for(String s : I18nUtil.resolveKeyArray("desc.item.upgrade.nullifier"))
				list.add(s);
		}
		
		if(this == ModItems.upgrade_gc_speed) {
			list.add(EnumChatFormatting.RED + I18nUtil.resolveKeyArray("desc.item.upgrade.machine")[14]);
			for(String s : I18nUtil.resolveKeyArray("desc.item.upgrade.gc_speed"))
				list.add(s);
		}
	}
	
	public static enum UpgradeType {
		SPEED,
		EFFECT,
		POWER,
		FORTUNE,
		AFTERBURN,
		OVERDRIVE,
		SPECIAL,
		LM_DESROYER,
		LM_SCREM,
		LM_SMELTER(true),
		LM_SHREDDER(true),
		LM_CENTRIFUGE(true),
		LM_CRYSTALLIZER(true),
		GS_SPEED;
		
		public boolean mutex = false;
		
		private UpgradeType() { }
		
		private UpgradeType(boolean mutex) {
			this.mutex = mutex;
		}
	}

}
