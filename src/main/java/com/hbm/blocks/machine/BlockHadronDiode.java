package com.hbm.blocks.machine;

import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityHadronDiode;
import com.hbm.tileentity.machine.TileEntityHadronDiode.DiodeConfig;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockHadronDiode extends BlockContainer {
	
	@SideOnly(Side.CLIENT)
	private IIcon iconIn;
	@SideOnly(Side.CLIENT)
	private IIcon iconOut;

	public BlockHadronDiode(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityHadronDiode();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.iconIn = iconRegister.registerIcon(RefStrings.MODID + ":hadron_diode_in");
		this.iconOut = iconRegister.registerIcon(RefStrings.MODID + ":hadron_diode_out");
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":hadron_diode");
	}

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
    	
		TileEntityHadronDiode diode = (TileEntityHadronDiode) world.getTileEntity(x, y, z);
		
		DiodeConfig conf = diode.getConfig(side);
		
		switch(conf) {
		case NONE:
			return blockIcon;
		case IN:
			return iconIn;
		case OUT:
		default:
			return iconOut;
		}
    }
	
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float fx, float fy, float fz) {
    	
    	if(player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.screwdriver) {
    		
    		if(!world.isRemote) {
    			TileEntityHadronDiode diode = (TileEntityHadronDiode) world.getTileEntity(x, y, z);
    			int config = diode.getConfig(side).ordinal();
    			config += 1;
    			config %= DiodeConfig.values().length;
    			diode.setConfig(side, config);
    		}
			
			world.markBlockForUpdate(x, y, z);

    		return true;
    	} else {
    		return false;
    	}
    }
}
