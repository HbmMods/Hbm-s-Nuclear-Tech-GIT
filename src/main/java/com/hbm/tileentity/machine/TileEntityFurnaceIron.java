package com.hbm.tileentity.machine;

import com.hbm.inventory.container.ContainerFurnaceIron;
import com.hbm.inventory.gui.GUIFurnaceIron;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;

public class TileEntityFurnaceIron extends TileEntityMachineBase implements IGUIProvider {
	
	public int maxBurnTime;
	public int burnTime;

	public TileEntityFurnaceIron() {
		super(5);
	}

	@Override
	public String getName() {
		return "container.furnaceIron";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(burnTime <= 0) {
				
				for(int i = 1; i < 3; i++) {
					if(slots[i] != null) {
						
						int fuel = TileEntityFurnace.getItemBurnTime(slots[i]);
						
						if(fuel > 0) {
							this.maxBurnTime = this.burnTime = fuel;
							break;
						}
					}
				} 
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("maxBurnTime", this.maxBurnTime);
			data.setInteger("burnTime", this.burnTime);
			this.networkPack(data, 50);
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.maxBurnTime = nbt.getInteger("maxBurnTime");
		this.burnTime = nbt.getInteger("burnTime");
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
