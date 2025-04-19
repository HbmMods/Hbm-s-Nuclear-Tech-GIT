package com.hbm.items.machine;

import java.util.List;
import java.util.Locale;

import com.hbm.items.ItemEnumMulti;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.render.icon.RGBMutatorInterpolatedComponentRemap;
import com.hbm.render.icon.TextureAtlasSpriteMutatable;
import com.hbm.util.EnumUtil;
import com.hbm.util.function.Function;
import com.hbm.util.function.Function.*;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

/*
 * Watz Isotropic Fuel, Oxidized
 */
public class ItemWatzPellet extends ItemEnumMulti {

	public ItemWatzPellet() {
		super(EnumWatzType.class, true, true);
		this.setMaxStackSize(16);
		this.setCreativeTab(MainRegistry.controlTab);
	}

	public static enum EnumWatzType {

		SCHRABIDIUM(	0x32FFFF, 0x005C5C, 2_000,	20D,	0.01D,		new FunctionLinear(1.5D), new FunctionSqrtFalling(10D), null),
		ANTIMATTER(		0x000000, 0x000000, 0,		20D,	0.005D,		new FunctionLinear(5D),  new FunctionSqrtFalling(100D), null),
		TESTOBJFOE(		0x3CB371, 0x000000, 0,		11.1D,	0.0011D,	new FunctionExperiment(11.1D), new FunctionSqrtFalling(50D), null),
		DIGAMMA(		0xFF1493, 0xC71585, 100_00000,	2000D,	11D,		new FunctionQuadratic(111000D), new FunctionSqrtFalling(5000D), null),
		XFE(			0x4169E1, 0x191970, 0,		5D,		0.0050D,	new FunctionLinear(5D), new FunctionSqrtFalling(50D), null),
		GLDONE(			0xDAA520, 0x8B4513, 0,		10D,	0.0062D,	new FunctionSqrt(16.2D), new FunctionSqrtFalling(15D), null),
		GLDSX(			0xDAA520, 0xCD853F, 3_100,	3.1D,	0.0062D,	new FunctionLinear(5D), new FunctionSqrtFalling(10D), null),
		GLDTWO(			0xDAA520, 0x800000, 0,		6.2D,	0.0062D,	new FunctionLinear(10D), new FunctionSqrtFalling(10D), null),
		GLDSY(			0xDAA520, 0xBC8F8F, 5_000,	10D,	0.005D,		new FunctionLinear(15D), new FunctionSqrtFalling(10D), null),
		HES(			0x66DCD6, 0x023933, 1_750,	20D,	0.005D,		new FunctionLinear(1.25D), new FunctionSqrtFalling(15D), null),
		MES(			0xCBEADF, 0x28473C, 1_500,	15D,	0.0025D,	new FunctionLinear(1.15D), new FunctionSqrtFalling(15D), null),
		LES(			0xABB4A8, 0x0C1105, 1_250,	15D,	0.00125D,	new FunctionLinear(1D), new FunctionSqrtFalling(20D), null),
		HEN(			0xA6B2A6, 0x030F03, 0,		10D,	0.0005D,	new FunctionSqrt(100), new FunctionSqrtFalling(10D), null),
		MEU(			0xC1C7BD, 0x2B3227, 0,		10D,	0.0005D,	new FunctionSqrt(75), new FunctionSqrtFalling(10D), null),
		MEP(			0x9AA3A0, 0x111A17, 0,		15D,	0.0005D,	new FunctionSqrt(150), new FunctionSqrtFalling(10D), null),
		LEAD(			0xA6A6B2, 0x03030F, 0,		0,		0.0025D,	null, null, new FunctionSqrt(10)), //standard absorber, negative coefficient
		BORON(			0xBDC8D2, 0x29343E, 0,		0,		0.0025D,	null, null, new FunctionLinear(10)), //improved absorber, linear
		DU(				0xC1C7BD, 0x2B3227, 0,		0,		0.0025D,	null, null, new FunctionQuadratic(1D, 1D).withDiv(100)), //absorber with positive coefficient
		NQD(			0x4B4B4B, 0x121212, 2_000,	20,		0.01D,		new FunctionLinear(2D), new FunctionSqrt(1D/25D).withOff(25D * 25D), null),
		NQR(			0x2D2D2D, 0x0B0B0B, 2_500,	30,		0.01D,		new FunctionLinear(1.5D), new FunctionSqrt(1D/25D).withOff(25D * 25D), null),
		// New realistic Watz Pellets
		AM241(			0x4B0082, 0x2E0051, 1_800,	25D,	0.0008D,		new FunctionLinear(2.0D), new FunctionSqrtFalling(20D), null), // High energy density, long half-life
		CM244(			0xFF6B00, 0xCC5500, 2_200,	30D,	0.0009D,		new FunctionLinear(2.5D), new FunctionSqrtFalling(25D), null), // High neutron emission
		CF252(			0xFFD700, 0xDAA520, 2_800,	35D,	0.0012D,		new FunctionLinear(3.0D), new FunctionSqrtFalling(30D), null), // Powerful neutron emitter
		TH232(			0xC0C0C0, 0x808080, 1_000,	15D,	0.0004D,		new FunctionLinear(1.2D), new FunctionSqrtFalling(15D), null), // Natural thorium fuel
		MOX(			0x654321, 0x3B2512, 1_600,	20D,	0.0006D,		new FunctionLinear(1.8D), new FunctionSqrtFalling(18D), null), // Mixed oxide fuel
		LEO(			0x87CEEB, 0x4F94CD, 1_400,	18D,	0.0005D,		new FunctionLinear(1.6D), new FunctionSqrtFalling(16D), null), // Low-enriched osmium
		HEO(			0x00008B, 0x000066, 2_000,	28D,	0.0007D,		new FunctionLinear(2.2D), new FunctionSqrtFalling(22D), null), // High-enriched osmium
		BER(			0x50C878, 0x2E8B57, 2_400,	32D,	0.0010D,		new FunctionLinear(2.8D), new FunctionSqrtFalling(15D), null), // Berkelium fuel
		NP237(			0x008080, 0x004040, 1_900,	26D,	0.0008D,		new FunctionLinear(2.1D), new FunctionSqrtFalling(21D), null), // Neptunium fuel
		TPO(			0x4B0082, 0x00008B, 2_600,	34D,	0.0011D,		new FunctionLinear(3.2D), new FunctionSqrtFalling(32D), null), // Triple-phase osmium
		
		// New Non-Self-Igniting Advanced Pellets
		PU239_NSI(		0xFF4500, 0xB22222, 0,		20D,	0.0007D,	new FunctionLinear(1.8D), new FunctionSqrtFalling(40D), null), // Plutonium-239, weapons grade
		U235_NSI(		0x98FB98, 0x228B22, 0,		18D,	0.0006D,	new FunctionLinear(1.6D), new FunctionSqrtFalling(36D), null), // Uranium-235, enriched
		U233_NSI(		0x7CFC00, 0x32CD32, 0,		16D,	0.0005D,	new FunctionLinear(1.4D), new FunctionSqrtFalling(32D), null), // Uranium-233, thorium cycle
		AM243_NSI(		0x9370DB, 0x6A5ACD, 0,		22D,	0.0008D,	new FunctionLinear(2.0D), new FunctionSqrtFalling(30D), null), // Americium-243
		CM245_NSI(		0xFFA500, 0xFF8C00, 0,		24D,	0.0009D,	new FunctionLinear(2.2D), new FunctionSqrtFalling(34D), null), // Curium-245
		
		// New Non-Self-Igniting Research Pellets
		BK247_NSI(		0x48D1CC, 0x20B2AA, 0,		26D,	0.0010D,	new FunctionSqrt(2.4D), new FunctionSqrtFalling(26D), null),   // Berkelium-247
		ES253_NSI(		0xE6E6FA, 0x9370DB, 0,		28D,	0.0011D,	new FunctionSqrt(2.6D), new FunctionSqrtFalling(28D), null),   // Einsteinium-253
		FM257_NSI(		0x9932CC, 0x8B008B, 0,		30D,	0.0012D,	new FunctionSqrt(2.8D), new FunctionSqrtFalling(30D), null),   // Fermium-257
		
		// New Non-Self-Igniting Experimental Pellets
		TIBERIUM_NSI(	0x7FFF00, 0x32CD32, 0,		32D,	0.0013D,	new FunctionLinear(3.0D), new FunctionSqrtFalling(32D), null), // Tiberium-based
		EUPHEMIUM_NSI(	0xFF1493, 0xC71585, 0,		34D,	0.0014D,	new FunctionSqrt(3.2D), new FunctionSqrtFalling(34D), null),   // Euphemium-based
		STARSTONE_NSI(	0x4169E1, 0x0000CD, 0,		36D,	0.0015D,	new FunctionLinear(3.4D), new FunctionSqrtFalling(36D), null); // Starstone alloy
		
		public double yield = 500_000_000;
		public int colorLight;
		public int colorDark;
		public double mudContent;	//how much mud per reaction flux should be produced
		public double passive;		//base flux emission
		public double heatEmission;	//reactivity(1) to heat (heat per outgoing flux)
		public Function burnFunc;	//flux to reactivity(0) (classic reactivity)
		public Function heatDiv;	//reactivity(0) to reactivity(1) based on heat (temperature coefficient)
		public Function absorbFunc;	//flux to heat (flux absobtion for non-active component)
		
		private EnumWatzType(int colorLight, int colorDark, double passive, double heatEmission, double mudContent, Function burnFunction, Function heatDivisor, Function absorbFunction) {
			this.colorLight = colorLight;
			this.colorDark = colorDark;
			this.passive = passive;
			this.heatEmission = heatEmission;
			this.mudContent = mudContent / 2D;
			this.burnFunc = burnFunction;
			this.heatDiv = heatDivisor;
			this.absorbFunc = absorbFunction;
		}
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		
		Enum[] enums = theEnum.getEnumConstants();
		this.icons = new IIcon[enums.length];
		
		if(reg instanceof TextureMap) {
			TextureMap map = (TextureMap) reg;
			
			for(int i = 0; i < EnumWatzType.values().length; i++) {
				EnumWatzType type = EnumWatzType.values()[i];
				String placeholderName = this.getIconString() + "-" + (type.name() + this.getUnlocalizedName());
				int light = this == ModItems.watz_pellet_depleted ? desaturate(type.colorLight) : type.colorLight;
				int dark = this == ModItems.watz_pellet_depleted ? desaturate(type.colorDark) : type.colorDark;
				TextureAtlasSpriteMutatable mutableIcon = new TextureAtlasSpriteMutatable(placeholderName, new RGBMutatorInterpolatedComponentRemap(0xD2D2D2, 0x333333, light, dark));
				map.setTextureEntry(placeholderName, mutableIcon);
				icons[i] = mutableIcon;
			}
		}
		
		this.itemIcon = reg.registerIcon(this.getIconString());
	}
	
	public static int desaturate(int color) {
		int r = (color & 0xff0000) >> 16;
		int g = (color & 0x00ff00) >> 8;
		int b = (color & 0x0000ff);
		
		int avg = (r + g + b) / 3;
		double approach = 0.9;
		double mult = 0.75;

		r -= (r - avg) * approach;
		g -= (g - avg) * approach;
		b -= (b - avg) * approach;

		r *= mult;
		g *= mult;
		b *= mult;
		
		return (r << 16) | (g << 8) | b;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		IIcon icon = super.getIconFromDamage(meta);
		return icon == null ? this.itemIcon : icon; //fallback if TextureMap fails during register
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		
		if(this != ModItems.watz_pellet) return;
		
		EnumWatzType num = EnumUtil.grabEnumSafely(EnumWatzType.class, stack.getItemDamage());
		
		list.add(EnumChatFormatting.GREEN + "Depletion: " + String.format(Locale.US, "%.1f", getDurabilityForDisplay(stack) * 100D) + "%");
		
		String color = EnumChatFormatting.GOLD + "";
		String reset = EnumChatFormatting.RESET + "";

		if(num.passive > 0){
			list.add(color + "Base fission rate: " + reset + num.passive);
			list.add(EnumChatFormatting.RED + "Self-igniting!");
		}
		if(num.heatEmission > 0) list.add(color + "Heat per flux: " + reset + num.heatEmission + " TU");
		if(num.burnFunc != null) {
			list.add(color + "Reaction function: " + reset + num.burnFunc.getLabelForFuel());
			list.add(color + "Fuel type: " + reset + num.burnFunc.getDangerFromFuel());
		}
		if(num.heatDiv != null) list.add(color + "Thermal multiplier: " + reset + num.heatDiv.getLabelForFuel() + " TU⁻¹");
		if(num.absorbFunc != null) list.add(color + "Flux capture: " + reset + num.absorbFunc.getLabelForFuel());
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return this == ModItems.watz_pellet && getDurabilityForDisplay(stack) > 0D;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return 1D - getEnrichment(stack);
	}
	
	public static double getEnrichment(ItemStack stack) {
		EnumWatzType num = EnumUtil.grabEnumSafely(EnumWatzType.class, stack.getItemDamage());
		return getYield(stack) / num.yield;
	}
	

	public static double getYield(ItemStack stack) {
		return getDouble(stack, "yield");
	}
	
	public static void setYield(ItemStack stack, double yield) {
		setDouble(stack, "yield", yield);
	}
	
	public static void setDouble(ItemStack stack, String key, double yield) {
		if(!stack.hasTagCompound()) setNBTDefaults(stack);
		stack.stackTagCompound.setDouble(key, yield);
	}
	
	public static double getDouble(ItemStack stack, String key) {
		if(!stack.hasTagCompound()) setNBTDefaults(stack);
		return stack.stackTagCompound.getDouble(key);
	}
	
	private static void setNBTDefaults(ItemStack stack) {
		EnumWatzType num = EnumUtil.grabEnumSafely(EnumWatzType.class, stack.getItemDamage());
		stack.stackTagCompound = new NBTTagCompound();
		setYield(stack, num.yield);
	}
	
	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		if(this != ModItems.watz_pellet) return;
		setNBTDefaults(stack); //minimize the window where NBT screwups can happen
	}
}

