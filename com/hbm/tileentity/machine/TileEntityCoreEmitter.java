package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.ILaserable;
import com.hbm.inventory.FluidTank;
import com.hbm.lib.ModDamageSource;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCoreEmitter extends TileEntityMachineBase implements IConsumer, IFluidAcceptor, ILaserable {
	
	public long power;
	public static final long maxPower = 1000000000L;
	public int watts;
	public int beam;
	public long joules;
	public boolean isOn;
	public FluidTank tank;
	public long prev;
	
	public static final int range = 50;

	public TileEntityCoreEmitter() {
		super(0);
		tank = new FluidTank(FluidType.CRYOGEL, 64000, 0);
	}

	@Override
	public String getName() {
		return "container.dfcEmitter";
	}

	@Override
	public void updateEntity() {

		if (!worldObj.isRemote) {
			
			watts = MathHelper.clamp_int(watts, 1, 100);
			long demand = maxPower * watts / 2000;

			tank.updateTank(xCoord, yCoord, zCoord);
			
			beam = 0;
			
			if(joules > 0 || prev > 0) {

				if(tank.getFill() >= 20) {
					tank.setFill(tank.getFill() - 20);
				} else {
					worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.flowing_lava);
					return;
				}
			}
			
			if(isOn) {
				
				if(power >= demand) {
					power -= demand;
					long add = watts;
					joules += add;
				}
				prev = joules;
				
				if(joules > 0) {
					ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata());
					for(int i = 1; i <= range; i++) {
						
						beam = i;
		
						int x = xCoord + dir.offsetX * i;
						int y = yCoord + dir.offsetY * i;
						int z = zCoord + dir.offsetZ * i;
						
						TileEntity te = worldObj.getTileEntity(x, y, z);
						
						if(te instanceof ILaserable) {
							
							((ILaserable)te).addEnergy(joules * 98 / 100);
							break;
						}
						
						Block b = worldObj.getBlock(x, y, z);
						
						if(b != Blocks.air) {
							
							float hardness = b.getExplosionResistance(null);
							if(hardness < 6000 && worldObj.rand.nextInt(20) == 0) {
								worldObj.func_147480_a(x, y, z, false);
							}
							
							break;
						}
					}
					
					
					joules = 0;
		
					double blx = Math.min(xCoord, xCoord + dir.offsetX * beam) + 0.2;
					double bux = Math.max(xCoord, xCoord + dir.offsetX * beam) + 0.8;
					double bly = Math.min(yCoord, yCoord + dir.offsetY * beam) + 0.2;
					double buy = Math.max(yCoord, yCoord + dir.offsetY * beam) + 0.8;
					double blz = Math.min(zCoord, zCoord + dir.offsetZ * beam) + 0.2;
					double buz = Math.max(zCoord, zCoord + dir.offsetZ * beam) + 0.8;
					
					List<Entity> list = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(blx, bly, blz, bux, buy, buz));
					
					for(Entity e : list) {
						e.attackEntityFrom(ModDamageSource.amsCore, 5);
						e.setFire(10);
					}
				}
			} else {
				joules = 0;
				prev = 0;
			}

			this.updateGauge((int) power, 0, 50);
			this.updateGauge(watts, 1, 50);
			this.updateGauge((int) prev, 2, 50);
			this.updateGauge(beam, 3, 50);
			this.updateGauge(isOn ? 1 : 0, 4, 250);
		}
	}
	
	public void processGauge(int val, int id) {

		if(id == 0) power = val;
		if(id == 1) watts = val;
		if(id == 2) joules = val;
		if(id == 3) beam = val;
		if(id == 4) isOn = val == 1;
	}
	
	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}
	
	public int getWattsScaled(int i) {
		return (watts * i) / 100;
	}

	@Override
	public void setFluidFill(int i, FluidType type) {
		if(type.name().equals(tank.getTankType().name()))
			tank.setFill(i);
	}

	@Override
	public int getFluidFill(FluidType type) {
		if(type.name().equals(tank.getTankType().name()))
			return tank.getFill();
		else
			return 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		if(type.name().equals(tank.getTankType().name()))
			return tank.getMaxFill();
		else
			return 0;
	}

	@Override
	public void setFillstate(int fill, int index) {
		tank.setFill(fill);
	}

	@Override
	public void setType(FluidType type, int index) {
		tank.setTankType(type);
	}

	@Override
	public List<FluidTank> getTanks() {
		List<FluidTank> list = new ArrayList();
		list.add(tank);
		
		return list;
	}

	@Override
	public void setPower(long i) {
		this.power = i;
	}

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public long getMaxPower() {
		return this.maxPower;
	}

	@Override
	public void addEnergy(long energy) {
		joules += energy;
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}

}
