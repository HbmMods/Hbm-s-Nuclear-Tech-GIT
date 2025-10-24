package com.hbm.blocks.generic;

import java.util.Locale;
import java.util.Random;

import com.hbm.blocks.BlockEnumMulti;
import com.hbm.lib.RefStrings;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.world.World;

public class BlockNTMSand extends BlockEnumMulti {

	public BlockNTMSand(Material mat) {
		super(mat, EnumSandType.class, true, true);
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block b) {
		world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		if(!world.isRemote) {
			this.fall(world, x, y, z);
		}
	}

	private void fall(World world, int x, int y, int z) {
		if(BlockFalling.func_149831_e(world, x, y - 1, z) && y >= 0) {
			byte b0 = 32;

			if(!BlockFalling.fallInstantly && world.checkChunksExist(x - b0, y - b0, z - b0, x + b0, y + b0, z + b0)) {
				if(!world.isRemote) {
					EntityFallingBlock entityfallingblock = new EntityFallingBlock(world, x + 0.5, y + 0.5, z + 0.5, this, world.getBlockMetadata(x, y, z));
					world.spawnEntityInWorld(entityfallingblock);
				}
			} else {
				world.setBlockToAir(x, y, z);

				while(BlockFalling.func_149831_e(world, x, y - 1, z) && y > 0) {
					--y;
				}

				if(y > 0) {
					world.setBlock(x, y, z, this);
				}
			}
		}
	}

	@Override
	public int tickRate(World p_149738_1_) {
		return 2;
	}

	@Override
	public String getTextureMultiName(Enum num) {
		return RefStrings.MODID + ":sand_" + num.name().toLowerCase(Locale.US);
	}

	@Override
	public String getUnlocalizedMultiName(Enum num) {
		return "tile.sand_" + num.name().toLowerCase(Locale.US);
	}

	public static enum EnumSandType {
		BORON, LEAD, URANIUM, POLONIUM, QUARTZ
	}
}
