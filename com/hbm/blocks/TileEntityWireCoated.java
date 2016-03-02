package com.hbm.blocks;

import com.hbm.lib.Library;

import net.minecraft.tileentity.TileEntity;

public class TileEntityWireCoated extends TileEntity {
	
	public static int power;
	public static final int maxPower = 600;
	
	@Override
	public void updateEntity() {
		
		//Energy distribution algorithm
		/*//Electric Furnace
		if(worldObj.getBlock(xCoord + 1, yCoord, zCoord) instanceof MachineElectricFurnace)
		{
			TileEntityMachineElectricFurnace entity = (TileEntityMachineElectricFurnace) worldObj.getTileEntity(xCoord + 1, yCoord, zCoord);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}
		if(worldObj.getBlock(xCoord - 1, yCoord, zCoord) instanceof MachineElectricFurnace)
		{
			TileEntityMachineElectricFurnace entity = (TileEntityMachineElectricFurnace) worldObj.getTileEntity(xCoord - 1, yCoord, zCoord);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}
		if(worldObj.getBlock(xCoord, yCoord + 1, zCoord) instanceof MachineElectricFurnace)
		{
			TileEntityMachineElectricFurnace entity = (TileEntityMachineElectricFurnace) worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}
		if(worldObj.getBlock(xCoord, yCoord - 1, zCoord) instanceof MachineElectricFurnace)
		{
			TileEntityMachineElectricFurnace entity = (TileEntityMachineElectricFurnace) worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}
		if(worldObj.getBlock(xCoord, yCoord, zCoord + 1) instanceof MachineElectricFurnace)
		{
			TileEntityMachineElectricFurnace entity = (TileEntityMachineElectricFurnace) worldObj.getTileEntity(xCoord, yCoord, zCoord + 1);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}
		if(worldObj.getBlock(xCoord, yCoord, zCoord - 1) instanceof MachineElectricFurnace)
		{
			TileEntityMachineElectricFurnace entity = (TileEntityMachineElectricFurnace) worldObj.getTileEntity(xCoord, yCoord, zCoord - 1);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}
		
		//Wire
		if(worldObj.getBlock(xCoord + 1, yCoord, zCoord) instanceof WireCoated)
		{
			TileEntityWireCoated entity = (TileEntityWireCoated) worldObj.getTileEntity(xCoord + 1, yCoord, zCoord);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}
		if(worldObj.getBlock(xCoord - 1, yCoord, zCoord) instanceof WireCoated)
		{
			TileEntityWireCoated entity = (TileEntityWireCoated) worldObj.getTileEntity(xCoord - 1, yCoord, zCoord);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}
		if(worldObj.getBlock(xCoord, yCoord + 1, zCoord) instanceof WireCoated)
		{
			TileEntityWireCoated entity = (TileEntityWireCoated) worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}
		if(worldObj.getBlock(xCoord, yCoord - 1, zCoord) instanceof WireCoated)
		{
			TileEntityWireCoated entity = (TileEntityWireCoated) worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}
		if(worldObj.getBlock(xCoord, yCoord, zCoord + 1) instanceof WireCoated)
		{
			TileEntityWireCoated entity = (TileEntityWireCoated) worldObj.getTileEntity(xCoord, yCoord, zCoord + 1);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}
		if(worldObj.getBlock(xCoord, yCoord, zCoord - 1) instanceof WireCoated)
		{
			TileEntityWireCoated entity = (TileEntityWireCoated) worldObj.getTileEntity(xCoord, yCoord, zCoord - 1);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}
		
		//Deuterium
		if(worldObj.getBlock(xCoord + 1, yCoord, zCoord) instanceof MachineDeuterium)
		{
			TileEntityMachineDeuterium entity = (TileEntityMachineDeuterium) worldObj.getTileEntity(xCoord + 1, yCoord, zCoord);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}
		if(worldObj.getBlock(xCoord - 1, yCoord, zCoord) instanceof MachineDeuterium)
		{
			TileEntityMachineDeuterium entity = (TileEntityMachineDeuterium) worldObj.getTileEntity(xCoord - 1, yCoord, zCoord);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}
		if(worldObj.getBlock(xCoord, yCoord + 1, zCoord) instanceof MachineDeuterium)
		{
			TileEntityMachineDeuterium entity = (TileEntityMachineDeuterium) worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}
		if(worldObj.getBlock(xCoord, yCoord - 1, zCoord) instanceof MachineDeuterium)
		{
			TileEntityMachineDeuterium entity = (TileEntityMachineDeuterium) worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}
		if(worldObj.getBlock(xCoord, yCoord, zCoord + 1) instanceof MachineDeuterium)
		{
			TileEntityMachineDeuterium entity = (TileEntityMachineDeuterium) worldObj.getTileEntity(xCoord, yCoord, zCoord + 1);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}
		if(worldObj.getBlock(xCoord, yCoord, zCoord - 1) instanceof MachineDeuterium)
		{
			TileEntityMachineDeuterium entity = (TileEntityMachineDeuterium) worldObj.getTileEntity(xCoord, yCoord, zCoord - 1);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}
		
		//Batteries
		if(worldObj.getBlock(xCoord + 1, yCoord, zCoord) instanceof MachineBattery)
		{
			TileEntityMachineBattery entity = (TileEntityMachineBattery) worldObj.getTileEntity(xCoord + 1, yCoord, zCoord);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}
		if(worldObj.getBlock(xCoord - 1, yCoord, zCoord) instanceof MachineBattery)
		{
			TileEntityMachineBattery entity = (TileEntityMachineBattery) worldObj.getTileEntity(xCoord - 1, yCoord, zCoord);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}
		if(worldObj.getBlock(xCoord, yCoord + 1, zCoord) instanceof MachineBattery)
		{
			TileEntityMachineBattery entity = (TileEntityMachineBattery) worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}
		if(worldObj.getBlock(xCoord, yCoord - 1, zCoord) instanceof MachineBattery)
		{
			TileEntityMachineBattery entity = (TileEntityMachineBattery) worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}
		if(worldObj.getBlock(xCoord, yCoord, zCoord + 1) instanceof MachineBattery)
		{
			TileEntityMachineBattery entity = (TileEntityMachineBattery) worldObj.getTileEntity(xCoord, yCoord, zCoord + 1);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}
		if(worldObj.getBlock(xCoord, yCoord, zCoord - 1) instanceof MachineBattery)
		{
			TileEntityMachineBattery entity = (TileEntityMachineBattery) worldObj.getTileEntity(xCoord, yCoord, zCoord - 1);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}*/
		
		/*if(power == 600) {
			TileEntity entity0 = worldObj.getTileEntity(xCoord + 1, yCoord, zCoord);
			TileEntity entity1 = worldObj.getTileEntity(xCoord - 1, yCoord, zCoord);
			TileEntity entity2 = worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
			TileEntity entity3 = worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
			TileEntity entity4 = worldObj.getTileEntity(xCoord, yCoord, zCoord + 1);
			TileEntity entity5 = worldObj.getTileEntity(xCoord, yCoord, zCoord - 1);
			
			TileEntity entity;
			
			for(int i = 0; i < 6; i++)
			{
				entity = entity0;
				if(i == 1) entity = entity1;
				if(i == 2) entity = entity2;
				if(i == 3) entity = entity3;
				if(i == 4) entity = entity4;
				if(i == 5) entity = entity5;
				
				if(entity instanceof TileEntityMachineElectricFurnace ||
						entity instanceof TileEntityWireCoated ||
						entity instanceof TileEntityMachineDeuterium ||
						entity instanceof TileEntityMachineBattery)
				{
					if(entity instanceof TileEntityMachineElectricFurnace && ((TileEntityMachineElectricFurnace)entity).power + 100 <= ((TileEntityMachineElectricFurnace)entity).maxPower)
					{
						((TileEntityMachineElectricFurnace)entity).power += 100;
					} else
					if(entity instanceof TileEntityWireCoated && ((TileEntityWireCoated)entity).power + 100 <= ((TileEntityWireCoated)entity).maxPower)
					{
						((TileEntityWireCoated)entity).power += 100;
					} else
					if(entity instanceof TileEntityMachineDeuterium && ((TileEntityMachineDeuterium)entity).power + 100 <= ((TileEntityMachineDeuterium)entity).maxPower)
					{
						((TileEntityMachineDeuterium)entity).power += 100;
					} else
					if(entity instanceof TileEntityMachineBattery && ((TileEntityMachineBattery)entity).power + 100 <= ((TileEntityMachineBattery)entity).maxPower)
					{
						((TileEntityMachineBattery)entity).power += 100;
					}
					this.power -= 100;
				}
			}
		}*/

		//Energy distributing algorithm V2.0    
		/*TileEntity entity0 = worldObj.getTileEntity(xCoord + 1, yCoord, zCoord);
		TileEntity entity1 = worldObj.getTileEntity(xCoord - 1, yCoord, zCoord);
		TileEntity entity2 = worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
		TileEntity entity3 = worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
		TileEntity entity4 = worldObj.getTileEntity(xCoord, yCoord, zCoord + 1);
		TileEntity entity5 = worldObj.getTileEntity(xCoord, yCoord, zCoord - 1);
		
		if(power == 600 && power != 0) {
			
			TileEntity entity;
			
			for(int i = 0; i < 6; i++)
			{
				entity = entity0;
				if(i == 1) entity = entity1;
				if(i == 2) entity = entity2;
				if(i == 3) entity = entity3;
				if(i == 4) entity = entity4;
				if(i == 5) entity = entity5;
				
				if(entity instanceof TileEntityMachineElectricFurnace ||
						entity instanceof TileEntityWireCoated ||
						entity instanceof TileEntityMachineDeuterium ||
						entity instanceof TileEntityMachineBattery)
				{
					if(entity instanceof TileEntityMachineElectricFurnace && ((TileEntityMachineElectricFurnace)entity).power + 100 <= ((TileEntityMachineElectricFurnace)entity).maxPower)
					{
						((TileEntityMachineElectricFurnace)entity).power += 100;
						this.power -= 100;
					} else
					if(entity instanceof TileEntityWireCoated && ((TileEntityWireCoated)entity).power + 100 <= ((TileEntityWireCoated)entity).maxPower)
					{
						((TileEntityWireCoated)entity).power += 100;
						this.power -= 100;
					} else
					if(entity instanceof TileEntityMachineDeuterium && ((TileEntityMachineDeuterium)entity).power + 100 <= ((TileEntityMachineDeuterium)entity).maxPower)
					{
						((TileEntityMachineDeuterium)entity).power += 100;
						this.power -= 100;
					} else
					if(entity instanceof TileEntityMachineBattery && ((TileEntityMachineBattery)entity).power + 100 <= ((TileEntityMachineBattery)entity).maxPower && !((TileEntityMachineBattery)entity).conducts)
					{
						((TileEntityMachineBattery)entity).power += 100;
						this.power -= 100;
					}
				}
			}
		}*/
		
		/*//Energy distributing algorithm V2.1
		TileEntity entity0 = worldObj.getTileEntity(xCoord + 1, yCoord, zCoord);
		TileEntity entity1 = worldObj.getTileEntity(xCoord - 1, yCoord, zCoord);
		TileEntity entity2 = worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
		TileEntity entity3 = worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
		TileEntity entity4 = worldObj.getTileEntity(xCoord, yCoord, zCoord + 1);
		TileEntity entity5 = worldObj.getTileEntity(xCoord, yCoord, zCoord - 1);
		
		TileEntity entity;
		
		for(int i = 0; i < 6; i++)
		{
			entity = entity0;
			if(i == 1) entity = entity1;
			if(i == 2) entity = entity2;
			if(i == 3) entity = entity3;
			if(i == 4) entity = entity4;
			if(i == 5) entity = entity5;
			
			if(entity instanceof TileEntityMachineElectricFurnace ||
					entity instanceof TileEntityWireCoated ||
					entity instanceof TileEntityMachineDeuterium ||
					entity instanceof TileEntityMachineBattery)
			{
				if(entity instanceof TileEntityMachineElectricFurnace && ((TileEntityMachineElectricFurnace)entity).power + 100 <= ((TileEntityMachineElectricFurnace)entity).maxPower)
				{
					((TileEntityMachineElectricFurnace)entity).power += 100;
					this.power -= 100;
				} else
				if(entity instanceof TileEntityWireCoated && ((TileEntityWireCoated)entity).power + 100 <= ((TileEntityWireCoated)entity).maxPower)
				{
					((TileEntityWireCoated)entity).power += 100;
					this.power -= 100;
				} else
				if(entity instanceof TileEntityMachineDeuterium && ((TileEntityMachineDeuterium)entity).power + 100 <= ((TileEntityMachineDeuterium)entity).maxPower)
				{
					((TileEntityMachineDeuterium)entity).power += 100;
					this.power -= 100;
				} else
				if(entity instanceof TileEntityMachineBattery && ((TileEntityMachineBattery)entity).power + 100 <= ((TileEntityMachineBattery)entity).maxPower && !((TileEntityMachineBattery)entity).conducts)
				{
					((TileEntityMachineBattery)entity).power += 100;
					this.power -= 100;
				}
			}
		}*/
		
		//Library.distributePower(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
	}

}
