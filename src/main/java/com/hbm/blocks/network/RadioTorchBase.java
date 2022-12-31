package com.hbm.blocks.network;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.inventory.gui.GUIScreenRadioTorch;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.network.TileEntityRadioTorchBase;
import com.hbm.util.I18nUtil;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class RadioTorchBase extends BlockContainer implements IGUIProvider, ILookOverlay, ITooltipProvider {

	@SideOnly(Side.CLIENT) protected IIcon iconOn;

	public RadioTorchBase() {
		super(Material.circuits);
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
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		return true;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return null;
	}
	
	@Override
	public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 vec0, Vec3 vec1) {
		
		int meta = world.getBlockMetadata(x, y, z) & 7;
		ForgeDirection dir = ForgeDirection.getOrientation(meta);
		
		this.setBlockBounds(
				dir.offsetX == 1 ? 0F : 0.375F,
				dir.offsetY == 1 ? 0F : 0.375F,
				dir.offsetZ == 1 ? 0F : 0.375F,
				dir.offsetX == -1 ? 1F : 0.625F,
				dir.offsetY == -1 ? 1F : 0.625F,
				dir.offsetZ == -1 ? 1F : 0.625F
				);

		return super.collisionRayTrace(world, x, y, z, vec0, vec1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == 0 ? this.blockIcon : this.iconOn;
	}

	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float fX, float fY, float fZ, int meta) {
		return side;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		
		int meta = world.getBlockMetadata(x, y, z);
		ForgeDirection dir = ForgeDirection.getOrientation(meta);
		Block b = world.getBlock(x - dir.offsetX, y - dir.offsetY, z - dir.offsetZ);
		
		if(!b.isSideSolid(world, x - dir.offsetX, y - dir.offsetY, z - dir.offsetZ, dir) && !b.hasComparatorInputOverride() && (!b.renderAsNormalBlock() || b.isAir(world, x, y, z))) {
			this.dropBlockAsItem(world, x, y, z, meta, 0);
			world.setBlockToAir(x, y, z);
		}
	}
	
	@Override
	public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side) {
		if(!super.canPlaceBlockOnSide(world, x, y, z, side)) return false;
		
		ForgeDirection dir = ForgeDirection.getOrientation(side);
		Block b = world.getBlock(x - dir.offsetX, y - dir.offsetY, z - dir.offsetZ);
		
		return b.isSideSolid(world, x - dir.offsetX, y - dir.offsetY, z - dir.offsetZ, dir) || b.hasComparatorInputOverride() || (b.renderAsNormalBlock() && !b.isAir(world, x, y, z));
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote && !player.isSneaking()) {
			FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, x, y, z);
			return true;
		} else {
			return !player.isSneaking();
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void printHook(Pre event, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		
		if(te instanceof TileEntityRadioTorchBase) {
			TileEntityRadioTorchBase radio = (TileEntityRadioTorchBase) te;
			List<String> text = new ArrayList();
			if(radio.channel != null && !radio.channel.isEmpty()) text.add(EnumChatFormatting.AQUA + "Freq: " + radio.channel);
			text.add(EnumChatFormatting.RED + "Signal: " + radio.lastState);
			ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
		}
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		addStandardInfo(stack, player, list, ext);
	}

	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return null; }

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		
		if(te instanceof TileEntityRadioTorchBase)
			return new GUIScreenRadioTorch((TileEntityRadioTorchBase) te);
		
		return null;
	}
}
