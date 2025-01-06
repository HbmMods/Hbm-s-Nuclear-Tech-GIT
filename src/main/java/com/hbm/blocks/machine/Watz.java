package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.inventory.material.Mats;
import com.hbm.items.ModItems;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityWatz;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class Watz extends BlockDummyable {

	public Watz() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= 12) return new TileEntityWatz();
		if(meta >= 6) return new TileEntityProxyCombo().inventory().fluid();
		return null;
	}

	@Override
	public Item getItemDropped(int i, Random rand, int j) {
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return super.standardOpenBehavior(world, x, y, z, player, 0);
	}

	@Override
	public int[] getDimensions() {
		return new int[] {2, 0, 3, 3, 1, 1};
	}

	@Override
	public int getOffset() {
		return 3;
	}

	@Override
	protected boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {
		return super.checkRequirement(world, x, y, z, dir, o) &&
				MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[]{2, 0, 2, 2, 2, -2}, x, y, z, dir) &&
				MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[]{2, 0, 2, 2, -2, 2}, x, y, z, dir) &&
				MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[]{2, 0, 1, 1, 3, -3}, x, y, z, dir) &&
				MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[]{2, 0, 1, 1, -3, 3}, x, y, z, dir);
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[]{2, 0, 2, 2, 2, -2}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[]{2, 0, 2, 2, -2, 2}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[]{2, 0, 1, 1, 3, -3}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[]{2, 0, 1, 1, -3, 3}, this, dir);

		x += dir.offsetX * o;
		z += dir.offsetZ * o;

		this.makeExtra(world, x + 2, y, z);
		this.makeExtra(world, x - 2, y, z);
		this.makeExtra(world, x, y, z + 2);
		this.makeExtra(world, x, y, z - 2);
		this.makeExtra(world, x + 2, y + 2, z);
		this.makeExtra(world, x - 2, y + 2, z);
		this.makeExtra(world, x, y + 2, z + 2);
		this.makeExtra(world, x, y + 2, z - 2);
		this.makeExtra(world, x, y + 2, z);
	}
	
	public static boolean drop = true;
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int i) {

		if(i >= 12 && drop) {

			world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, new ItemStack(ModBlocks.watz_end, 48)));
			for(int j = 0; j < 3; j++) world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, Mats.MAT_DURA.make(ModItems.bolt, 64)));
			world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, new ItemStack(ModBlocks.watz_element, 36)));
			world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, new ItemStack(ModBlocks.watz_cooler, 26)));
			world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, new ItemStack(ModBlocks.struct_watz_core, 1)));
		}

		super.breakBlock(world, x, y, z, block, i);
	}
}
