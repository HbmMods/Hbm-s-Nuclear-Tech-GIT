package com.hbm.blocks.machine;

import java.util.ArrayList;

import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityCustomMachine;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class BlockCustomMachine extends BlockContainer {
	
	@SideOnly(Side.CLIENT)
	private IIcon iconFront;

	public BlockCustomMachine() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityCustomMachine();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.iconFront = iconRegister.registerIcon(RefStrings.MODID + ":cm_terminal_front");
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":cm_terminal_side");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return metadata == 0 && side == 3 ? this.iconFront : (side == metadata ? this.iconFront : this.blockIcon);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			
			TileEntityCustomMachine tile = (TileEntityCustomMachine) world.getTileEntity(x, y, z);
			
			if(tile != null) {
				
				if(tile.checkStructure()) {
					FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, x, y, z);
				} else if(player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.wand_s) {
					tile.buildStructure();
				}
			}
			return true;
		}
		
		return false;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if(i == 0) world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		if(i == 1) world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		if(i == 2) world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		if(i == 3) world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		
		TileEntityCustomMachine tile = (TileEntityCustomMachine) world.getTileEntity(x, y, z);
		
		if(tile != null && stack.hasTagCompound()) {
			tile.machineType = stack.stackTagCompound.getString("machineType");
			tile.init();
			tile.markChanged();
		}
	}

	@Override
	public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player) {
		
		if(!player.capabilities.isCreativeMode) {
			harvesters.set(player);
			this.dropBlockAsItem(world, x, y, z, meta, 0);
			harvesters.set(null);
		}
	}
	
	@Override
	public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta) {
		player.addStat(StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
		player.addExhaustion(0.025F);
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		Item item = getItemDropped(metadata, world.rand, fortune);
		if(item != null) {

			ItemStack stack = new ItemStack(item);
			TileEntityCustomMachine tile = (TileEntityCustomMachine) world.getTileEntity(x, y, z);
			
			if(tile != null) {
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setString("machineType", tile.machineType);
			}
			
			ret.add(stack);
		}
		
		return ret;
	}
	
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) { //using the deprecated one to make NEI happy
		
		TileEntityCustomMachine tile = (TileEntityCustomMachine) world.getTileEntity(x, y, z);

		ItemStack stack = new ItemStack(this);
		
		if(tile != null && tile.machineType != null && !tile.machineType.isEmpty()) {
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setString("machineType", tile.machineType);
			return stack;
		}
		
		return super.getPickBlock(target, world, x, y, z);
	}
}
