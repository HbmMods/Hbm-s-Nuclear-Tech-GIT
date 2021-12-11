package com.hbm.items.machine;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.util.I18nUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;

public class ItemCANDUBundle extends Item {
	
	public String fullName = "";		//name and composition of the rod
	public double reactivity = 30D;		//what the fuck does endpoint mean
	public EnumFluxFunction function = EnumFluxFunction.TEST_FUNC;
	public double heatPerFission = 1D;	//how much heat is generated when the rod fissions
	public double diffusion = 10D;		//how fast heat from the core of the rod is transferred to the skin
	public double meltPoint = 2000D;	//how hot can it get?
	public double yield = 100000D;		//how much "durability" the rod has
	public double SFrate = 0D;			//how fast the rod self fissions, if at all
	public double nGen = 0.5D;			//neutron poison generation rate
	public double nBurn = 50D;			//neutron poison burn rate
	public boolean poisonBurnable;		//if the poison is burnable (Xe-135) or not (Sm-149)
	
	public double coreHeat = 20D;
	public double skinHeat = 20D;
	public double depletion = 0D;
	public double nPoisonLevels = 0D;
	
	// time for bodged solution
	public double[] inFlux = new double[4];
	public double[] outFlux = new double[4];
	
	public ItemCANDUBundle(String name) {
		this.fullName = name;
		
		this.setContainerItem(ModItems.rbmk_fuel_empty);
		this.setMaxStackSize(1);
		this.setCreativeTab(MainRegistry.controlTab);
		
		for (int i = 0; i<=3; i++) {
			this.inFlux[i] = 0D;
			this.outFlux[i] = 0D;
		}
	}
	
	public ItemCANDUBundle setFunction(EnumFluxFunction function) {
		this.function = function;
		return this;
	}
	public ItemCANDUBundle setFissionEnergy(double heat) {
		this.heatPerFission = heat;
		return this;
	}
	public ItemCANDUBundle setDiffusion(double diffusion) {
		this.diffusion = diffusion;
		return this;
	}
	public ItemCANDUBundle setMeltPoint(double melt) {
		this.meltPoint = melt;
		return this;
	}
	public ItemCANDUBundle setYield(double yield) {
		this.yield = yield;
		return this;
	}
	public ItemCANDUBundle setStats(double funcEnd) {
		return setStats(funcEnd, 0);
	}

	public ItemCANDUBundle setStats(double funcEnd, double selfRate) {
		this.reactivity = funcEnd;
		this.SFrate = selfRate;
		return this;
	}
	public ItemCANDUBundle setBurnable(boolean burnable) {
		this.poisonBurnable = burnable;
		return this;
	}
	
	public static enum EnumFluxFunction {
		
		TEST_FUNC(EnumChatFormatting.RED + "EH SAFE ENOUGH/20 ON A GOOD DAY"),
		
		TANH(EnumChatFormatting.GREEN + "SAFE / HYBERBOLIC TANGENT"),			////
		NEG_GAUSS(EnumChatFormatting.GREEN + "SAFE / NEGATIVE GAUSSIAN"),		////
		SILU(EnumChatFormatting.RED + "DANGEROUS / NEGATIVE SIGMOID LINEAR UNIT"),//
		ELLIPSE(EnumChatFormatting.YELLOW + "DANGEROUS / HALF-ELLIPSE"),			////
		SQRT(EnumChatFormatting.YELLOW + "MEDIUM / SURD"),						////
		
		// this is all from the RBMK fuel functions, how fun
		PASSIVE(EnumChatFormatting.DARK_GREEN + "SAFE / PASSIVE"),				//const, no reactivity
		LOG_TEN(EnumChatFormatting.YELLOW + "MEDIUM / LOGARITHMIC"),			//log10(x + 1) * reactivity * 50
		PLATEU(EnumChatFormatting.GREEN + "SAFE / EULER"),						//(1 - e^(-x/25)) * reactivity * 100
		ARCH(EnumChatFormatting.YELLOW + "MEDIUM / NEGATIVE-QUADRATIC"),		//x-(xÂ²/1000) * reactivity
		SIGMOID(EnumChatFormatting.GREEN + "SAFE / SIGMOID"),					//100 / (1 + e^(-(x - 50) / 10)) <- tiny amount of reactivity at x=0 !
		SQUARE_ROOT(EnumChatFormatting.YELLOW + "MEDIUM / SQUARE ROOT"),		//sqrt(x) * 10 * reactivity
		LINEAR(EnumChatFormatting.RED + "DANGEROUS / LINEAR"),					//x * reactivity
		QUADRATIC(EnumChatFormatting.RED + "DANGEROUS / QUADRATIC");			//x^2 / 100 * reactivity
		
		public String title = "";
		
		private EnumFluxFunction(String title) {
			this.title = title;
		}
	}
	
public double reactivityFunc(double in, double enrichment) {
		
		double flux = in * enrichment;
		
		switch(this.function) {
		case TEST_FUNC: return Math.log10(flux + 1) * 0.5D * reactivity;
		
		case TANH: return Math.tanh(flux / 10D) * reactivity;
		case NEG_GAUSS: return reactivity - (reactivity * Math.pow(Math.E, - ((flux  / 100D) * (flux / 100D))));
		case SILU: return flux / 50D + Math.pow(Math.E, (flux / 10000D)) * reactivity;
		case ELLIPSE: return (Math.sqrt((100000D * flux) - (flux * flux))) / 1750D;
		case SQRT: return ((Math.sqrt(flux) * flux) / 2000D) * reactivity;
		
		case PASSIVE: return SFrate * enrichment;
		case LOG_TEN: return Math.log10(flux + 1) * 0.5D * reactivity;
		case PLATEU: return (1 - Math.pow(Math.E, -flux / 25D)) * reactivity;
		case ARCH: return Math.max(flux - (flux * flux / 100000D) / 100D * reactivity, 0D);
		case SIGMOID: return reactivity / (1 + Math.pow(Math.E, -(flux - 50D) / 10D));
		case SQUARE_ROOT: return Math.sqrt(flux) * reactivity / 10D;
		case LINEAR: return flux / 100D * reactivity;
		case QUADRATIC: return flux * flux / 10000D * reactivity;
		}
		
		return 0;
	}

public String getFuncDescription() {
	
	String x = "x";
	
	EnumChatFormatting reactivityScore = this.reactivity >= 80D ? EnumChatFormatting.DARK_RED : 
		this.reactivity >= 60D ? EnumChatFormatting.RED :
		this.reactivity >= 40D ? EnumChatFormatting.GOLD :
		this.reactivity >= 20D ? EnumChatFormatting.YELLOW :
								 EnumChatFormatting.GREEN;
	String reactivity = reactivityScore + "" + this.reactivity + EnumChatFormatting.WHITE; 
	
	if(SFrate > 0)
		x = "(x" + EnumChatFormatting.RED + " + " + SFrate + "" + EnumChatFormatting.WHITE + ")";
	
	switch(this.function) {
	case TANH: return "tanh(" + x + " / 10) * " + reactivity;
	case NEG_GAUSS: return reactivity + " - (" + reactivity + " * e ^ -(" + x + " / 100) ^ 2)";
	case SILU: return x + "/ (50 + e ^ (" + x + " / 10000)) * " + reactivity;
	case ELLIPSE: return "sqrt(100000 * " + x + " - " + x + " ^ 2) / - 1750 * " + reactivity;
	case SQRT: return "(sqrt(" + x + ") * " + x + " / 2000) * " + reactivity;
	
	case PASSIVE: return EnumChatFormatting.RED + "" + SFrate;
	case LOG_TEN: return "log10(" + x + " + 1) * 0.5 * " + reactivity;
	case PLATEU: return "(1 - e^-" + x + " / 25)) * " + reactivity;
	case ARCH: return "(" + x + " - " + x + "Â² / 100000) / 100 * " + reactivity + " [0;âˆž]";
	case SIGMOID: return reactivity + " / (1 + e^(-(" + x + " - 50) / 10)";
	case SQUARE_ROOT: return "sqrt(" + x + ") * " + reactivity + " / 10";
	case LINEAR: return x + " / 100 * " + reactivity;
	case QUADRATIC: return x + "Â² / 10000 * " + reactivity;
	}
	
	return "ERROR";
}
	
	/*
	 *  To be called on the creation of the item
	 * 
	 */
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		EnumChatFormatting selfFissioning = this.SFrate > 0D ? EnumChatFormatting.RED : EnumChatFormatting.YELLOW;
		
		list.add(EnumChatFormatting.ITALIC + this.fullName);
		list.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey("trait.candu.depletion", (1 - (yield - this.getDepletion(stack)) / yield ) + "%"));
		
		if(poisonBurnable) {
			list.add(EnumChatFormatting.DARK_PURPLE + I18nUtil.resolveKey("trait.candu.xPoison", getNPoisonLevels(stack) + "%"));
			list.add(EnumChatFormatting.DARK_PURPLE + I18nUtil.resolveKey("trait.candu.xGen", EnumChatFormatting.WHITE + "x * " + nGen));
			list.add(EnumChatFormatting.DARK_PURPLE + I18nUtil.resolveKey("trait.candu.xBurn", EnumChatFormatting.WHITE + "x² * " + nBurn));
		} else {
			list.add(EnumChatFormatting.YELLOW + I18nUtil.resolveKey("trait.candu.sPoison", EnumChatFormatting.GREEN + "" + getNPoisonLevels(stack) + "%"));
			list.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey("trait.candu.sGen", EnumChatFormatting.YELLOW + "x * " + nGen));
		}
		list.add(EnumChatFormatting.DARK_AQUA + I18nUtil.resolveKey("trait.candu.fluxFunc", EnumChatFormatting.WHITE + getFuncDescription()));
		list.add(EnumChatFormatting.AQUA + I18nUtil.resolveKey("trait.candu.funcType", this.function.title));
		list.add(selfFissioning + I18nUtil.resolveKey("trait.candu.nSource", this.SFrate));
		list.add(EnumChatFormatting.GOLD + I18nUtil.resolveKey("trait.candu.fissionEnergy", this.heatPerFission + "°C"));
		list.add(EnumChatFormatting.GOLD + I18nUtil.resolveKey("trait.candu.diffusion", this.diffusion + "mm²/s"));
		list.add(EnumChatFormatting.RED + I18nUtil.resolveKey("trait.candu.skinHeat", ((int)(getSkinHeat(stack) * 10D) / 10D) + "°C"));
		list.add(EnumChatFormatting.DARK_RED + I18nUtil.resolveKey("trait.candu.coreHeat", ((int)(getCoreHeat(stack) * 10D) / 10D) + "°C"));
		list.add(EnumChatFormatting.DARK_RED + I18nUtil.resolveKey("trait.candu.meltingPoint", this.meltPoint + "°C"));
		
		
		super.addInformation(stack, player, list, bool);
	}
	
	public static double getEnrichment(ItemStack stack) {
		return 1 - (getDepletion(stack) / ((ItemCANDUBundle) stack.getItem()).yield);
	}
	
	// actual useful functions
	
	public double nGenFunc(double flux) {
		if(this.poisonBurnable) {
			return flux * nGen;
		} else {
			return flux * (nGen / 100D);
		}
	}
	
	public double nBurnFunc(double flux) {
		if(this.poisonBurnable) {
			return (flux * flux) / nBurn;
		} else {
			return 0;
		}
	}
	
	public static double getNPoisonLevelPercent(ItemStack stack) {
		return getNPoisonLevels(stack) / 100D;
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return getDurabilityForDisplay(stack) > 0D;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return 1D - getEnrichment(stack);
	}
	
	// NBT? more like CBT
	
	public static void setDepletion(ItemStack stack, double depletion) {
		setDouble(stack, "yield", depletion);
	}
	
	public static double getDepletion(ItemStack stack) {
		
		if(stack.getItem() instanceof ItemCANDUBundle) {
			return getDouble(stack, "depletion");
		}
		
		return 0;
	}
	
	public static void setCoreHeat(ItemStack stack, double coreHeat) {
		setDouble(stack, "coreHeat", coreHeat);
	}
	
	public static double getCoreHeat(ItemStack stack) {
		return getDouble(stack, "coreHeat");
	}
	
	public static void setSkinHeat(ItemStack stack, double skinHeat) {
		setDouble(stack, "skinHeat", skinHeat);
	}
	
	public static double getSkinHeat(ItemStack stack) {
		return getDouble(stack, "skinHeat");
	}
	
	public static void setNPoisonLevels(ItemStack stack, double nPoisonLevels) {
		setDouble(stack, "nPoisonLevels", nPoisonLevels);
	}
	
	public static double getNPoisonLevels(ItemStack stack) {
		return getDouble(stack, "nPoisonLevels");
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
	
	private static void setNBTDefaults(ItemStack stack) {

		stack.stackTagCompound = new NBTTagCompound();
		setDepletion(stack, ((ItemCANDUBundle)stack.getItem()).depletion);
		setCoreHeat(stack, 20.0D);
		setSkinHeat(stack, 20.0D);
		setNPoisonLevels(stack, 0D);
	}
	
}