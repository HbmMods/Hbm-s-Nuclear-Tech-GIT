package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.entity.effect.EntityCloudFleijaRainbow;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemCatalyst;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.ArmorUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityCore extends TileEntityMachineBase {
	
	public int field;
	public int heat;
	public int color;
	public FluidTank[] tanks;

	public TileEntityCore() {
		super(3);
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(Fluids.DEUTERIUM, 128000, 0);
		tanks[1] = new FluidTank(Fluids.TRITIUM, 128000, 1);
	}

	@Override
	public String getName() {
		return "container.dfcCore";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(heat > 0 && heat >= field) {
				
				int fill = tanks[0].getFill() + tanks[1].getFill();
				int max = tanks[0].getMaxFill() + tanks[1].getMaxFill();
				int mod = heat * 10;
				
				int size = Math.max(Math.min(fill * mod / max, 1000), 50);
				
				//System.out.println(fill + " * " + mod + " / " + max + " = " + size);

				worldObj.playSoundEffect(xCoord, yCoord, zCoord, "random.explode", 100000.0F, 1.0F);
				worldObj.spawnEntityInWorld(EntityNukeExplosionMK3.statFacFleija(worldObj, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, size));
				
				EntityCloudFleijaRainbow cloud = new EntityCloudFleijaRainbow(worldObj, size);
				cloud.posX = xCoord;
				cloud.posY = yCoord;
				cloud.posZ = zCoord;
				worldObj.spawnEntityInWorld(cloud);
			}
			
			if(slots[0] != null && slots[2] != null && slots[0].getItem() instanceof ItemCatalyst && slots[2].getItem() instanceof ItemCatalyst)
				color = calcAvgHex(
						((ItemCatalyst)slots[0].getItem()).getColor(),
						((ItemCatalyst)slots[2].getItem()).getColor()
				);
			else
				color = 0;
			
			if(heat > 0)
				radiation();
			
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("tank0", tanks[0].getTankType().ordinal());
			data.setInteger("tank1", tanks[1].getTankType().ordinal());
			data.setInteger("fill0", tanks[0].getFill());
			data.setInteger("fill1", tanks[1].getFill());
			data.setInteger("field", field);
			data.setInteger("heat", heat);
			data.setInteger("color", color);
			networkPack(data, 250);
			
			heat = 0;
			field = 0;
			this.markDirty();
		} else {
			
			//TODO: sick particle effects
		}
		
	}
	
	public void networkUnpack(NBTTagCompound data) {

		tanks[0].setTankType(Fluids.fromID(data.getInteger("tank0")));
		tanks[1].setTankType(Fluids.fromID(data.getInteger("tank1")));
		tanks[0].setFill(data.getInteger("fill0"));
		tanks[1].setFill(data.getInteger("fill1"));
		field = data.getInteger("field");
		heat = data.getInteger("heat");
		color = data.getInteger("color");
	}
	
	private void radiation() {
		
		double scale = 2;
		
		List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(xCoord - 10 + 0.5, yCoord - 10 + 0.5 + 6, zCoord - 10 + 0.5, xCoord + 10 + 0.5, yCoord + 10 + 0.5 + 6, zCoord + 10 + 0.5));
		
		for(Entity e : list) {
			if(!(e instanceof EntityPlayer && ArmorUtil.checkForHazmat((EntityPlayer)e)))
				if(!Library.isObstructed(worldObj, xCoord + 0.5, yCoord + 0.5 + 6, zCoord + 0.5, e.posX, e.posY + e.getEyeHeight(), e.posZ)) {
					e.attackEntityFrom(ModDamageSource.ams, 1000);
					e.setFire(3);
				}
		}

		List<Entity> list2 = worldObj.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(xCoord - scale + 0.5, yCoord - scale + 0.5 + 6, zCoord - scale + 0.5, xCoord + scale + 0.5, yCoord + scale + 0.5 + 6, zCoord + scale + 0.5));
		
		for(Entity e : list2) {
			if(!(e instanceof EntityPlayer && ArmorUtil.checkForHaz2((EntityPlayer)e)))
					e.attackEntityFrom(ModDamageSource.amsCore, 10000);
		}
	}
	
	public int getFieldScaled(int i) {
		return (field * i) / 100;
	}
	
	public int getHeatScaled(int i) {
		return (heat * i) / 100;
	}
	
	public boolean isReady() {
		
		if(getCore() == 0)
			return false;
		
		if(color == 0)
			return false;
		
		if(getFuelEfficiency(tanks[0].getTankType()) <= 0 || getFuelEfficiency(tanks[1].getTankType()) <= 0)
			return false;
		
		return true;
	}
	
	//100 emitter watt = 10000 joules = 1 heat = 10mB burned
	public long burn(long joules) {
		
		//check if a reaction can take place
		if(!isReady())
			return joules;
		
		int demand = (int)Math.ceil((double)joules / 1000D);
		
		//check if the reaction has enough valid fuel
		if(tanks[0].getFill() < demand || tanks[1].getFill() < demand)
			return joules;
		
		heat += (int)Math.ceil((double)joules / 10000D);

		tanks[0].setFill(tanks[0].getFill() - demand);
		tanks[1].setFill(tanks[1].getFill() - demand);
		
		return (long) (joules * getCore() * getFuelEfficiency(tanks[0].getTankType()) * getFuelEfficiency(tanks[1].getTankType()));
	}
	
	public float getFuelEfficiency(FluidType type) {
		if(type == Fluids.HYDROGEN)
			return 1.0F;
		if(type == Fluids.DEUTERIUM)
			return 1.5F;
		if(type == Fluids.TRITIUM)
			return 1.7F;
		if(type == Fluids.OXYGEN)
			return 1.2F;
		if(type == Fluids.ACID)
			return 1.4F;
		if(type == Fluids.XENON)
			return 1.5F;
		if(type == Fluids.SAS3)
			return 2.0F;
		if(type == Fluids.BALEFIRE)
			return 2.5F;
		if(type == Fluids.AMAT)
			return 2.2F;
		if(type == Fluids.ASCHRAB)
			return 2.7F;
		return 0;
	}
	
	//TODO: move stats to the AMSCORE class
	public int getCore() {
		
		if(slots[1] == null) {
			return 0;
		}
		
		if(slots[1].getItem() == ModItems.ams_core_sing)
			return 500;
		
		if(slots[1].getItem() == ModItems.ams_core_wormhole)
			return 650;
		
		if(slots[1].getItem() == ModItems.ams_core_eyeofharmony)
			return 800;
		
		if(slots[1].getItem() == ModItems.ams_core_thingy)
			return 2500;
		
		return 0;
	}
	
	private int calcAvgHex(int h1, int h2) {

		int r1 = ((h1 & 0xFF0000) >> 16);
		int g1 = ((h1 & 0x00FF00) >> 8);
		int b1 = ((h1 & 0x0000FF) >> 0);
		
		int r2 = ((h2 & 0xFF0000) >> 16);
		int g2 = ((h2 & 0x00FF00) >> 8);
		int b2 = ((h2 & 0x0000FF) >> 0);

		int r = (((r1 + r2) / 2) << 16);
		int g = (((g1 + g2) / 2) << 8);
		int b = (((b1 + b2) / 2) << 0);
		
		return r | g | b;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		tanks[0].readFromNBT(nbt, "fuel1");
		tanks[1].readFromNBT(nbt, "fuel2");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		tanks[0].writeToNBT(nbt, "fuel1");
		tanks[1].writeToNBT(nbt, "fuel2");
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord + 0.5 - 4,
					yCoord + 0.5 - 4,
					zCoord + 0.5 - 4,
					xCoord + 0.5 + 5,
					yCoord + 0.5 + 5,
					zCoord + 0.5 + 5
					);
		}
		
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}
