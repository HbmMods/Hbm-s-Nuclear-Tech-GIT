package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.atmosphere.IBlockSealable;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityOrbitalStation;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockOrbitalStation extends BlockDummyable implements IBlockSealable {

	public BlockOrbitalStation(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityOrbitalStation();
		if(meta >= 6) return new TileEntityProxyCombo(true, false, true);
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {1, 0, 2, 2, 2, 2};
	}

	@Override
	public int getOffset() {
		return 2;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(player.isSneaking())
			return false;

		int[] pos = this.findCore(world, x, y, z);

		if(pos == null)
			return false;

		// If activating the side blocks, ignore, to allow placing
		if(Math.abs(pos[0] - x) >= 2 || Math.abs(pos[2] - z) >= 2)
			return false;

		if(world.isRemote) {
			return true;
		} else {
			TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);

			if(!(te instanceof TileEntityOrbitalStation))
				return false;

			((TileEntityOrbitalStation)te).enterCapsule(player);
			
			return true;
		}
	}

	@Override
	public boolean isSealed(World world, int x, int y, int z) {
		return true;
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		x += dir.offsetX * o;
		z += dir.offsetZ * o;

		this.makeExtra(world, x + 2, y + 1, z - 1);
		this.makeExtra(world, x + 2, y + 1, z + 0);
		this.makeExtra(world, x + 2, y + 1, z + 1);
		this.makeExtra(world, x - 2, y + 1, z - 1);
		this.makeExtra(world, x - 2, y + 1, z + 0);
		this.makeExtra(world, x - 2, y + 1, z + 1);
		this.makeExtra(world, x - 1, y + 1, z + 2);
		this.makeExtra(world, x + 0, y + 1, z + 2);
		this.makeExtra(world, x + 1, y + 1, z + 2);
		this.makeExtra(world, x - 1, y + 1, z - 2);
		this.makeExtra(world, x + 0, y + 1, z - 2);
		this.makeExtra(world, x + 1, y + 1, z - 2);
	}
	
}
