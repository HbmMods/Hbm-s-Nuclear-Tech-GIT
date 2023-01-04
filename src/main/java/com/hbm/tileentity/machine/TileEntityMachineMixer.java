package com.hbm.tileentity.machine;

import com.hbm.inventory.container.ContainerMixer;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIMixer;
import com.hbm.inventory.recipes.MixerRecipes;
import com.hbm.inventory.recipes.MixerRecipes.MixerRecipe;
import com.hbm.lib.Library;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energy.IEnergyUser;
import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityMachineMixer extends TileEntityMachineBase implements INBTPacketReceiver, IGUIProvider, IEnergyUser, IFluidStandardTransceiver {
	
	public long power;
	public static final long maxPower = 100_000;
	public int progress;
	public int processTime;
	
	public float rotation;
	public float prevRotation;
	public boolean wasOn = false;
	
	public FluidTank[] tanks;

	public TileEntityMachineMixer() {
		super(5);
		this.tanks = new FluidTank[3];
		this.tanks[0] = new FluidTank(Fluids.NONE, 16_000);
		this.tanks[1] = new FluidTank(Fluids.NONE, 16_000);
		this.tanks[2] = new FluidTank(Fluids.NONE, 16_000);
	}

	@Override
	public String getName() {
		return "container.machineMixer";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			tanks[2].setType(2, slots);
			
			for(DirPos pos : getConPos()) {
				this.trySubscribe(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				if(tanks[0].getTankType() != Fluids.NONE) this.trySubscribe(tanks[0].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				if(tanks[1].getTankType() != Fluids.NONE) this.trySubscribe(tanks[1].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}
			
			this.wasOn = this.canProcess();
			
			if(this.wasOn) {
				this.progress++;
				
				if(this.progress >= this.processTime) {
					this.process();
					this.progress = 0;
				}
				
			} else {
				this.progress = 0;
			}
			
			for(DirPos pos : getConPos()) {
				if(tanks[2].getFill() > 0) this.sendFluid(tanks[2].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			data.setInteger("processTime", processTime);
			data.setInteger("progress", progress);
			data.setBoolean("wasOn", wasOn);
			for(int i = 0; i < 3; i++) {
				tanks[i].writeToNBT(data, i + "");
			}
			this.networkPack(data, 50);
			
		} else {
			
			this.prevRotation = this.rotation;
			
			if(this.wasOn) {
				this.rotation += 20F;
			}
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.power = nbt.getLong("powe");
		this.processTime = nbt.getInteger("processTime");
		this.progress = nbt.getInteger("progress");
		this.wasOn = nbt.getBoolean("wasOn");
		for(int i = 0; i < 3; i++) {
			tanks[i].readFromNBT(nbt, i + "");
		}
	}
	
	public boolean canProcess() {
		
		MixerRecipe recipe = MixerRecipes.getOutput(tanks[2].getTankType());
		
		if(recipe == null) return false;
		
		if(recipe.input1 != null) {
			
			if(recipe.input1.type != tanks[0].getTankType()) {
				tanks[0].setTankType(recipe.input1.type);
			}
			
			if(tanks[0].getFill() < recipe.input1.fill) return false;
		}
		
		if(recipe.input2 != null) {
			
			if(recipe.input2.type != tanks[1].getTankType()) {
				tanks[1].setTankType(recipe.input2.type);
			}
			
			if(tanks[1].getFill() < recipe.input2.fill) return false;
		}
		
		/* simplest check would usually go first, but fluid checks also do the setup and we want that to happen even without power */
		if(this.power < getConsumption()) return false;
		
		if(recipe.output + tanks[2].getFill() > tanks[2].getMaxFill()) return false;
		
		if(recipe.solidInput != null) {
			
			if(slots[1] == null) return false;
			
			if(!recipe.solidInput.matchesRecipe(slots[1], true) || recipe.solidInput.stacksize > slots[1].stackSize) return false; 
		}
		
		this.processTime = recipe.processTime;
		return true;
	}
	
	protected void process() {
		
		MixerRecipe recipe = MixerRecipes.getOutput(tanks[2].getTankType());

		if(recipe.input1 != null) tanks[0].setFill(tanks[0].getFill() - recipe.input1.fill);
		if(recipe.input2 != null) tanks[1].setFill(tanks[1].getFill() - recipe.input2.fill);
		if(recipe.solidInput != null) this.decrStackSize(1, recipe.solidInput.stacksize);
		tanks[2].setFill(tanks[2].getFill() + recipe.output);
	}
	
	public int getConsumption() {
		return 50;
	}
	
	protected DirPos[] getConPos() {
		return new DirPos[] {
				new DirPos(xCoord, yCoord - 1, zCoord, Library.NEG_Y),
				new DirPos(xCoord + 1, yCoord, zCoord, Library.POS_X),
				new DirPos(xCoord - 1, yCoord, zCoord, Library.POS_X),
				new DirPos(xCoord, yCoord, zCoord + 1, Library.POS_Z),
				new DirPos(xCoord, yCoord, zCoord - 1, Library.POS_Z),
		};
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}

	@Override
	public FluidTank[] getAllTanks() {
		return tanks;
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {tanks[2]};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {tanks[0], tanks[1]};
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMixer(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMixer(player.inventory, this);
	}
	
	AxisAlignedBB aabb;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(aabb != null)
			return aabb;
		
		aabb = AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 3, zCoord + 1);
		return aabb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}
