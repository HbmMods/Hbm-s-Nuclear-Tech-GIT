package com.hbm.tileentity;

import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.items.tool.ItemBlowtorch;
import com.hbm.util.InventoryUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public interface IRepairable {

	public boolean isDamaged();
	public List<AStack> getRepairMaterials();
	public void repair();
	
	public static List<AStack> getRepairMaterials(World world, int x, int y, int z, BlockDummyable dummy, EntityPlayer player) {
		
		ItemStack held = player.getHeldItem();
		
		if(held == null || !(held.getItem() instanceof ItemBlowtorch)) return null;

		int[] pos = dummy.findCore(world, x, y, z);
		if(pos == null) return null;
		TileEntity core = world.getTileEntity(pos[0], pos[1], pos[2]);
		if(!(core instanceof IRepairable)) return null;
		
		IRepairable repairable = (IRepairable) core;
		
		if(!repairable.isDamaged()) return null;
		return repairable.getRepairMaterials();
	}
	
	public static boolean tryRepairMultiblock(World world, int x, int y, int z, BlockDummyable dummy, EntityPlayer player) {

		int[] pos = dummy.findCore(world, x, y, z);
		if(pos == null) return false;
		TileEntity core = world.getTileEntity(pos[0], pos[1], pos[2]);
		if(!(core instanceof IRepairable)) return false;
		
		IRepairable repairable = (IRepairable) core;
		
		if(!repairable.isDamaged()) return false;
		
		List<AStack> list = repairable.getRepairMaterials();
		if(list == null || list.isEmpty() || InventoryUtil.doesPlayerHaveAStacks(player, list, true)) {
			if(!world.isRemote) repairable.repair();
			return true;
		}
		
		return false;
	}
}
