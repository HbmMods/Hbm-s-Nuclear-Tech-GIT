package com.hbm.blocks.machine;


import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.BossSpawnHandler;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityReactorZirnox;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.material.Material;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ReactorZirnox extends BlockDummyable {

	public ReactorZirnox(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		if(meta >= 12)
			return new TileEntityReactorZirnox();
		if(meta >= 6)
			return new TileEntityProxyCombo(true, true, true);

		return null;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			BossSpawnHandler.markFBI(player);
			
			int[] pos = this.findCore(world, x, y, z);

			if(pos == null)
				return false;

			FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, pos[0], pos[1], pos[2]);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int[] getDimensions() {
		return new int[] {1, 0, 2, 2, 2, 2,}; 
	}

	@Override
	public int getOffset() {
		return 2;
	}

	@Override
	protected boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {
		return super.checkRequirement(world, x, y, z, dir, o) &&
				MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {4, -2, 1, 1, 1, 1}, x, y, z, dir) &&
				MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {4, -2, 0, 0, 2, -2}, x, y, z, dir) &&
				MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {4, -2, 0, 0, -2, 2}, x, y, z, dir);
	}

	@Override
	protected void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {4, -2, 1, 1, 1, 1}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {4, -2, 0, 0, 2, -2}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {4, -2, 0, 0, -2, 2}, this, dir);

		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		this.makeExtra(world, x + dir.offsetX * o + rot.offsetX * 2, y + 1, z + dir.offsetZ * o + rot.offsetZ * 2);
		this.makeExtra(world, x + dir.offsetX * o + rot.offsetX * 2, y + 3, z + dir.offsetZ * o + rot.offsetZ * 2);
		this.makeExtra(world, x + dir.offsetX * o + rot.offsetX * -2, y + 1, z + dir.offsetZ * o + rot.offsetZ * -2);
		this.makeExtra(world, x + dir.offsetX * o + rot.offsetX * -2, y + 3, z + dir.offsetZ * o + rot.offsetZ * -2);
		//i still don't know why the ports were such an issue all those months ago
		this.makeExtra(world, x + dir.offsetX * o, y + 4, z + dir.offsetZ * o);
	}

}