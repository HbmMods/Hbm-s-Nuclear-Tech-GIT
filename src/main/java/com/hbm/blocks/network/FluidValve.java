package com.hbm.blocks.network;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ILookOverlay;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.network.TileEntityFluidValve;
import com.hbm.util.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class FluidValve extends FluidDuctBase implements ILookOverlay {

	@SideOnly(Side.CLIENT)
	private IIcon iconOn;

	public FluidValve(Material mat) {
		super(mat);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.iconOn = iconRegister.registerIcon(RefStrings.MODID + ":fluid_valve_on");
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":fluid_valve_off");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return metadata == 1 ? iconOn : blockIcon;
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityFluidValve();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(world.isRemote) return true;
		
		if(super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ)) return true;
		
		if(!player.isSneaking()) {
			int meta = world.getBlockMetadata(x, y, z);
			TileEntityFluidValve te = (TileEntityFluidValve) world.getTileEntity(x, y, z);
			if(meta == 0) {
				world.setBlockMetadataWithNotify(x, y, z, 1, 2);
				world.playSoundEffect(x, y, z, "hbm:block.reactorStart", 1.0F, 1.0F);
			} else {
				world.setBlockMetadataWithNotify(x, y, z, 0, 2);
				world.playSoundEffect(x, y, z, "hbm:block.reactorStart", 1.0F, 0.85F);
			}
			
			te.updateState();
			
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		
		TileEntity te = world.getTileEntity(x, y, z);
		
		if(!(te instanceof TileEntityFluidValve))
			return;
		
		TileEntityFluidValve duct = (TileEntityFluidValve) te;
		
		List<String> text = new ArrayList();
		text.add("&[" + duct.getType().getColor() + "&]" + duct.getType().getLocalizedName());
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}
}
