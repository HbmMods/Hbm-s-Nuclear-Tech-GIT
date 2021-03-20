package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.inventory.FluidTank;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.SILEXRecipes;
import com.hbm.inventory.SILEXRecipes.SILEXRecipe;
import com.hbm.items.ModItems;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.InventoryUtil;
import com.hbm.util.WeightedRandomObject;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.WeightedRandom;

public class TileEntitySILEX extends TileEntityMachineBase implements IFluidAcceptor {
	
	public FluidTank tank;
	public ComparableStack current;
	public int currentFill;
	public static final int maxFill = 16000;
	public int progress;
	public final int processTime = 100;
	
	//0: Input
	//1: Fluid ID
	//2-3: Fluid Containers
	//4: Output
	//5-10: Queue

	public TileEntitySILEX() {
		super(11);
		tank = new FluidTank(FluidType.ACID, 16000, 0);
	}

	@Override
	public String getName() {
		return "container.machineSILEX";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {

			tank.setType(1, 1, slots);
			tank.loadTank(2, 3, slots);
			
			loadFluid();
			
			if(!process()) {
				this.progress = 0;
			}
			
			dequeue();
			
			if(currentFill <= 0) {
				current = null;
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("fill", currentFill);
			data.setInteger("progress", progress);
			
			if(this.current != null) {
				data.setInteger("item", Item.getIdFromItem(this.current.item));
				data.setInteger("meta", this.current.meta);
			}

			tank.updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			this.networkPack(data, 50);
		}
	}
	
	public void networkUnpack(NBTTagCompound nbt) {
		
		this.currentFill = nbt.getInteger("fill");
		this.progress = nbt.getInteger("progress");
		
		if(this.currentFill > 0) {
			this.current = new ComparableStack(Item.getItemById(nbt.getInteger("item")), 1, nbt.getInteger("meta"));
			
		} else {
			this.current = null;
		}
	}
	
	public void handleButtonPacket(int value, int meta) {
		
		this.currentFill = 0;
		this.current = null;
	}
	
	public int getProgressScaled(int i) {
		return (progress * i) / processTime;
	}
	
	public int getFluidScaled(int i) {
		return (tank.getFill() * i) / tank.getMaxFill();
	}
	
	public int getFillScaled(int i) {
		return (currentFill * i) / maxFill;
	}
	
	public static final HashMap<FluidType, ComparableStack> fluidConversion = new HashMap();
	
	static {
		fluidConversion.put(FluidType.UF6, new ComparableStack(ModItems.ingot_uranium));
		fluidConversion.put(FluidType.PUF6, new ComparableStack(ModItems.ingot_plutonium));
	}
	
	int loadDelay;
	
	public void loadFluid() {
		
		ComparableStack conv = fluidConversion.get(tank.getTankType());
		
		if(conv != null) {
			
			if(currentFill == 0) {
				current = (ComparableStack) conv.copy();
			}
			
			if(current != null && current.equals(conv)) {
				
				int toFill = Math.min(10, Math.min(maxFill - currentFill, tank.getFill()));
				currentFill += toFill;
				tank.setFill(tank.getFill() - toFill);
			}
		}
		
		loadDelay++;
		
		if(loadDelay > 20)
			loadDelay = 0;
		
		if(loadDelay == 0 && slots[0] != null && tank.getTankType() == FluidType.ACID && (this.current == null || this.current.equals(new ComparableStack(slots[0]).makeSingular()))) {
			SILEXRecipe recipe = SILEXRecipes.getOutput(slots[0]);
			
			if(recipe == null)
				return;
			
			int load = recipe.fluidProduced;
			
			if(load <= this.maxFill - this.currentFill && load < tank.getFill()) {
				this.currentFill += load;
				this.current = new ComparableStack(slots[0]).makeSingular();
				tank.setFill(tank.getFill() - load);
				this.decrStackSize(0, 1);
			}
		}
	}
	
	private boolean process() {
		
		if(current == null || currentFill <= 0)
			return false;
		
		SILEXRecipe recipe = SILEXRecipes.getOutput(current.toStack());
		
		if(recipe == null)
			return false;
		
		if(currentFill < recipe.fluidConsumed)
			return false;
		
		if(slots[4] != null)
			return false;
		
		progress++;
		
		if(progress >= processTime) {
			
			currentFill -= recipe.fluidConsumed;
			
			ItemStack out = ((WeightedRandomObject)WeightedRandom.getRandomItem(worldObj.rand, recipe.outputs)).asStack();
			slots[4] = out.copy();
			progress = 0;
			this.markDirty();
		}
		
		return true;
	}
	
	private void dequeue() {
		
		if(slots[4] != null) {
			
			for(int i = 5; i < 11; i++) {
				
				if(slots[i] != null && slots[i].stackSize < slots[i].getMaxStackSize() && InventoryUtil.doesStackDataMatch(slots[4], slots[i])) {
					slots[i].stackSize++;
					this.decrStackSize(4, 1);
					return;
				}
			}
			
			for(int i = 5; i < 11; i++) {
				
				if(slots[i] == null) {
					slots[i] = slots[4].copy();
					slots[4] = null;
					return;
				}
			}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.tank.readFromNBT(nbt, "tank");
		this.currentFill = nbt.getInteger("fill");
		
		if(this.currentFill > 0) {
			this.current = new ComparableStack(Item.getItemById(nbt.getInteger("item")), 1, nbt.getInteger("meta"));
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		this.tank.writeToNBT(nbt, "tank");
		nbt.setInteger("fill", this.currentFill);
		
		if(this.current != null) {
			nbt.setInteger("item", Item.getIdFromItem(this.current.item));
			nbt.setInteger("meta", this.current.meta);
		}
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(
				xCoord - 1,
				yCoord,
				zCoord - 1,
				xCoord + 2,
				yCoord + 3,
				zCoord + 2
			);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}

	@Override
	public void setFillstate(int fill, int index) {
		tank.setFill(fill);
	}

	@Override
	public void setFluidFill(int fill, FluidType type) {
		
		if(type == tank.getTankType())
			tank.setFill(fill);
	}

	@Override
	public void setType(FluidType type, int index) {
		tank.setTankType(type);
	}

	@Override
	public List<FluidTank> getTanks() {
		return new ArrayList() {{ add(tank); }};
	}

	@Override
	public int getFluidFill(FluidType type) {
		
		if(type == tank.getTankType())
			return tank.getFill();
		
		return 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		
		if(type == tank.getTankType())
			return tank.getMaxFill();
		
		return 0;
	}
}
