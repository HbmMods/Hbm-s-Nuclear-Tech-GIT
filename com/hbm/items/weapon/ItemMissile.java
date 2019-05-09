package com.hbm.items.weapon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.hbm.items.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemMissile extends Item {
	
	public PartType type;
	public PartSize top;
	public PartSize bottom;
	
	public static HashMap<Integer, ItemMissile> parts = new HashMap();
	
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
	 * [1]: lift strength
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
		
		HE,
		INC,
		BUSTER,
		CLUSTER,
		NUCLEAR,
		TX,
		N2,
		BALEFIRE
	}
	
	public enum FuelType {
		
		KEROSENE,
		SOLID,
		HYDROGEN,
		XENON,
		BALEFIRE
	}
	
	public ItemMissile makeChip(float inaccuracy) {
		
		this.type = PartType.CHIP;
		this.top = PartSize.ANY;
		this.bottom = PartSize.ANY;
		this.attributes = new Object[] { inaccuracy };
		
		parts.put(this.hashCode(), this);
		
		return this;
	}
	
	public ItemMissile makeWarhead(WarheadType type, float punch, float weight, PartSize size) {

		this.type = PartType.WARHEAD;
		this.top = PartSize.NONE;
		this.bottom = size;
		this.attributes = new Object[] { type, punch, weight };
		
		parts.put(this.hashCode(), this);
		
		return this;
	}
	
	public ItemMissile makeFuselage(FuelType type, float fuel, PartSize top, PartSize bottom) {

		this.type = PartType.FUSELAGE;
		this.top = top;
		this.bottom = bottom;
		attributes = new Object[] { type, fuel };
		
		parts.put(this.hashCode(), this);
		
		return this;
	}
	
	public ItemMissile makeStability(float inaccuracy, PartSize size) {

		this.type = PartType.FINS;
		this.top = size;
		this.bottom = size;
		this.attributes = new Object[] { inaccuracy };
		
		parts.put(this.hashCode(), this);
		
		return this;
	}
	
	public ItemMissile makeThruster(FuelType type, float consumption, float lift, PartSize size) {

		this.type = PartType.THRUSTER;
		this.top = size;
		this.bottom = PartSize.NONE;
		this.attributes = new Object[] { type, consumption, lift };
		
		parts.put(this.hashCode(), this);
		
		return this;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
	{
		try {
			switch(type) {
			case CHIP:
				list.add("Inaccuracy: " + (Float)attributes[0]);
				break;
			case WARHEAD:
				list.add("Size: " + getSize(bottom));
				list.add("Type: " + getWarhead((WarheadType)attributes[0]));
				list.add("Strength: " + (Float)attributes[1]);
				list.add("Weight: " + (Float)attributes[2] + "t");
				break;
			case FUSELAGE:
				list.add("Top size: " + getSize(top));
				list.add("Bottom size: " + getSize(bottom));
				list.add("Fuel type: " + getFuel((FuelType)attributes[0]));
				list.add("Fuel amount: " + (Float)attributes[1] + "l");
				break;
			case FINS:
				list.add("Size: " + getSize(top));
				list.add("Inaccuracy: " + (Float)attributes[0]);
				break;
			case THRUSTER:
				list.add("Size: " + getSize(top));
				list.add("Fuel type: " + getFuel((FuelType)attributes[0]));
				list.add("Fuel consumption: " + (Float)attributes[1] + "l/tick");
				list.add("Max. payload: " + (Float)attributes[2] + "t");
				break;
			}
		} catch(Exception ex) {
			list.add("### I AM ERROR ###");
		}
	}
	
	private String getSize(PartSize size) {
		
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
	
	private String getWarhead(WarheadType type) {
		
		switch(type) {
		case HE:
			return "HE";
		case INC:
			return "Incendiary";
		case CLUSTER:
			return "Cluster";
		case BUSTER:
			return "Bunker Buster";
		case NUCLEAR:
			return "Nuclear";
		case TX:
			return "Thermonuclear";
		case N2:
			return "N²";
		case BALEFIRE:
			return "BF";
		default:
			return "N/A";
		}
	}
	
	private String getFuel(FuelType type) {
		
		switch(type) {
		case KEROSENE:
			return "Kerosene";
		case SOLID:
			return "Solid Fuel";
		case HYDROGEN:
			return "Hydrogen";
		case XENON:
			return "Xenon Gas";
		case BALEFIRE:
			return "BF Inferno Fuel";
		default:
			return "N/A";
		}
	}

}
