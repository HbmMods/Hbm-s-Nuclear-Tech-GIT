package com.hbm.blocks.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityDeuteriumExtractor;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class MachineDeuteriumExtractor extends BlockContainer {

	public MachineDeuteriumExtractor(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		meta = 0;
		return new TileEntityDeuteriumExtractor();
	}
	
	@SideOnly(Side.CLIENT)
    private IIcon iconTopH;
	private IIcon iconTopH2O;
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":deuterium_extractor_side");
		this.iconTopH = iconRegister.registerIcon(RefStrings.MODID + ":deuterium_extractor_top_hydrogen");
		this.iconTopH2O = iconRegister.registerIcon(RefStrings.MODID + ":deuterium_extractor_top_water");
	}
		
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		if(side == 0 || side == 1) {
			return metadata == 0 ? iconTopH : iconTopH2O;
		} else {
			return blockIcon;
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking())
		{
			TileEntityDeuteriumExtractor entity = (TileEntityDeuteriumExtractor) world.getTileEntity(x, y, z);
			if(entity != null)
			{
				switch(entity.tanks[0].getTankType()) {
				case HYDROGEN: 
					entity.tanks[0].setTankType(FluidType.WATER); 
					entity.tanks[1].setTankType(FluidType.HEAVYWATER); 
					world.setBlockMetadataWithNotify(x, y, z, 1, 2);
					break;
				case WATER: 
					entity.tanks[0].setTankType(FluidType.HYDROGEN); 
					entity.tanks[1].setTankType(FluidType.DEUTERIUM); 
					world.setBlockMetadataWithNotify(x, y, z, 0, 2);
					break;
				}
			}
			return true;
		} else {
			return false;
		}
	}
}