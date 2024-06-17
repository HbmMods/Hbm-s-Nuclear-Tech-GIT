package com.hbm.blocks.network;

import com.hbm.lib.RefStrings;
import com.hbm.tileentity.network.TileEntityCraneBase;
import com.hbm.tileentity.network.TileEntityCraneGrabber;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.world.World;

public class CraneGrabber extends BlockCraneBase {

	public CraneGrabber() {
		super(Material.iron);
	}

	@Override
	public TileEntityCraneBase createNewTileEntity(World world, int meta) {
		return new TileEntityCraneGrabber();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		this.iconIn = iconRegister.registerIcon(RefStrings.MODID + ":crane_pull");
		this.iconSideIn = iconRegister.registerIcon(RefStrings.MODID + ":crane_side_pull");
		this.iconDirectional = iconRegister.registerIcon(RefStrings.MODID + ":crane_grabber_top");
		this.iconDirectionalUp = iconRegister.registerIcon(RefStrings.MODID + ":crane_grabber_side_up");
		this.iconDirectionalDown = iconRegister.registerIcon(RefStrings.MODID + ":crane_grabber_side_down");
		this.iconDirectionalTurnLeft = iconRegister.registerIcon(RefStrings.MODID + ":crane_grabber_top_left");
		this.iconDirectionalTurnRight = iconRegister.registerIcon(RefStrings.MODID + ":crane_grabber_top_right");
		this.iconDirectionalSideLeftTurnUp = iconRegister.registerIcon(RefStrings.MODID + ":crane_grabber_side_left_turn_up");
		this.iconDirectionalSideRightTurnUp = iconRegister.registerIcon(RefStrings.MODID + ":crane_grabber_side_right_turn_up");
		this.iconDirectionalSideLeftTurnDown = iconRegister.registerIcon(RefStrings.MODID + ":crane_grabber_side_left_turn_down");
		this.iconDirectionalSideRightTurnDown = iconRegister.registerIcon(RefStrings.MODID + ":crane_grabber_side_right_turn_down");
		this.iconDirectionalSideUpTurnLeft = iconRegister.registerIcon(RefStrings.MODID + ":crane_grabber_side_up_turn_left");
		this.iconDirectionalSideUpTurnRight = iconRegister.registerIcon(RefStrings.MODID + ":crane_grabber_side_up_turn_right");
		this.iconDirectionalSideDownTurnLeft = iconRegister.registerIcon(RefStrings.MODID + ":crane_grabber_side_down_turn_left");
		this.iconDirectionalSideDownTurnRight = iconRegister.registerIcon(RefStrings.MODID + ":crane_grabber_side_down_turn_right");
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		this.dropContents(world, x, y, z, block, meta, 9, 11);
		super.breakBlock(world, x, y, z, block, meta);
	}
}
