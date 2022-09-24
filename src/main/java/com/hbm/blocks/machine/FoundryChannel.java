package com.hbm.blocks.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityFoundryChannel;

import api.hbm.block.ICrucibleAcceptor;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class FoundryChannel extends BlockContainer implements ICrucibleAcceptor {

	@SideOnly(Side.CLIENT) public IIcon iconTop;
	@SideOnly(Side.CLIENT) public IIcon iconSide;
	@SideOnly(Side.CLIENT) public IIcon iconBottom;
	@SideOnly(Side.CLIENT) public IIcon iconInner;
	@SideOnly(Side.CLIENT) public IIcon iconLava;

	public FoundryChannel() {
		super(Material.rock);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":foundry_channel_top");
		this.iconSide = iconRegister.registerIcon(RefStrings.MODID + ":foundry_channel_side");
		this.iconBottom = iconRegister.registerIcon(RefStrings.MODID + ":foundry_channel_bottom");
		this.iconInner = iconRegister.registerIcon(RefStrings.MODID + ":foundry_channel_inner");
		this.iconLava = iconRegister.registerIcon(RefStrings.MODID + ":lava_gray");
	}

	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityFoundryChannel();
	}

	@Override
	public boolean canAcceptPartialPour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack) {
		return ((TileEntityFoundryChannel) world.getTileEntity(x, y, z)).canAcceptPartialPour(world, x, y, z, dX, dY, dZ, side, stack);
	}

	@Override
	public MaterialStack pour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack) {
		return ((TileEntityFoundryChannel) world.getTileEntity(x, y, z)).pour(world, x, y, z, dX, dY, dZ, side, stack);
	}

	@Override
	public boolean canAcceptPartialFlow(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack) {
		return ((TileEntityFoundryChannel) world.getTileEntity(x, y, z)).canAcceptPartialFlow(world, x, y, z, side, stack);
	}
	
	@Override
	public MaterialStack flow(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack) {
		return ((TileEntityFoundryChannel) world.getTileEntity(x, y, z)).flow(world, x, y, z, side, stack);
	}
	
	public boolean canConnectTo(IBlockAccess world, int x, int y, int z, ForgeDirection dir) {
		
		if(dir == ForgeDirection.UP || dir == ForgeDirection.DOWN || dir == ForgeDirection.UNKNOWN)
			return false;
		
		Block b = world.getBlock(x + dir.offsetX, y, z + dir.offsetZ);
		
		return b == ModBlocks.foundry_channel || b == ModBlocks.foundry_mold;
	}

	public static int renderID = RenderingRegistry.getNextAvailableRenderId();

	@Override
	public int getRenderType() {
		return renderID;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
}
