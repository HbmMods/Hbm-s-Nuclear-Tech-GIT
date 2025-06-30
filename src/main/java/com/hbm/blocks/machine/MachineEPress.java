package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachineEPress;

import api.hbm.block.IToolable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MachineEPress extends BlockDummyable implements IToolable {

	public MachineEPress(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityMachineEPress();
		if(meta >= 6) return new TileEntityProxyCombo(true, false, false);
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {2, 0, 0, 0, 0, 0};
	}

	@Override
	public int getOffset() {
		return 0;
	}

	@Override
	protected boolean isLegacyMonoblock(World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		return te != null && te instanceof TileEntityMachineEPress;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		super.onBlockPlacedBy(world, x, y, z, player, itemStack);

		if(itemStack.hasDisplayName()) {
			int[] pos = this.findCore(world, x, y, z);
			if(pos != null) {
				TileEntityMachineEPress entity = (TileEntityMachineEPress) world.getTileEntity(pos[0], pos[1], pos[2]);
				if(entity != null) {
					entity.setCustomName(itemStack.getDisplayName());
				}
			}
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return this.standardOpenBehavior(world, x, y, z, player, 0);
	}

	// Un-multiblickable with a hand drill for schenanigans
	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {
		
		if (tool != ToolType.HAND_DRILL) 
			return false;
		
		int meta = world.getBlockMetadata(x, y, z);
		if (meta >= 12)
			return false;
		
		safeRem = true;
		world.setBlockToAir(x, y, z);
		safeRem = false;
		return true;
	}
}