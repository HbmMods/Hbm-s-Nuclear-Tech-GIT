package com.hbm.inventory;

import java.util.Arrays;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.inventory.gui.GuiFluidContainer;
import com.hbm.items.tool.ItemFluidIdentifier;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEFluidPacket;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class FluidTank {
	
	FluidType type;
	int fluid;
	int maxFluid;
	public int index;
	public static ResourceLocation fluidTextures = new ResourceLocation(RefStrings.MODID + ":textures/gui/fluids2.png");
	public static int x = 16;
	public static int y = 100;
	
	public FluidTank(FluidType type, int maxFluid, int index) {
		this.type = type;
		this.maxFluid = maxFluid;
		this.index = index;
	}
	
	public void setFill(int i) {
		fluid = i;
	}
	
	public void setTankType(FluidType type) {
		
		if(this.type.name().equals(type.name()))
			return;
		
		this.type = type;
		this.setFill(0);
	}
	
	public FluidType getTankType() {
		
		return type;
	}
	
	public int getFill() {
		return fluid;
	}
	
	public int getMaxFill() {
		return maxFluid;
	}
	
	//Called on TE update
	public void updateTank(int x, int y, int z) {

		PacketDispatcher.wrapper.sendToAll(new TEFluidPacket(x, y, z, fluid, index, type));
	}
	
	//Fills tank from canisters
	public void loadTank(int in, int out, ItemStack[] slots) {
		
		FluidType inType = FluidType.NONE;
		if(slots[in] != null) {
			inType = FluidContainerRegistry.getFluidType(slots[in]);
			
			if(FluidContainerRegistry.getFluidContent(slots[in], type) <= 0)
				return;
		} else {
			return;
		}
		
		if(slots[in] != null && inType.name().equals(type.name()) && fluid + FluidContainerRegistry.getFluidContent(slots[in], type) <= maxFluid) {
			if(slots[out] == null) {
				fluid += FluidContainerRegistry.getFluidContent(slots[in], type);
				slots[out] = FluidContainerRegistry.getEmptyContainer(slots[in]);
				slots[in].stackSize--;
				if(slots[in].stackSize <= 0)
					slots[in] = null;
			} else if(slots[out] != null && slots[out].getItem() == FluidContainerRegistry.getEmptyContainer(slots[in]).getItem() && slots[out].stackSize < slots[out].getMaxStackSize()) {
				fluid += FluidContainerRegistry.getFluidContent(slots[in], type);
				slots[in].stackSize--;
				if(slots[in].stackSize <= 0)
					slots[in] = null;
				slots[out].stackSize++;
			}
		}
	}
	
	//Fills canisters from tank
	public void unloadTank(int in, int out, ItemStack[] slots) {

		ItemStack full = null;
		if(slots[in] != null) {
			full = FluidContainerRegistry.getFullContainer(slots[in], type);
		}
		if(full == null)
			return;
		
		if(slots[in] != null && fluid - FluidContainerRegistry.getFluidContent(full, type) >= 0) {
			if(slots[out] == null) {
				fluid -= FluidContainerRegistry.getFluidContent(full, type);
				slots[out] = full.copy();
				slots[in].stackSize--;
				if(slots[in].stackSize <= 0)
					slots[in] = null;
			} else if(slots[out] != null && slots[out].getItem() == FluidContainerRegistry.getFullContainer(slots[in], type).getItem() && slots[out].stackSize < slots[out].getMaxStackSize()) {
				fluid -= FluidContainerRegistry.getFluidContent(full, type);
				slots[in].stackSize--;
				if(slots[in].stackSize <= 0)
					slots[in] = null;
				slots[out].stackSize++;
			}
		}
	}
	
	//Changes tank type
	public void setType(int in, int out, ItemStack[] slots) {
		
		if(slots[in] != null && slots[out] == null && slots[in].getItem() instanceof ItemFluidIdentifier) {
			FluidType newType = ItemFluidIdentifier.getType(slots[in].copy());
			if(!type.name().equals(newType.name())) {
				type = newType;
				slots[out] = slots[in].copy();
				slots[in] = null;
				fluid = 0;
			}
		}
	}
	
	//Used in the GUI rendering, renders correct fluid type in container with progress
	public void renderTank(GuiContainer gui, int x, int y, int tx, int ty, int width, int height) {
		
		int i = (fluid * height) / maxFluid;
		gui.drawTexturedModalRect(x, y - i, tx, ty - i, width, i);
	}

	public void renderTankInfo(GuiContainer gui, int mouseX, int mouseY, int x, int y, int width, int height) {
		if(gui instanceof GuiFluidContainer)
			renderTankInfo((GuiFluidContainer)gui, mouseX, mouseY, x, y, width, height);
	}
	
	public void renderTankInfo(GuiFluidContainer gui, int mouseX, int mouseY, int x, int y, int width, int height) {
		if(x <= mouseX && x + width > mouseX && y < mouseY && y + height >= mouseY)
			gui.drawFluidInfo(new String[] { I18n.format(this.type.getUnlocalizedName()), fluid + "/" + maxFluid + "mB" }, mouseX, mouseY);
	}

	//Called by TE to save fillstate
	public void writeToNBT(NBTTagCompound nbt, String s) {
		nbt.setInteger(s, fluid);
		nbt.setInteger(s + "_type", Arrays.asList(FluidType.values()).indexOf(type));
	}
	
	//Called by TE to load fillstate
	public void readFromNBT(NBTTagCompound nbt, String s) {
		fluid = nbt.getInteger(s);
		type = FluidType.getEnum(nbt.getInteger(s + "_type"));
	}

}
