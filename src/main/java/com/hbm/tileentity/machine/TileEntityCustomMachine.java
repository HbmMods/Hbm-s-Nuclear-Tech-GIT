package com.hbm.tileentity.machine;

import com.hbm.config.CustomMachineConfigJSON;
import com.hbm.config.CustomMachineConfigJSON.MachineConfiguration;
import com.hbm.config.CustomMachineConfigJSON.MachineConfiguration.ComponentDefinition;
import com.hbm.inventory.container.ContainerMachineCustom;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIMachineCustom;
import com.hbm.module.ModulePatternMatcher;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCustomMachine extends TileEntityMachineBase implements IGUIProvider {
	
	public String machineType;
	public MachineConfiguration config;
	
	public long power;
	public int progress;
	public FluidTank[] inputTanks;
	public FluidTank[] outputTanks;
	public ModulePatternMatcher matcher;
	public int structureCheckDelay;
	public boolean structureOK = false;

	public TileEntityCustomMachine() {
		/*
		 * 0: Battery
		 * 1-3: Fluid IDs
		 * 4-9: Inputs
		 * 10-15: Template
		 * 16-21: Output
		 */
		super(22);
	}
	
	public void init() {
		MachineConfiguration config = CustomMachineConfigJSON.customMachines.get(this.machineType);
		
		if(config != null) {
			this.config = config;

			inputTanks = new FluidTank[config.fluidInCount];
			for(int i = 0; i < inputTanks.length; i++) inputTanks[i] = new FluidTank(Fluids.NONE, config.fluidInCap);
			outputTanks = new FluidTank[config.fluidOutCount];
			for(int i = 0; i < outputTanks.length; i++) outputTanks[i] = new FluidTank(Fluids.NONE, config.fluidOutCap);
			
			matcher = new ModulePatternMatcher(config.itemInCount);
			
		} else {
			worldObj.func_147480_a(xCoord, yCoord, zCoord, false);
		}
	}

	@Override
	public String getName() {
		return config != null ? config.localizedName : "INVALID";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(config == null) {
				worldObj.func_147480_a(xCoord, yCoord, zCoord, false);
				return;
			}
			
			this.structureCheckDelay--;
			if(this.structureCheckDelay <= 0) this.checkStructure();
			
			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", this.machineType);
			data.setLong("power", power);
			data.setInteger("progress", progress);
			for(int i = 0; i < inputTanks.length; i++) inputTanks[i].writeToNBT(data, "i" + i);
			for(int i = 0; i < outputTanks.length; i++) outputTanks[i].writeToNBT(data, "o" + i);
			this.matcher.writeToNBT(data);
			this.networkPack(data, 50);
		}
	}
	
	public boolean checkStructure() {
		
		this.structureCheckDelay = 300;
		this.structureOK = false;
		if(this.config == null) return false;
		
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata());
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		
		for(ComponentDefinition comp : config.components) {
			
			/* vvv precisely the same method used for defining ports vvv */
			int x = xCoord - dir.offsetX * comp.x + rot.offsetX * comp.x;
			int y = yCoord + comp.y;
			int z = zCoord - dir.offsetZ * comp.z + rot.offsetZ * comp.z;
			/* but for EW directions it just stops working entirely */
			/* there is absolutely zero reason why this should be required */
			if(dir == ForgeDirection.EAST || dir == ForgeDirection.WEST) {
				x = xCoord + dir.offsetZ * comp.z - rot.offsetZ * comp.z;
				z = zCoord + dir.offsetX * comp.x - rot.offsetX * comp.x;
			}
			/* i wholeheartedly believe it is the computer who is wrong here */
			
			Block b = worldObj.getBlock(x, y, z);
			if(b != comp.block) return false;
			
			int meta = worldObj.getBlockMetadata(x, y, z);
			if(!comp.allowedMetas.contains(meta)) return false;
		}
		
		this.structureOK = true;
		return true;
	}
	
	public void buildStructure() {
		
		if(this.config == null) return;
		
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata());
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		
		for(ComponentDefinition comp : config.components) {
			
			int x = xCoord - dir.offsetX * comp.x + rot.offsetX * comp.x;
			int y = yCoord + comp.y;
			int z = zCoord - dir.offsetZ * comp.z + rot.offsetZ * comp.z;
			if(dir == ForgeDirection.EAST || dir == ForgeDirection.WEST) {
				x = xCoord + dir.offsetZ * comp.z - rot.offsetZ * comp.z;
				z = zCoord + dir.offsetX * comp.x - rot.offsetX * comp.x;
			}
			
			worldObj.setBlock(x, y, z, comp.block, (int) comp.allowedMetas.toArray()[0], 3);
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.machineType = nbt.getString("type");
		if(this.config == null) this.init();
		
		this.power = nbt.getLong("power");
		this.progress = nbt.getInteger("progress");
		for(int i = 0; i < inputTanks.length; i++) inputTanks[i].readFromNBT(nbt, "i" + i);
		for(int i = 0; i < outputTanks.length; i++) outputTanks[i].readFromNBT(nbt, "o" + i);
		
		this.matcher.readFromNBT(nbt);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		
		this.machineType = nbt.getString("machineType");
		this.init();
		
		super.readFromNBT(nbt);

		for(int i = 0; i < inputTanks.length; i++) inputTanks[i].readFromNBT(nbt, "i" + i);
		for(int i = 0; i < outputTanks.length; i++) outputTanks[i].readFromNBT(nbt, "o" + i);
		
		this.matcher.readFromNBT(nbt);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		
		nbt.setString("machineType", machineType);
		
		super.writeToNBT(nbt);

		for(int i = 0; i < inputTanks.length; i++) inputTanks[i].writeToNBT(nbt, "i" + i);
		for(int i = 0; i < outputTanks.length; i++) outputTanks[i].writeToNBT(nbt, "o" + i);
		
		this.matcher.writeToNBT(nbt);
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(this.config == null) return null;
		return new ContainerMachineCustom(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(this.config == null) return null;
		return new GUIMachineCustom(player.inventory, this);
	}
}
