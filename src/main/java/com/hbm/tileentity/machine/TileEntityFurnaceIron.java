package com.hbm.tileentity.machine;

import com.hbm.inventory.UpgradeManager;
import com.hbm.inventory.container.ContainerFurnaceIron;
import com.hbm.inventory.gui.GUIFurnaceIron;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.module.ModuleBurnTime;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class TileEntityFurnaceIron extends TileEntityMachineBase implements IGUIProvider {
	
	public int maxBurnTime;
	public int burnTime;

	public int progress;
	public int processingTime;
	public static final int baseTime = 200;
	
	public ModuleBurnTime burnModule;

	public TileEntityFurnaceIron() {
		super(5);
		
		burnModule = new ModuleBurnTime()
				.setLigniteMod(1.25)
				.setCoalMod(1.25)
				.setCokeMod(1.5)
				.setSolidMod(2)
				.setRocketMod(2);
	}

	@Override
	public String getName() {
		return "container.furnaceIron";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			UpgradeManager.eval(slots, 4, 4);
			this.processingTime = baseTime - (100 * Math.min(UpgradeManager.getLevel(UpgradeType.SPEED), 3) / 3);
			
			if(burnTime <= 0) {
				
				for(int i = 1; i < 3; i++) {
					if(slots[i] != null) {
						
						int fuel = burnModule.getBurnTime(slots[i]);
						
						if(fuel > 0) {
							this.maxBurnTime = this.burnTime = fuel;
							slots[i].stackSize--;

							if(slots[i].stackSize == 0) {
								slots[i] = slots[i].getItem().getContainerItem(slots[i]);
							}
							
							break;
						}
					}
				} 
			}
			
			if(canSmelt()) {
				this.progress++;
				
				if(this.progress > this.processingTime) {
					ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(slots[0]);
					
					if(slots[3] == null) {
						slots[3] = result.copy();
					} else {
						slots[3].stackSize += result.stackSize;
					}
					
					this.decrStackSize(0, 1);
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("maxBurnTime", this.maxBurnTime);
			data.setInteger("burnTime", this.burnTime);
			data.setInteger("progress", this.progress);
			data.setInteger("processingTime", this.processingTime);
			this.networkPack(data, 50);
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.maxBurnTime = nbt.getInteger("maxBurnTime");
		this.burnTime = nbt.getInteger("burnTime");
		this.progress = nbt.getInteger("progress");
		this.processingTime = nbt.getInteger("processingTime");
	}
	
	public boolean canSmelt() {
		
		if(this.burnTime <= 0) return false;
		if(slots[0] == null) return false;
		
		ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(slots[0]);
		
		if(result == null) return false;
		if(slots[3] == null) return true;
		
		if(!result.isItemEqual(slots[3])) return false;
		if(result.stackSize + slots[3].stackSize > slots[3].getMaxStackSize()) return false;
		
		return true;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerFurnaceIron(player.inventory, this);
	}

	@Override
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIFurnaceIron(player.inventory, this);
	}
}
