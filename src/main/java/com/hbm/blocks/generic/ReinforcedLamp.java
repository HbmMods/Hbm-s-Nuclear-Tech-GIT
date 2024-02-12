package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ReinforcedLamp extends Block {

	private final boolean isOn;

	public ReinforcedLamp(Material mat, boolean isOn) {
		super(mat);
		this.isOn = isOn;

		if(isOn) {
			this.setLightLevel(1.0F);
		}
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		
		if(!world.isRemote) {
			
			if(this.isOn && !world.isBlockIndirectlyGettingPowered(x, y, z)) {
				world.scheduleBlockUpdate(x, y, z, this, 4);
				
			} else if(!this.isOn && world.isBlockIndirectlyGettingPowered(x, y, z)) {
				world.setBlock(x, y, z, getOn(), 0, 2);
			}
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block b) {
		
		if(!world.isRemote) {
			
			if(this.isOn && !world.isBlockIndirectlyGettingPowered(x, y, z)) {
				world.scheduleBlockUpdate(x, y, z, this, 4);
				
			} else if(!this.isOn && world.isBlockIndirectlyGettingPowered(x, y, z)) {
				world.setBlock(x, y, z, getOn(), 0, 2);
			}
		}
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random p_149674_5_) {
		
		if(!world.isRemote && this.isOn && !world.isBlockIndirectlyGettingPowered(x, y, z)) {
			world.setBlock(x, y, z, getOff(), 0, 2);
		}
	}

	@Override
	public Item getItemDropped(int i, Random r, int j) {
		return Item.getItemFromBlock(getOff());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World world, int x, int y, int z) {
		return Item.getItemFromBlock(getOff());
	}

	@Override
	protected ItemStack createStackedBlock(int e) {
		return new ItemStack(getOff());
	}
	
	protected Block getOff() {
		if(this == ModBlocks.reinforced_lamp_on) return ModBlocks.reinforced_lamp_off;
		return this;
	}
	
	protected Block getOn() {
		if(this == ModBlocks.reinforced_lamp_off) return ModBlocks.reinforced_lamp_on;
		return this;
	}
}
