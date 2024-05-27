package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachineIGenerator;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineIGenerator extends BlockDummyable implements ILookOverlay {

	public MachineIGenerator(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= 12)
			return new TileEntityMachineIGenerator();
		
		if(meta >= extra)
			return new TileEntityProxyCombo(false, true, true);
		
		return new TileEntityProxyCombo(true, false, false);
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
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			
			int[] pos = this.findCore(world, x, y, z);
			
			if(pos == null)
				return false;
			
			//TileEntityMachineIGenerator gen = (TileEntityMachineIGenerator)world.getTileEntity(pos[0], pos[1], pos[2]);
			
			//if(gen != null) FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, pos[0], pos[1], pos[2]);
			
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
		/*this.makeExtra(world, x + dir.offsetX * (o - 3), y, z + dir.offsetZ * (o - 3));
		this.makeExtra(world, x + dir.offsetX * (o - 2), y, z + dir.offsetZ * (o - 2));
		this.makeExtra(world, x + dir.offsetX * (o - 1), y, z + dir.offsetZ * (o - 1));
		this.makeExtra(world, x + dir.offsetX * (o + 2), y, z + dir.offsetZ * (o + 2));*/
	}

	@Override
	public int[] getDimensions() {
		//return new int [] {2, 0, 3, 2, 1, 1};
		return new int [] {0, 0, 0, 0, 0, 0};
	}

	@Override
	public int getOffset() {
		//return 2;
		return 0;
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		List<String> text = new ArrayList();
		text.add("In memory of all that we have lost");
		ILookOverlay.printGeneric(event, "Industrial Generator Memorial", 0xff8000, 0x804000, text);
	}
}
