package com.hbm.blocks.machine.rbmk;

import com.hbm.handler.BossSpawnHandler;
import com.hbm.items.machine.ItemRBMKLid;
import com.hbm.items.machine.ItemRBMKRod;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityProxyInventory;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKRod;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class RBMKRod extends RBMKBase {
	
	public boolean moderated = false;
	
	public RBMKRod(boolean moderated) {
		super();
		this.moderated = moderated;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= this.offset)
			return new TileEntityRBMKRod();
		
		if(hasExtra(meta))
			return new TileEntityProxyInventory();
		
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		BossSpawnHandler.markFBI(player);

		if(world.isRemote) return true;

		int[] pos = this.findCore(world, x, y, z);
		if(pos == null) return false;

		TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);
		if(!(te instanceof TileEntityRBMKRod)) return false;
		TileEntityRBMKRod rbmk = (TileEntityRBMKRod) te;

		if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemRBMKLid) {
			if(!rbmk.hasLid()) return false;
		}
		
		if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemRBMKRod && rbmk.slots[0] == null) {
			rbmk.slots[0] = player.getHeldItem().copy();
			rbmk.slots[0].stackSize = 1;
			player.getHeldItem().stackSize--;
			world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, "hbm:item.upgradePlug", 1.0F, 1.0F);
			return false;
		}

		if(!player.isSneaking()) {
			FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, pos[0], pos[1], pos[2]);
			return true;
		} else {
			return true;
		}
	}
	
	@Override
	public int getRenderType(){
		return this.renderIDRods;
	}
}
