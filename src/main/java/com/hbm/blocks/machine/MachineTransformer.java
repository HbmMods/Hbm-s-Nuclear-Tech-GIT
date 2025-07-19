package com.hbm.blocks.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class MachineTransformer extends Block {

	@SideOnly(Side.CLIENT)
	private IIcon iconTop;

	public MachineTransformer(Material p_i45394_1_, long b, int d) {
		super(p_i45394_1_);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {

		if(this == ModBlocks.machine_transformer) {
			this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":machine_transformer_top_iron");
			this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":machine_transformer_iron");
		}
		if(this == ModBlocks.machine_transformer_dnt) {
			this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":machine_transformer_top");
			this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":machine_transformer");
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == 1 ? this.iconTop : (side == 0 ? this.iconTop : this.blockIcon);
	}
}
