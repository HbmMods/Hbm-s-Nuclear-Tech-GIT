package com.hbm.blocks.generic;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockNTMGlassPane extends BlockPane
{
	int renderLayer;
	boolean doesDrop = false;
	
	public BlockNTMGlassPane(int layer, String name, String rimTextureName, Material material, boolean doesDrop) {
		super(name, rimTextureName, material, false);
		this.renderLayer = layer;
		this.doesDrop = doesDrop;
		this.opaque = true;
		this.setLightOpacity(1);
	}

	@Override
	public boolean canPaneConnectTo(IBlockAccess world, int x, int y, int z, ForgeDirection dir) {
		Block b = world.getBlock(x, y, z);
		return super.canPaneConnectTo(world, x, y, z, dir) || b instanceof BlockNTMGlass;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass() {
		return renderLayer;
	}

	@Override
	public int quantityDropped(Random rand) {
		return doesDrop ? 1 : 0;
	}
}
