package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.BlockEnumMulti;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class BlockDeepCobble extends BlockEnumMulti {
	
	public static enum EnumDeepCobbleTypes {
		NORMAL,
		BURNING,
		STEAMING
	}

	public BlockDeepCobble() {
		super(Material.rock, EnumDeepCobbleTypes.class, true, true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		super.randomDisplayTick(world, x, y, z, rand);
		
		int meta = world.getBlockMetadata(x, y, z);
		Block b = world.getBlock(x, y + 1, z);
		
		if(!b.isNormalCube()) {
			if(meta == EnumDeepCobbleTypes.BURNING.ordinal()) {
				world.spawnParticle("flame", x + rand.nextDouble(), y + 1.0625, z + rand.nextDouble(), 0.0, 0.0, 0.0);
			}
			
			if(meta == EnumDeepCobbleTypes.STEAMING.ordinal()) {
				world.spawnParticle("cloud", x + 0.25 + rand.nextDouble() * 0.5, y + 1.0625, z + 0.25 + rand.nextDouble() * 0.5, 0.0, 0.05, 0.0);
			}
		}
	}
}