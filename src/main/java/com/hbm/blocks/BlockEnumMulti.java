package com.hbm.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockEnumMulti extends BlockMulti {

	public Class<? extends Enum<?>> theEnum;
	public boolean multiName;
	private boolean multiTexture;

	public BlockEnumMulti(Material mat, Class<? extends Enum<?>> theEnum, boolean multiName, boolean multiTexture) {
		super(mat);
		this.theEnum = theEnum;
		this.multiName = multiName;
		this.multiTexture = multiTexture;
	}
	
	private IIcon[] icons;

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		
		if(multiTexture) {
			Enum<?>[] enums = theEnum.getEnumConstants();
			this.icons = new IIcon[enums.length];
			
			for(int i = 0; i < icons.length; i++) {
				Enum<?> num = enums[i];
				this.icons[i] = reg.registerIcon(this.getTextureName() + "." + num.name().toLowerCase());
			}
		} else {
			this.blockIcon = reg.registerIcon(this.getTextureName());
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return this.icons[meta % this.icons.length];
	}

	@Override
	public int getSubCount() {
		return this.theEnum.getEnumConstants().length;
	}
}
