package com.hbm.inventory.fluid.tank;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.FluidStack;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.gui.GuiInfoContainer;
import com.hbm.items.ModItems;
import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.util.BobMathUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;

public class FluidTank implements Cloneable {
	
	public static final FluidTank[] EMPTY_ARRAY = new FluidTank[0];

	public static final List<FluidLoadingHandler> loadingHandlers = new ArrayList<FluidLoadingHandler>();
	public static final Set<Item> noDualUnload = new HashSet<Item>();
	
	static {
		loadingHandlers.add(new FluidLoaderStandard());
		loadingHandlers.add(new FluidLoaderFillableItem());
		loadingHandlers.add(new FluidLoaderInfinite());
	}
	
	FluidType type;
	int fluid;
	int maxFluid;
	int pressure = 0;
	
	public FluidTank(FluidType type, int maxFluid) {
		this.type = type;
		this.maxFluid = maxFluid;
	}
	
	public FluidTank withPressure(int pressure) {
		if(this.pressure != pressure) this.setFill(0);
		this.pressure = pressure;
		return this;
	}
	
	public void setFill(int i) { fluid = i; }
	
	public void setTankType(FluidType type) {
		if(type == null) type = Fluids.NONE;
		if(this.type == type) return;
		
		this.type = type;
		this.setFill(0);
	}
	
	/** Changes type and pressure based on a fluid stack, useful for changing tank types based on recipes */
	public FluidTank conform(FluidStack stack) {
		this.setTankType(stack.type);
		this.withPressure(stack.pressure);
		return this;
	}
	
	public FluidType getTankType() { return type; }
	public int getFill() { return fluid; }
	public int getMaxFill() { return maxFluid; }
	public int getPressure() { return pressure; }
	
	public int changeTankSize(int size) {
		maxFluid = size;
		
		if(fluid > maxFluid) {
			int dif = fluid - maxFluid;
			fluid = maxFluid;
			return dif;
		}
		return 0;
	}
	
	//Fills tank from canisters
	public boolean loadTank(int in, int out, ItemStack[] slots) {
		if(slots[in] == null) return false;

		boolean isInfiniteBarrel = slots[in].getItem() == ModItems.fluid_barrel_infinite;
		if(!isInfiniteBarrel && pressure != 0) return false;
		
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
		if(slots[in] == null) return false;
		
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
	@SideOnly(Side.CLIENT) public void renderTank(int x, int y, double z, int width, int height) {
		renderTank(x, y, z, width, height, 0);
	}
	
	@SideOnly(Side.CLIENT) public void renderTank(int x, int y, double z, int width, int height, int orientation) {

		GL11.glEnable(GL11.GL_BLEND);
		
		int color = type.getTint();
		double r = ((color & 0xff0000) >> 16) / 255D;
		double g = ((color & 0x00ff00) >> 8) / 255D;
		double b = ((color & 0x0000ff) >> 0) / 255D;
		GL11.glColor3d(r, g, b);

		y -= height;
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(type.getTexture());
		
		int i = (fluid * height) / maxFluid;
		
		double minX = x;
		double maxX = x;
		double minY = y;
		double maxY = y;
		
		double minV = 1D - i / 16D;
		double maxV = 1D;
		double minU = 0D;
		double maxU = width / 16D;
		
		if(orientation == 0) {
			maxX += width;
			minY += height - i;
			maxY += height;
		}
		
		if(orientation == 1) {
			i = (fluid * width) / maxFluid;
			maxX += i;
			maxY += height;
			
			minV = 0D;
			maxV = height / 16D;
			minU = 1D;
			maxU = 1D - i / 16D;
		}
		
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(minX, maxY, z, minU, maxV);
		tessellator.addVertexWithUV(maxX, maxY, z, maxU, maxV);
		tessellator.addVertexWithUV(maxX, minY, z, maxU, minV);
		tessellator.addVertexWithUV(minX, minY, z, minU, minV);
		tessellator.draw();

		GL11.glColor3d(1D, 1D, 1D);
		GL11.glDisable(GL11.GL_BLEND);
	}
	
	@SideOnly(Side.CLIENT) public void renderTankInfo(GuiInfoContainer gui, int mouseX, int mouseY, int x, int y, int width, int height) {
		if(x <= mouseX && x + width > mouseX && y < mouseY && y + height >= mouseY) {
			
			List<String> list = new ArrayList();
			list.add(this.type.getLocalizedName());
			list.add(fluid + "/" + maxFluid + "mB");
			
			if(this.pressure != 0) {
				list.add(EnumChatFormatting.RED + "Pressure: " + this.pressure + " PU");
				list.add((BobMathUtil.getBlink() ? EnumChatFormatting.RED : EnumChatFormatting.DARK_RED) + "Pressurized, use compressor!");
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
		
		type = Fluids.fromNameCompat(nbt.getString(s + "_type")); //compat
		if(type == Fluids.NONE)
			type = Fluids.fromID(nbt.getInteger(s + "_type"));
		
		this.pressure = nbt.getShort(s + "_p");
	}
	
	public void serialize(ByteBuf buf) {
		buf.writeInt(fluid);
		buf.writeInt(maxFluid);
		buf.writeInt(type.getID());
		buf.writeShort((short) pressure);
	}

	public void deserialize(ByteBuf buf) {
		fluid = buf.readInt();
		maxFluid = buf.readInt();
		type = Fluids.fromID(buf.readInt());
		pressure = buf.readShort();
	}
}
