package com.hbm.items;

import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class ItemGenericPart extends ItemEnumMulti {
	
	public static enum EnumPartTpe {
		PISTON_PNEUMATIC("piston_pneumatic"),
		PISTON_HYDRAULIC("piston_hydraulic"),
		PISTON_ELECTRIC("piston_electric");
		
		private String texName;
		
		private EnumPartTpe(String texName) {
			this.texName = texName;
		}
	}

	public ItemGenericPart() {
		super(EnumPartTpe.class, true, true);
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		
		Enum[] enums = theEnum.getEnumConstants();
		this.icons = new IIcon[enums.length];
		
		for(int i = 0; i < icons.length; i++) {
			EnumPartTpe num = (EnumPartTpe)enums[i];
			this.icons[i] = reg.registerIcon(RefStrings.MODID + ":" + num.texName);
		}
	}
}
