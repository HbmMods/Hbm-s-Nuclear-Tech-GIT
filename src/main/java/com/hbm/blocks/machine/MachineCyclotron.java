package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachineCyclotron;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineCyclotron extends BlockDummyable {

	public MachineCyclotron(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= 12)
			return new TileEntityMachineCyclotron();
		
		if(meta >= 6)
			return new TileEntityProxyCombo().inventory().power().fluid();
		
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
			
			TileEntityMachineCyclotron cyc = (TileEntityMachineCyclotron)world.getTileEntity(pos[0], pos[1], pos[2]);
			
			if(player.getHeldItem() != null) {
				
				for(int i = 0; i < 4; i++) {
					
					if(player.getHeldItem().getItem() == TileEntityMachineCyclotron.getItemForPlug(i) && !cyc.getPlug(i)) {
						player.getHeldItem().stackSize--;
						cyc.setPlug(i);
						world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, "hbm:item.upgradePlug", 1.5F, 1.0F);
						return true;
					}
				}
			}
			
			FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, pos[0], pos[1], pos[2]);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {2, 0, 2, 2, 2, 2};
	}

	@Override
	public int getOffset() {
		return 2;
	}
	@Override
	public int[][] getAllPorts(ForgeDirection dir) {
		return rotatePorts(new int[][] {
			{2, 0, 1, ForgeDirection.EAST.ordinal()},
			{2, 0, 0, ForgeDirection.EAST.ordinal()},
			{2, 0, -1, ForgeDirection.EAST.ordinal()},
			{-2, 0, 1, ForgeDirection.WEST.ordinal()},
			{-2, 0, 0, ForgeDirection.WEST.ordinal()},
			{-2, 0, -1, ForgeDirection.WEST.ordinal()},
			{1, 0, 2, ForgeDirection.SOUTH.ordinal()},
			{0, 0, 2, ForgeDirection.SOUTH.ordinal()},
			{-1, 0, 2, ForgeDirection.SOUTH.ordinal()},
			{1, 0, -2, ForgeDirection.NORTH.ordinal()},
			{0, 0, -2, ForgeDirection.NORTH.ordinal()},
			{-1, 0, -2, ForgeDirection.NORTH.ordinal()}
		}, dir);
	}	
	protected void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		for(int[] offset : getAllPorts(dir)) {
			this.makeExtra(world, x + offset[0], y + offset[1], z + offset[2]);
		}
	}
}
