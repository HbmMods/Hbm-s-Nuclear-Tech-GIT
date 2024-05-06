package com.hbm.blocks.generic;

import com.hbm.blocks.ILookOverlay;
import com.hbm.interfaces.Untested;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class Guide extends Block implements ILookOverlay {
	
	@SideOnly(Side.CLIENT)
	private IIcon iconTop;
	private IIcon iconFront;
	private IIcon iconBack;
	private IIcon iconLeft;
	private IIcon iconRight;

	public Guide(Material p_i45394_1_) {
		super(p_i45394_1_);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":guide_bottom");
		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":guide_top");
		this.iconFront = iconRegister.registerIcon(RefStrings.MODID + ":guide_front");
		this.iconBack = iconRegister.registerIcon(RefStrings.MODID + ":guide_back");
		this.iconLeft = iconRegister.registerIcon(RefStrings.MODID + ":guide_side_left");
		this.iconRight = iconRegister.registerIcon(RefStrings.MODID + ":guide_side_right");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		if(metadata == 5)
		{
			if(side == 0) return blockIcon;
			if(side == 1) return iconTop;
			if(side == 2) return iconFront;
			if(side == 3) return iconBack;
			if(side == 4) return iconRight;
			if(side == 5) return iconLeft;
		}
		if(metadata == 3)
		{
			if(side == 0) return blockIcon;
			if(side == 1) return iconTop;
			if(side == 2) return iconRight;
			if(side == 3) return iconLeft;
			if(side == 4) return iconBack;
			if(side == 5) return iconFront;
		}
		if(metadata == 4)
		{
			if(side == 0) return blockIcon;
			if(side == 1) return iconTop;
			if(side == 2) return iconBack;
			if(side == 3) return iconFront;
			if(side == 4) return iconLeft;
			if(side == 5) return iconRight;
		}
		if(metadata == 2)
		{
			if(side == 0) return blockIcon;
			if(side == 1) return iconTop;
			if(side == 2) return iconLeft;
			if(side == 3) return iconRight;
			if(side == 4) return iconFront;
			if(side == 5) return iconBack;
		}

		if(side == 0) return blockIcon;
		if(side == 1) return iconTop;
		if(side == 2) return iconRight;
		if(side == 3) return iconLeft;
		if(side == 4) return iconBack;
		if(side == 5) return iconFront;
		
		return null;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		
		if(i == 0)
		{
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}
		if(i == 1)
		{
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}
		if(i == 2)
		{
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}
		if(i == 3)
		{
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(world.isRemote && !player.isSneaking()) {
			MainRegistry.proxy.openLink("https://nucleartech.wiki/wiki/Main_Page");
			return true;
		}
		
		return super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
	}

	@Override
	@Untested
	public void printHook(Pre event, World world, int x, int y, int z) {

		Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution resolution = event.resolution;

		int pX = resolution.getScaledWidth() / 2 + 8;
		int pZ = resolution.getScaledHeight() / 2;

		String title = "Click to open wiki";
		mc.fontRenderer.drawString(title, pX + 1, pZ - 19, 0x006000);
		mc.fontRenderer.drawString(title, pX, pZ - 20, 0x00FF00);
	}

}
