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
	
	String fullName = "";
	double funcStart;
	double funcEnd;
	double xGen = 0.5D;;
	double xBurn = 50D;
	double heat = 1D;
	double yield;

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
	
	public double burn(ItemStack stack, double flux) {
		return 0;
	}
	
	/**
	 * @param flux [0;100] ...or at least those are sane levels
	 * @return the amount of reactivity yielded, unmodified by xenon
	 */
	public double reactivityFunc(double flux) {
		return funcStart + (funcEnd - funcStart) * flux / 100D; //goodness gracious i guessed the right formula on the first try!
	}
	
	public double xenonGenFunc(double flux) {
		return flux * xGen;
	}
	
	public double xenonBurnFunc(double flux) {
		return (flux * flux) / xBurn;
	}
	
	/**
	 * @param stack
	 * @return enrichment [0;1]
	 */
	public double getEnrichment(ItemStack stack) {
		return getYield(stack) / ((ItemRBMKRod) stack.getItem()).yield;
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

	public boolean showDurabilityBar(ItemStack stack) {
		return getDurabilityForDisplay(stack) < 1D;
	}

	public double getDurabilityForDisplay(ItemStack stack) {
		return getEnrichment(stack);
	}
}
