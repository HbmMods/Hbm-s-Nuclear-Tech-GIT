package com.hbm.blocks.generic;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;

public class BlockNTMGlassPane extends BlockBreakable {

	int renderLayer;
	boolean doesDrop = false;
	
	public BlockNTMGlassPane(int layer, String name, Material material) {
		this(layer, name, material, false);
	}

	public BlockNTMGlassPane(int layer, String name, Material material, boolean doesDrop) {
		super(name, material, false);
		this.renderLayer = layer;
		this.doesDrop = doesDrop;
	}

	public int quantityDropped(Random rand) {
		return doesDrop ? 1 : 0;
	}

	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass() {
		return renderLayer;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	protected boolean canSilkHarvest() {
		return true;
	}

}
