package com.hbm.blocks.generic;

import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;

public class BlockNTMGlassPaneRot extends BlockPane
{
	int renderLayer;
	boolean doesDrop = false;
	
    //NOTE when you have eclipse make the constructor for you it *WILL BE 'protected'* so make sure to make this public like below.
	public BlockNTMGlassPaneRot(String flatFaceTextureName, String rimTextureName,
		Material mat, boolean bool) {
	super(flatFaceTextureName, rimTextureName, mat, bool);
	// TODO Auto-generated constructor stub

		this.opaque = true;
	}
	
	public BlockNTMGlassPaneRot(int layer, String name, String rimTextureName, Material material, boolean doesDrop) {
		super(name, rimTextureName, material, false);
		this.renderLayer = layer;
		this.doesDrop = doesDrop;
		this.opaque = true;
		this.setLightOpacity(1);
	}

}
