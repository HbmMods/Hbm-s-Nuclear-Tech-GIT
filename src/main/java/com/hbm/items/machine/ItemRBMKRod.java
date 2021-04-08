package com.hbm.items.machine;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.items.special.ItemHazard;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.rbmk.IRBMKFluxReceiver.NType;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;

public class ItemRBMKRod extends ItemHazard {
	
	String fullName = "";				//full name of the fuel rod
	double funcStart;					//starting point of the linear reactivity function
	double funcEnd;						//endpoint of the function
	double xGen = 0.5D;;				//multiplier for xenon production
	double xBurn = 50D;					//divider for xenon burnup
	double heat = 1D;					//heat produced per outFlux
	double yield;						//total potential inFlux the rod can take in its lifetime
	double meltingPoint = 1000D;		//the maximum heat of the rod's hull before shit hits the fan. the core can be as hot as it wants to be
	double diffusion = 1D;				//the speed at which the core heats the hull
	public NType nType = NType.SLOW;	//neutronType, the most efficient neutron type for fission
	public NType rType = NType.FAST;	//releaseType, the type of neutrons released by this fuel
	
	/*   _____
	 * ,I I I I,
	 * |'-----'|
	 * |       |
	 *  '-----'
	 * 	I I I I
	 * 	I I I I
	 * 	I I I I
	 * 	I I I I
	 * 	I I I I
	 * 	I I I I
	 * 	I I I I
	 * |'-----'|
	 * |       |
	 *  '-----'
	 *  I I I I
	 *  
	 *  i drew a fuel rod yay
	 */

	public ItemRBMKRod(String fullName) {
		
		this.fullName = fullName;
		
		this.setContainerItem(ModItems.rbmk_fuel_empty);
		this.setMaxStackSize(1);
		this.setCreativeTab(MainRegistry.controlTab);
	}

	public ItemRBMKRod setYield(double yield) {
		this.yield = yield;
		return this;
	}

	public ItemRBMKRod setStats(double funcStart, double funcEnd) {
		this.funcStart = funcStart;
		this.funcEnd = funcEnd;
		return this;
	}

	public ItemRBMKRod setNeutronTypes(NType nType, NType rType) {
		this.nType = nType;
		this.rType = rType;
		return this;
	}
	
	/**
	 * Adjusts the input flux using the poison level
	 * Generates, then burns poison
	 * Calculates the outflux based on influx, enrichment and poison
	 * Depletes the yield, then returns the outflux
	 * @param stack
	 * @param inFlux
	 * @return outFlux
	 */
	public double burn(ItemStack stack, double inFlux) {
		
		inFlux *= getPoisonLevel(stack);
		
		double xenon = getPoison(stack);
		xenon += xenonGenFunc(inFlux);
		xenon -= xenonBurnFunc(inFlux);
		
		if(xenon < 0D) xenon = 0D;
		if(xenon > 100D) xenon = 100D;
		
		setPoison(stack, xenon);
		
		double outFlux = reactivityFunc(inFlux * getEnrichment(stack));
		
		double y = getYield(stack);
		y -= inFlux;
		
		if(y < 0D) y = 0D;
		
		setYield(stack, y);
		
		//TODO: core heatup
		
		return outFlux;
	}
	
	/**
	 * Heat up the core based on the outFlux, then move some heat to the hull
	 * @param stack
	 */
	public void updateHeat(ItemStack stack) {
		//TODO
	}
	
	/**
	 * return one tick's worth of heat and cool the hull of the fuel rod, this heat goes into the fuel rod assembly block
	 * @param stack
	 * @return
	 */
	public double provideHeat(ItemStack stack) {
		return 0; //TODO
	}
	
	/**
	 * @param flux [0;100] ...or at least those are sane levels
	 * @return the amount of reactivity yielded, unmodified by xenon
	 */
	public double reactivityFunc(double flux) {
		return funcStart + (funcEnd - funcStart) * flux / 100D; //goodness gracious i guessed the right formula on the first try!
	}
	
	/**
	 * Xenon generated per tick, linear function
	 * @param flux
	 * @return
	 */
	public double xenonGenFunc(double flux) {
		return flux * xGen;
	}
	
	/**
	 * Xenon burned away per tick, quadratic function
	 * @param flux
	 * @return
	 */
	public double xenonBurnFunc(double flux) {
		return (flux * flux) / xBurn;
	}
	
	/**
	 * @param stack
	 * @return enrichment [0;1]
	 */
	public static double getEnrichment(ItemStack stack) {
		return getYield(stack) / ((ItemRBMKRod) stack.getItem()).yield;
	}
	
	/**
	 * @param stack
	 * @return poison [0;1]
	 */
	public static double getPoisonLevel(ItemStack stack) {
		return getPoison(stack) / 100D;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		
		list.add(EnumChatFormatting.ITALIC + this.fullName);
		
		if(funcStart > 0) {
			list.add(EnumChatFormatting.RED + "Self-igniting");
		}

		list.add(EnumChatFormatting.GREEN + "Depletion: " + (100D - ((getYield(stack) * 1000D / yield) / 10D)) + "%");
		list.add(EnumChatFormatting.DARK_PURPLE + "Xenon poison: " + ((getPoison(stack) * 10D) / 10D) + "%");
		list.add(EnumChatFormatting.BLUE + "Splits with: " + nType.localized);
		list.add(EnumChatFormatting.BLUE + "Splits into: " + rType.localized);
		list.add(EnumChatFormatting.YELLOW + "Flux function: " + EnumChatFormatting.WHITE + "" + funcStart + " + " + (funcEnd - funcStart) + " * x");
		list.add(EnumChatFormatting.YELLOW + "Xenon gen function: " + EnumChatFormatting.WHITE + "x * " + xGen);
		list.add(EnumChatFormatting.YELLOW + "Xenon burn function: " + EnumChatFormatting.WHITE + "x² * " + xBurn);
		list.add(EnumChatFormatting.GOLD + "Heat per tick at full power: " + heat + "°C");
		list.add(EnumChatFormatting.GOLD + "Diffusion: " + diffusion + "°C/t");
		list.add(EnumChatFormatting.RED + "Skin temp: " + ((int)(getHullHeat(stack) * 10D) / 10D) + "°C");
		list.add(EnumChatFormatting.RED + "Core temp: " + ((int)(getCoreHeat(stack) * 10D) / 10D) + "°C");
		list.add(EnumChatFormatting.DARK_RED + "Melting point: " + meltingPoint + "°C");
		
		super.addInformation(stack, player, list, bool);
	}
	
	/*  __    __   ____     ________
	 * |  \  |  | |  __ \  |__    __|
	 * |   \ |  | | |__| |    |  |
	 * |  |\\|  | |  __ <     |  |
	 * |  | \   | | |__| |    |  |
	 * |__|  \__| |_____/     |__|
	 */
	
	public static void setYield(ItemStack stack, double yield) {
		setDouble(stack, "yield", yield);
	}
	
	public static double getYield(ItemStack stack) {
		
		if(stack.getItem() instanceof ItemRBMKRod) {
			return getDouble(stack, "yield");
		}
		
		return 0;
	}
	
	public static void setPoison(ItemStack stack, double xenon) {
		setDouble(stack, "xenon", xenon);
	}
	
	public static double getPoison(ItemStack stack) {
		return getDouble(stack, "xenon");
	}
	
	public static void setCoreHeat(ItemStack stack, double heat) {
		setDouble(stack, "core", heat);
	}
	
	public static double getCoreHeat(ItemStack stack) {
		return getDouble(stack, "core");
	}
	
	public static void setHullHeat(ItemStack stack, double heat) {
		setDouble(stack, "hull", heat);
	}
	
	public static double getHullHeat(ItemStack stack) {
		return getDouble(stack, "hull");
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return getDurabilityForDisplay(stack) > 0D;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return 1D - getEnrichment(stack);
	}
	
	public static void setDouble(ItemStack stack, String key, double yield) {
		
		if(!stack.hasTagCompound())
			setNBTDefaults(stack);
		
		stack.stackTagCompound.setDouble(key, yield);
	}
	
	public static double getDouble(ItemStack stack, String key) {
		
		if(!stack.hasTagCompound())
			setNBTDefaults(stack);

		return stack.stackTagCompound.getDouble(key);
	}
	
	/**
	 * Sets up the default values for all NBT data because doing it one-by-one will only correctly set the first called value and the rest stays 0 which is very not good
	 * @param stack
	 */
	private static void setNBTDefaults(ItemStack stack) {

		stack.stackTagCompound = new NBTTagCompound();
		setYield(stack, ((ItemRBMKRod)stack.getItem()).yield);
		setCoreHeat(stack, 20.0D);
		setHullHeat(stack, 20.0D);
	}
}
