package com.hbm.blocks.machine;

import com.hbm.tileentity.machine.TileEntityBroadcaster;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class PinkCloudBroadcaster extends BlockContainer {

	public PinkCloudBroadcaster(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityBroadcaster();
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
	        this.setBlockBounds(4*f, 0.0F, 1*f, 12*f, 10*f, 15*f);
            break;
		case 2:
	        this.setBlockBounds(1*f, 0.0F, 4*f, 15*f, 10*f, 12*f);
            break;
		case 5:
	        this.setBlockBounds(4*f, 0.0F, 1*f, 12*f, 10*f, 15*f);
            break;
		case 3:
	        this.setBlockBounds(1*f, 0.0F, 4*f, 15*f, 10*f, 12*f);
            break;
		}
    }

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {

		int te = world.getBlockMetadata(x, y, z);
        float f = 0.0625F;

        this.setBlockBounds(0.0F, 0.0F, 2*f, 1.0F, 1.0F, 14*f);
        switch(te)
		{
		case 4:
	        this.setBlockBounds(4*f, 0.0F, 1*f, 12*f, 10*f, 15*f);
            break;
		case 2:
	        this.setBlockBounds(1*f, 0.0F, 4*f, 15*f, 10*f, 12*f);
            break;
		case 5:
	        this.setBlockBounds(4*f, 0.0F, 1*f, 12*f, 10*f, 15*f);
            break;
		case 3:
	        this.setBlockBounds(1*f, 0.0F, 4*f, 15*f, 10*f, 12*f);
            break;
		}

		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}

}
