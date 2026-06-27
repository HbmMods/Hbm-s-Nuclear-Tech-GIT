package com.hbm.blocks.network.pneumatic;

import com.hbm.main.MainRegistry;
import com.hbm.tileentity.network.pneumatic.TileEntityPneumoStorageMono;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PneumoStorageMono extends BlockContainer {

	public PneumoStorageMono() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityPneumoStorageMono();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) return true;
		if(!player.isSneaking()) {
			FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, x, y, z);
			return true;
		}
		return false;
	}

	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest) {

		if(!player.capabilities.isCreativeMode && !world.isRemote && willHarvest) {

			TileEntityPneumoStorageMono inv = (TileEntityPneumoStorageMono) world.getTileEntity(x, y, z);
			ItemStack drop = new ItemStack(this);
			NBTTagCompound nbt = new NBTTagCompound();

			if(inv != null) {
				for(int i = 0; i < 3; i++) {
					ItemStack stack = inv.getStackInSlot(i);
					if(stack == null) continue;
					NBTTagCompound slot = new NBTTagCompound();
					stack.writeToNBT(slot);
					nbt.setTag("slot" + i, slot);
					nbt.setInteger("amount" + i, inv.amounts[i]);
				}
			}

			if(!nbt.hasNoTags()) drop.stackTagCompound = nbt;
			world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, drop));
		}

		return world.setBlockToAir(x, y, z);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {

		TileEntityPneumoStorageMono inv = (TileEntityPneumoStorageMono) world.getTileEntity(x, y, z);

		if(inv != null && stack.hasTagCompound()) {
			for(int i = 0; i < 3; i++) {
				inv.setInventorySlotContents(i, ItemStack.loadItemStackFromNBT(stack.stackTagCompound.getCompoundTag("slot" + i)));
				inv.amounts[i] = stack.stackTagCompound.getInteger("amount" + i);
			}
		}

		super.onBlockPlacedBy(world, x, y, z, player, stack);
	}
}