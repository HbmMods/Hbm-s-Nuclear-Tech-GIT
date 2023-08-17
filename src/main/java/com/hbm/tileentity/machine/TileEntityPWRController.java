package com.hbm.tileentity.machine;

import java.util.HashMap;
import java.util.Map.Entry;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerPWR;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Heatable;
import com.hbm.inventory.fluid.trait.FT_Heatable.HeatingType;
import com.hbm.inventory.gui.GUIPWR;
import com.hbm.items.ModItems;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.fauxpointtwelve.BlockPos;

import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPWRController extends TileEntityMachineBase implements IGUIProvider, IControlReceiver {
	
	public FluidTank[] tanks;
	public int coreHeat;
	public int coreHeatCapacity;
	public int hullHeat;
	public int hullHeatCapacity;
	
	public int rodLevel;
	public int rodTarget;
	
	public int typeLoaded;
	public int amountLoaded;
	public double progress;
	public int processTime;
	
	public int rodCount;
	public int connections;
	public int connectionsControlled;
	public int heatexCount;
	public int channelCount;
	public int sourceCount;
	
	public boolean assembled;

	public TileEntityPWRController() {
		super(3);
		
		this.tanks = new FluidTank[2];
		this.tanks[0] = new FluidTank(Fluids.COOLANT, 128_000);
		this.tanks[1] = new FluidTank(Fluids.COOLANT_HOT, 128_000);
	}
	
	public void setup(HashMap<BlockPos, Block> partMap, HashMap<BlockPos, Block> rodMap) {
		
		rodCount = 0;
		connections = 0;
		connectionsControlled = 0;
		heatexCount = 0;
		channelCount = 0;
		sourceCount = 0;

		int connectionsDouble = 0;
		int connectionsControlledDouble = 0;
		
		for(Entry<BlockPos, Block> entry : partMap.entrySet()) {
			Block block = entry.getValue();

			if(block == ModBlocks.pwr_fuel) rodCount++;
			if(block == ModBlocks.pwr_heatex) heatexCount++;
			if(block == ModBlocks.pwr_channel) channelCount++;
			if(block == ModBlocks.pwr_neutron_source) sourceCount++;
		}
		
		for(Entry<BlockPos, Block> entry : rodMap.entrySet()) {
			BlockPos fuelPos = entry.getKey();
			
			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				
				boolean controlled = false;
				
				for(int i = 1; i < 16; i++) {
					BlockPos checkPos = fuelPos.offset(dir, i);
					Block atPos = partMap.get(checkPos);
					if(atPos == null || atPos == ModBlocks.pwr_casing) break;
					if(atPos == ModBlocks.pwr_control) controlled = true;
					if(atPos == ModBlocks.pwr_fuel) {
						if(controlled) {
							connectionsControlledDouble++;
						} else {
							connectionsDouble++;
						}
						break;
					}
					if(atPos == ModBlocks.pwr_reflector) {
						if(controlled) {
							connectionsControlledDouble += 2;
						} else {
							connectionsDouble += 2;
						}
						break;
					}
				}
			}
		}

		connections = connectionsDouble / 2;
		connectionsControlled = connectionsControlledDouble / 2;

		System.out.println("Finalized nuclear reactor!");
		System.out.println("Rods: " + rodCount);
		System.out.println("Connections: " + connections);
		System.out.println("Controlled connections: " + connectionsControlled);
		System.out.println("Heatex: " + heatexCount);
		System.out.println("Channels: " + channelCount);
		System.out.println("Sources: " + sourceCount);
	}

	@Override
	public String getName() {
		return "container.pwrController";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			this.tanks[0].setType(2, slots);
			setupTanks();
			
			if(typeLoaded == -1 || amountLoaded <= 0 && slots[0] != null && slots[0].getItem() == ModItems.pwr_fuel) {
				typeLoaded = slots[0].getItemDamage();
				amountLoaded++;
				this.decrStackSize(0, 1);
				this.markChanged();
			}

			if(this.rodTarget > this.rodLevel) this.rodLevel++;
			if(this.rodTarget < this.rodLevel) this.rodLevel--;
			
			NBTTagCompound data = new NBTTagCompound();
			tanks[0].writeToNBT(data, "t0");
			tanks[1].writeToNBT(data, "t1");
		}
	}
	
	protected void setupTanks() {
		
		FT_Heatable trait = tanks[0].getTankType().getTrait(FT_Heatable.class);
		
		if(trait == null || trait.getEfficiency(HeatingType.PWR) <= 0) {
			tanks[0].setTankType(Fluids.NONE);
			tanks[1].setTankType(Fluids.NONE);
		}
		
		tanks[1].setTankType(trait.getFirstStep().typeProduced);
	}
	
	public double getTotalProcessMultiplier() {
		double totalConnections = this.connections + this.connectionsControlled * (1D - (this.rodLevel / 100D));
		double connectionsEff = connectinFunc(totalConnections);
		return connectionsEff;
	}
	
	public double connectinFunc(double connections) {
		return connections * (1D - getXOverE(connections, 300D)) + connections / 2D * getXOverE(connections, 300D); //creates a curve that smoothly transitions from f(x)=x to f(x)=x/2
	}
	
	public double getXOverE(double x, double d) {
		return -Math.pow(Math.E, -x / d);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.assembled = nbt.getBoolean("assembled");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setBoolean("assembled", assembled);
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return this.isUseableByPlayer(player);
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		
		if(data.hasKey("control")) {
			this.rodTarget = MathHelper.clamp_int(data.getInteger("control"), 0, 100);
		}
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerPWR(player.inventory, this);
	}

	@Override
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIPWR(player.inventory, this);
	}
}
