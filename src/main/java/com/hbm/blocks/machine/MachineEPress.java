package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityMachineEPress;
import com.hbm.world.gen.INBTTransformable;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MachineEPress extends BlockDummyable implements INBTTransformable {

	public MachineEPress(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityMachineEPress();
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {2, 0, 0, 0, 0, 0};
	}

	@Override
	public int getOffset() {
		return 0;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		super.onBlockPlacedBy(world, x, y, z, player, itemStack);

		if(itemStack.hasDisplayName()) {
			int[] pos = this.findCore(world, x, y, z);
			if(pos != null) {
				TileEntityMachineEPress entity = (TileEntityMachineEPress) world.getTileEntity(pos[0], pos[1], pos[2]);
				if(entity != null) {
					entity.setCustomName(itemStack.getDisplayName());
				}
			}
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			int[] pos = this.findCore(world, x, y, z);
			if(pos == null)
				return false;

			TileEntityMachineEPress entity = (TileEntityMachineEPress) world.getTileEntity(pos[0], pos[1], pos[2]);
			if(entity == null) 
				return false;
			
			FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, x, y, z);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int transformMeta(int meta, int coordBaseMode) {
		return INBTTransformable.transformMetaDeco(meta, coordBaseMode);
	}
}