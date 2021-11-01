package com.hbm.blocks.bomb;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionNukeSmall;
import com.hbm.interfaces.IBomb;
import com.hbm.items.ModItems;
import com.hbm.tileentity.bomb.TileEntityLandmine;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class Landmine extends BlockContainer implements IBomb {

	public static boolean safeMode = false;
	static Random rand = new Random();;

	public Landmine(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileEntityLandmine();
	}

	@Override
	public int getRenderType() {
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
	public Item getItemDropped(int i, Random rand, int j) {
		return null;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_,
			int p_149719_4_) {
		float f = 0.0625F;
		if (this == ModBlocks.mine_ap)
			this.setBlockBounds(6 * f, 0.0F, 6 * f, 10 * f, 2 * f, 10 * f);
		if (this == ModBlocks.mine_he)
			this.setBlockBounds(4 * f, 0.0F, 4 * f, 12 * f, 2 * f, 12 * f);
		if (this == ModBlocks.mine_shrap)
			this.setBlockBounds(4 * f, 0.0F, 4 * f, 12 * f, 2 * f, 12 * f);
		if (this == ModBlocks.mine_fat)
			this.setBlockBounds(5 * f, 0.0F, 4 * f, 11 * f, 6 * f, 12 * f);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		float f = 0.0625F;
		if (this == ModBlocks.mine_ap)
			this.setBlockBounds(6 * f, 0.0F, 6 * f, 10 * f, 2 * f, 10 * f);
		if (this == ModBlocks.mine_he)
			this.setBlockBounds(4 * f, 0.0F, 4 * f, 12 * f, 2 * f, 12 * f);
		if (this == ModBlocks.mine_shrap)
			this.setBlockBounds(4 * f, 0.0F, 4 * f, 12 * f, 2 * f, 12 * f);
		if (this == ModBlocks.mine_fat)
			this.setBlockBounds(5 * f, 0.0F, 4 * f, 11 * f, 6 * f, 12 * f);
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY,
				z + this.maxZ);
	}

	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		return World.doesBlockHaveSolidTopSurface(world, x, y - 1, z)
				|| BlockFence.func_149825_a(world.getBlock(x, y - 1, z));
	}

	public void onNeighborBlockChange(World world, int x, int y, int z,
			Block block) {

        if (world.isBlockIndirectlyGettingPowered(x, y, z))
        {
        	explode(world, x, y, z);
        }
        
		boolean flag = false;

		if (!World.doesBlockHaveSolidTopSurface(world, x, y - 1, z)
				&& !BlockFence.func_149825_a(world.getBlock(x, y - 1, z))) {
			flag = true;
		}

		if (flag) {
			this.dropBlockAsItem(world, x, y, z,
					world.getBlockMetadata(x, y, z), 0);
			world.setBlockToAir(x, y, z);
		}
	}

	public void breakBlock(World world, int x, int y, int z, Block block, int i) {

		if (!safeMode) {
			explode(world, x, y, z);
		}

		super.breakBlock(world, x, y, z, block, i);
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float fx, float fy,
			float fz) {
		if (player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.defuser) {

			safeMode = true;
			world.setBlockToAir(x, y, z);

			ItemStack itemstack = new ItemStack(this, 1);
			float f = world.rand.nextFloat() * 0.6F + 0.2F;
			float f1 = world.rand.nextFloat() * 0.2F;
			float f2 = world.rand.nextFloat() * 0.6F + 0.2F;
			
			EntityItem entityitem = new EntityItem(world, x + f, y + f1 + 1, z + f2, itemstack);

			float f3 = 0.05F;
			entityitem.motionX = (float) world.rand.nextGaussian() * f3;
			entityitem.motionY = (float) world.rand.nextGaussian() * f3 + 0.2F;
			entityitem.motionZ = (float) world.rand.nextGaussian() * f3;

			if(!world.isRemote)
				world.spawnEntityInWorld(entityitem);

			safeMode = false;
			return true;
		}

		return false;
	}

	@Override
	public void explode(World world, int x, int y, int z) {

		if(!world.isRemote) {
			
			Landmine.safeMode = true;
			world.func_147480_a(x, y, z, false);
			Landmine.safeMode = false;
			
			if (this == ModBlocks.mine_ap) {
				world.newExplosion(null, x + 0.5, y + 0.5, z + 0.5, 2.5F, false, false);
			}
			if (this == ModBlocks.mine_he) {
				ExplosionLarge.explode(world, x + 0.5, y + 0.5, z + 0.5, 10F, true, false, false);
			}
			if (this == ModBlocks.mine_shrap) {
				ExplosionLarge.explode(world, x + 0.5, y + 0.5, z + 0.5, 1, true, false, false);
				ExplosionLarge.spawnShrapnelShower(world, x + 0.5, y + 0.5, z + 0.5, 0, 1D, 0, 45, 0.2D);
				ExplosionLarge.spawnShrapnels(world, x + 0.5, y + 0.5, z + 0.5, 5);
			}
			if (this == ModBlocks.mine_fat) {
				

				ExplosionNukeSmall.explode(world, x + 0.5, y + 0.5, z + 0.5, ExplosionNukeSmall.medium);
			}
		}
	}

}
