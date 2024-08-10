package com.hbm.items.weapon;

import java.util.HashMap;
import java.util.List;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.special.ItemLootCrate;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;

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
	public int mass = 0;
	private String title;
	private String author;
	private String witty;
	
	public ItemCustomMissilePart() {
		this.setCreativeTab(MainRegistry.missileTab);
	}
	
	public static HashMap<Integer, ItemCustomMissilePart> parts = new HashMap<>();
	
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
	 * ROCKET SPECIFIC
	 * [3]: thrust (N)
	 * [4]: ISP (s)
	 */
	public Object[] attributes;
	
	public enum PartType {
		CHIP,
		WARHEAD,
		FUSELAGE,
		FINS,
		THRUSTER,
	}
	
	public enum PartSize {
		//for chips
		ANY,
		//for missile tips and thrusters
		NONE,
		//regular sizes, 1.0m, 1.5m and 2.0m
		SIZE_10(1.0),
		SIZE_15(1.5),
		SIZE_20(2.0),
		// Space-grade
		SIZE_25(2.5),
		SIZE_30(3.0);

		PartSize() {
			this.radius = 0;
		}

		PartSize(double radius) {
			this.radius = radius;
		}

		public double radius;
	}
	
	public enum WarheadType {
		HE,
		INC,
		BUSTER,
		CLUSTER,
		NUCLEAR,
		TX,
		N2,
		BALEFIRE,
		SCHRAB,
		TAINT,
		CLOUD,
		TURBINE,
		APOLLO,
	}
	
	public enum FuelType {
		ANY, // Used by space-grade fuselages
		KEROSENE,
		SOLID,
		HYDROGEN,
		XENON,
		BALEFIRE,
		HYDRAZINE,
		METHALOX,
		KEROLOX, // oxygen rather than peroxide
	}
	
	public enum Rarity {
		
		COMMON(EnumChatFormatting.GRAY + "Common"),
		UNCOMMON(EnumChatFormatting.YELLOW + "Uncommon"),
		RARE(EnumChatFormatting.AQUA + "Rare"),
		EPIC(EnumChatFormatting.LIGHT_PURPLE + "Epic"),
		LEGENDARY(EnumChatFormatting.DARK_GREEN + "Legendary"),
		SEWS_CLOTHES_AND_SUCKS_HORSE_COCK(EnumChatFormatting.DARK_AQUA + "Strange");
		
		String name;
		
		Rarity(String name) {
			this.name = name;
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
	
	public ItemCustomMissilePart makeWarhead(WarheadType type, float punch, int mass, PartSize size) {

		this.type = PartType.WARHEAD;
		this.top = PartSize.NONE;
		this.bottom = size;
		this.mass = mass;
		this.attributes = new Object[] { type, punch };
		setTextureName(RefStrings.MODID + ":mp_warhead");
		
		parts.put(this.hashCode(), this);
		
		return this;
	}
	
	public ItemCustomMissilePart makeFuselage(FuelType type, int fuel, int mass, PartSize top, PartSize bottom) {

		this.type = PartType.FUSELAGE;
		this.top = top;
		this.bottom = bottom;
		this.mass = mass;
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
	
	public ItemCustomMissilePart makeThruster(FuelType type, float consumption, float lift, PartSize size, int thrust, int mass, int isp) {

		this.type = PartType.THRUSTER;
		this.top = size;
		this.bottom = PartSize.NONE;
		this.mass = mass;
		this.attributes = new Object[] { type, consumption, lift, thrust, isp };
		setTextureName(RefStrings.MODID + ":mp_thruster");
		
		parts.put(this.hashCode(), this);
		
		return this;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
	{

		if(title != null)
			list.add(EnumChatFormatting.DARK_PURPLE + "\"" + title + "\"");
		
		try {
			switch(type) {
			case CHIP:
				list.add(EnumChatFormatting.BOLD + "Inaccuracy: " + EnumChatFormatting.GRAY + (Float)attributes[0] * 100 + "%");
				break;
			case WARHEAD:
				list.add(EnumChatFormatting.BOLD + "Size: " + EnumChatFormatting.GRAY + getSize(bottom));
				list.add(EnumChatFormatting.BOLD + "Type: " + EnumChatFormatting.GRAY + getWarhead());
				list.add(EnumChatFormatting.BOLD + "Strength: " + EnumChatFormatting.GRAY + (Float)attributes[1]);
				list.add(EnumChatFormatting.BOLD + "Mass: " + EnumChatFormatting.GRAY + mass + "kg");
				break;
			case FUSELAGE:
				list.add(EnumChatFormatting.BOLD + "Top size: " + EnumChatFormatting.GRAY + getSize(top));
				list.add(EnumChatFormatting.BOLD + "Bottom size: " + EnumChatFormatting.GRAY + getSize(bottom));
				list.add(EnumChatFormatting.BOLD + "Fuel type: " + EnumChatFormatting.GRAY + getFuelName());
				list.add(EnumChatFormatting.BOLD + "Fuel amount: " + EnumChatFormatting.GRAY + getTankSize() + "mB");
				list.add(EnumChatFormatting.BOLD + "Mass: " + EnumChatFormatting.GRAY + mass + "kg");
				break;
			case FINS:
				list.add(EnumChatFormatting.BOLD + "Size: " + EnumChatFormatting.GRAY + getSize(top));
				list.add(EnumChatFormatting.BOLD + "Inaccuracy: " + EnumChatFormatting.GRAY + (Float)attributes[0] * 100 + "%");
				break;
			case THRUSTER:
				list.add(EnumChatFormatting.BOLD + "Size: " + EnumChatFormatting.GRAY + getSize(top));
				list.add(EnumChatFormatting.BOLD + "Fuel type: " + EnumChatFormatting.GRAY + getFuelName());
				// list.add(EnumChatFormatting.BOLD + "Fuel consumption: " + EnumChatFormatting.GRAY + (Float)attributes[1] + "mB/tick");
				// list.add(EnumChatFormatting.BOLD + "Max. payload: " + EnumChatFormatting.GRAY + (Float)attributes[2] + "t");
				list.add(EnumChatFormatting.BOLD + "Thrust: " + EnumChatFormatting.GRAY + (Integer)attributes[3] + "N");
				list.add(EnumChatFormatting.BOLD + "ISP: " + EnumChatFormatting.GRAY + (Integer)attributes[4] + "s");
				list.add(EnumChatFormatting.BOLD + "Mass: " + EnumChatFormatting.GRAY + mass + "kg");
				break;
			}
		} catch(Exception ex) {
			list.add("### I AM ERROR ###");
		}
		
		if(type != PartType.CHIP)
			list.add(EnumChatFormatting.BOLD + "Health: " + EnumChatFormatting.GRAY + health + "HP");
		
		if(this.rarity != null)
			list.add(EnumChatFormatting.BOLD + "Rarity: " + EnumChatFormatting.GRAY + this.rarity.name);
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
		case SIZE_25:
			return "2.5m";
		case SIZE_30:
			return "3.0m";
		default:
			return "None";
		}
	}
	
	public String getWarhead() {
		if(!(attributes[0] instanceof WarheadType)) return EnumChatFormatting.BOLD + "N/A";
		
		switch((WarheadType)attributes[0]) {
		case HE:
			return EnumChatFormatting.YELLOW + "HE";
		case INC:
			return EnumChatFormatting.GOLD + "Incendiary";
		case CLUSTER:
			return EnumChatFormatting.GRAY + "Cluster";
		case BUSTER:
			return EnumChatFormatting.WHITE + "Bunker Buster";
		case NUCLEAR:
			return EnumChatFormatting.DARK_GREEN + "Nuclear";
		case TX:
			return EnumChatFormatting.DARK_PURPLE + "Thermonuclear (TX)";
		case N2:
			return EnumChatFormatting.RED + "N²";
		case BALEFIRE:
			return EnumChatFormatting.GREEN + "BF";
		case SCHRAB:
			return EnumChatFormatting.AQUA + "Schrabidium";
		case TAINT:
			return EnumChatFormatting.DARK_PURPLE + "Taint";
		case CLOUD:
			return EnumChatFormatting.LIGHT_PURPLE + "Cloud";
		case TURBINE:
			return (System.currentTimeMillis() % 1000 < 500 ? EnumChatFormatting.RED : EnumChatFormatting.LIGHT_PURPLE) + "Turbine";
		case APOLLO:
			return (System.currentTimeMillis() % 1000 < 500 ? EnumChatFormatting.GOLD : EnumChatFormatting.RED) + "Capsule";
		default:
			return EnumChatFormatting.BOLD + "N/A";
		}
	}
	
	public String getFuelName() {
		if(!(attributes[0] instanceof FuelType)) return EnumChatFormatting.BOLD + "N/A";

		switch((FuelType)attributes[0]) {
		case ANY:
			return EnumChatFormatting.GRAY + "Any Liquid Fuel";
		case KEROSENE:
			return EnumChatFormatting.LIGHT_PURPLE + "Kerosene / Peroxide";
		case METHALOX:
			return EnumChatFormatting.YELLOW + "Natural Gas / Oxygen";
		case KEROLOX:
			return EnumChatFormatting.LIGHT_PURPLE + "Kerosene / Oxygen";
		case SOLID:
			return EnumChatFormatting.GOLD + "Solid Fuel";
		case HYDROGEN:
			return EnumChatFormatting.DARK_AQUA + "Hydrogen / Oxygen";
		case XENON:
			return EnumChatFormatting.DARK_PURPLE + "Xenon Gas";
		case BALEFIRE:
			return EnumChatFormatting.GREEN + "BF Rocket Fuel / Peroxide";
		case HYDRAZINE:
			return EnumChatFormatting.AQUA + "Hydrazine";
		default:
			return EnumChatFormatting.BOLD + "N/A";
		}
	}

	public FluidType getFuel() {
		if(!(attributes[0] instanceof FuelType)) return null;

		switch((FuelType)attributes[0]) {
		case KEROSENE:
			return Fluids.KEROSENE;
		case KEROLOX:
			return Fluids.KEROSENE;
		case METHALOX:
			return Fluids.GAS;
		case HYDROGEN:
			return Fluids.HYDROGEN;
		case XENON:
			return Fluids.XENON;
		case BALEFIRE:
			return Fluids.BALEFIRE;
		case HYDRAZINE:
			return Fluids.HYDRAZINE;
		case SOLID:
			return Fluids.NONE; // Requires non-fluid fuel
		default:
			return null;
		}
	}

	public FluidType getOxidizer() {
		if(!(attributes[0] instanceof FuelType)) return null;

		switch((FuelType)attributes[0]) {
		case KEROLOX:
		case HYDROGEN:
		case METHALOX:
			return Fluids.OXYGEN;
		case KEROSENE:
		case BALEFIRE:
			return Fluids.PEROXIDE;
		default:
			return null;
		}
	}

	public int getThrust() {
		if(type != PartType.THRUSTER) return 0;
		if(attributes[3] == null || !(attributes[3] instanceof Integer)) return 0;
		return (Integer) attributes[3];
	}

	public int getISP() {
		if(type != PartType.THRUSTER) return 0;
		if(attributes[4] == null || !(attributes[4] instanceof Integer)) return 0;
		return (Integer) attributes[4];
	}

	public int getTankSize() {
		if(type != PartType.FUSELAGE) return 0;
		if(!(attributes[1] instanceof Integer)) return 0;
		return (Integer) attributes[1];
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
		part.mass = this.mass;
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
