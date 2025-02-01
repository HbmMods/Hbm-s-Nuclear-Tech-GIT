package com.hbm.blocks;

import com.hbm.entity.item.EntityFallingBlockNT;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class BlockFallingNT extends Block {

	public static boolean fallInstantly;

	public BlockFallingNT() {
		super(Material.sand);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}

	public BlockFallingNT(Material mat) {
		super(mat);
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		if(!world.isRemote) {
			this.fall(world, x, y, z);
		}
	}

	protected void fall(World world, int x, int y, int z) {

		if(canFallThrough(world, x, y - 1, z) && y >= 0) {
			byte range = 32;

			if(!fallInstantly && world.checkChunksExist(x - range, y - range, z - range, x + range, y + range, z + range)) {
				if(!world.isRemote) {
					EntityFallingBlockNT entityfallingblock = new EntityFallingBlockNT(world, x + 0.5D, y + 0.5D, z + 0.5D, this, world.getBlockMetadata(x, y, z));
					this.modifyFallingBlock(entityfallingblock);
					world.spawnEntityInWorld(entityfallingblock);
				}
			} else {
				world.setBlockToAir(x, y, z);

				while(canFallThrough(world, x, y - 1, z) && y > 0) {
					--y;
				}

				if(y > 0) {
					world.setBlock(x, y, z, this);
				}
			}
		}
	}

	protected void modifyFallingBlock(EntityFallingBlockNT falling) { }

	@Override
	public int tickRate(World world) {
		return 2;
	}

	public static boolean canFallThrough(World world, int x, int y, int z) {
		Block block = world.getBlock(x, y, z);

		if(block.isAir(world, x, y, z)) {
			return true;
		} else if(block == Blocks.fire) {
			return true;
		} else {
			Material material = block.getMaterial();
			return material == Material.water ? true : material == Material.lava;
		}
	}

	public void onLand(World world, int x, int y, int z, int meta) { }

	@SideOnly(Side.CLIENT) public boolean shouldOverrideRenderer() { return false; }
	@SideOnly(Side.CLIENT) public void overrideRenderer(EntityFallingBlockNT falling, RenderBlocks renderBlocks, Tessellator tessellator) { }
}
