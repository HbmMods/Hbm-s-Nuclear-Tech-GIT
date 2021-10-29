package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.Random;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IMultiblock;
import com.hbm.items.ModItems;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityZirnoxDestroyed;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ZirnoxDestroyed extends BlockDummyable implements IMultiblock {
	
	public ZirnoxDestroyed(Material mat) {
		super(mat);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= 12)
			return new TileEntityZirnoxDestroyed();
		if(meta >= 6)
			return new TileEntityProxyCombo(false, true, true);
		
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
			return false;
	}
	
	@Override
	public Item getItemDropped(int meta, Random rand, int fortune) {
		return null;
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune) {
		ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
		drops.add(new ItemStack(ModBlocks.concrete_smooth, 6));
		drops.add(new ItemStack(ModBlocks.deco_pipe_quad, 4));
		drops.add(new ItemStack(ModBlocks.steel_grate, 2));
		drops.add(new ItemStack(ModItems.debris_metal, 4));
		drops.add(new ItemStack(ModItems.debris_graphite, 1));
		return drops;
	}
	
	@Override
	public int[] getDimensions() {
		return new int[] {1, 0, 2, 2, 2, 2,}; 
	}
	
	@Override
	public int getOffset() {
		return 2;
	}
	
	protected void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
	}
	
}
