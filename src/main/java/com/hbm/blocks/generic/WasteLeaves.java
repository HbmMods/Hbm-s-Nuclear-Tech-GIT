package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.RogueWorldHandler;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.main.ModEventHandlerRogue;
import com.hbm.saveddata.RogueWorldSaveData;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class WasteLeaves extends Block {

	public WasteLeaves(Material mat) {
		super(mat);
		this.setTickRandomly(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":waste_leaves");
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return null;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {

		if(this==ModBlocks.waste_leaves)
		{
			if(rand.nextInt(30) == 0) {
				world.setBlockToAir(x, y, z);
				
				if(world.getBlock(x, y - 1, z).getMaterial() == Material.air) {
					EntityFallingBlock leaves = new EntityFallingBlock(world, x + 0.5, y + 0.5, z + 0.5, ModBlocks.leaves_layer);
					leaves.field_145812_b = 2;
					leaves.field_145813_c = false;
					world.spawnEntityInWorld(leaves);
				}
			}
		}
		if(this==ModBlocks.frozen_leaves)
		{
			RogueWorldSaveData data = RogueWorldSaveData.forWorld(world);
			float temp = ModEventHandlerRogue.getTemperatureAtDepth(y, world);
			if(temp <0)
			{
				for(int i = -1; i < 2; i++) {
					for(int j = -1; j < 2; j++) {
						for(int k = -1; k < 2; k++) {
							RogueWorldHandler.freeze(world, x+i, y+j, z+k, temp);
						}
					}
				}
			}
			for(int i = -1; i < 2; i++) {
				for(int j = -1; j < 2; j++) {
					for(int k = -1; k < 2; k++) {
						Block b0 = world.getBlock(x + i, y + j, z + k);
						if(b0 instanceof BlockFire || b0.getMaterial()==Material.lava);
						{
							world.setBlock(x, y, z, Blocks.air);
						}
					}
				}
			}	
		}    	
		super.updateTick(world, x, y, z, rand);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		super.randomDisplayTick(world, x, y, z, rand);
		
		if(rand.nextInt(7) == 0 && world.getBlock(x, y - 1, z).getMaterial() == Material.air) {
			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "deadleaf");
			data.setDouble("posX", x + rand.nextDouble());
			data.setDouble("posY", y - 0.05);
			data.setDouble("posZ", z + rand.nextDouble());
			MainRegistry.proxy.effectNT(data);
		}
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	protected boolean canSilkHarvest() {
		return false;
	}
}