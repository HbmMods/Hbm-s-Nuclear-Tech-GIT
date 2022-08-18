package com.hbm.items.machine;

import java.util.List;

import com.hbm.items.ModItems;

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
			list.add(EnumChatFormatting.RED + "Mining Drill:");
			list.add("Delay -" + (15 * this.tier) + "% / Consumption +" + (300 * this.tier) + "HE/t");
			list.add(EnumChatFormatting.RED + "Laser Miner:");
			list.add("Delay ÷" + (1 + this.tier) + " / Consumption +" + (625 * this.tier) + "HE/t");
			list.add(EnumChatFormatting.RED + "Electric Furnace:");
			list.add("Delay -" + (25 * this.tier) + "% / Consumption +" + (50 * this.tier) + "HE/t");
			list.add(EnumChatFormatting.RED + "Assembly Machine:");
			list.add("Delay -" + (25 * this.tier) + "% / Consumption +" + (300 * this.tier) + "HE/t");
			list.add(EnumChatFormatting.RED + "Chemical Plant:");
			list.add("Delay -" + (25 * this.tier) + "% / Consumption +" + (300 * this.tier) + "HE/t");
			list.add(EnumChatFormatting.RED + "Oil Wells:");
			list.add("Delay -" + (25 * this.tier) + "% / Consumption +" + (25 * this.tier) + "%");
			list.add(EnumChatFormatting.RED + "Crystallizer:");
			list.add("Delay -" + (20 * this.tier) + "% / Consumption +" + (1000 * this.tier) + "HE/t");
			list.add(EnumChatFormatting.RED + "Cyclotron:");
			list.add("Speed x" + (1 + this.tier));
			list.add(EnumChatFormatting.RED + "Flare Stack:");
			list.add("Speed x" + (1 + this.tier));
			list.add(EnumChatFormatting.RED + "Maxwell:");
			list.add("Damage +" + (0.25 * (double)this.tier) + "dmg/t");
		}
		
		if(this.type == UpgradeType.EFFECT) {
			list.add(EnumChatFormatting.RED + "Mining Drill:");
			list.add("Radius +" + this.tier + "m / Consumption +" + (80 * this.tier) + "HE/t");
			list.add(EnumChatFormatting.RED + "Crystallizer:");
			list.add("+" + (5 * this.tier) + "% chance of not consuming an item / Acid consumption +" + (1000 * this.tier) + "mB");
			list.add(EnumChatFormatting.RED + "Cyclotron:");
			list.add("-" + (100 - 100 / (this.tier + 1)) + "% chance of incrementing overheat counter");
			list.add(EnumChatFormatting.RED + "Flare Stack:");
			list.add("+" + (100 * this.tier / 3) + "% power production");
			list.add(EnumChatFormatting.RED + "Maxwell:");
			list.add("Range +" + (3 * this.tier) + "m");
		}
		
		if(this.type == UpgradeType.POWER) {
			list.add(EnumChatFormatting.RED + "Mining Drill:");
			list.add("Consumption -" + (30 * this.tier) + "HE/t / Delay +" + (5 * this.tier) + "%");
			list.add(EnumChatFormatting.RED + "Electric Furnace:");
			list.add("Consumption -" + (15 * this.tier) + "HE/t / Delay +" + (10 * this.tier) + "%");
			list.add(EnumChatFormatting.RED + "Assembly Machine:");
			list.add("Consumption -" + (30 * this.tier) + "HE/t / Delay +" + (5 * this.tier) + "%");
			list.add(EnumChatFormatting.RED + "Chemical Plant:");
			list.add("Consumption -" + (30 * this.tier) + "HE/t / Delay +" + (5 * this.tier) + "%");
			list.add(EnumChatFormatting.RED + "Oil Wells:");
			list.add("Consumption -" + (25 * this.tier) + "% / Delay +" + (10 * this.tier) + "%");
			list.add(EnumChatFormatting.RED + "Cyclotron:");
			list.add("Consumption -" + (100 * this.tier) + "kHE/t");
			list.add(EnumChatFormatting.RED + "Maxwell:");
			list.add("Consumption -" + (150 * this.tier) + "HE/t");
			list.add("Consumption when firing -" + (1500 * this.tier) + "HE/t");
		}
		
		if(this == ModItems.upgrade_fortune_1) {
			list.add(EnumChatFormatting.RED + "Mining Drill:");
			list.add("Fortune +1 / Delay +15");
		}
		
		if(this == ModItems.upgrade_fortune_2) {
			list.add(EnumChatFormatting.RED + "Mining Drill:");
			list.add("Fortune +2 / Delay +30");
		}
		
		if(this == ModItems.upgrade_fortune_3) {
			list.add(EnumChatFormatting.RED + "Mining Drill:");
			list.add("Fortune +3 / Delay +45");
		}
		
		if(this.type == UpgradeType.AFTERBURN) {
			list.add(EnumChatFormatting.RED + "Turbofan:");
			list.add("Production x" + (this.tier + 1) + " / Consumption x" + (this.tier + 2));
			list.add(EnumChatFormatting.RED + "Maxwell:");
			list.add("Afterburn +" + (this.tier * 3) + "s");
			list.add(EnumChatFormatting.RED + "Oil Wells:");
			list.add("Burn " + (this.tier * 10) + "mB of gas for " + (this.tier * 50) + "HE/t");
		}
		
		if(this == ModItems.upgrade_radius) {
			list.add(EnumChatFormatting.RED + "Forcefield Range Upgrade");
			list.add("Radius +16 / Consumption +500");
			list.add("Stacks to 16");
		}
		
		if(this == ModItems.upgrade_health) {
			list.add(EnumChatFormatting.RED + "Forcefield Health Upgrade");
			list.add("Max. Health +50 / Consumption +250");
			list.add("Stacks to 16");
		}
		
		if(this == ModItems.upgrade_smelter) {
			list.add(EnumChatFormatting.RED + "Mining Laser Upgrade");
			list.add("Smelts blocks. Easy enough.");
		}
		
		if(this == ModItems.upgrade_shredder) {
			list.add(EnumChatFormatting.RED + "Mining Laser Upgrade");
			list.add("Crunches ores");
		}
		
		if(this == ModItems.upgrade_centrifuge) {
			list.add(EnumChatFormatting.RED + "Mining Laser Upgrade");
			list.add("Hopefully self-explanatory");
		}
		
		if(this == ModItems.upgrade_crystallizer) {
			list.add(EnumChatFormatting.RED + "Mining Laser Upgrade");
			list.add("Your new best friend");
		}
		
		if(this == ModItems.upgrade_screm) {
			list.add(EnumChatFormatting.RED + "Mining Laser Upgrade");
			list.add("It's like in Super Mario where all blocks are");
			list.add("actually Toads, but here it's Half-Life scientists");
			list.add("and they scream. A lot.");
		}
		
		if(this == ModItems.upgrade_nullifier) {
			list.add(EnumChatFormatting.RED + "Mining Laser Upgrade");
			list.add("50% chance to override worthless items with /dev/zero");
			list.add("50% chance to move worthless items to /dev/null");
		}
		
		if(this == ModItems.upgrade_gc_speed) {
			list.add(EnumChatFormatting.RED + "Gas Centrifuge Upgrade");
			list.add("Allows for total isotopic separation of HEUF6");
			list.add(EnumChatFormatting.YELLOW + "also your centrifuge goes sicko mode");
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
