package com.hbm.blocks.generic;

import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockNTMGlassPane extends BlockPane
{
	int renderLayer;
	boolean doesDrop = false;
	
    //NOTE when you have eclipse make the constructor for you it *WILL BE 'protected'* so make sure to make this public like below.
	/*public BlockNTMGlassPane(String flatFaceTextureName, String rimTextureName,
		Material mat, boolean bool) {
	super(flatFaceTextureName, rimTextureName, mat, bool);
	// TODO Auto-generated constructor stub
		this.setLightOpacity(1);
		this.opaque = true;
	}*/
	
	public BlockNTMGlassPane(int layer, String name, String rimTextureName, Material material, boolean doesDrop) {
		super(name, rimTextureName, material, false);
		this.renderLayer = layer;
		this.doesDrop = doesDrop;
		this.opaque = true;
		this.setLightOpacity(1);
		
		
	}
	
	public boolean canPaneConnectTo(IBlockAccess world, int x, int y, int z, ForgeDirection dir)
    {
        
		if (getIdFromBlock(world.getBlock(x,y,z)) == 0)
			return false;
		else
			return true;
		
		/*return canPaneConnectToBlock(world.getBlock(x, y, z)) || 
                world.isSideSolid(x, y, z, dir.getOpposite(), false);*/
		
    }

}
