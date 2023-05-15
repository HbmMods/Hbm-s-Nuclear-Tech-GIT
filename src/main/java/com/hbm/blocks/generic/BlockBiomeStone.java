package com.hbm.blocks.generic;

import java.util.Locale;

import com.hbm.blocks.BlockEnumMulti;
import com.hbm.blocks.BlockEnums.EnumBiomeType;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class BlockBiomeStone extends BlockEnumMulti {

	public BlockBiomeStone() {
		super(Material.rock, EnumBiomeType.class, true, true);
	}

	protected IIcon[] iconsTop;
	protected IIcon[] iconsLayer;

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		
		Enum[] enums = theEnum.getEnumConstants();
		this.icons = new IIcon[enums.length];
		this.iconsTop = new IIcon[enums.length];
		this.iconsLayer = new IIcon[enums.length];
		
		for(int i = 0; i < icons.length; i++) {
			Enum num = enums[i];
			this.icons[i] = reg.registerIcon(this.getTextureName() + "." + num.name().toLowerCase(Locale.US));
			this.iconsTop[i] = reg.registerIcon(this.getTextureName() + "_top." + num.name().toLowerCase(Locale.US));
			this.iconsLayer[i] = reg.registerIcon(this.getTextureName() + "_layer." + num.name().toLowerCase(Locale.US));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		
		int meta = world.getBlockMetadata(x, y, z);
		if(side == 0) return this.iconsTop[meta % this.icons.length];
		if(side == 1) return this.iconsTop[meta % this.icons.length];
		
		if(world.getBlock(x, y + 1, z) == this && world.getBlockMetadata(x, y + 1, z) == meta) {
			return this.getIcon(side, meta);
		} else {
			return this.iconsLayer[meta % this.icons.length];
		}
	}
}
