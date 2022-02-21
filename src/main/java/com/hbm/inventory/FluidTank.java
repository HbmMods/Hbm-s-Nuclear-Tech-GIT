package com.hbm.inventory;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.handler.ArmorModHandler;
import com.hbm.interfaces.IPartiallyFillable;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.FluidType.FluidTrait;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.gui.GuiInfoContainer;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFluidIdentifier;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEFluidPacket;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class FluidTank {
	
	FluidType type;
	int fluid;
	int maxFluid;
	public int index;
	
	public FluidTank(FluidType type, int maxFluid, int index) {
		this.type = type;
		this.maxFluid = maxFluid;
		this.index = index;
	}
	
	public void setFill(int i) {
		fluid = i;
	}
	
	public void setTankType(FluidType type) {
		
		if(this.type == type)
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
	
	public int changeTankSize(int size) {
		maxFluid = size;
		
		if(fluid > maxFluid) {
			int dif = fluid - maxFluid;
			fluid = maxFluid;
			return dif;
		}
			
		return 0;
	}
	
	//Called on TE update
	public void updateTank(TileEntity te) {
		updateTank(te.xCoord, te.yCoord, te.zCoord, te.getWorldObj().provider.dimensionId);
	}
	
	public void updateTank(int x, int y, int z, int dim) {
		PacketDispatcher.wrapper.sendToAllAround(new TEFluidPacket(x, y, z, fluid, index, type), new TargetPoint(dim, x, y, z, 100));
	}
	
	//Fills tank from canisters
	public boolean loadTank(int in, int out, ItemStack[] slots) {
		
		FluidType inType = Fluids.NONE;
		if(slots[in] != null) {
			
			//TODO: add IPartiallyFillable case for unloading, useful for infinite tanks so they don't need to be hardcoded
			
			inType = FluidContainerRegistry.getFluidType(slots[in]);
			
			if(slots[in].getItem() == ModItems.fluid_barrel_infinite && type != Fluids.NONE) {
				this.fluid = this.maxFluid;
				return true;
			}
			
			if(slots[in].getItem() == ModItems.inf_water && this.type == Fluids.WATER) {
				this.fluid += 50;
				if(this.fluid > this.maxFluid)
					this.fluid = this.maxFluid;
				return true;
			}
			
			if(slots[in].getItem() == ModItems.inf_water_mk2 && this.type == Fluids.WATER) {
				this.fluid += 500;
				if(this.fluid > this.maxFluid)
					this.fluid = this.maxFluid;
				return true;
			}
			
			if(FluidContainerRegistry.getFluidContent(slots[in], type) <= 0)
				return false;
		} else {
			return false;
		}
		
		if(slots[in] != null && inType.getName().equals(type.getName()) && fluid + FluidContainerRegistry.getFluidContent(slots[in], type) <= maxFluid) {
			if(slots[out] == null) {
				fluid += FluidContainerRegistry.getFluidContent(slots[in], type);
				slots[out] = FluidContainerRegistry.getEmptyContainer(slots[in]);
				slots[in].stackSize--;
				if(slots[in].stackSize <= 0)
					slots[in] = null;
			} else if(slots[out] != null && (FluidContainerRegistry.getEmptyContainer(slots[in]) == null || slots[out].getItem() == FluidContainerRegistry.getEmptyContainer(slots[in]).getItem()) && slots[out].stackSize < slots[out].getMaxStackSize()) {
				fluid += FluidContainerRegistry.getFluidContent(slots[in], type);
				
				if(FluidContainerRegistry.getEmptyContainer(slots[in]) != null)
					slots[out].stackSize++;
				
				slots[in].stackSize--;
				if(slots[in].stackSize <= 0)
					slots[in] = null;
			}
			
			return true;
		}
		
		return false;
	}
	
	//Fills canisters from tank
	public void unloadTank(int in, int out, ItemStack[] slots) {

		ItemStack full = null;
		if(slots[in] != null) {
			
			ItemStack partial = slots[in];
			
			if(partial.getItem() instanceof ItemArmor && ArmorModHandler.hasMods(partial)) {
				
				partial = ArmorModHandler.pryMods(partial)[ArmorModHandler.plate_only];
				
				if(partial == null)
					return;
			}
			
			if(partial.getItem() instanceof IPartiallyFillable) {
				IPartiallyFillable fillable = (IPartiallyFillable)partial.getItem();
				int speed = fillable.getLoadSpeed(partial);
				
				if(fillable.getType(partial) == this.type && speed > 0) {
					
					int toLoad = Math.min(this.fluid, speed);
					int fill = fillable.getFill(partial);
					toLoad = Math.min(toLoad, fillable.getMaxFill(partial) - fill);
					
					if(toLoad > 0) {
						this.fluid -= toLoad;
						fillable.setFill(partial, fill + toLoad);
					}
				}
				
				if(slots[in].getItem() instanceof ItemArmor) {
					ArmorModHandler.applyMod(slots[in], partial);
				}
				
				return;
			}
			
			if(slots[in].getItem() == ModItems.fluid_barrel_infinite) {
				this.fluid = 0;
				return;
			}
			
			if(slots[in].getItem() == ModItems.inf_water && type == Fluids.WATER) {
				this.fluid -= 50;
				if(this.fluid < 0)
					this.fluid = 0;
				return;
			}
			
			if(slots[in].getItem() == ModItems.inf_water_mk2 && type == Fluids.WATER) {
				this.fluid -= 500;
				if(this.fluid < 0)
					this.fluid = 0;
				return;
			}
			
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

	public void setType(int in, ItemStack[] slots) {
		setType(in, in, slots);
	}
	
	//Changes tank type
	public void setType(int in, int out, ItemStack[] slots) {
		
		if(slots[in] != null && slots[in].getItem() instanceof ItemFluidIdentifier) {
			
			if(in == out) {
				FluidType newType = ItemFluidIdentifier.getType(slots[in]);
				
				if(type != newType) {
					type = newType;
					fluid = 0;
				}
				
			} else if(slots[out] == null) {
				FluidType newType = ItemFluidIdentifier.getType(slots[in]);
				if(type != newType) {
					type = newType;
					slots[out] = slots[in].copy();
					slots[in] = null;
					fluid = 0;
				}
			}
		}
	}
	
	/**
	 * Renders the fluid texture into a GUI, with the height based on the fill state
	 * @param x the tank's left side
	 * @param y the tank's bottom side (convention from the old system, changing it now would be a pain in the ass)
	 * @param z the GUI's zLevel
	 * @param width
	 * @param height
	 */
	//TODO: add a directional parameter to allow tanks to grow horizontally
	public void renderTank(int x, int y, double z, int width, int height) {

		GL11.glEnable(GL11.GL_BLEND);

		y -= height;
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(type.getTexture());
		
		int i = (fluid * height) / maxFluid;
		
		double minX = x;
		double maxX = x + width;
		double minY = y + (height - i);
		double maxY = y + height;
		
		double minV = 1D - i / 16D;
		double maxV = 1D;
		double minU = 0D;
		double maxU = width / 16D;
		
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(minX, maxY, z, minU, maxV);
		tessellator.addVertexWithUV(maxX, maxY, z, maxU, maxV);
		tessellator.addVertexWithUV(maxX, minY, z, maxU, minV);
		tessellator.addVertexWithUV(minX, minY, z, minU, minV);
		tessellator.draw();

		GL11.glDisable(GL11.GL_BLEND);
	}
	
	public void renderTankInfo(GuiInfoContainer gui, int mouseX, int mouseY, int x, int y, int width, int height) {
		if(x <= mouseX && x + width > mouseX && y < mouseY && y + height >= mouseY) {
			
			List<String> list = new ArrayList();
			list.add(I18n.format(this.type.getUnlocalizedName()));
			list.add(fluid + "/" + maxFluid + "mB");
			
			type.addInfo(list);
			gui.drawFluidInfo(list.toArray(new String[0]), mouseX, mouseY);
		}
	}

	//Called by TE to save fillstate
	public void writeToNBT(NBTTagCompound nbt, String s) {
		nbt.setInteger(s, fluid);
		nbt.setInteger(s + "_max", maxFluid);
		nbt.setInteger(s + "_type", type.getID());
	}
	
	//Called by TE to load fillstate
	public void readFromNBT(NBTTagCompound nbt, String s) {
		fluid = nbt.getInteger(s);
		int max = nbt.getInteger(s + "_max");
		if(max > 0)
			maxFluid = nbt.getInteger(s + "_max");
		
		type = Fluids.fromName(nbt.getString(s + "_type")); //compat
		if(type == Fluids.NONE)
			type = Fluids.fromID(nbt.getInteger(s + "_type"));
	}

}
