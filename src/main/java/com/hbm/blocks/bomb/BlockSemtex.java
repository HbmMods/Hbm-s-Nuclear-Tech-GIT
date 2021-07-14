package com.hbm.blocks.bomb;

import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockSemtex extends Block {
	
	@SideOnly(Side.CLIENT)
	private IIcon topIcon;

	public BlockSemtex(Material mat) {
		super(mat);
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_) {
		this.blockIcon = p_149651_1_.registerIcon(RefStrings.MODID + ":block_semtex");
		this.topIcon = p_149651_1_.registerIcon(RefStrings.MODID + ":block_semtex_front");
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		int k = getPistonOrientation(meta);
		return ForgeDirection.getOrientation(k).getOpposite().ordinal() == side ? this.topIcon : this.blockIcon;
	}

	public static int getPistonOrientation(int meta) {
		return meta & 7;
	}

	@Override
	public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_) {
		int l = determineOrientation(p_149689_1_, p_149689_2_, p_149689_3_, p_149689_4_, p_149689_5_);
		p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, l, 2);
	}

	public static int determineOrientation(World p_150071_0_, int p_150071_1_, int p_150071_2_, int p_150071_3_, EntityLivingBase p_150071_4_) {

		if(MathHelper.abs((float) p_150071_4_.posX - (float) p_150071_1_) < 2.0F && MathHelper.abs((float) p_150071_4_.posZ - (float) p_150071_3_) < 2.0F) {
			double d0 = p_150071_4_.posY + 1.82D - (double) p_150071_4_.yOffset;
			
			if(d0 - (double) p_150071_2_ > 2.0D) {
				return 0;
			}
			
			if((double) p_150071_2_ - d0 > 0.0D) {
				return 1;
			}
		}
		
		int l = MathHelper.floor_double((double) (p_150071_4_.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		
		return l == 0 ? 3 : (l == 1 ? 4 : (l == 2 ? 2 : (l == 3 ? 5 : 1)));
	}
}
