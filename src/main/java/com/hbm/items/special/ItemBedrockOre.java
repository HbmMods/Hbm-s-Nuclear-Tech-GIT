package com.hbm.items.special;

import com.hbm.items.ItemEnumMulti;
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
		String oreName = StatCollector.translateToLocal("item.ore." + ore.oreName.toLowerCase());
		return StatCollector.translateToLocalFormatted(this.getUnlocalizedNameInefficiently(stack) + ".name", oreName);
	}

	public static enum EnumBedrockOre {
		IRON("Iron", 0xE2C0AA),
		COPPER("Copper", 0xEC9A63),
		BORAX("Borax", 0xE4BE74),
		ASBESTOS("Asbestos", 0xBFBFB9),
		NIOBIUM("Niobium", 0xAF58D8),
		TITANIUM("Titanium", 0xF2EFE2),
		TUNGSTEN("Tungsten", 0x2C293C),
		GOLD("Gold", 0xF9D738);
		
		public String oreName;
		public int color;
		
		private EnumBedrockOre(String name, int color) {
			this.oreName = name;
			this.color = color;
		}
	}
}
