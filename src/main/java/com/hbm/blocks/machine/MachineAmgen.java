package com.hbm.blocks.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineAmgen;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class MachineAmgen extends BlockContainer {
	
	@SideOnly(Side.CLIENT)
	private IIcon iconTop;

	public MachineAmgen(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityMachineAmgen();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {

		if(this == ModBlocks.machine_amgen) {
			this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":machine_amgen_top");
			this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":machine_amgen_side");
		}
		if(this == ModBlocks.machine_geo) {
			this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":machine_geo_top");
			this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":machine_geo_side");
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == 1 ? this.iconTop : (side == 0 ? this.iconTop : this.blockIcon);
	}

}
