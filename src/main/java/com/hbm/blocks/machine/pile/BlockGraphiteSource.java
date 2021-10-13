package com.hbm.blocks.machine.pile;

import java.util.ArrayList;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.tileentity.machine.pile.TileEntityPileSource;

import api.hbm.block.IToolable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockGraphiteSource extends BlockGraphiteDrilledTE implements IToolable {

	@Override
	public TileEntity createNewTileEntity(World world, int mets) {
		return new TileEntityPileSource();
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> drops = super.getDrops(world, x, y, z, metadata, fortune);
		drops.add(new ItemStack(whoAmIAgain()));
		return drops;
	}

	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {
		
		if(tool != ToolType.SCREWDRIVER)
			return false;
		
		if(!world.isRemote) {

			int meta = world.getBlockMetadata(x, y, z) & 3;
			
			if(side == meta * 2 || side == meta * 2 + 1) {
				world.setBlock(x, y, z, ModBlocks.block_graphite_drilled, meta, 3);
				this.ejectItem(world, x, y, z, ForgeDirection.getOrientation(side), new ItemStack(whoAmIAgain()));
			}
		}
		
		return true;
	}
	
	private Item whoAmIAgain() {
		return this == ModBlocks.block_graphite_plutonium ? ModItems.pile_rod_plutonium : ModItems.pile_rod_source;
	}
}
