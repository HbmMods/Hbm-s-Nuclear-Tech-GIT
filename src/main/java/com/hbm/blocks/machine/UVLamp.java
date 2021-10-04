package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityUVLamp;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class UVLamp extends BlockDummyable {

	public UVLamp(boolean isOn) {
		super(Material.iron);
		this.setStepSound(Block.soundTypeMetal);
		this.setHardness(3.0F);
		this.setBlockTextureName(RefStrings.MODID + ":block_steel");
		
		if(isOn) this.setLightLevel(5F/15F);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityUVLamp();
	}

	@Override
	public int[] getDimensions() {
		return new int[] {1, 0, 0, 0, 0, 0};
	}

	@Override
	public int getOffset() {
		return 0;
	}
}
