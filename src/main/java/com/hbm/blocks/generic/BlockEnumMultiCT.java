package com.hbm.blocks.generic;

import com.hbm.blocks.BlockEnumMulti;
import com.hbm.render.block.ct.CT;
import com.hbm.render.block.ct.CTStitchReceiver;
import com.hbm.render.block.ct.IBlockCT;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class BlockEnumMultiCT extends BlockEnumMulti implements IBlockCT {

	@SideOnly(Side.CLIENT)
	public CTStitchReceiver[] receivers;
   // im planning ts for shi like deco blocks n shit when the time comes
	public BlockEnumMultiCT(Material mat, Class<? extends Enum> theEnum, boolean multiName, boolean multiTexture)
	{
		super(mat, theEnum, multiName, multiTexture);
	}

	@Override
	public int getRenderType() {
		return CT.renderID;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		if(multiTexture) {
			Enum[] enums = theEnum.getEnumConstants();
			this.icons = new IIcon[enums.length];
			this.receivers = new CTStitchReceiver[enums.length];
			for(int i = 0; i < icons.length; i++)
			{
				Enum num = enums[i];
				String texName = this.getTextureMultiName(num);
				this.icons[i] = reg.registerIcon(texName);
				this.receivers[i] = IBlockCT.primeReceiver(reg, texName, this.icons[i]);
			}
		}
		else
		{
			this.blockIcon = reg.registerIcon(this.getTextureName());
			this.receivers = new CTStitchReceiver[1];
			this.receivers[0] = IBlockCT.primeReceiver(reg, this.getTextureName(), this.blockIcon);
		}
	}

	@Override
	public IIcon[] getFragments(IBlockAccess world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z);
		return receivers[meta % receivers.length].fragCache;

	}
	@Override
	public boolean canConnect(IBlockAccess world, int x, int y, int z, Block block) {
		return this == block;
	}
}
