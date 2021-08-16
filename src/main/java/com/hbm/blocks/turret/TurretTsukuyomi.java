package com.hbm.blocks.turret;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockGeneric;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.turret.TileEntityTsukuyomi;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class TurretTsukuyomi extends BlockGeneric
{

	public TurretTsukuyomi()
	{
		super(Material.iron);
		setHardness(5.0F);
		setResistance(600.0F);
		setCreativeTab(MainRegistry.weaponTab);
	}

	@Override
	public TileEntity createTileEntity(World world, int metadata)
	{
		if (metadata >= 12)
			return new TileEntityTsukuyomi();
		return new TileEntityProxyCombo(true, true, false);
	}
	@Override
	public boolean onBlockActivated(World worldIn, int x, int y, int z,
			EntityPlayer player, int size, float hitX, float hitY, float hitZ)
	{
		if (worldIn.isRemote)
			return true;
		else if (!player.isSneaking())
		{
//			int[] pos = findCore(worldIn, x, y, z);
//			if (pos == null)
//				return false;
			
			FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_tsukuyomi, worldIn, x, y, z);
			return true;
		}
		else
			return false;
	}
	@Override
	public boolean renderAsNormalBlock()
	{
		return true;
	}
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_,
			int p_149719_4_)
	{
		super.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World worldIn, int x, int y,
			int z)
	{
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
		return AxisAlignedBB.getBoundingBox(x + minX, y + minY, z + minZ, x + maxX, y + maxY, z + maxZ);
	}
	
//	@Override
//	public int[] getDimensions()
//	{
//		return new int[] { 0, 0, 2, 0, 2, 0 };
//	}
//
//	@Override
//	public int getOffset()
//	{
//		return 0;
//	}

}
