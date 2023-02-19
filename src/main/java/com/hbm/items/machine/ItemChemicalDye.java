package com.hbm.items.machine;

import com.hbm.items.ItemEnumMulti;
import com.hbm.lib.RefStrings;
import com.hbm.util.EnumUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemChemicalDye extends ItemEnumMulti {

	@SideOnly(Side.CLIENT) protected IIcon overlayIcon;

	public ItemChemicalDye() {
		super(EnumChemDye.class, true, false);
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

		this.overlayIcon = reg.registerIcon(RefStrings.MODID + ":chemical_dye_overlay");
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
			EnumChemDye dye = EnumUtil.grabEnumSafely(EnumChemDye.class, stack.getItemDamage());
			return dye.color;
		}
		
		return 0xffffff;
	}
	
	public static enum EnumChemDye {
		BLACK(1973019, "Black"),
		RED(11743532, "Red"),
		GREEN(3887386, "Green"),
		BROWN(5320730, "Brown"),
		BLUE(2437522, "Blue"),
		PURPLE(8073150, "Purple"),
		CYAN(2651799, "Cyan"),
		SILVER(11250603, "LightGray"),
		GRAY(4408131, "Gray"),
		PINK(14188952, "Pink"),
		LIME(4312372, "Lime"),
		YELLOW(14602026, "Yellow"),
		LIGHTBLUE(6719955, "LightBlue"),
		MAGENTA(12801229, "Magenta"),
		ORANGE(15435844, "Orange"),
		WHITE(15790320, "White");
		
		public int color;
		public String dictName;

		private EnumChemDye(int color, String name) {
			this.color = color;
			this.dictName = name;
		}
	}
}
