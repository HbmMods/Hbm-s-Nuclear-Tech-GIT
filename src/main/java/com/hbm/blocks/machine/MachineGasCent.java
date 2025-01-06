package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.interfaces.IMultiblock;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachineGasCent;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineGasCent extends BlockDummyable implements IMultiblock {

	public MachineGasCent(Material mat) {
		super(mat);
		this.bounding.add(AxisAlignedBB.getBoundingBox(-0.5D, 0D, -0.5D, 0.5D, 1D, 0.5D));
		this.bounding.add(AxisAlignedBB.getBoundingBox(-0.4375D, 1D, -0.4375D, 0.4375D, 4D, 0.4375D));
		this.maxY = 0.999D; //item bounce prevention
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		if(meta >= 12)
			return new TileEntityMachineGasCent();
		if(meta >= 6)
			return new TileEntityProxyCombo(false, true, true);

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

			FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, pos[0], pos[1], pos[2]);
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
