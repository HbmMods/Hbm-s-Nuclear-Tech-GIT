package com.hbm.blocks.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityCondenser;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class MachineCondenser extends BlockContainer {

	public MachineCondenser(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityCondenser();
	}
	
	@SideOnly(Side.CLIENT)
    private IIcon iconHeavy;
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":condenser_light");
		this.iconHeavy = iconRegister.registerIcon(RefStrings.MODID + ":condenser_heavy");
	}
		
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return metadata == 0 ? blockIcon : iconHeavy;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking())
		{
			TileEntityCondenser entity = (TileEntityCondenser) world.getTileEntity(x, y, z);
			if(entity != null)
			{
				switch(entity.tanks[0].getTankType()) {
				case SPENTSTEAM: 
					entity.tanks[0].setTankType(FluidType.SPENTHEAVYSTEAM); 
					entity.tanks[1].setTankType(FluidType.HEAVYWATER); 
					world.setBlockMetadataWithNotify(x, y, z, 1, 2);
					break;
				case SPENTHEAVYSTEAM: 
					entity.tanks[0].setTankType(FluidType.SPENTSTEAM); 
					entity.tanks[1].setTankType(FluidType.WATER); 
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
