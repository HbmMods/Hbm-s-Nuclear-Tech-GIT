package com.hbm.items.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.BiFunction;
import java.util.function.Function;

import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.rbmk.IRBMKFluxReceiver.NType;
import com.hbm.util.i18n.I18nUtil;
import com.hbm.tileentity.machine.rbmk.RBMKDials;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemRBMKRod extends Item {

	public ItemRBMKPellet pellet;
	public String fullName = "";			//full name of the fuel rod
	public double reactivity;					//endpoint of the function
	public double selfRate;					//self-inflicted flux from self-igniting fuels
	public EnumBurnFunc function = EnumBurnFunc.LOG_TEN;
	public EnumDepleteFunc depFunc = EnumDepleteFunc.GENTLE_SLOPE;
	public double xGen = 0.5D;				//multiplier for xenon production
	public double xBurn = 50D;				//divider for xenon burnup
	public double heat = 1D;				//heat produced per outFlux
	public double yield;					//total potential inFlux the rod can take in its lifetime
	public double meltingPoint = 1000D;		//the maximum heat of the rod's hull before shit hits the fan. the core can be as hot as it wants to be
	public double diffusion = 0.02D;		//the speed at which the core heats the hull
	public NType nType = NType.SLOW;		//neutronType, the most efficient neutron type for fission
	public NType rType = NType.FAST;		//releaseType, the type of neutrons released by this fuel

	/*   _____
	 * ,I I I I,
	 * |'-----'|
	 * |       |
	 *  '-----'
	 *  I I I I
	 *  I I I I
	 *  I I I I
	 *  I I I I
	 *  I I I I
	 *  I I I I
	 * ,I I I I,
	 * |'-----'|
	 * |       |
	 *  '-----'
	 *  I I I I
	 *
	 *  i drew a fuel rod yay
	 */

	public static List<ItemRBMKRod> craftableRods = new ArrayList<>();

	public ItemRBMKRod(ItemRBMKPellet pellet) {
		this(pellet.fullName);
		this.pellet = pellet;

		craftableRods.add(this);
	}

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

	public ItemRBMKRod setStats(double funcEnd) {
		return setStats(funcEnd, 0);
	}

	public ItemRBMKRod setStats(double funcEnd, double selfRate) {
		this.reactivity = funcEnd;
		this.selfRate = selfRate;
		return this;
	}

	public ItemRBMKRod setFunction(EnumBurnFunc func) {
		this.function = func;
		return this;
	}

	public ItemRBMKRod setDepletionFunction(EnumDepleteFunc func) {
		this.depFunc = func;
		return this;
	}

	public ItemRBMKRod setXenon(double gen, double burn) {
		this.xGen = gen;
		this.xBurn = burn;
		return this;
	}

	public ItemRBMKRod setHeat(double heat) {
		this.heat = heat;
		return this;
	}

	public ItemRBMKRod setDiffusion(double diffusion) {
		this.diffusion = diffusion;
		return this;
	}

	public ItemRBMKRod setMeltingPoint(double meltingPoint) {
		this.meltingPoint = meltingPoint;
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
	public double burn(World world, ItemStack stack, double inFlux) {

		inFlux += selfRate;

		//if xenon poison is enabled
		if(RBMKDials.getXenon(world)) {
			double xenon = getPoison(stack);
			xenon -= xenonBurnFunc(inFlux);

			inFlux *= (1D - getPoisonLevel(stack));

			xenon += xenonGenFunc(inFlux);

			if(xenon < 0D) xenon = 0D;
			if(xenon > 100D) xenon = 100D;

			setPoison(stack, xenon);
		}

		double outFlux = reactivityFunc(inFlux, getEnrichment(stack)) * RBMKDials.getReactivityMod(world);

		//if depletion is enabled
		if(RBMKDials.getDepletion(world)) {
			double y = getYield(stack);
			y -= inFlux;

			if(y < 0D) y = 0D;

			setYield(stack, y);
		}

		double coreHeat = this.getCoreHeat(stack);
		coreHeat += outFlux * heat;

		this.setCoreHeat(stack, rectify(coreHeat));

		return outFlux;
	}

	private double rectify(double num) {

		if(num > 1_000_000D) num = 1_000_000D;
		if(num < 20D || Double.isNaN(num)) num = 20D;

		return num;
	}

	/**
	 * Heat up the core based on the outFlux, then move some heat to the hull
	 * @param stack
	 */
	public void updateHeat(World world, ItemStack stack, double mod) {

		double coreHeat = this.getCoreHeat(stack);
		double hullHeat = this.getHullHeat(stack);

		if(coreHeat > hullHeat) {

			double mid = (coreHeat - hullHeat) / 2D;

			coreHeat -= mid * this.diffusion * RBMKDials.getFuelDiffusionMod(world) * mod;
			hullHeat += mid * this.diffusion * RBMKDials.getFuelDiffusionMod(world) * mod;

			this.setCoreHeat(stack, rectify(coreHeat));
			this.setHullHeat(stack, rectify(hullHeat));
		}
	}

	/**
	 * return one tick's worth of heat and cool the hull of the fuel rod, this heat goes into the fuel rod assembly block
	 * @param stack
	 * @return
	 */
	public double provideHeat(World world, ItemStack stack, double heat, double mod) {

		double hullHeat = this.getHullHeat(stack);

		//metldown! the hull melts so the entire structure stops making sense
		//hull and core heats are instantly equalized into 33% of their sum each,
		//the rest is sent to the component which is always fatal
		if(hullHeat > this.meltingPoint) {
			double coreHeat = this.getCoreHeat(stack);
			double avg = (heat + hullHeat + coreHeat) / 3D;
			this.setCoreHeat(stack, avg);
			this.setHullHeat(stack, avg);
			return avg - heat;
		}

		if(hullHeat <= heat)
			return 0;

		double ret = (hullHeat - heat) / 2;

		ret *= RBMKDials.getFuelHeatProvision(world) * mod;

		hullHeat -= ret;
		this.setHullHeat(stack, hullHeat);

		return ret;
	}

	public static enum EnumBurnFunc {
		PASSIVE(EnumChatFormatting.DARK_GREEN + "SAFE / PASSIVE"),			//const, no reactivity
		LOG_TEN(EnumChatFormatting.YELLOW + "MEDIUM / LOGARITHMIC"),		//log10(x + 1) * reactivity * 50
		PLATEU(EnumChatFormatting.GREEN + "SAFE / EULER"),					//(1 - e^(-x/25)) * reactivity * 100
		ARCH(EnumChatFormatting.RED + "DANGEROUS / NEGATIVE-QUADRATIC"),	//x-(x²/1000) * reactivity
		SIGMOID(EnumChatFormatting.GREEN + "SAFE / SIGMOID"),				//100 / (1 + e^(-(x - 50) / 10)) <- tiny amount of reactivity at x=0 !
		SQUARE_ROOT(EnumChatFormatting.YELLOW + "MEDIUM / SQUARE ROOT"),	//sqrt(x) * 10 * reactivity
		LINEAR(EnumChatFormatting.RED + "DANGEROUS / LINEAR"),				//x * reactivity
		QUADRATIC(EnumChatFormatting.RED + "DANGEROUS / QUADRATIC"),		//x^2 / 100 * reactivity
		EXPERIMENTAL(EnumChatFormatting.RED + "EXPERIMENTAL / SINE SLOPE");		//x * (sin(x) + 1)

		public String title = "";

		private EnumBurnFunc(String title) {
			this.title = title;
		}
	}

	/**
	 * @param enrichment [0;100] ...or at least those are sane levels
	 * @return the amount of reactivity yielded, unmodified by xenon
	 */
	public double reactivityFunc(double in, double enrichment) {

		double flux = in * reactivityModByEnrichment(enrichment);

		switch(this.function) {
		case PASSIVE: return selfRate * enrichment;
		case LOG_TEN: return Math.log10(flux + 1) * 0.5D * reactivity;
		case PLATEU: return (1 - Math.pow(Math.E, -flux / 25D)) * reactivity;
		case ARCH: return Math.max((flux - (flux * flux / 10000D)) / 100D * reactivity, 0D);
		case SIGMOID: return reactivity / (1 + Math.pow(Math.E, -(flux - 50D) / 10D));
		case SQUARE_ROOT: return Math.sqrt(flux) * reactivity / 10D;
		case LINEAR: return flux / 100D * reactivity;
		case QUADRATIC: return flux * flux / 10000D * reactivity;
		case EXPERIMENTAL: return flux * (Math.sin(flux) + 1) * reactivity;
		}

		return 0;
	}

	public String getFuncDescription(ItemStack stack) {

		String function;

		switch(this.function) {
		case PASSIVE: function = EnumChatFormatting.RED + "" + selfRate;
			break;
		case LOG_TEN: function = "log10(%1$s + 1) * 0.5 * %2$s";
			break;
		case PLATEU: function = "(1 - e^(-%1$s / 25)) * %2$s";
			break;
		case ARCH: function = "(%1$s - %1$s² / 10000) / 100 * %2$s [0;∞]";
			break;
		case SIGMOID: function = "%2$s / (1 + e^(-(%1$s - 50) / 10))";
			break;
		case SQUARE_ROOT: function = "sqrt(%1$s) * %2$s / 10";
			break;
		case LINEAR: function = "%1$s / 100 * %2$s";
			break;
		case QUADRATIC: function = "%1$s² / 10000 * %2$s";
			break;
		case EXPERIMENTAL: function = "%1$s * (sin(%1$s) + 1) * %2$s";
			break;
		default: function = "ERROR";
		}

		double enrichment = getEnrichment(stack);

		if(enrichment < 1) {
			enrichment = reactivityModByEnrichment(enrichment);
			String reactivity = EnumChatFormatting.YELLOW + "" + ((int)(this.reactivity * enrichment * 1000D) / 1000D) + EnumChatFormatting.WHITE;
			String enrichmentPer = EnumChatFormatting.GOLD + " (" + ((int)(enrichment * 1000D) / 10D) + "%)";

			return String.format(Locale.US, function, selfRate > 0 ? "(x" + EnumChatFormatting.RED + " + " + selfRate + "" + EnumChatFormatting.WHITE + ")" : "x", reactivity).concat(enrichmentPer);
		}

		return String.format(Locale.US, function, selfRate > 0 ? "(x" + EnumChatFormatting.RED + " + " + selfRate + "" + EnumChatFormatting.WHITE + ")" : "x", reactivity);
	}

	public static enum EnumDepleteFunc {
		LINEAR,			//old function
		RAISING_SLOPE,	//for breeding fuels such as MEU, maximum of 110% at 28% depletion
		BOOSTED_SLOPE,	//for strong breeding fuels such Th232, maximum of 132% at 64% depletion
		GENTLE_SLOPE,	//recommended for most fuels, maximum barely over the start, near the beginning
		STATIC;			//for arcade-style neutron sources
	}

	public double reactivityModByEnrichment(double enrichment) {

		switch(this.depFunc) {
		default:
		case LINEAR: return enrichment;
		case STATIC: return 1D;
		case BOOSTED_SLOPE: return enrichment + Math.sin((enrichment - 1) * (enrichment - 1) * Math.PI); //x + sin([x - 1]^2 * pi) works
		case RAISING_SLOPE: return enrichment + (Math.sin(enrichment * Math.PI) / 2D); //x + (sin(x * pi) / 2) actually works
		case GENTLE_SLOPE: return enrichment + (Math.sin(enrichment * Math.PI) / 3D); //x + (sin(x * pi) / 3) also works
		}
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

	// START Special flux curve handling!
	// Nothing really uses this yet, though it's a really fun feature to play around with.

	// For the RBMK handler to see if the rod is special.
	public boolean specialFluxCurve = false;

	public ItemRBMKRod setFluxCurve(boolean bool) {
		specialFluxCurve = bool;
		return this;
	}

	/** Double 1: Flux ratio in.
	 * Double 2: Depletion value.
	 * Return double: Output flux ratio.
	 **/
	BiFunction<Double, Double, Double> ratioCurve;

	/** Double 1: Flux quantity in. <br>
	 * Double 2: Flux ratio in. <br>
	 * Return double: Output flux quantity.
	 **/
	BiFunction<Double, Double, Double> fluxCurve;

	public ItemRBMKRod setOutputRatioCurve(Function<Double, Double> func) {
		this.ratioCurve = (fluxRatioIn, depletion) -> func.apply(fluxRatioIn) * 1.0D;
		return this;
	}

	public ItemRBMKRod setDepletionOutputRatioCurve(BiFunction<Double, Double, Double> func) {
		this.ratioCurve = func;
		return this;
	}

	public ItemRBMKRod setOutputFluxCurve(BiFunction<Double, Double, Double> func) {
		this.fluxCurve = func;
		return this;
	}

	public double fluxRatioOut(double fluxRatioIn, double depletion) {
		return MathHelper.clamp_double(ratioCurve.apply(fluxRatioIn, depletion), 0, 1);
	}

	public double fluxFromRatio(double quantity, double ratio) {
		return fluxCurve.apply(quantity, ratio);
	}

	// END Special flux curve handling.

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {

		list.add(EnumChatFormatting.ITALIC + this.fullName);

		if(this == ModItems.rbmk_fuel_drx) {

			if(ItemRBMKRod.getHullHeat(stack) >= 50 || ItemRBMKRod.getCoreHeat(stack) >= 50) {
				list.add(EnumChatFormatting.GOLD + I18nUtil.resolveKey("desc.item.wasteCooling"));
			}

			if(selfRate > 0 || this.function == EnumBurnFunc.SIGMOID) {
				list.add(EnumChatFormatting.RED + I18nUtil.resolveKey("trait.rbmx.source"));
			}

			list.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey("trait.rbmx.depletion", ((int)(((yield - getYield(stack)) / yield) * 100000)) / 1000D + "%"));
			list.add(EnumChatFormatting.DARK_PURPLE + I18nUtil.resolveKey("trait.rbmx.xenon", ((int)(getPoison(stack) * 1000D) / 1000D) + "%"));
			list.add(EnumChatFormatting.BLUE + I18nUtil.resolveKey("trait.rbmx.splitsWith", I18nUtil.resolveKey(nType.unlocalized + ".x")));
			list.add(EnumChatFormatting.BLUE + I18nUtil.resolveKey("trait.rbmx.splitsInto", I18nUtil.resolveKey(rType.unlocalized + ".x")));
			list.add(EnumChatFormatting.YELLOW + I18nUtil.resolveKey("trait.rbmx.fluxFunc", EnumChatFormatting.WHITE + getFuncDescription(stack)));
			list.add(EnumChatFormatting.YELLOW + I18nUtil.resolveKey("trait.rbmx.funcType", this.function.title));
			list.add(EnumChatFormatting.YELLOW + I18nUtil.resolveKey("trait.rbmx.xenonGen", EnumChatFormatting.WHITE + "x * " + xGen));
			list.add(EnumChatFormatting.YELLOW + I18nUtil.resolveKey("trait.rbmx.xenonBurn", EnumChatFormatting.WHITE + "x² / " + xBurn));
			list.add(EnumChatFormatting.GOLD + I18nUtil.resolveKey("trait.rbmx.heat", heat + "°C"));
			list.add(EnumChatFormatting.GOLD + I18nUtil.resolveKey("trait.rbmx.diffusion", diffusion + "¹/²"));
			list.add(EnumChatFormatting.RED + I18nUtil.resolveKey("trait.rbmx.skinTemp", ((int)(getHullHeat(stack) * 10D) / 10D) + "m"));
			list.add(EnumChatFormatting.RED + I18nUtil.resolveKey("trait.rbmx.coreTemp", ((int)(getCoreHeat(stack) * 10D) / 10D) + "m"));
			list.add(EnumChatFormatting.DARK_RED + I18nUtil.resolveKey("trait.rbmx.melt", meltingPoint + "m"));

		} else {

			if(ItemRBMKRod.getHullHeat(stack) >= 50 || ItemRBMKRod.getCoreHeat(stack) >= 50) {
				list.add(EnumChatFormatting.GOLD + I18nUtil.resolveKey("desc.item.wasteCooling"));
			}

			if(selfRate > 0 || this.function == EnumBurnFunc.SIGMOID) {
				list.add(EnumChatFormatting.RED + I18nUtil.resolveKey("trait.rbmk.source"));
			}

			list.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey("trait.rbmk.depletion", ((int)(((yield - getYield(stack)) / yield) * 100000D)) / 1000D + "%"));
			list.add(EnumChatFormatting.DARK_PURPLE + I18nUtil.resolveKey("trait.rbmk.xenon", ((int)(getPoison(stack) * 1000D) / 1000D) + "%"));
			list.add(EnumChatFormatting.BLUE + I18nUtil.resolveKey("trait.rbmk.splitsWith", I18nUtil.resolveKey(nType.unlocalized)));
			list.add(EnumChatFormatting.BLUE + I18nUtil.resolveKey("trait.rbmk.splitsInto", I18nUtil.resolveKey(rType.unlocalized)));
			list.add(EnumChatFormatting.YELLOW + I18nUtil.resolveKey("trait.rbmk.fluxFunc", EnumChatFormatting.WHITE + getFuncDescription(stack)));
			list.add(EnumChatFormatting.YELLOW + I18nUtil.resolveKey("trait.rbmk.funcType", this.function.title));
			list.add(EnumChatFormatting.YELLOW + I18nUtil.resolveKey("trait.rbmk.xenonGen", EnumChatFormatting.WHITE + "x * " + xGen));
			list.add(EnumChatFormatting.YELLOW + I18nUtil.resolveKey("trait.rbmk.xenonBurn", EnumChatFormatting.WHITE + "x² / " + xBurn));
			list.add(EnumChatFormatting.GOLD + I18nUtil.resolveKey("trait.rbmk.heat", heat + "°C"));
			list.add(EnumChatFormatting.GOLD + I18nUtil.resolveKey("trait.rbmk.diffusion", diffusion + "¹/²"));
			list.add(EnumChatFormatting.RED + I18nUtil.resolveKey("trait.rbmk.skinTemp", ((int)(getHullHeat(stack) * 10D) / 10D) + "°C"));
			list.add(EnumChatFormatting.RED + I18nUtil.resolveKey("trait.rbmk.coreTemp", ((int)(getCoreHeat(stack) * 10D) / 10D) + "°C"));
			list.add(EnumChatFormatting.DARK_RED + I18nUtil.resolveKey("trait.rbmk.melt", meltingPoint + "°C"));
		}

		/*list.add(EnumChatFormatting.GREEN + "Depletion: " + ((int)(((yield - getYield(stack)) / yield) * 10000)) / 10000D + "%");
		list.add(EnumChatFormatting.DARK_PURPLE + "Xenon poison: " + ((getPoison(stack) * 100D) / 100D) + "%");
		list.add(EnumChatFormatting.BLUE + "Splits with: " + nType.unlocalized);
		list.add(EnumChatFormatting.BLUE + "Splits into: " + rType.unlocalized);
		list.add(EnumChatFormatting.YELLOW + "Flux function: " + EnumChatFormatting.WHITE + "" + funcEnd + " * x" + (selfRate > 0 ? (EnumChatFormatting.RED + " + " + selfRate) : ""));
		list.add(EnumChatFormatting.YELLOW + "Xenon gen function: " + EnumChatFormatting.WHITE + "x * " + xGen);
		list.add(EnumChatFormatting.YELLOW + "Xenon burn function: " + EnumChatFormatting.WHITE + "x² * " + xBurn);
		list.add(EnumChatFormatting.GOLD + "Heat per tick at full power: " + heat + "°C");
		list.add(EnumChatFormatting.GOLD + "Diffusion: " + diffusion + "°C/t");
		list.add(EnumChatFormatting.RED + "Skin temp: " + ((int)(getHullHeat(stack) * 10D) / 10D) + "°C");
		list.add(EnumChatFormatting.RED + "Core temp: " + ((int)(getCoreHeat(stack) * 10D) / 10D) + "°C");
		list.add(EnumChatFormatting.DARK_RED + "Melting point: " + meltingPoint + "°C");*/

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

	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		setNBTDefaults(stack); //minimize the window where NBT screwups can happen
	}
}
