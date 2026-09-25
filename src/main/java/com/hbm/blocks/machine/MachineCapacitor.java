package com.hbm.blocks.machine;

import com.hbm.lib.RefStrings;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class MachineCapacitor extends Block {

	@SideOnly(Side.CLIENT) public IIcon iconTop;
	@SideOnly(Side.CLIENT) public IIcon iconSide;
	@SideOnly(Side.CLIENT) public IIcon iconBottom;
	@SideOnly(Side.CLIENT) public IIcon iconInnerTop;
	@SideOnly(Side.CLIENT) public IIcon iconInnerSide;

	String name;

	public MachineCapacitor(Material mat, long power, String name) {
		super(mat);
		this.name = name;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":capacitor_" + name + "_top");
		this.iconSide = iconRegister.registerIcon(RefStrings.MODID + ":capacitor_" + name + "_side");
		this.iconBottom = iconRegister.registerIcon(RefStrings.MODID + ":capacitor_" + name + "_bottom");
		this.iconInnerTop = iconRegister.registerIcon(RefStrings.MODID + ":capacitor_" + name + "_inner_top");
		this.iconInnerSide = iconRegister.registerIcon(RefStrings.MODID + ":capacitor_" + name + "_inner_side");
	}

	public static int renderID = RenderingRegistry.getNextAvailableRenderId();

	@Override public int getRenderType() { return renderID; }
	@Override public boolean isOpaqueCube() { return false; }
	@Override public boolean renderAsNormalBlock() { return false; }

	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float fX, float fY, float fZ, int meta) {
		return side;
	}
}
