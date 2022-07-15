package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.handler.EnumGUI;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.oil.TileEntityMachinePumpjack;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachinePumpjack extends BlockDummyable {

	public MachinePumpjack() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= 12) return new TileEntityMachinePumpjack();
		if(meta >= 6) return new TileEntityProxyCombo(false, true, true);
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {3, 0, 0, 0, 0, 6};
	}

	@Override
	public int getOffset() {
		return 0;
	}

	@Override
	protected boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {
		return super.checkRequirement(world, x, y, z, dir, o) &&
				MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {0, 0, -1, 1, -2, 4}, x, y, z, dir) &&
				MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {0, 0, 1, -1, -1, 5}, x, y, z, dir);
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
		
		ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);
		MultiblockHandlerXR.fillSpace(world, x + rot.offsetX * 3, y, z + rot.offsetZ * 3, new int[] {0, 0, -1, 1, 1, 1}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + rot.offsetX * 3, y, z + rot.offsetZ * 3, new int[] {0, 0, 1, -1, 2, 2}, this, dir);
		
		this.makeExtra(world, x + rot.offsetX * 3 + 1, y, z + rot.offsetZ * 3 + 1);
		this.makeExtra(world, x + rot.offsetX * 3 + 1, y, z + rot.offsetZ * 3 - 1);
		this.makeExtra(world, x + rot.offsetX * 3 - 1, y, z + rot.offsetZ * 3 + 1);
		this.makeExtra(world, x + rot.offsetX * 3 - 1, y, z + rot.offsetZ * 3 - 1);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			
			int[] pos = this.findCore(world, x, y, z);
			
			if(pos == null)
				return false;
			
			FMLNetworkHandler.openGui(player, MainRegistry.instance, EnumGUI.OIL_WELL.ordinal(), world, pos[0], pos[1], pos[2]);
			return true;
		} else {
			return true;
		}
	}
}
