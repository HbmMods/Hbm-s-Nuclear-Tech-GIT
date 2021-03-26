package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.interfaces.IItemHazard;
import com.hbm.modules.ItemHazardModule;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class BlockLithium extends Block implements IItemHazard {

	ItemHazardModule module;

	public BlockLithium(Material material) {
		super(material);
		this.module = new ItemHazardModule();
	}

	@Override
	public ItemHazardModule getModule() {
		return module;
	}

	private boolean touchesWater(World world, int x, int y, int z) {

		if (world.isRemote)
			return false;

		return world.getBlock(x + 1, y, z).getMaterial() == Material.water
				|| world.getBlock(x - 1, y, z).getMaterial() == Material.water
				|| world.getBlock(x, y + 1, z).getMaterial() == Material.water
				|| world.getBlock(x, y - 1, z).getMaterial() == Material.water
				|| world.getBlock(x, y, z + 1).getMaterial() == Material.water
				|| world.getBlock(x, y, z - 1).getMaterial() == Material.water;
	}

	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {

		if (touchesWater(world, x, y, z)) {
			world.func_147480_a(x, y, z, false);
			world.newExplosion(null, x + 0.5, y + 0.5, z + 0.5, 15, false, true);
		}
	}

	public int onBlockPlaced(World world, int x, int y, int z, int side, float fx, float fy, float fz, int meta) {

		if (touchesWater(world, x, y, z)) {
			world.func_147480_a(x, y, z, false);
			world.newExplosion(null, x + 0.5, y + 0.5, z + 0.5, 15, false, true);
		}

		return meta;
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {

		if (world.canLightningStrikeAt(x, y + 1, z)) {

			float ox = rand.nextFloat();
			float oz = rand.nextFloat();

			world.spawnParticle("largesmoke", x + ox, y + 1, z + oz, 0.0D, 0.0D, 0.0D);
		}
	}
}
