package com.hbm.blocks.bomb;

import com.hbm.blocks.BlockDummyable;
import com.hbm.interfaces.IBomb;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.bomb.TileEntityLaunchPad;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class LaunchPad extends BlockDummyable implements IBomb {

	public LaunchPad(Material mat) {
		super(mat);
		this.bounding.add(AxisAlignedBB.getBoundingBox(-1.5D, 0D, -1.5D, -0.5D, 1D, -0.5D));
		this.bounding.add(AxisAlignedBB.getBoundingBox(0.5D, 0D, -1.5D, 1.5D, 1D, -0.5D));
		this.bounding.add(AxisAlignedBB.getBoundingBox(-1.5D, 0D, 0.5D, -0.5D, 1D, 1.5D));
		this.bounding.add(AxisAlignedBB.getBoundingBox(0.5D, 0D, 0.5D, 1.5D, 1D, 1.5D));
		this.bounding.add(AxisAlignedBB.getBoundingBox(-0.5D, 0.5D, -1.5D, 0.5D, 1D, 1.5D));
		this.bounding.add(AxisAlignedBB.getBoundingBox(-1.5D, 0.5D, -0.5D, 1.5D, 1D, 0.5D));
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityLaunchPad();
		if(meta >= 6) return new TileEntityProxyCombo().inventory().power().fluid();
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return this.standardOpenBehavior(world, x, y, z, player, 0);
	}

	@Override
	public int[] getDimensions() {
		return new int[] {0, 0, 1, 1, 1, 1};
	}

	@Override
	public int getOffset() {
		return 1;
	}

	@Override
	public BombReturnCode explode(World world, int x, int y, int z) {
		
		if(!world.isRemote) {
			
			int[] corePos = findCore(world, x, y, z);
			if(corePos != null){
				TileEntity core = world.getTileEntity(corePos[0], corePos[1], corePos[2]);
				if(core instanceof TileEntityLaunchPad){
					TileEntityLaunchPad entity = (TileEntityLaunchPad)core;
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
				if(core instanceof TileEntityLaunchPad){
					TileEntityLaunchPad launchpad = (TileEntityLaunchPad)core;
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

		this.makeExtra(world, x + 1, y, z + 1);
		this.makeExtra(world, x + 1, y, z - 1);
		this.makeExtra(world, x - 1, y, z + 1);
		this.makeExtra(world, x - 1, y, z - 1);
	}
}
