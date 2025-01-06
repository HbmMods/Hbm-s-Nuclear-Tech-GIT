package com.hbm.blocks.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineTransformer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class MachineTransformer extends BlockContainer {

	long buffer;
	int delay;

	@SideOnly(Side.CLIENT)
	private IIcon iconTop;

	public MachineTransformer(Material p_i45394_1_, long b, int d) {
		super(p_i45394_1_);
		buffer = b;
		delay = d;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {

		if(this == ModBlocks.machine_transformer || this == ModBlocks.machine_transformer_20) {
			this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":machine_transformer_top_iron");
			this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":machine_transformer_iron");
		}
		if(this == ModBlocks.machine_transformer_dnt || this == ModBlocks.machine_transformer_dnt_20) {
			this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":machine_transformer_top");
			this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":machine_transformer");
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == 1 ? this.iconTop : (side == 0 ? this.iconTop : this.blockIcon);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityMachineTransformer();
	}
}
