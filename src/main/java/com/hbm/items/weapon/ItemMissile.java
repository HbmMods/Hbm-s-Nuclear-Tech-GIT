package com.hbm.items.weapon;

import java.util.List;

import com.hbm.items.ItemCustomLore;
import com.hbm.util.i18n.I18nUtil;

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
		// Tier localized: missile.tier.tier0, missile.tier.tier1, ...
		String tierKey = "item.missile.tier." + this.tier.name().toLowerCase();
		list.add(EnumChatFormatting.ITALIC + I18nUtil.resolveKey(tierKey));

		if(!this.launchable) {
			list.add(EnumChatFormatting.RED + I18nUtil.resolveKey("item.missile.desc.notLaunchable"));
		} else {
			// Fuel localized & colored via enum helper
			list.add(I18nUtil.resolveKey("item.missile.desc.fuel") + ": " + this.fuel.getDisplay());
			if(this.fuelCap > 0) list.add(I18nUtil.resolveKey("item.missile.desc.fuelCapacity") + ": " + this.fuelCap + "mB");
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
		SOLID("item.missile.fuel.solid.prefueled", EnumChatFormatting.GOLD, 0),
		ETHANOL_PEROXIDE("item.missile.fuel.ethanol_peroxide", EnumChatFormatting.AQUA, 4_000),
		KEROSENE_PEROXIDE("item.missile.fuel.kerosene_peroxide", EnumChatFormatting.BLUE, 8_000),
		KEROSENE_LOXY("item.missile.fuel.kerosene_loxy", EnumChatFormatting.LIGHT_PURPLE, 12_000),
		JETFUEL_LOXY("item.missile.fuel.jetfuel_loxy", EnumChatFormatting.RED, 16_000);

		private final String key;
		public final EnumChatFormatting color;
		public final int defaultCap;

		private MissileFuel(String key, EnumChatFormatting color, int defaultCap) {
			this.key = key;
			this.color = color;
			this.defaultCap = defaultCap;
		}

		/** Returns a color localized string for display */
		public String getDisplay() {
			return color + I18nUtil.resolveKey(this.key);
		}
	}
}
