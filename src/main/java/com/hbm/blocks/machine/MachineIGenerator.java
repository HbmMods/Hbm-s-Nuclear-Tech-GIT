package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachineIGenerator;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineIGenerator extends BlockDummyable {

	public MachineIGenerator(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= 12)
			return new TileEntityMachineIGenerator();
		
		if(meta >= extra)
			return new TileEntityProxyCombo(false, true, true);
		
		return null;
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
	
	/*@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			
			int[] pos = this.findCore(world, x, y, z);
			
			if(pos == null)
				return false;
			
			TileEntityMachineIGenerator gen = (TileEntityMachineIGenerator)world.getTileEntity(pos[0], pos[1], pos[2]);
			
			if(gen != null)
				FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_machine_industrial_generator, world, pos[0], pos[1], pos[2]);
			
			return true;
		} else {
			return false;
		}
<<<<<<< HEAD
	}
=======
	}*/
>>>>>>> master

	@Override
	public int[] getDimensions() {
		return new int [] {1,0,2,2,2,4};
	}

	@Override
	public int getOffset() {
		return 2;
	}
	
	protected boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {

		if(!MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, getDimensions(), x, y, z, dir))
			return false;
		if(!MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, new int [] {5,0,2,2,8,-2}, x, y, z, dir))
			return false;
		if(!MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, new int [] {4,0,2,2,-4,8}, x, y, z, dir))
			return false;
		if(!MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, new int [] {3,-2,1,1,-1,3}, x, y, z, dir))
			return false;
		if(!MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, new int [] {4,-2,1,1,1,0}, x, y, z, dir))
			return false;
		
		return true;
	}
	
	protected void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {

		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, getDimensions(), this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, new int [] {5,0,2,2,8,-2}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, new int [] {4,0,2,2,-4,8}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, new int [] {3,-2,1,1,-1,3}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, new int [] {4,-2,1,1,1,0}, this, dir);
		
<<<<<<< HEAD
		int[] rot = MultiblockHandlerXR.rotate(new int [] {1,0,2,2,8,8}, dir);
=======
		/*int[] rot = MultiblockHandlerXR.rotate(new int [] {1,0,2,2,8,8}, dir);
>>>>>>> master
		
		for(int iy = 0; iy <= 1; iy++) {
			for(int ix = -rot[4]; ix <= rot[5]; ix++) {
				for(int iz = -rot[2]; iz <= rot[3]; iz++) {
					
					if(ix == -rot[4] || ix == rot[5] || iz == -rot[2] || iz == rot[3]) {
						this.makeExtra(world, x + dir.offsetX * o + ix, y + iy, z + dir.offsetZ * o + iz);
					}
				}
			}
<<<<<<< HEAD
		}
=======
		}*/
>>>>>>> master
	}
}
