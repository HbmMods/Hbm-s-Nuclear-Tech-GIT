package com.hbm.blocks;

import com.hbm.util.EnumUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.Locale;

public class BlockEnumMulti extends BlockMulti {

	public Class<? extends Enum> theEnum;
	public boolean multiName;
	private boolean multiTexture;

	public BlockEnumMulti(Material mat, Class<? extends Enum> theEnum, boolean multiName, boolean multiTexture) {
		super(mat);
		this.theEnum = theEnum;
		this.multiName = multiName;
		this.multiTexture = multiTexture;
	}

	protected IIcon[] icons;

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {

		if(multiTexture) {
			Enum[] enums = theEnum.getEnumConstants();
			this.icons = new IIcon[enums.length];

			for(int i = 0; i < icons.length; i++) {
				Enum num = enums[i];
				this.icons[i] = reg.registerIcon(this.getTextureMultiName(num));
			}
		} else {
			this.blockIcon = reg.registerIcon(this.getTextureName());
		}
	}

	public String getUnlocalizedName(ItemStack stack) {

		if(this.multiName) {
			Enum num = EnumUtil.grabEnumSafely(this.theEnum, stack.getItemDamage());
			return getUnlocalizedMultiName(num);
		}

		return this.getUnlocalizedName();
	}

	public String getTextureMultiName(Enum num) {
		return this.getTextureName() + "." + num.name().toLowerCase(Locale.US);
	}

	public String getUnlocalizedMultiName(Enum num) {
		return super.getUnlocalizedName() + "." + num.name().toLowerCase(Locale.US);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return multiTexture ? this.icons[meta % this.icons.length] : this.blockIcon;
	}

	@Override
	public int getSubCount() {
		return this.theEnum.getEnumConstants().length;
	}
}
