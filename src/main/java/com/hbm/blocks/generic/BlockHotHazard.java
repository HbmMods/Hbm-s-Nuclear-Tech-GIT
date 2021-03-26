package com.hbm.blocks.generic;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockHotHazard extends BlockHazard {

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		super.randomDisplayTick(world, x, y, z, rand);
		
		if (world.canLightningStrikeAt(x, y + 1, z)) {

			float ox = rand.nextFloat();
			float oz = rand.nextFloat();

			world.spawnParticle("cloud", x + ox, y + 1, z + oz, 0.0D, 0.0D, 0.0D);
		}

		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {

			if(dir == ForgeDirection.DOWN)
				continue;

			if(world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ).getMaterial() == Material.water) {

				double ix = x + 0.5F + dir.offsetX + rand.nextDouble() - 0.5D;
				double iy = y + 0.5F + dir.offsetY + rand.nextDouble() - 0.5D;
				double iz = z + 0.5F + dir.offsetZ + rand.nextDouble() - 0.5D;

				if(dir.offsetX != 0)
					ix = x + 0.5F + dir.offsetX * 0.5 + rand.nextDouble() * 0.125 * dir.offsetX;
				if(dir.offsetY != 0)
					iy = y + 0.5F + dir.offsetY * 0.5 + rand.nextDouble() * 0.125 * dir.offsetY;
				if(dir.offsetZ != 0)
					iz = z + 0.5F + dir.offsetZ * 0.5 + rand.nextDouble() * 0.125 * dir.offsetZ;

				world.spawnParticle("cloud", ix, iy, iz, 0.0, 0.0, 0.0);
			}
		}
	}
}
