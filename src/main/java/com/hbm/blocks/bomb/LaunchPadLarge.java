package com.hbm.blocks.bomb;

import com.hbm.blocks.BlockDummyable;
import com.hbm.interfaces.IBomb;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.bomb.TileEntityLaunchPadLarge;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class LaunchPadLarge extends BlockDummyable implements IBomb {

	public LaunchPadLarge(Material mat) {
		super(mat);
		this.bounding.add(AxisAlignedBB.getBoundingBox(-4.5D, 0D, -4.5D, 4.5D, 1D, -0.5D));
		this.bounding.add(AxisAlignedBB.getBoundingBox(-4.5D, 0D, 0.5D, 4.5D, 1D, 4.5D));
		this.bounding.add(AxisAlignedBB.getBoundingBox(-4.5D, 0.875D, -0.5D, 4.5D, 1D, 0.5D));
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityLaunchPadLarge();
		if(meta >= 6) return new TileEntityProxyCombo().inventory().power().fluid();
		return new TileEntityProxyCombo().inventory();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return this.standardOpenBehavior(world, x, y, z, player, 0);
	}

	@Override
	public int[] getDimensions() {
		return new int[] {0, 0, 4, 4, 4, 4};
	}

	@Override
	public int getOffset() {
		return 4;
	}

	@Override
	public BombReturnCode explode(World world, int x, int y, int z) {
		
		if(!world.isRemote) {
			
			int[] corePos = findCore(world, x, y, z);
			if(corePos != null){
				TileEntity core = world.getTileEntity(corePos[0], corePos[1], corePos[2]);
				if(core instanceof TileEntityLaunchPadLarge){
					TileEntityLaunchPadLarge entity = (TileEntityLaunchPadLarge)core;
					return entity.launchFromDesignator();
				}
			}
		}
		
		return BombReturnCode.UNDEFINED;
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block blockIn){
		
		if(!world.isRemote){
			
			int[] corePos = findCore(world, x, y, z);
			if(corePos != null){
				TileEntity core = world.getTileEntity(corePos[0], corePos[1], corePos[2]);
				if(core instanceof TileEntityLaunchPadLarge){
					TileEntityLaunchPadLarge launchpad = (TileEntityLaunchPadLarge)core;
					launchpad.updateRedstonePower(x, y, z);
				}
			}
		}
		super.onNeighborBlockChange( world, x, y, z, blockIn);
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		x += dir.offsetX * o;
		z += dir.offsetZ * o;

		this.makeExtra(world, x + 4, y, z + 2);
		this.makeExtra(world, x + 4, y, z - 2);
		this.makeExtra(world, x - 4, y, z + 2);
		this.makeExtra(world, x - 4, y, z - 2);
		this.makeExtra(world, x + 2, y, z + 4);
		this.makeExtra(world, x - 2, y, z + 4);
		this.makeExtra(world, x + 2, y, z - 4);
		this.makeExtra(world, x - 2, y, z - 4);
	}
}
