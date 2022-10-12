package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IMultiblock;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachineCentrifuge;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineCentrifuge extends BlockDummyable implements IMultiblock {

	public MachineCentrifuge(Material mat) {
		super(mat);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		if(meta >= 12)
			return new TileEntityMachineCentrifuge();
		if(meta >= 6)
			return new TileEntityProxyCombo(false, true, true);

		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			int[] pos = this.findCore(world, x, y, z);

			if(pos == null)
				return false;

			FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_centrifuge, world, pos[0], pos[1], pos[2]);
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public int[] getDimensions() {
		return new int[] {3, 0, 0, 0, 0, 0,}; 
	}

	@Override
	public int getOffset() {
		return 0;
	}
	
	protected void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
	}
}
