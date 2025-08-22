package com.hbm.items.weapon;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

import com.hbm.entity.missile.EntityMissileCustom;
import com.hbm.items.special.ItemLootCrate;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;

import com.hbm.util.i18n.I18nUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemCustomMissilePart extends Item {

	public PartType type;
	public PartSize top;
	public PartSize bottom;
	public Rarity rarity;
	public float health;
	private String title;
	private String author;
	private String witty;

	public ItemCustomMissilePart() {
		this.setMaxStackSize(1);
		this.setCreativeTab(MainRegistry.missileTab);
	}

	public static HashMap<Integer, ItemCustomMissilePart> parts = new HashMap();

	/**
	 * == Chips ==
	 * [0]: inaccuracy
	 *
	 * == Warheads ==
	 * [0]: type
	 * [1]: strength/radius/cluster count
	 * [2]: weight
	 *
	 * == Fuselages ==
	 * [0]: type
	 * [1]: tank size
	 *
	 * == Stability ==
	 * [0]: inaccuracy mod
	 *
	 * == Thrusters ===
	 * [0]: type
	 * [1]: consumption
	 * [2]: lift strength
	 */
	public Object[] attributes;

	public enum PartType {
		CHIP,
		WARHEAD,
		FUSELAGE,
		FINS,
		THRUSTER
	}

	public enum PartSize {

		//for chips
		ANY,
		//for missile tips and thrusters
		NONE,
		//regular sizes, 1.0m, 1.5m and 2.0m
		SIZE_10,
		SIZE_15,
		SIZE_20
	}

	public enum WarheadType {

		HE("item.custom_missile_part.he.type"),
		INC("item.custom_missile_part.inc.type"),
		BUSTER("item.custom_missile_part.buster.type"),
		CLUSTER("item.custom_missile_part.cluster.type"),
		NUCLEAR("item.custom_missile_part.nuclear.type"),
		TX("item.custom_missile_part.tx.type"),
		N2("item.custom_missile_part.n2.type"),
		BALEFIRE("item.custom_missile_part.balefire.type"),
		SCHRAB("item.custom_missile_part.schrab.type"),
		TAINT("item.custom_missile_part.taint.type"),
		CLOUD("item.custom_missile_part.cloud.type"),
		TURBINE("item.custom_missile_part.turbine.type"),

		//shit solution but it works. this allows traits to be attached to these empty dummy types, allowing for custom warheads
		CUSTOM0("item.custom_missile_part.custom0.type"),
		CUSTOM1("item.custom_missile_part.custom1.type"),
		CUSTOM2("item.custom_missile_part.custom2.type"),
		CUSTOM3("item.custom_missile_part.custom3.type"),
		CUSTOM4("item.custom_missile_part.custom4.type"),
		CUSTOM5("item.custom_missile_part.custom5.type"),
		CUSTOM6("item.custom_missile_part.custom6.type"),
		CUSTOM7("item.custom_missile_part.custom7.type"),
		CUSTOM8("item.custom_missile_part.custom8.type"),
		CUSTOM9("item.custom_missile_part.custom9.type");

		public final String unlocalizedName;
		WarheadType(String unlocalizedName){
			this.unlocalizedName = unlocalizedName;
		}

		/** Overrides that type's impact effect. Only runs serverside */
		public Consumer<EntityMissileCustom> impactCustom = null;
		/** Runs at the beginning of the missile's update cycle, both client and serverside. */
		public Consumer<EntityMissileCustom> updateCustom = null;
		/** Override for the warhead's name in the missile description */
		public String labelCustom = null;
	}

	public enum FuelType {

		KEROSENE("item.custom_missile_part.fuel.kerosene"),
		SOLID("item.custom_missile_part.fuel.solid"),
		HYDROGEN("item.custom_missile_part.fuel.hydrogen"),
		XENON("item.custom_missile_part.fuel.xenon"),
		BALEFIRE("item.custom_missile_part.fuel.balefire"),

		public final String unlocalizedName;

		FuelType(String unlocalizedName) {
			this.unlocalizedName = unlocalizedName;
		}
	}

	public enum Rarity {

		COMMON("item.custom_missile_part.rarity.common"),
		UNCOMMON("item.custom_missile_part.rarity.uncommon"),
		RARE("item.custom_missile_part.rarity.rare"),
		EPIC("item.custom_missile_part.rarity.epic"),
		LEGENDARY("item.custom_missile_part.rarity.legendary"),
		SEWS_CLOTHES_AND_SUCKS_HORSE_COCK("item.custom_missile_part.rarity.strange");

		final String unlocalizedName;

		Rarity(String unlocalizedName) {
			this.unlocalizedName = unlocalizedName;
		}
	}

	public ItemCustomMissilePart makeChip(float inaccuracy) {

		this.type = PartType.CHIP;
		this.top = PartSize.ANY;
		this.bottom = PartSize.ANY;
		this.attributes = new Object[] { inaccuracy };

		parts.put(this.hashCode(), this);

		return this;
	}

	public ItemCustomMissilePart makeWarhead(WarheadType type, float punch, float weight, PartSize size) {

		this.type = PartType.WARHEAD;
		this.top = PartSize.NONE;
		this.bottom = size;
		this.attributes = new Object[] { type, punch, weight };
		setTextureName(RefStrings.MODID + ":mp_warhead");

		parts.put(this.hashCode(), this);

		return this;
	}

	public ItemCustomMissilePart makeFuselage(FuelType type, float fuel, PartSize top, PartSize bottom) {

		this.type = PartType.FUSELAGE;
		this.top = top;
		this.bottom = bottom;
		attributes = new Object[] { type, fuel };
		setTextureName(RefStrings.MODID + ":mp_fuselage");

		parts.put(this.hashCode(), this);

		return this;
	}

	public ItemCustomMissilePart makeStability(float inaccuracy, PartSize size) {

		this.type = PartType.FINS;
		this.top = size;
		this.bottom = size;
		this.attributes = new Object[] { inaccuracy };
		setTextureName(RefStrings.MODID + ":mp_stability");

		parts.put(this.hashCode(), this);

		return this;
	}

	public ItemCustomMissilePart makeThruster(FuelType type, float consumption, float lift, PartSize size) {

		this.type = PartType.THRUSTER;
		this.top = size;
		this.bottom = PartSize.NONE;
		this.attributes = new Object[] { type, consumption, lift };
		setTextureName(RefStrings.MODID + ":mp_thruster");

		parts.put(this.hashCode(), this);

		return this;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
	{

		if(title != null)
			list.add(EnumChatFormatting.DARK_PURPLE + "\"" + title + "\"");

		try {
			switch(type) {
			case CHIP:
				list.add(EnumChatFormatting.BOLD + I18nUtil.resolveKey("desc.util.inaccuracy") + ": " + EnumChatFormatting.GRAY + (Float)attributes[0] * 100 + "%");
				break;
			case WARHEAD:
				list.add(EnumChatFormatting.BOLD + I18nUtil.resolveKey("desc.util.size") + ": " + EnumChatFormatting.GRAY + getSize(bottom));
				list.add(EnumChatFormatting.BOLD + I18nUtil.resolveKey("desc.util.type") + ": " + EnumChatFormatting.GRAY + getWarhead((WarheadType)attributes[0]));
				list.add(EnumChatFormatting.BOLD + I18nUtil.resolveKey("desc.util.strength") + ": " + EnumChatFormatting.GRAY + (Float)attributes[1]);
				list.add(EnumChatFormatting.BOLD + I18nUtil.resolveKey("desc.util.weight") + ": " + EnumChatFormatting.GRAY + (Float)attributes[2] + "t");
				break;
			case FUSELAGE:
				list.add(EnumChatFormatting.BOLD + I18nUtil.resolveKey("desc.util.top_size") + ": " + EnumChatFormatting.GRAY + getSize(top));
				list.add(EnumChatFormatting.BOLD + I18nUtil.resolveKey("desc.util.bottom_size") + ": " + EnumChatFormatting.GRAY + getSize(bottom));
				list.add(EnumChatFormatting.BOLD + I18nUtil.resolveKey("desc.util.fuel_type") + ": " + EnumChatFormatting.GRAY + getFuel((FuelType)attributes[0]));
				list.add(EnumChatFormatting.BOLD + I18nUtil.resolveKey("desc.util.fuel_amount") + ": " + EnumChatFormatting.GRAY + (Float)attributes[1] + "l");
				break;
			case FINS:
				list.add(EnumChatFormatting.BOLD + I18nUtil.resolveKey("desc.util.size") + ": " + EnumChatFormatting.GRAY + getSize(top));
				list.add(EnumChatFormatting.BOLD + I18nUtil.resolveKey("desc.util.inaccuracy") + ": " + EnumChatFormatting.GRAY + (Float)attributes[0] * 100 + "%");
				break;
			case THRUSTER:
				list.add(EnumChatFormatting.BOLD + I18nUtil.resolveKey("desc.util.size") + ": " + EnumChatFormatting.GRAY + getSize(top));
				list.add(EnumChatFormatting.BOLD + I18nUtil.resolveKey("desc.util.fuel_type") + ": " + EnumChatFormatting.GRAY + getFuel((FuelType)attributes[0]));
				list.add(EnumChatFormatting.BOLD + I18nUtil.resolveKey("desc.util.fuel_consumption") + ": " + EnumChatFormatting.GRAY + (Float)attributes[1] + "l/tick");
				list.add(EnumChatFormatting.BOLD + I18nUtil.resolveKey("desc.util.payload") + ": " + EnumChatFormatting.GRAY + (Float)attributes[2] + "t");
				break;
			}
		} catch(Exception ex) {
			list.add("### I AM ERROR ###");
		}

		if(type != PartType.CHIP)
			list.add(EnumChatFormatting.BOLD + "I18nUtil.resolveKey(\"desc.util.health\") + \": " + EnumChatFormatting.GRAY + health + "HP");

		if(this.rarity != null)
			list.add(EnumChatFormatting.BOLD + "I18nUtil.resolveKey(\"desc.util.rarity\") + \": " + EnumChatFormatting.GRAY + I18nUtil.resolveKey(this.rarity.unlocalizedName));
		if(author != null)
			list.add(EnumChatFormatting.WHITE + "   by " + author);
		if(witty != null)
			list.add(EnumChatFormatting.GOLD + "   " + EnumChatFormatting.ITALIC + "\"" + witty + "\"");
	}

	public String getSize(PartSize size) {

		switch(size) {
		case ANY:
			return "Any";
		case SIZE_10:
			return "1.0m";
		case SIZE_15:
			return "1.5m";
		case SIZE_20:
			return "2.0m";
		default:
			return "None";
		}
	}

	public String getWarhead(WarheadType type) {

		if(type.labelCustom != null) return type.labelCustom;

		switch(type) {
		case HE:
			return EnumChatFormatting.YELLOW + I18nUtil.resolveKey(WarheadType.HE.unlocalizedName);
		case INC:
			return EnumChatFormatting.GOLD + I18nUtil.resolveKey(WarheadType.INC.unlocalizedName);
		case CLUSTER:
			return EnumChatFormatting.GRAY + I18nUtil.resolveKey(WarheadType.CLUSTER.unlocalizedName);
		case BUSTER:
			return EnumChatFormatting.WHITE + I18nUtil.resolveKey(WarheadType.BUSTER.unlocalizedName);
		case NUCLEAR:
			return EnumChatFormatting.DARK_GREEN + I18nUtil.resolveKey(WarheadType.NUCLEAR.unlocalizedName);
		case TX:
			return EnumChatFormatting.DARK_PURPLE + I18nUtil.resolveKey(WarheadType.TX.unlocalizedName);
		case N2:
			return EnumChatFormatting.RED + I18nUtil.resolveKey(WarheadType.N2.unlocalizedName);
		case BALEFIRE:
			return EnumChatFormatting.GREEN + I18nUtil.resolveKey(WarheadType.BALEFIRE.unlocalizedName);
		case SCHRAB:
			return EnumChatFormatting.AQUA + I18nUtil.resolveKey(WarheadType.SCHRAB.unlocalizedName);
		case TAINT:
			return EnumChatFormatting.DARK_PURPLE + I18nUtil.resolveKey(WarheadType.TAINT.unlocalizedName);
		case CLOUD:
			return EnumChatFormatting.LIGHT_PURPLE + I18nUtil.resolveKey(WarheadType.CLOUD.unlocalizedName);
		case TURBINE:
			return (System.currentTimeMillis() % 1000 < 500 ? EnumChatFormatting.RED : EnumChatFormatting.LIGHT_PURPLE) + I18nUtil.resolveKey(WarheadType.TURBINE.unlocalizedName);
		default:
			return EnumChatFormatting.BOLD + "N/A";
		}
	}

	public String getFuel(FuelType type) {

		switch(type) {
		case KEROSENE:
			return EnumChatFormatting.LIGHT_PURPLE + I18nUtil.resolveKey(FuelType.KEROSENE.unlocalizedName);
		case SOLID:
			return EnumChatFormatting.GOLD + I18nUtil.resolveKey(FuelType.METHALOX.unlocalizedName);
		case HYDROGEN:
			return EnumChatFormatting.DARK_AQUA + I18nUtil.resolveKey(FuelType.HYDROGEN.unlocalizedName);
		case XENON:
			return EnumChatFormatting.DARK_PURPLE + I18nUtil.resolveKey(FuelType.XENON.unlocalizedName);
		case BALEFIRE:
			return EnumChatFormatting.GREEN + I18nUtil.resolveKey(FuelType.BALEFIRE.unlocalizedName);
		default:
			return EnumChatFormatting.BOLD + "N/A";
		}
	}

	//am i retarded?
	/* yes */
	public ItemCustomMissilePart copy() {

		ItemCustomMissilePart part = new ItemCustomMissilePart();
		part.type = this.type;
		part.top = this.top;
		part.bottom = this.bottom;
		part.health = this.health;
		part.attributes = this.attributes;
		part.health = this.health;
		part.setTextureName(this.iconString);

		return part;
	}

	public ItemCustomMissilePart setAuthor(String author) {
		this.author = author;
		return this;
	}

	public ItemCustomMissilePart setTitle(String title) {
		this.title = title;
		return this;
	}

	public ItemCustomMissilePart setWittyText(String witty) {
		this.witty = witty;
		return this;
	}

	public ItemCustomMissilePart setHealth(float health) {
		this.health = health;
		return this;
	}

	public ItemCustomMissilePart setRarity(Rarity rarity) {
		this.rarity = rarity;

		if(this.type == PartType.FUSELAGE) {
			if(this.top == PartSize.SIZE_10)
				ItemLootCrate.list10.add(this);
			if(this.top == PartSize.SIZE_15)
				ItemLootCrate.list15.add(this);
		} else {
			ItemLootCrate.listMisc.add(this);
		}
		return this;
	}

}
