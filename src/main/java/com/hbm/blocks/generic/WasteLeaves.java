package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class WasteLeaves extends Block {
	
	public WasteLeaves(Material mat) {
		super(mat);
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
		
		if(rand.nextInt(300) == 0) {
			world.setBlockToAir(x, y, z);
		}
		
		super.updateTick(world, x, y, z, rand);
	}
	
	/*@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		super.randomDisplayTick(world, x, y, z, rand);
		
		NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "deadleaf");

		MainRegistry.proxy.effectNT(data);
	}*/
	
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
