package com.hbm.blocks.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockPillar extends Block {

	@SideOnly(Side.CLIENT)
	private IIcon iconTop;
	@SideOnly(Side.CLIENT)
	private IIcon iconAlt;

	private String textureTop = "";
	private String textureAlt = "";

	public BlockPillar(Material mat, String top) {
		super(mat);
		textureTop = top;
	}

	public BlockPillar(Material mat, String top, String bottom) {
		this(mat, top);
		textureAlt = bottom;
	}

    public Block setBlockTextureName(String name) {
    	
    	if(textureTop.isEmpty())
    		textureTop = name;
    	
    	if(textureAlt.isEmpty())
    		textureAlt = name;
    	
        this.textureName = name;
        
        return this;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		
		this.iconAlt = iconRegister.registerIcon(textureAlt.isEmpty() ? RefStrings.MODID + ":code" : textureAlt);
		this.iconTop = iconRegister.registerIcon(textureTop);
		this.blockIcon = iconRegister.registerIcon(this.textureName);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		
		if(this == ModBlocks.reactor_element && metadata == 1)
			return side == 1 ? this.iconTop : (side == 0 ? this.iconTop : this.iconAlt);
		
		return side == 1 ? this.iconTop : (side == 0 ? this.iconTop : this.blockIcon);
	}

	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(this != ModBlocks.reactor_element)
			return super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
		
		if(player.isSneaking())
		{
			if(world.getBlockMetadata(x, y, z) == 0) {
				world.setBlockMetadataWithNotify(x, y, z, 1, 3);
			} else {
				world.setBlockMetadataWithNotify(x, y, z, 0, 3);
			}
			
			return true;
		}
		
		return false;
	}

}
