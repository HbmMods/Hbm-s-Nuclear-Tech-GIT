package com.hbm.blocks.generic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemLock;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityCrateIron;
import com.hbm.tileentity.machine.TileEntityCrateSteel;
import com.hbm.tileentity.machine.TileEntityCrateTungsten;
import com.hbm.tileentity.machine.TileEntityLockableBase;
import com.hbm.tileentity.machine.TileEntitySafe;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockStorageCrate extends BlockContainer {

	private final Random field_149933_a = new Random();

	@SideOnly(Side.CLIENT)
	private IIcon iconTop;

	public BlockStorageCrate(Material p_i45394_1_) {
		super(p_i45394_1_);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		if(this == ModBlocks.crate_iron) {
			this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":crate_iron_top");
			this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":crate_iron_side");
		}
		if(this == ModBlocks.crate_steel) {
			this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":crate_steel_top");
			this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":crate_steel_side");
		}
		if(this == ModBlocks.crate_tungsten) {
			this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":crate_tungsten_top");
			this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":crate_tungsten_side");
		}
		if(this == ModBlocks.safe) {
			this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":safe_front");
			this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":safe_side");
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {

		if(this == ModBlocks.safe)
			return metadata == 0 && side == 3 ? this.iconTop : (side == metadata ? this.iconTop : this.blockIcon);

		return side == 1 ? this.iconTop : (side == 0 ? this.iconTop : this.blockIcon);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		if(this == ModBlocks.crate_iron)
			return new TileEntityCrateIron();
		if(this == ModBlocks.crate_steel)
			return new TileEntityCrateSteel();
		if(this == ModBlocks.crate_tungsten)
			return new TileEntityCrateTungsten();
		if(this == ModBlocks.safe)
			return new TileEntitySafe();
		return null;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> drops = new ArrayList();
		
		ItemStack drop = new ItemStack(this);
		ISidedInventory inv = (ISidedInventory)world.getTileEntity(x, y, z);
		
		if(inv != null) {
		
			drop.stackTagCompound = new NBTTagCompound();
			
			for(int i = 0; i < inv.getSizeInventory(); i++) {
				
				ItemStack stack = inv.getStackInSlot(i);
				if(stack == null)
					continue;
				
				NBTTagCompound slot = new NBTTagCompound();
				stack.writeToNBT(slot);
				drop.stackTagCompound.setTag("slot" + i, slot);
			}
		}
		
		drops.add(drop);
		
		return drops;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
		} else if(player.getHeldItem() != null && (player.getHeldItem().getItem() instanceof ItemLock || player.getHeldItem().getItem() == ModItems.key_kit)) {
			return false;

		} else if(!player.isSneaking()) {
			TileEntity entity = world.getTileEntity(x, y, z);
			if(entity instanceof TileEntityCrateIron && ((TileEntityCrateIron) entity).canAccess(player)) {
				FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_crate_iron, world, x, y, z);
			}
			if(entity instanceof TileEntityCrateSteel && ((TileEntityCrateSteel) entity).canAccess(player)) {
				FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_crate_steel, world, x, y, z);
			}
			if(entity instanceof TileEntityCrateTungsten && ((TileEntityCrateTungsten) entity).canAccess(player)) {
				FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_crate_tungsten, world, x, y, z);
			}
			if(entity instanceof TileEntitySafe && ((TileEntitySafe) entity).canAccess(player)) {
				FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_safe, world, x, y, z);
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		
		ISidedInventory inv = (ISidedInventory)world.getTileEntity(x, y, z);
		
		if(inv != null && stack.hasTagCompound()) {
			
			for(int i = 0; i < inv.getSizeInventory(); i++) {
				inv.setInventorySlotContents(i, ItemStack.loadItemStackFromNBT(stack.stackTagCompound.getCompoundTag("slot" + i)));
			}
		}

		if(this != ModBlocks.safe)
			super.onBlockPlacedBy(world, x, y, z, player, stack);

		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if(i == 0) {
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}
		if(i == 1) {
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}
		if(i == 2) {
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}
		if(i == 3) {
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}
	}
}
