package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachineCryoDistill;
import com.hbm.tileentity.machine.oil.TileEntityMachineCatalyticReformer;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineCryoDistill extends BlockDummyable {

	public MachineCryoDistill(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityMachineCryoDistill();
		if(meta >= 6) return new TileEntityProxyCombo().fluid().power();
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {4, 0, 2, 1, 4, 3};
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return this.standardOpenBehavior(world, x, y, z, player, 0);
	}

	@Override
	public int getOffset() {
		return 2;
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		x += dir.offsetX * o;
		z += dir.offsetZ * o;
		ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);
		
		this.safeRem = true;

		//this.makeExtra(world, x - dir.offsetX + 1, y, z - dir.offsetZ + 1);
		//this.makeExtra(world, x - dir.offsetX + 1, y, z - dir.offsetZ - 1);
		
		//this.makeExtra(world, x - dir.offsetX - 2, y, z - dir.offsetZ + 1);
		//this.makeExtra(world, x - dir.offsetX + 2, y, z - dir.offsetZ + 3);
		
		///this.makeExtra(world, x - dir.offsetX + 3, y, z - dir.offsetZ + 1);
		//this.makeExtra(world, x - dir.offsetX + 2, y, z - dir.offsetZ - 2);
		
		//this.makeExtra(world, x - dir.offsetX + 2, y, z - dir.offsetZ - 1);
		
		//EAST-WEST
		this.makeExtra(world, x - dir.offsetX - 1, y, z + dir.offsetZ - 2); 
		this.makeExtra(world, x - dir.offsetX - 1, y, z - dir.offsetZ + 3); 
		this.makeExtra(world, x - dir.offsetX - 1, y, z + dir.offsetZ - 1);

		this.makeExtra(world, x - dir.offsetX - 2, y, z - dir.offsetZ - 3);
		this.makeExtra(world, x - dir.offsetX - 2, y, z + dir.offsetZ + 2);
		this.makeExtra(world, x - dir.offsetX - 2, y, z + dir.offsetZ + 1); 
		
		this.makeExtra(world, x - dir.offsetX + 2, y, z + dir.offsetZ - 2); 
		this.makeExtra(world, x - dir.offsetX + 2, y, z - dir.offsetZ + 3); 
		this.makeExtra(world, x - dir.offsetX + 2, y, z + dir.offsetZ - 1);
		
		this.makeExtra(world, x - dir.offsetX + 1, y, z + dir.offsetZ + 1); 
		this.makeExtra(world, x - dir.offsetX + 1, y, z + dir.offsetZ + 2);
		this.makeExtra(world, x - dir.offsetX + 1, y, z - dir.offsetZ - 3);
		
		//NS
		this.makeExtra(world, x + dir.offsetX + 2, y, z - dir.offsetZ - 1);
		this.makeExtra(world, x - dir.offsetX - 3, y, z - dir.offsetZ - 1); 
		this.makeExtra(world, x + dir.offsetX - 2, y, z - dir.offsetZ - 1); 
		
		this.makeExtra(world, x - dir.offsetX + 2, y, z - dir.offsetZ - 2);
		this.makeExtra(world, x - dir.offsetX + 3, y, z - dir.offsetZ - 2);
		this.makeExtra(world, x + dir.offsetX - 2, y, z - dir.offsetZ - 2);

		this.makeExtra(world, x - dir.offsetX - 3, y, z - dir.offsetZ + 2);
		this.makeExtra(world, x - dir.offsetX - 2, y, z - dir.offsetZ + 2);
		this.makeExtra(world, x + dir.offsetX + 2, y, z - dir.offsetZ + 2); 
		
		this.makeExtra(world, x - dir.offsetX + 2, y, z - dir.offsetZ + 3); 
		this.makeExtra(world, x - dir.offsetX + 3, y, z - dir.offsetZ + 1); 
		this.makeExtra(world, x + dir.offsetX - 2, y, z - dir.offsetZ + 1); 



		
		this.safeRem = false;
	}
}

