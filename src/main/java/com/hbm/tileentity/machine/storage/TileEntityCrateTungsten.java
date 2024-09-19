package com.hbm.tileentity.machine.storage;

import com.hbm.inventory.container.ContainerCrateTungsten;
import com.hbm.inventory.gui.GUICrateTungsten;
import com.hbm.items.ModItems;

import api.hbm.block.ILaserable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCrateTungsten extends TileEntityCrateBase implements ILaserable {
	private int heatTimer;

	public TileEntityCrateTungsten() {
		super(27);
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.crateTungsten";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			if(heatTimer > 0)
				heatTimer--;
	
			if(worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != 1 && heatTimer > 0)
				worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 3);
			
			if(worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != 0 && heatTimer <= 0)
				worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 3);
		}
		
		if(worldObj.getBlockMetadata(xCoord, yCoord, zCoord) == 1) {
			worldObj.spawnParticle("flame", xCoord + worldObj.rand.nextDouble(), yCoord + 1.1, zCoord + worldObj.rand.nextDouble(), 0.0, 0.0, 0.0);
			worldObj.spawnParticle("smoke", xCoord + worldObj.rand.nextDouble(), yCoord + 1.1, zCoord + worldObj.rand.nextDouble(), 0.0, 0.0, 0.0);
			
			worldObj.spawnParticle("flame", xCoord - 0.1, yCoord + worldObj.rand.nextDouble(), zCoord + worldObj.rand.nextDouble(), 0.0, 0.0, 0.0);
			worldObj.spawnParticle("smoke", xCoord - 0.1, yCoord + worldObj.rand.nextDouble(), zCoord + worldObj.rand.nextDouble(), 0.0, 0.0, 0.0);
			
			worldObj.spawnParticle("flame", xCoord + 1.1, yCoord + worldObj.rand.nextDouble(), zCoord + worldObj.rand.nextDouble(), 0.0, 0.0, 0.0);
			worldObj.spawnParticle("smoke", xCoord + 1.1, yCoord + worldObj.rand.nextDouble(), zCoord + worldObj.rand.nextDouble(), 0.0, 0.0, 0.0);
			
			worldObj.spawnParticle("flame", xCoord + worldObj.rand.nextDouble(), yCoord + worldObj.rand.nextDouble(), zCoord - 0.1, 0.0, 0.0, 0.0);
			worldObj.spawnParticle("smoke", xCoord + worldObj.rand.nextDouble(), yCoord + worldObj.rand.nextDouble(), zCoord - 0.1, 0.0, 0.0, 0.0);
			
			worldObj.spawnParticle("flame", xCoord + worldObj.rand.nextDouble(), yCoord + worldObj.rand.nextDouble(), zCoord + 1.1, 0.0, 0.0, 0.0);
			worldObj.spawnParticle("smoke", xCoord + worldObj.rand.nextDouble(), yCoord + worldObj.rand.nextDouble(), zCoord + 1.1, 0.0, 0.0, 0.0);
		}
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		if(this.isLocked())
			return false;
		
		if(itemStack.getItem() == ModItems.billet_polonium)
			return false;
		
		if(itemStack.getItem() == ModItems.crucible && itemStack.getItemDamage() > 0)
			return false;
		
		if(FurnaceRecipes.smelting().getSmeltingResult(itemStack) == null)
			return true;
		
		return false;
	}

	@Override
	public void addEnergy(World world, int x, int y, int z, long energy, ForgeDirection dir) {
		heatTimer = 5;
		
		for(int i = 0; i < slots.length; i++) {
			
			if(slots[i] == null)
				continue;
			
			ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(slots[i]);
			
			if(slots[i].getItem() == ModItems.billet_polonium && energy > 10000000)
				result = new ItemStack(ModItems.billet_yharonite);
			
			if(slots[i].getItem() == ModItems.crucible && slots[i].getItemDamage() > 0 && energy > 10000000)
				result = new ItemStack(ModItems.crucible, 1, 0);
			
			int size = slots[i].stackSize;
			
			if(result != null && result.stackSize * size <= result.getMaxStackSize()) {
				slots[i] = result.copy();
				slots[i].stackSize *= size;
			}
		}
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerCrateTungsten(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUICrateTungsten(player.inventory, this);
	}
}
