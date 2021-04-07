package com.hbm.items.machine;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.items.special.ItemHazard;
import com.hbm.main.MainRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;

public class ItemRBMKRod extends ItemHazard {
	
	String fullName = "";	//full name of the fuel rod
	double funcStart;		//starting point of the linear reactivity function
	double funcEnd;			//endpoint of the function
	double xGen = 0.5D;;	//multiplier for xenon production
	double xBurn = 50D;		//divider for xenon burnup
	double heat = 1D;		//heat produced per outFlux
	double yield;			//total potential inFlux the rod can take in its lifetime

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
		
		return outFlux;
	}
	
	/**
	 * Call this after 'burn' and supply its returned outFlux to get the appropriate heat
	 * @param flux
	 * @return heat generated from outFlux
	 */
	public double heatFromFlux(double flux) {
		return flux * this.heat;
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
		list.add(EnumChatFormatting.LIGHT_PURPLE + "Xenon poison: " + ((getPoison(stack) * 10D) / 10D) + "%");
		list.add(EnumChatFormatting.GOLD + "Heat per tick at full power: " + heat);
		list.add(EnumChatFormatting.YELLOW + "Flux function:");
		list.add(EnumChatFormatting.WHITE + "  f(0) = " + funcStart);
		list.add(EnumChatFormatting.WHITE + "  f(1) = " + funcEnd);
		list.add(EnumChatFormatting.WHITE + "  f(x) = " + funcStart + " + " + (funcEnd - funcStart) + " * x");
		list.add(EnumChatFormatting.YELLOW + "Xenon gen function:");
		list.add(EnumChatFormatting.WHITE + "  g(x) = x * " + xGen);
		list.add(EnumChatFormatting.YELLOW + "Xenon burn function:");
		list.add(EnumChatFormatting.WHITE + "  b(x) = x² * " + xBurn);
		
		super.addInformation(stack, player, list, bool);
	}
	
	public static void setYield(ItemStack stack, double yield) {
		
		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		
		stack.stackTagCompound.setDouble("yield", yield);
	}
	
	public static double getYield(ItemStack stack) {
		
		if(stack.hasTagCompound()) {
			return stack.stackTagCompound.getDouble("yield");
		}
		
		if(stack.getItem() instanceof ItemRBMKRod) {
			return ((ItemRBMKRod)stack.getItem()).yield;
		}
		
		return 0;
	}
	
	public static void setPoison(ItemStack stack, double yield) {
		
		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		
		stack.stackTagCompound.setDouble("xenon", yield);
	}
	
	public static double getPoison(ItemStack stack) {
		
		if(stack.hasTagCompound()) {
			return stack.stackTagCompound.getDouble("xenon");
		}
		
		return 0;
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return getDurabilityForDisplay(stack) < 1D;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return getEnrichment(stack);
	}
}
