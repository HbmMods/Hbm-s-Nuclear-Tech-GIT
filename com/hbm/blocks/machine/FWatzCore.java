package com.hbm.blocks.machine;

import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityFWatzCore;
import com.hbm.tileentity.TileEntityMachineBattery;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class FWatzCore extends BlockContainer {

	public FWatzCore(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityFWatzCore();
	}

}
