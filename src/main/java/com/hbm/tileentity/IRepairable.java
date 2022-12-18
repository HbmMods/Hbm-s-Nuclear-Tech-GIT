package com.hbm.tileentity;

import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.items.tool.ItemBlowtorch;
import com.hbm.tileentity.machine.storage.TileEntityMachineFluidTank;

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
		if(!(core instanceof TileEntityMachineFluidTank)) return null;
		
		IRepairable repairable = (IRepairable) core;
		
		if(!repairable.isDamaged()) return null;
		return repairable.getRepairMaterials();
	}
}
