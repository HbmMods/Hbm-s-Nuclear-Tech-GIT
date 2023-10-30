package com.hbm.tileentity.machine;

import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Flammable;
import com.hbm.module.ModuleBurnTime;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachinePolluting;

import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class TileEntityMachineWoodBurner extends TileEntityMachinePolluting implements IFluidStandardTransceiver, IGUIProvider {
	
	public long power;
	public static final long maxPower = 100_000;
	public int burnTime;
	public int maxBurnTime;
	public boolean liquidBurn = false;
	public boolean isOn = false;
	
	public FluidTank tank;
	
	public static ModuleBurnTime burnModule = new ModuleBurnTime().setLogTimeMod(3).setWoodTimeMod(2);

	public int ashLevelWood;
	public int ashLevelCoal;
	public int ashLevelMisc;

	public TileEntityMachineWoodBurner() {
		super(6, 60);
		this.tank = new FluidTank(Fluids.WOODOIL, 16_000);
	}

	@Override
	public String getName() {
		return "container.machineWoodBurner";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(!liquidBurn) {
				
				if(this.burnTime <= 0) {
					
					if(slots[0] != null) {
						int burn = this.burnModule.getBurnTime(slots[0]);
						if(burn > 0) {
							this.maxBurnTime = burn;
							this.decrStackSize(0, 1);
							this.markChanged();
						}
					}
					
				} else if(this.power < this.maxPower){
					this.burnTime--;
					this.power += 100;
					if(power > maxPower) this.power = this.maxPower;
				}
				
			} else {
				
				if(this.power < this.maxPower && tank.getFill() > 0) {
					FT_Flammable trait = tank.getTankType().getTrait(FT_Flammable.class);
					
					if(trait != null) {
						this.power += trait.getHeatEnergy() / 2L;
						tank.setFill(tank.getFill() - 1);
					}
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			this.networkPack(data, 25);
		}
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] {tank, smoke, smoke_leaded, smoke_poison};
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return this.getSmokeTanks();
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {tank};
	}
}
