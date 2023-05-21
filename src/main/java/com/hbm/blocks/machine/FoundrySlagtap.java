package com.hbm.blocks.machine;

import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityFoundrySlagtap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class FoundrySlagtap extends FoundryOutlet {
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":foundry_slagtap_top");
		this.iconSide = iconRegister.registerIcon(RefStrings.MODID + ":foundry_slagtap_side");
		this.iconBottom = iconRegister.registerIcon(RefStrings.MODID + ":foundry_slagtap_bottom");
		this.iconInner = iconRegister.registerIcon(RefStrings.MODID + ":foundry_slagtap_inner");
		this.iconFront = iconRegister.registerIcon(RefStrings.MODID + ":foundry_slagtap_front");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityFoundrySlagtap();
	}
	
	@Override public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) { return false; }
	@Override public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) { return false; }
	@Override public void printHook(Pre event, World world, int x, int y, int z) { }
}
