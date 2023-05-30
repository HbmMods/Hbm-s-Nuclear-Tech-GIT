package com.hbm.inventory.fluid.tank;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.gui.GuiInfoContainer;
import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEFluidPacket;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;

public class FluidTank {
	
	public static final List<FluidLoadingHandler> loadingHandlers = new ArrayList();
	
	static {
		loadingHandlers.add(new FluidLoaderStandard());
		loadingHandlers.add(new FluidLoaderFillableItem());
		loadingHandlers.add(new FluidLoaderInfinite());
	}
	
	FluidType type;
	int fluid;
	int maxFluid;
	public int index = 0;
	int pressure = 0;
	
	public FluidTank(FluidType type, int maxFluid) {
		this.type = type;
		this.maxFluid = maxFluid;
	}
	
	public FluidTank withPressure(int pressure) {
		this.pressure = pressure;
		return this;
	}
	
	@Deprecated // indices are no longer needed
	public FluidTank(FluidType type, int maxFluid, int index) {
		this.type = type;
		this.maxFluid = maxFluid;
		this.index = index;
	}
	
	public void setFill(int i) {
		fluid = i;
	}
	
	public void setTankType(FluidType type) {
		
		if(type == null) {
			type = Fluids.NONE;
		}
		
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
	
	public int getPressure() {
		return pressure;
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
	@Deprecated public void updateTank(TileEntity te) {
		updateTank(te, 100);
	}
	@Deprecated public void updateTank(TileEntity te, int range) {
		updateTank(te.xCoord, te.yCoord, te.zCoord, te.getWorldObj().provider.dimensionId, range);
	}
	@Deprecated public void updateTank(int x, int y, int z, int dim) {
		updateTank(x, y, z, dim, 100);
	}
	@Deprecated public void updateTank(int x, int y, int z, int dim, int range) {
		PacketDispatcher.wrapper.sendToAllAround(new TEFluidPacket(x, y, z, fluid, index, type), new TargetPoint(dim, x, y, z, range));
	}
	
	//Fills tank from canisters
	public boolean loadTank(int in, int out, ItemStack[] slots) {
		
		if(slots[in] == null)
			return false;
		
		if(this.pressure != 0) return false; //for now, canisters can only be loaded from high-pressure tanks, not unloaded
		
		int prev = this.getFill();
		
		for(FluidLoadingHandler handler : loadingHandlers) {
			if(handler.emptyItem(slots, in, out, this)) {
				break;
			}
		}
		
		return this.getFill() > prev;
	}
	
	//Fills canisters from tank
	public boolean unloadTank(int in, int out, ItemStack[] slots) {
		
		if(slots[in] == null)
			return false;
		
		int prev = this.getFill();
		
		for(FluidLoadingHandler handler : loadingHandlers) {
			if(handler.fillItem(slots, in, out, this)) {
				break;
			}
		}
		
		return this.getFill() < prev;
	}

	public boolean setType(int in, ItemStack[] slots) {
		return setType(in, in, slots);
	}
	
	/**
	 * Changes the tank type and returns true if successful
	 * @param in
	 * @param out
	 * @param slots
	 * @return
	 */
	public boolean setType(int in, int out, ItemStack[] slots) {
		
		if(slots[in] != null && slots[in].getItem() instanceof IItemFluidIdentifier) {
			IItemFluidIdentifier id = (IItemFluidIdentifier) slots[in].getItem();
			
			if(in == out) {
				FluidType newType = id.getType(null, 0, 0, 0, slots[in]);
				
				if(type != newType) {
					type = newType;
					fluid = 0;
					return true;
				}
				
			} else if(slots[out] == null) {
				FluidType newType = id.getType(null, 0, 0, 0, slots[in]);
				if(type != newType) {
					type = newType;
					slots[out] = slots[in].copy();
					slots[in] = null;
					fluid = 0;
					return true;
				}
			}
		}
		
		return false;
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
			
			if(this.pressure != 0) {
				list.add(EnumChatFormatting.RED + "" + this.pressure + "mB/l");
			}
			
			type.addInfo(list);
			gui.drawInfo(list.toArray(new String[0]), mouseX, mouseY);
		}
	}

	//Called by TE to save fillstate
	public void writeToNBT(NBTTagCompound nbt, String s) {
		nbt.setInteger(s, fluid);
		nbt.setInteger(s + "_max", maxFluid);
		nbt.setInteger(s + "_type", type.getID());
		nbt.setShort(s + "_p", (short) pressure);
	}
	
	//Called by TE to load fillstate
	public void readFromNBT(NBTTagCompound nbt, String s) {
		fluid = nbt.getInteger(s);
		int max = nbt.getInteger(s + "_max");
		if(max > 0)
			maxFluid = max;
		
		fluid = MathHelper.clamp_int(fluid, 0, max);
		
		type = Fluids.fromName(nbt.getString(s + "_type")); //compat
		if(type == Fluids.NONE)
			type = Fluids.fromID(nbt.getInteger(s + "_type"));
		
		this.pressure = nbt.getShort(s + "_p");
	}

}
