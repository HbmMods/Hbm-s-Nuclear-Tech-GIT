package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.dim.SolarSystem;
import com.hbm.items.ItemVOTVdrive;
import com.hbm.items.ItemVOTVdrive.Destination;
import com.hbm.tileentity.machine.TileEntityOrbitalStationComputer;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockOrbitalStationComputer extends BlockDummyable {

	public BlockOrbitalStationComputer(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityOrbitalStationComputer();
	}

	@Override
	public int[] getDimensions() {
		return new int[] {0, 0, 0, 0, 0, 0};
	}

	@Override
	public int getOffset() {
		return 0;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		ItemStack heldStack = player.getHeldItem();
		if(heldStack == null || !(heldStack.getItem() instanceof ItemVOTVdrive))
			return false;

		Destination destination = ItemVOTVdrive.getDestination(heldStack);

		if(destination.body == SolarSystem.Body.ORBIT)
			return false;

		if(world.isRemote) {
			return true;
		} else {
			int[] pos = this.findCore(world, x, y, z);
	
			if(pos == null)
				return false;

			TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);

			if(!(te instanceof TileEntityOrbitalStationComputer))
				return false;

			((TileEntityOrbitalStationComputer)te).travelTo(destination.body.getBody());
			
			return true;
		}
	}
	
}
