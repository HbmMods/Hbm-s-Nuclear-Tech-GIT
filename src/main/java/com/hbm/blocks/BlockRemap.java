package com.hbm.blocks;

import java.util.ArrayList;
import java.util.Random;

import com.hbm.lib.RefStrings;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class BlockRemap extends Block implements ILookOverlay {
	
	public Block remapBlock;
	public int remapMeta;

	protected BlockRemap(Block block, int meta) {
		super(Material.tnt);
		this.remapBlock = block;
		this.remapMeta = meta;
		this.setTickRandomly(true);
	}
	
	@Override
	public Block setBlockName(String name) {
		super.setBlockName(name);
		this.setBlockTextureName(RefStrings.MODID + ":" + name);
		return this;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		world.setBlock(x, y, z, this.remapBlock, this.remapMeta, 2);
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		ILookOverlay.printGeneric(event, "Compatibility block, will convert on update tick.", 0xffff00, 0x404000, new ArrayList());
	}
}
