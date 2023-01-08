package com.hbm.items.special;

import com.hbm.items.ItemEnumMulti;
import com.hbm.lib.RefStrings;
import com.hbm.util.EnumUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemBedrockOre extends ItemEnumMulti {

	protected IIcon overlayIcon;

	public ItemBedrockOre() {
		super(EnumBedrockOre.class, true, false);
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

	public static enum EnumBedrockOre {
		IRON("Iron", 0xE2C0AA),
		COPPER("Copper", 0xFDCA88);
		
		public String oreName;
		public int color;
		
		private EnumBedrockOre(String name, int color) {
			this.oreName = name;
			this.color = color;
		}
	}
}
