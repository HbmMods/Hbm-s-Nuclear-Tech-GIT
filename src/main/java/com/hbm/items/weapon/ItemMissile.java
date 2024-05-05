package com.hbm.items.weapon;

import java.util.List;

import com.hbm.items.ItemCustomLore;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemMissile extends ItemCustomLore {
	
	public final MissileFormFactor formFactor;
	public final MissileTier tier;
	public final MissileFuel fuel;
	public int fuelCap;
	public boolean launchable = true;
	
	public ItemMissile(MissileFormFactor form, MissileTier tier) {
		this(form, tier, form.defaultFuel);
	}
	
	public ItemMissile(MissileFormFactor form, MissileTier tier, MissileFuel fuel) {
		this.formFactor = form;
		this.tier = tier;
		this.fuel = fuel;
		this.setFuelCap(this.fuel.defaultCap);
	}
	
	public ItemMissile notLaunchable() {
		this.launchable = false;
		return this;
	}
	
	public ItemMissile setFuelCap(int fuelCap) {
		this.fuelCap = fuelCap;
		return this;
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		list.add(EnumChatFormatting.ITALIC + this.tier.display);
		
		if(!this.launchable) {
			list.add(EnumChatFormatting.RED + "Not launchable!");
		} else {
			list.add("Fuel: " + this.fuel.display);
			if(this.fuelCap > 0) list.add("Fuel capacity: " + this.fuelCap + "mB");
			super.addInformation(itemstack, player, list, bool);
		}
	}
	
	public enum MissileFormFactor {
		ABM(MissileFuel.SOLID),
		MICRO(MissileFuel.SOLID),
		V2(MissileFuel.ETHANOL_PEROXIDE),
		STRONG(MissileFuel.KEROSENE_PEROXIDE),
		HUGE(MissileFuel.KEROSENE_LOXY),
		ATLAS(MissileFuel.JETFUEL_LOXY),
		OTHER(MissileFuel.KEROSENE_PEROXIDE);
		
		protected MissileFuel defaultFuel;
		
		private MissileFormFactor(MissileFuel defaultFuel) {
			this.defaultFuel = defaultFuel;
		}
	}
	
	public enum MissileTier {
		TIER0("Tier 0"),
		TIER1("Tier 1"),
		TIER2("Tier 2"),
		TIER3("Tier 3"),
		TIER4("Tier 4");
		
		public String display;
		
		private MissileTier(String display) {
			this.display = display;
		}
	}
	
	public enum MissileFuel {
		SOLID(EnumChatFormatting.GOLD + "Solid Fuel (pre-fueled)", 0),
		ETHANOL_PEROXIDE(EnumChatFormatting.AQUA + "Ethanol / Hydrogen Peroxide", 4_000),
		KEROSENE_PEROXIDE(EnumChatFormatting.BLUE + "Kerosene / Hydrogen Peroxide", 8_000),
		KEROSENE_LOXY(EnumChatFormatting.LIGHT_PURPLE + "Kerosene / Liquid Oxygen", 12_000),
		JETFUEL_LOXY(EnumChatFormatting.RED + "Jet Fuel / Liquid Oxygen", 16_000);
		
		public String display;
		public int defaultCap;
		
		private MissileFuel(String display, int defaultCap) {
			this.display = display;
			this.defaultCap = defaultCap;
		}
	}
}
