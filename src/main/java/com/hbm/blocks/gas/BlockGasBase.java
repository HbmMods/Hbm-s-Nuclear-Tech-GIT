package com.hbm.blocks.gas;

import java.util.Random;

import com.hbm.blocks.ModBlocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class BlockGasBase extends Block {

	public BlockGasBase() {
		super(ModBlocks.materialGas);
		this.setHardness(0.0F);
		this.setResistance(0.0F);
		this.lightOpacity = 0;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_) {
		return null;
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return null;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		return false;
	}

	@Override
	public boolean canCollideCheck(int p_149678_1_, boolean p_149678_2_) {
		return false;
	}

	@Override
	public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
		return true;
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {

		if(!world.isRemote)
			world.scheduleBlockUpdate(x, y, z, this, 10);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {

		if(!world.isRemote) {
			
			world.scheduledUpdatesAreImmediate = false; //prevent recursive loop when some dumbass forgets to clean up immediate updating

			if(!tryMove(world, x, y, z, getFirstDirection(world, x, y, z)))
				if(!tryMove(world, x, y, z, getSecondDirection(world, x, y, z)))
					world.scheduleBlockUpdate(x, y, z, this, getDelay(world));
		}
	}

	public abstract ForgeDirection getFirstDirection(World world, int x, int y, int z);

	public ForgeDirection getSecondDirection(World world, int x, int y, int z) {
		return getFirstDirection(world, x, y, z);
	}

	public boolean tryMove(World world, int x, int y, int z, ForgeDirection dir) {

		if(world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ) == Blocks.air) {
			world.setBlockToAir(x, y, z);
			world.setBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, this);
			world.scheduleBlockUpdate(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, this, getDelay(world));
			return true;
		}

		return false;
	}

	public int getDelay(World world) {
		return 2;
	}

	public ForgeDirection randomHorizontal(World world) {
		return ForgeDirection.getOrientation(world.rand.nextInt(4) + 2);
	}
}
