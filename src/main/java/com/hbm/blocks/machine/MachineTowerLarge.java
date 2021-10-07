package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityTowerLarge;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineTowerLarge extends BlockDummyable {

	public MachineTowerLarge(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int meta) {
		
		if(meta >= 12)
			return new TileEntityTowerLarge();
		
		if(meta >= 8)
			return new TileEntityProxyCombo(false, false, true);
		
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking())
		{
			int[] pos = this.findCore(world, x, y, z);
			if(pos == null)
				return false;
			TileEntityTowerLarge entity = (TileEntityTowerLarge) world.getTileEntity(pos[0], pos[1], pos[2]);
			if(entity != null)
			{
				switch(entity.tanks[0].getTankType()) {
				case SPENTSTEAM: 
					entity.tanks[0].setTankType(FluidType.SPENTHEAVYSTEAM); 
					entity.tanks[1].setTankType(FluidType.HEAVYWATER); 
					break;
				case SPENTHEAVYSTEAM: 
					entity.tanks[0].setTankType(FluidType.SPENTSTEAM); 
					entity.tanks[1].setTankType(FluidType.WATER); 
					break;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int[] getDimensions() {
		return new int[] {12, 0, 4, 4, 4, 4};
	}

	@Override
	public int getOffset() {
		return 4;
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
		
		x = x + dir.offsetX * o;
		z = z + dir.offsetZ * o;
		
		for(int i = 2; i <= 6; i++) {
			ForgeDirection dr2 = ForgeDirection.getOrientation(i);
			ForgeDirection rot = dr2.getRotation(ForgeDirection.UP);
			this.makeExtra(world, x + dr2.offsetX * 4, y, z + dr2.offsetZ * 4);
			this.makeExtra(world, x + dr2.offsetX * 4 + rot.offsetX * 3, y, z + dr2.offsetZ * 4 + rot.offsetZ * 3);
			this.makeExtra(world, x + dr2.offsetX * 4 + rot.offsetX * -3, y, z + dr2.offsetZ * 4 + rot.offsetZ * -3);
		}
	}
}
