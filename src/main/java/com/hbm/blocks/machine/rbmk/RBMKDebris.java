package com.hbm.blocks.machine.rbmk;

import java.util.Random;

import com.hbm.main.MainRegistry;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class RBMKDebris extends Block {

	public RBMKDebris() {
		super(Material.iron);
	}

	public static int renderID = RenderingRegistry.getNextAvailableRenderId();
	
	@Override
	public int getRenderType(){
		return this.renderID;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {

		if(world.getBlock(x, y + 1, z) == Blocks.air && rand.nextInt(100) == 0) {
			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "rbmkflame");
			data.setInteger("maxAge", 300);
			data.setDouble("posX", x + 0.5);
			data.setDouble("posY", y + 1.75);
			data.setDouble("posZ", z + 0.5);
			MainRegistry.proxy.effectNT(data);
		}
	}
}
