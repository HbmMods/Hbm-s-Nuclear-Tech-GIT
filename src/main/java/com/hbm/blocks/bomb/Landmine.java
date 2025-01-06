package com.hbm.blocks.bomb;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionNukeSmall;
import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.standard.BlockAllocatorStandard;
import com.hbm.explosion.vanillant.standard.BlockProcessorStandard;
import com.hbm.explosion.vanillant.standard.EntityProcessorCrossSmooth;
import com.hbm.explosion.vanillant.standard.ExplosionEffectWeapon;
import com.hbm.explosion.vanillant.standard.PlayerProcessorStandard;
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

	public double range;
	public double height;

	public Landmine(Material mat, double range, double height) {
		super(mat);
		this.range = range;
		this.height = height;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileEntityLandmine();
	}

	@Override public int getRenderType() { return -1; }
	@Override public boolean isOpaqueCube() { return false; }
	@Override public boolean renderAsNormalBlock() { return false; }

	@Override
	public Item getItemDropped(int i, Random rand, int j) {
		return null;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		float f = 0.0625F;
		if(this == ModBlocks.mine_ap) this.setBlockBounds(5 * f, 0.0F, 5 * f, 11 * f, 1 * f, 11 * f);
		if(this == ModBlocks.mine_he) this.setBlockBounds(4 * f, 0.0F, 4 * f, 12 * f, 2 * f, 12 * f);
		if(this == ModBlocks.mine_shrap) this.setBlockBounds(5 * f, 0.0F, 5 * f, 11 * f, 1 * f, 11 * f);
		if(this == ModBlocks.mine_fat) this.setBlockBounds(5 * f, 0.0F, 4 * f, 11 * f, 6 * f, 12 * f);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		setBlockBoundsBasedOnState(world, x, y, z);
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}

	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		return World.doesBlockHaveSolidTopSurface(world, x, y - 1, z) || BlockFence.func_149825_a(world.getBlock(x, y - 1, z));
	}

	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {

		if(world.isBlockIndirectlyGettingPowered(x, y, z)) {
			explode(world, x, y, z);
		}

		if(!World.doesBlockHaveSolidTopSurface(world, x, y - 1, z) && !BlockFence.func_149825_a(world.getBlock(x, y - 1, z))) {
			if(!safeMode) {
				explode(world, x, y, z);
			} else {
				world.setBlockToAir(x, y, z);
			}
		}
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta) {

		if(!safeMode) {
			explode(world, x, y, z);
		}

		super.onBlockDestroyedByPlayer(world, x, y, z, meta);
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float fx, float fy, float fz) {
		if(player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.defuser) {

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
	public BombReturnCode explode(World world, int x, int y, int z) {

		if(!world.isRemote) {

			Landmine.safeMode = true;
			world.func_147480_a(x, y, z, false);
			Landmine.safeMode = false;

			if(this == ModBlocks.mine_ap) {
				ExplosionVNT vnt = new ExplosionVNT(world, x + 0.5, y + 0.5, z + 0.5, 3F);
				vnt.setEntityProcessor(new EntityProcessorCrossSmooth(0.5, 10F).setupPiercing(5F, 0.2F));
				vnt.setPlayerProcessor(new PlayerProcessorStandard());
				vnt.setSFX(new ExplosionEffectWeapon(5, 1F, 0.5F));
				vnt.explode();
			} else if(this == ModBlocks.mine_he) {
				ExplosionVNT vnt = new ExplosionVNT(world, x + 0.5, y + 0.5, z + 0.5, 4F);
				vnt.setBlockAllocator(new BlockAllocatorStandard());
				vnt.setBlockProcessor(new BlockProcessorStandard());
				vnt.setEntityProcessor(new EntityProcessorCrossSmooth(1, 35).setupPiercing(15F, 0.2F));
				vnt.setPlayerProcessor(new PlayerProcessorStandard());
				vnt.setSFX(new ExplosionEffectWeapon(15, 3.5F, 1.25F));
				vnt.explode();
			} else if(this == ModBlocks.mine_shrap) {
				ExplosionVNT vnt = new ExplosionVNT(world, x + 0.5, y + 0.5, z + 0.5, 3F);
				vnt.setEntityProcessor(new EntityProcessorCrossSmooth(0.5, 7.5F));
				vnt.setPlayerProcessor(new PlayerProcessorStandard());
				vnt.setSFX(new ExplosionEffectWeapon(5, 1F, 0.5F));
				vnt.explode();
				
				ExplosionLarge.spawnShrapnelShower(world, x + 0.5, y + 0.5, z + 0.5, 0, 1D, 0, 45, 0.2D);
				ExplosionLarge.spawnShrapnels(world, x + 0.5, y + 0.5, z + 0.5, 5);
			} else if(this == ModBlocks.mine_fat) {
				ExplosionNukeSmall.explode(world, x + 0.5, y + 0.5, z + 0.5, ExplosionNukeSmall.PARAMS_MEDIUM);
			}
		}

		return BombReturnCode.DETONATED;
	}
}
