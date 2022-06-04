package com.hbm.blocks.network;

import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class BlockCraneBase extends BlockContainer {

	@SideOnly(Side.CLIENT) protected IIcon iconSide;
	@SideOnly(Side.CLIENT) protected IIcon iconIn;
	@SideOnly(Side.CLIENT) protected IIcon iconSideIn;
	@SideOnly(Side.CLIENT) protected IIcon iconOut;
	@SideOnly(Side.CLIENT) protected IIcon iconSideOut;

	@SideOnly(Side.CLIENT) protected IIcon iconDirectional;
	@SideOnly(Side.CLIENT) protected IIcon iconDirectionalUp;
	@SideOnly(Side.CLIENT) protected IIcon iconDirectionalDown;

	public BlockCraneBase(Material mat) {
		super(mat);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":crane_top");
		this.iconSide = iconRegister.registerIcon(RefStrings.MODID + ":crane_side");
		this.iconIn = iconRegister.registerIcon(RefStrings.MODID + ":crane_in");
		this.iconSideIn = iconRegister.registerIcon(RefStrings.MODID + ":crane_side_in");
		this.iconOut = iconRegister.registerIcon(RefStrings.MODID + ":crane_out");
		this.iconSideOut = iconRegister.registerIcon(RefStrings.MODID + ":crane_side_out");
	}
	
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		int l = BlockPistonBase.determineOrientation(world, x, y, z, player);
		world.setBlockMetadataWithNotify(x, y, z, l, 2);
	}

	@Override
	public IIcon getIcon(int side, int metadata) {
		
		if(side == 0 || side == 1) {
			if(side == metadata) {
				return this.iconOut;
			}
			if(side == ForgeDirection.getOrientation(metadata).getOpposite().ordinal()) {
				return this.iconIn;
			}
			
			return side == 1 ? this.iconDirectional : this.blockIcon;
		}
		
		if(side == metadata) {
			return this.iconSideOut;
		}
		if(side == ForgeDirection.getOrientation(metadata).getOpposite().ordinal()) {
			return this.iconSideIn;
		}

		if(metadata == 0) {
			return this.iconDirectionalUp;
		}
		if(metadata == 1) {
			return this.iconDirectionalDown;
		}
		
		return this.iconSide;
	}
}
