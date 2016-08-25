package com.hbm.blocks;

import com.hbm.lib.RefStrings;
import com.hbm.tileentity.TileEntityTestRender;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

//Testblock zum Rendern/Ändern der visuellen Eigenschaften eines Tiles (Blockes)

//"extends BlockContainer" um ein TileEntity zu erschaffen

public class TestRender extends BlockContainer {
	
	//Y U NO WANTED WORKINGS? NAO U WORKINGS AN I KICK UR ARSE

	//Normaler Matrial-Constructor
	public TestRender(Material p_i45394_1_) {
		super(p_i45394_1_);
	}
	
	//Nicht verfügbarer Rendertyp, setzt den Switch auf "Default" und ermöglicht einen Customrenderer
	@Override
	public int getRenderType(){
		return -1;
	}
	
	//Ob der Block transparent ist (Glas, Glowstone, Wasser, etc)
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	//"Default" beim Switch zum Rendern ermöglicht die Abfrage "renderAsNormalBlock", "true" um es als einen normalen Block rendern zu lassen, "false" für einen Customrenderer
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	
	//Erstellen eines TileEntitys
	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityTestRender();
	}
	
	//GUI Blocktextur muss für Custommodel-Blocke nachträglich geändert werden
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconregister) {
		this.blockIcon = iconregister.registerIcon(RefStrings.MODID + ":test_render");
	}
	
	//Setzt die Blockkollisionsbox (xMin, yMin, zMin, xMax, yMax, zMax)
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
    {
        float f = 0.0625F;
        this.setBlockBounds(2*f, 0.0F, 2*f, 14*f, 1.0F, 14*f);
    }

}
