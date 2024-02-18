package com.hbm.items.special;

import com.hbm.items.ItemEnumMulti;
import com.hbm.items.special.ItemByproduct.EnumByproduct;
import static com.hbm.items.special.ItemByproduct.EnumByproduct.*;

import java.util.Locale;

import com.hbm.lib.RefStrings;
import com.hbm.util.EnumUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

public class ItemBedrockOre extends ItemEnumMulti {

	protected IIcon overlayIcon;

	public ItemBedrockOre() {
		super(EnumBedrockOre.class, false, false);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister p_94581_1_) {
		super.registerIcons(p_94581_1_);

		this.overlayIcon = p_94581_1_.registerIcon(RefStrings.MODID + ":ore_overlay");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int meta, int pass) {
		return pass == 1 ? this.overlayIcon : super.getIconFromDamageForRenderPass(meta, pass);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass) {
		
		if(pass == 1) {
			EnumBedrockOre ore = EnumUtil.grabEnumSafely(EnumBedrockOre.class, stack.getItemDamage());
			return ore.color;
		}
		
		return 0xffffff;
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		
		EnumBedrockOre ore = EnumUtil.grabEnumSafely(EnumBedrockOre.class, stack.getItemDamage());
		String oreName = StatCollector.translateToLocal("item.ore." + ore.oreName.toLowerCase(Locale.US));
		return StatCollector.translateToLocalFormatted(this.getUnlocalizedNameInefficiently(stack) + ".name", oreName);
	}
	
	/*
	 * BYPRODUCT TIER 1: NITRIC ACID - CHEMPLANT GATE / NO GATE
	 * BYPRODUCT TIER 2: ORGANIC SOLVENT - CRACKING OIL GATE
	 * BYPRODUCT TIER 3: HIPERF SOLVENT - RBMK GATE
	 * BYPRODUCT TIER 4: SCHRABIDIC ACID - FUSION GATE?
	 */
	
	/*
	 * [BEDROCK x1] -C-> [CENTRIFUGED x4] -(PER)-> [CLEANED x4] -C-> [SEPARATED x16] -(SUL)-> [PURIFIED x16] -C-> [ENRICHED x64]
	 *                                                                       \
	 *                                                                        \-------(NIT)-> [NITRATED x16] -C-> [NITROCRYSTALLINE x32] -(ORG)-> [DEEP-CLEANED x32] -C-> [ENRICHED x64]
	 *                                                                                                        v             \                                         v
	 *                                                                                                [BYPRODUCT TIER 1]     \                                [BYPRODUCT TIER 2]
	 *                                                                                                                        \
	 *                                                                                                                         \-----------(HPS)-> [SEARED x32] -C-> [ENRICHED x64]
	 *                                                                                                                                                           v
	 *                                                                                                                                                   [BYPRODUCT TIER 3]
	 */

	public static enum EnumBedrockOre {
		//Ore								Byproduct	1,			2,			3
		IRON("Iron", 0xE2C0AA,						B_SULFUR,	B_TITANIUM,	B_TITANIUM), //titanium, sulfur from pyrite
		COPPER("Copper", 0xEC9A63,					B_SULFUR,	B_SULFUR,	B_SULFUR), //sulfur sulfur sulfur sulfur
		BORAX("Borax", 0xE4BE74, 					B_LITHIUM, 	B_CALCIUM, 	B_CALCIUM), //calcium from ulexite, uhhh lithium?
		ASBESTOS("Asbestos", 0xBFBFB9,				B_SILICON,	B_SILICON,	B_SILICON), //quartz i guess?
		NIOBIUM("Niobium", 0xAF58D8,				B_IRON,		B_IRON,		B_IRON), //iron in columbite, often found along tantalite
		TITANIUM("Titanium", 0xF2EFE2,				B_SILICON,	B_CALCIUM,	B_ALUMINIUM), //titanite is titanium + calcium + silicon with traces of iron and aluminium
		TUNGSTEN("Tungsten", 0x2C293C,				B_LEAD,		B_IRON,		B_BISMUTH), //ferberite has iron, raspite has lead, russelite is bismuth tungsten
		GOLD("Gold", 0xF9D738,						B_LEAD,		B_COPPER,	B_BISMUTH), //occurs with copper, lead and rare bismuthide
		URANIUM("Uranium", 0x868D82,				B_LEAD,		B_RADIUM,	B_POLONIUM), //uranium and its decay products
		THORIUM("Thorium232", 0x7D401D,				B_SILICON,	B_URANIUM,	B_TECHNETIUM), //thorium occours with uraninite and decay products
		CHLOROCALCITE("Chlorocalcite", 0xCDE036, 	B_LITHIUM, 	B_SILICON, 	B_SILICON), //i guess?
		FLUORITE("Fluorite", 0xF6F3E7, 				B_SILICON, 	B_LITHIUM, 	B_ALUMINIUM), //different silicon-bearing gemstones, generic lithium, aluminium from sodium compound trailings
		HEMATITE("Hematite", 0xA37B72,				B_SULFUR,	B_TITANIUM,	B_TITANIUM), //titanium, sulfur from pyrite
		MALACHITE("Malachite", 0x66B48C,			B_SULFUR,	B_SULFUR,	B_SULFUR), //sulfur sulfur sulfur sulfur
		NEODYMIUM("Neodymium", 0x8F8F5F,			B_LITHIUM,	B_SILICON,	B_BISMUTH); //yeah whatever
		
		public String oreName;
		public int color;
		public EnumByproduct[] byproducts;
		
		/** Byproduct count must be consistent with current tier count, use NULL if no byproduct should be generated! */
		private EnumBedrockOre(String name, int color, EnumByproduct... by) {
			this.oreName = name;
			this.color = color;
			this.byproducts = by;
		}
	}
}
