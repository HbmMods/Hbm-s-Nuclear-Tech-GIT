package com.hbm.blocks.machine;

import com.hbm.tileentity.machine.TileEntityGeiger;
import com.hbm.util.ContaminationUtil;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class GeigerCounter extends BlockContainer {

	public GeigerCounter(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityGeiger();
	}

	@Override
	public int getRenderType(){
		return -1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if(i == 0)
		{
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}
		if(i == 1)
		{
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}
		if(i == 2)
		{
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}
		if(i == 3)
		{
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
    {
		int te = p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_);
        float f = 0.0625F;

        this.setBlockBounds(0.0F, 0.0F, 2*f, 1.0F, 1.0F, 14*f);
        switch(te)
		{
		case 4:
	        this.setBlockBounds(0*f, 0.0F, 0*f, 14*f, 9*f, 14.5F*f);
            break;
		case 2:
	        this.setBlockBounds(1.5F*f, 0.0F, 0*f, 16*f, 9*f, 14*f);
            break;
		case 5:
	        this.setBlockBounds(2*f, 0.0F, 1.5F*f, 16*f, 9*f, 16*f);
            break;
		case 3:
	        this.setBlockBounds(0*f, 0.0F, 2*f, 14.5F*f, 9*f, 16*f);
            break;
		}
    }

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		// xyz,xyz (negative, positive)
		int te = world.getBlockMetadata(x, y, z);
        float f = 0.0625F;

        this.setBlockBounds(0.0F, 0.0F, 2*f, 1.0F, 1.0F, 14*f);
        switch(te)
		{
		// FACING WEST
		case 4:
	        this.setBlockBounds(0*f, 0.0F, 0*f, 14*f, 9*f, 14.5F*f);
            break;
		// FACING NORTH
		case 2:
	        this.setBlockBounds(1.5F*f, 0.0F, 0*f, 16*f, 9*f, 14*f);
            break;
		// FACING EAST
		case 5:
	        this.setBlockBounds(2*f, 0.0F, 1.5F*f, 16*f, 9*f, 16*f);
            break;
		// FACING SOUTH
		case 3:
	        this.setBlockBounds(0*f, 0.0F, 2*f, 14.5F*f, 9*f, 16*f);
            break;
		}

		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {

		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking())
		{
	    	world.playSoundAtEntity(player, "hbm:item.techBoop", 1.0F, 1.0F);
	    	ContaminationUtil.printGeigerData(player);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean hasComparatorInputOverride() {
		return true;
	}

	@Override
	public int getComparatorInputOverride(World world, int x, int y, int z, int side) {
		TileEntityGeiger te = (TileEntityGeiger)world.getTileEntity(x, y, z);
		if (te == null) return 0;

		float rad = te.check();

		// 0 at exactly 0 rads/sec
		// +1 per 5 rads/sec
		// 15 at 75+ rads/sec
		return Math.min((int)Math.ceil(rad / 5f), 15);
	}
}
