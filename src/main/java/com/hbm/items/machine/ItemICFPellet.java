package com.hbm.items.machine;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.NTMMaterial;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.util.BobMathUtil;
import com.hbm.util.EnumUtil;
import com.hbm.util.i18n.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;

public class ItemICFPellet extends Item {
	
	protected IIcon iconBG;
	
	public static enum EnumICFFuel {

		HYDROGEN(	0x4040FF,	1.00D,	0.85D,	1.00D),
		DEUTERIUM(	0x2828CB,	1.25D,	1.00D,	1.00D),
		TRITIUM(	0x000092,	1.50D,	1.00D,	1.05D),
		HELIUM3(	0xFFF09F,	1.75D,	1.00D,	1.25D),
		HELIUM4(	0xFF9B60,	2.00D,	1.00D,	1.50D),
		LITHIUM(	0xE9E9E9,	1.25D,	0.85D,	2.00D),
		BERYLLIUM(	0xA79D80,	2.00D,	1.00D,	2.50D),
		BORON(		0x697F89,	3.00D,	0.50D,	3.50D),
		CARBON(		0x454545,	2.00D,	1.00D,	5.00D),
		OXYGEN(		0xB4E2FF,	1.25D,	1.50D,	7.50D),
		SODIUM(		0xDFE4E7,	3.00D,	0.75D,	8.75D),
		//aluminium, silicon, phosphorus
		CHLORINE(	0xDAE598,	2.50D,	1.00D,	10.0D),
		CALCIUM(	0xD2C7A9,	3.00D,	1.00D,	12.5D),
		//titanium
		;
		
		public int color;
		public double reactionMult;
		public double depletionSpeed;
		public double fusingDifficulty;
		
		private EnumICFFuel(int color, double react, double depl, double laser) {
			this.color = color;
			this.reactionMult = react;
			this.depletionSpeed = depl;
			this.fusingDifficulty = laser;
		}
	}
	
	public static HashMap<FluidType, EnumICFFuel> fluidMap = new HashMap();
	public static HashMap<NTMMaterial, EnumICFFuel> materialMap = new HashMap();
	
	public static void init() {
		if(!fluidMap.isEmpty() && !materialMap.isEmpty()) return;
		fluidMap.put(Fluids.HYDROGEN, EnumICFFuel.HYDROGEN);
		fluidMap.put(Fluids.DEUTERIUM, EnumICFFuel.DEUTERIUM);
		fluidMap.put(Fluids.TRITIUM, EnumICFFuel.TRITIUM);
		fluidMap.put(Fluids.HELIUM3, EnumICFFuel.HELIUM3);
		fluidMap.put(Fluids.HELIUM4, EnumICFFuel.HELIUM4);
		materialMap.put(Mats.MAT_LITHIUM, EnumICFFuel.LITHIUM);
		materialMap.put(Mats.MAT_BERYLLIUM, EnumICFFuel.BERYLLIUM);
		materialMap.put(Mats.MAT_BORON, EnumICFFuel.BORON);
		materialMap.put(Mats.MAT_GRAPHITE, EnumICFFuel.CARBON);
		fluidMap.put(Fluids.OXYGEN, EnumICFFuel.OXYGEN);
		materialMap.put(Mats.MAT_SODIUM, EnumICFFuel.SODIUM);
		fluidMap.put(Fluids.CHLORINE, EnumICFFuel.CHLORINE);
		materialMap.put(Mats.MAT_CALCIUM, EnumICFFuel.CALCIUM);
	}

	public ItemICFPellet() {
		this.setMaxStackSize(1);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		list.add(this.setup(EnumICFFuel.DEUTERIUM, EnumICFFuel.TRITIUM, false));
		list.add(this.setup(EnumICFFuel.HELIUM3, EnumICFFuel.HELIUM4, false));
		list.add(this.setup(EnumICFFuel.LITHIUM, EnumICFFuel.OXYGEN, false));
		list.add(this.setup(EnumICFFuel.SODIUM, EnumICFFuel.CHLORINE, true));
		list.add(this.setup(EnumICFFuel.BERYLLIUM, EnumICFFuel.CALCIUM, true));
	}
	
	public static long getMaxDepletion(ItemStack stack) {
		long base = 50_000_000_000L;
		base /= getType(stack, true).depletionSpeed;
		base /= getType(stack, false).depletionSpeed;
		return base;
	}
	
	public static long getFusingDifficulty(ItemStack stack) {
		long base = 10_000_000L;
		base *= getType(stack, true).fusingDifficulty * getType(stack, false).fusingDifficulty;
		if(stack.hasTagCompound() && stack.stackTagCompound.getBoolean("muon")) base /= 4;
		return base;
	}
	
	public static long getDepletion(ItemStack stack) {
		if(!stack.hasTagCompound()) return 0L;
		return stack.stackTagCompound.getLong("depletion");
	}
	
	public static long react(ItemStack stack, long heat) {
		if(!stack.hasTagCompound()) stack.stackTagCompound = new NBTTagCompound();
		stack.stackTagCompound.setLong("depletion", stack.stackTagCompound.getLong("depletion") + heat);
		return (long) (heat * getType(stack, true).reactionMult * getType(stack, false).reactionMult);
	}
	
	public static ItemStack setup(EnumICFFuel type1, EnumICFFuel type2, boolean muon) {
		return setup(new ItemStack(ModItems.icf_pellet), type1, type2, muon);
	}
	
	public static ItemStack setup(ItemStack stack, EnumICFFuel type1, EnumICFFuel type2, boolean muon) {
		if(!stack.hasTagCompound()) stack.stackTagCompound = new NBTTagCompound();
		stack.stackTagCompound.setByte("type1", (byte) type1.ordinal());
		stack.stackTagCompound.setByte("type2", (byte) type2.ordinal());
		stack.stackTagCompound.setBoolean("muon", muon);
		return stack;
	}
	
	public static EnumICFFuel getType(ItemStack stack, boolean first) {
		if(!stack.hasTagCompound()) return first ? EnumICFFuel.DEUTERIUM : EnumICFFuel.TRITIUM;
		return EnumUtil.grabEnumSafely(EnumICFFuel.class, stack.stackTagCompound.getByte("type" + (first ? 1 : 2)));
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return getDurabilityForDisplay(stack) > 0D;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return (double) getDepletion(stack) / (double) getMaxDepletion(stack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		super.registerIcons(reg);
		this.iconBG = reg.registerIcon(RefStrings.MODID + ":icf_pellet_bg");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int meta, int pass) {
		return pass == 1 ? super.getIconFromDamageForRenderPass(meta, pass) : this.iconBG;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass) {
		if(pass == 0) {
			EnumICFFuel type1 = this.getType(stack, true);
			EnumICFFuel type2 = this.getType(stack, false);
			int r = (((type1.color & 0xff0000) >> 16) + ((type2.color & 0xff0000) >> 16)) / 2;
			int g = (((type1.color & 0x00ff00) >> 8) + ((type2.color & 0x00ff00) >> 8)) / 2;
			int b = ((type1.color & 0x0000ff) + (type2.color & 0x0000ff)) / 2;
			return r << 16 | g << 8 | b;
		}
		return 0xffffff;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		boolean muon = stack.hasTagCompound() && stack.stackTagCompound.getBoolean("muon");
		list.add(EnumChatFormatting.GREEN + "Depletion: " + String.format(Locale.US, "%.1f", getDurabilityForDisplay(stack) * 100D) + "%");
		list.add(EnumChatFormatting.YELLOW + "Fuel: " + I18nUtil.resolveKey("icffuel." + getType(stack, true).name().toLowerCase(Locale.US)) + " / " + I18nUtil.resolveKey("icffuel." + getType(stack, false).name().toLowerCase(Locale.US)));
		list.add(EnumChatFormatting.YELLOW + "Heat required: " + BobMathUtil.getShortNumber(this.getFusingDifficulty(stack)) + "TU");
		list.add(EnumChatFormatting.YELLOW + "Reactivity multiplier: x" + (int) (getType(stack, true).reactionMult * getType(stack, false).reactionMult * 100) / 100D);
		if(muon) list.add(EnumChatFormatting.DARK_AQUA + "Muon catalyzed!");
	}
}
