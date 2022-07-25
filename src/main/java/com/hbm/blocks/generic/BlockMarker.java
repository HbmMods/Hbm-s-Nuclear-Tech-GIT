package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.tileentity.machine.TileEntityStructureMarker;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class BlockMarker extends BlockContainer {

	public BlockMarker(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityStructureMarker();
	}

    @Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
        return null;
    }

    @Override
	public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
	public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
	public int getRenderType()
    {
        return 2;
    }

    private boolean func_150107_m(World p_150107_1_, int p_150107_2_, int p_150107_3_, int p_150107_4_)
    {
        if (World.doesBlockHaveSolidTopSurface(p_150107_1_, p_150107_2_, p_150107_3_, p_150107_4_))
        {
            return true;
        }
        else
        {
            Block block = p_150107_1_.getBlock(p_150107_2_, p_150107_3_, p_150107_4_);
            return block.canPlaceTorchOnTop(p_150107_1_, p_150107_2_, p_150107_3_, p_150107_4_);
        }
    }

    @Override
	public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_)
    {
        return func_150107_m(p_149742_1_, p_149742_2_, p_149742_3_ - 1, p_149742_4_);
    }

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		
		if(i == 0)
		{
			world.setBlockMetadataWithNotify(x, y, z, 6, 2);
		}
		if(i == 1)
		{
			world.setBlockMetadataWithNotify(x, y, z, 7, 2);
		}
		if(i == 2)
		{
			world.setBlockMetadataWithNotify(x, y, z, 8, 2);
		}
		if(i == 3)
		{
			world.setBlockMetadataWithNotify(x, y, z, 9, 2);
		}
	}

    @Override
	public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
    {
        super.updateTick(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, p_149674_5_);

        if (p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_) == 0)
        {
            this.onBlockAdded(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_);
        }
    }

    @Override
	public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
    {
        this.func_150108_b(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_5_);
    }

    protected boolean func_150108_b(World p_150108_1_, int p_150108_2_, int p_150108_3_, int p_150108_4_, Block p_150108_5_)
    {
        if (this.func_150109_e(p_150108_1_, p_150108_2_, p_150108_3_, p_150108_4_))
        {
            boolean flag = false;

            if (!this.func_150107_m(p_150108_1_, p_150108_2_, p_150108_3_ - 1, p_150108_4_))
            {
                flag = true;
            }

            if (flag)
            {
                this.dropBlockAsItem(p_150108_1_, p_150108_2_, p_150108_3_, p_150108_4_, p_150108_1_.getBlockMetadata(p_150108_2_, p_150108_3_, p_150108_4_), 0);
                p_150108_1_.setBlockToAir(p_150108_2_, p_150108_3_, p_150108_4_);
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return true;
        }
    }

    protected boolean func_150109_e(World p_150109_1_, int p_150109_2_, int p_150109_3_, int p_150109_4_)
    {
        if (!this.canPlaceBlockAt(p_150109_1_, p_150109_2_, p_150109_3_, p_150109_4_))
        {
            if (p_150109_1_.getBlock(p_150109_2_, p_150109_3_, p_150109_4_) == this)
            {
                this.dropBlockAsItem(p_150109_1_, p_150109_2_, p_150109_3_, p_150109_4_, p_150109_1_.getBlockMetadata(p_150109_2_, p_150109_3_, p_150109_4_), 0);
                p_150109_1_.setBlockToAir(p_150109_2_, p_150109_3_, p_150109_4_);
            }

            return false;
        }
        else
        {
            return true;
        }
    }

    @Override
	public MovingObjectPosition collisionRayTrace(World p_149731_1_, int p_149731_2_, int p_149731_3_, int p_149731_4_, Vec3 p_149731_5_, Vec3 p_149731_6_)
    {
        float f = 0.15F;
        f = 0.1F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.6F, 0.5F + f);

        return super.collisionRayTrace(p_149731_1_, p_149731_2_, p_149731_3_, p_149731_4_, p_149731_5_, p_149731_6_);
    }
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
		{
			int i = ((TileEntityStructureMarker)world.getTileEntity(x, y, z)).type + 1;
			if(i > 2)
				i -= 3;
			if(i == 0) player.addChatMessage(new ChatComponentText("[Structure Marker] Set template: Nuclear Reactor"));
			if(i == 1) player.addChatMessage(new ChatComponentText("[Structure Marker] Set template: Watz Power Plant"));
			if(i == 2) player.addChatMessage(new ChatComponentText("[Structure Marker] Set template: Fusionary Watz Plant"));
			return true;
		} else if(!player.isSneaking())
		{
			if(world.getTileEntity(x, y, z) != null && world.getTileEntity(x, y, z) instanceof TileEntityStructureMarker) {
				((TileEntityStructureMarker)world.getTileEntity(x, y, z)).type ++;
			}
			return true;
		} else {
			return false;
		}
	}

}
