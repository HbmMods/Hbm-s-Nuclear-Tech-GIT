package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.items.machine.ItemScraps;
import com.hbm.lib.Library;
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
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
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
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB entityBounding, List list, Entity entity) {
		
		List<AxisAlignedBB> bbs = new ArrayList();
		
		bbs.add(AxisAlignedBB.getBoundingBox(x + 0.3125D, y, z + 0.3125D, x + 0.6875D, y + 0.5D, z + 0.6875D));

		if(canConnectTo(world, x, y, z, Library.POS_X)) bbs.add(AxisAlignedBB.getBoundingBox(x + 0.6875D, y, z + 0.3125D, x + 1D, y + 0.5D, z + 0.6875D));
		if(canConnectTo(world, x, y, z, Library.NEG_X)) bbs.add(AxisAlignedBB.getBoundingBox(x, y, z + 0.3125D, x + 0.3125D, y + 0.5D, z + 0.6875D));
		if(canConnectTo(world, x, y, z, Library.POS_Z)) bbs.add(AxisAlignedBB.getBoundingBox(x + 0.3125D, y, z + 0.6875D, x + 0.6875D, y + 0.5D, z + 1D));
		if(canConnectTo(world, x, y, z, Library.NEG_Z)) bbs.add(AxisAlignedBB.getBoundingBox(x + 0.3125D, y, z, x + 0.6875D, y + 0.5D, z + 0.3125D));
		
		for(AxisAlignedBB bb : bbs) {
			if(entityBounding.intersectsWith(bb)) {
				list.add(bb);
			}
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		setBlockBoundsBasedOnState(world, x, y, z);
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
		setBlockBoundsBasedOnState(world, x, y, z);
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		this.setBlockBounds(
				canConnectTo(world, x, y, z, Library.NEG_X) ? 0F : 0.3125F,
				0F,
				canConnectTo(world, x, y, z, Library.NEG_Z) ? 0F : 0.3125F,
				canConnectTo(world, x, y, z, Library.POS_X) ? 1F : 0.6875F,
				0.5F,
				canConnectTo(world, x, y, z, Library.POS_Z) ? 1F : 0.6875F);
	}

	@Override
	public boolean canAcceptPartialPour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack) {
		return ((ICrucibleAcceptor) world.getTileEntity(x, y, z)).canAcceptPartialPour(world, x, y, z, dX, dY, dZ, side, stack);
	}

	@Override
	public MaterialStack pour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack) {
		return ((ICrucibleAcceptor) world.getTileEntity(x, y, z)).pour(world, x, y, z, dX, dY, dZ, side, stack);
	}

	@Override
	public boolean canAcceptPartialFlow(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack) {
		return ((ICrucibleAcceptor) world.getTileEntity(x, y, z)).canAcceptPartialFlow(world, x, y, z, side, stack);
	}
	
	@Override
	public MaterialStack flow(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack) {
		return ((ICrucibleAcceptor) world.getTileEntity(x, y, z)).flow(world, x, y, z, side, stack);
	}
	
	public boolean canConnectTo(IBlockAccess world, int x, int y, int z, ForgeDirection dir) {
		
		if(dir == ForgeDirection.UP || dir == ForgeDirection.DOWN || dir == ForgeDirection.UNKNOWN)
			return false;
		
		Block b = world.getBlock(x + dir.offsetX, y, z + dir.offsetZ);
		int meta = world.getBlockMetadata(x + dir.offsetX, y, z + dir.offsetZ);
		
		if((b == ModBlocks.foundry_outlet || b == ModBlocks.foundry_slagtap) && meta == dir.ordinal())
			return true;
		
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
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
		}
		
		TileEntityFoundryChannel cast = (TileEntityFoundryChannel) world.getTileEntity(x, y, z);
		
		if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemTool && ((ItemTool) player.getHeldItem().getItem()).getToolClasses(player.getHeldItem()).contains("shovel")) {
			if(cast.amount > 0) {
				ItemStack scrap = ItemScraps.create(new MaterialStack(cast.type, cast.amount));
				if(!player.inventory.addItemStackToInventory(scrap)) {
					EntityItem item = new EntityItem(world, x + 0.5, y + this.maxY, z + 0.5, scrap);
					world.spawnEntityInWorld(item);
				} else {
					player.inventoryContainer.detectAndSendChanges();
				}
				cast.amount = 0;
				cast.type = null;
				cast.propagateMaterial(null);
				cast.markDirty();
				world.markBlockForUpdate(x, y, z);
			}
			return true;
		}
		
		return false;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block b, int i) {
		
		TileEntityFoundryChannel channel = (TileEntityFoundryChannel) world.getTileEntity(x, y, z);
		if(channel.amount > 0) {
			ItemStack scrap = ItemScraps.create(new MaterialStack(channel.type, channel.amount));
			EntityItem item = new EntityItem(world, x + 0.5, y + this.maxY, z + 0.5, scrap);
			world.spawnEntityInWorld(item);
			channel.amount = 0;
		}
		
		super.breakBlock(world, x, y, z, b, i);
	}
}
