package com.hbm.tileentity.turret;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.inventory.FluidTank;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;

import api.hbm.fluid.IFluidStandardReceiver;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityTurretFritz extends TileEntityTurretBaseNT implements IFluidAcceptor, IFluidStandardReceiver {
	
	public FluidTank tank;
	
	public TileEntityTurretFritz() {
		super();
		this.tank = new FluidTank(Fluids.DIESEL, 16000, 0);
	}
	
	@Override
	public String getName() {
		return "container.turretFritz";
	}

	@Override
	protected List<Integer> getAmmoList() {
		return null;
	}

	@SideOnly(Side.CLIENT)
	public List<ItemStack> getAmmoTypesForDisplay() {
		
		if(ammoStacks != null)
			return ammoStacks;
		
		ammoStacks = new ArrayList();

		ammoStacks.add(new ItemStack(ModItems.fluid_icon, 1, Fluids.DIESEL.getID()));
		ammoStacks.add(new ItemStack(ModItems.ammo_fuel));
		
		return ammoStacks;
	}
	
	@Override
	public double getDecetorRange() {
		return 32D;
	}
	
	@Override
	public double getDecetorGrace() {
		return 2D;
	}

	@Override
	public double getTurretElevation() {
		return 45D;
	}

	@Override
	public long getMaxPower() {
		return 10000;
	}

	@Override
	public double getBarrelLength() {
		return 2.25D;
	}

	@Override
	public double getAcceptableInaccuracy() {
		return 15;
	}

	@Override
	public void updateFiringTick() {
		
		if(this.tank.getTankType() == Fluids.DIESEL && this.tank.getFill() >= 10) {
			
			BulletConfiguration conf = BulletConfigSyncingUtil.pullConfig(BulletConfigSyncingUtil.FLA_NORMAL);
			this.spawnBullet(conf);
			this.tank.setFill(this.tank.getFill() - 10);
			
			Vec3 pos = this.getTurretPos();
			Vec3 vec = Vec3.createVectorHelper(this.getBarrelLength(), 0, 0);
			vec.rotateAroundZ((float) -this.rotationPitch);
			vec.rotateAroundY((float) -(this.rotationYaw + Math.PI * 0.5));
			
			worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:weapon.flamethrowerShoot", 2F, 1F + worldObj.rand.nextFloat() * 0.5F);
			
			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "vanillaburst");
			data.setString("mode", "flame");
			data.setInteger("count", 2);
			data.setDouble("motion", 0.025D);
			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, pos.xCoord + vec.xCoord, pos.yCoord + vec.yCoord, pos.zCoord + vec.zCoord), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
		}
	}
	
	public int getDelay() {
		return 2;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(!worldObj.isRemote) {
			tank.setType(9, 9, slots);
			tank.loadTank(0, 1, slots);
			tank.updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			
			for(int i = 1; i < 10; i++) {
				
				if(slots[i] != null && slots[i].getItem() == ModItems.ammo_fuel) {
					if(this.tank.getTankType() == Fluids.DIESEL && this.tank.getFill() + 1000 <= this.tank.getMaxFill()) {
						this.tank.setFill(this.tank.getFill() + 1000);
						this.decrStackSize(i, 1);
					}
				}
			}
		}
	}

	@Override //TODO: clean this shit up
	protected void updateConnections() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset).getOpposite();
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		this.trySubscribe(worldObj, xCoord + dir.offsetX * -1 + rot.offsetX * 0, yCoord, zCoord + dir.offsetZ * -1 + rot.offsetZ * 0, ForgeDirection.UNKNOWN);
		this.trySubscribe(worldObj, xCoord + dir.offsetX * -1 + rot.offsetX * -1, yCoord, zCoord + dir.offsetZ * -1 + rot.offsetZ * -1, ForgeDirection.UNKNOWN);
		this.trySubscribe(worldObj, xCoord + dir.offsetX * 0 + rot.offsetX * -2, yCoord, zCoord + dir.offsetZ * 0 + rot.offsetZ * -2, ForgeDirection.UNKNOWN);
		this.trySubscribe(worldObj, xCoord + dir.offsetX * 1 + rot.offsetX * -2, yCoord, zCoord + dir.offsetZ * 1 + rot.offsetZ * -2, ForgeDirection.UNKNOWN);
		this.trySubscribe(worldObj, xCoord + dir.offsetX * 0 + rot.offsetX * 1, yCoord, zCoord + dir.offsetZ * 0 + rot.offsetZ * 1, ForgeDirection.UNKNOWN);
		this.trySubscribe(worldObj, xCoord + dir.offsetX * 1 + rot.offsetX * 1, yCoord, zCoord + dir.offsetZ * 1 + rot.offsetZ * 1, ForgeDirection.UNKNOWN);
		this.trySubscribe(worldObj, xCoord + dir.offsetX * 2 + rot.offsetX * 0, yCoord, zCoord + dir.offsetZ * 2 + rot.offsetZ * 0, ForgeDirection.UNKNOWN);
		this.trySubscribe(worldObj, xCoord + dir.offsetX * 2 + rot.offsetX * -1, yCoord, zCoord + dir.offsetZ * 2 + rot.offsetZ * -1, ForgeDirection.UNKNOWN);
		
		this.trySubscribe(tank.getTankType(), worldObj, xCoord + dir.offsetX * -1 + rot.offsetX * 0, yCoord, zCoord + dir.offsetZ * -1 + rot.offsetZ * 0, ForgeDirection.UNKNOWN);
		this.trySubscribe(tank.getTankType(), worldObj, xCoord + dir.offsetX * -1 + rot.offsetX * -1, yCoord, zCoord + dir.offsetZ * -1 + rot.offsetZ * -1, ForgeDirection.UNKNOWN);
		this.trySubscribe(tank.getTankType(), worldObj, xCoord + dir.offsetX * 0 + rot.offsetX * -2, yCoord, zCoord + dir.offsetZ * 0 + rot.offsetZ * -2, ForgeDirection.UNKNOWN);
		this.trySubscribe(tank.getTankType(), worldObj, xCoord + dir.offsetX * 1 + rot.offsetX * -2, yCoord, zCoord + dir.offsetZ * 1 + rot.offsetZ * -2, ForgeDirection.UNKNOWN);
		this.trySubscribe(tank.getTankType(), worldObj, xCoord + dir.offsetX * 0 + rot.offsetX * 1, yCoord, zCoord + dir.offsetZ * 0 + rot.offsetZ * 1, ForgeDirection.UNKNOWN);
		this.trySubscribe(tank.getTankType(), worldObj, xCoord + dir.offsetX * 1 + rot.offsetX * 1, yCoord, zCoord + dir.offsetZ * 1 + rot.offsetZ * 1, ForgeDirection.UNKNOWN);
		this.trySubscribe(tank.getTankType(), worldObj, xCoord + dir.offsetX * 2 + rot.offsetX * 0, yCoord, zCoord + dir.offsetZ * 2 + rot.offsetZ * 0, ForgeDirection.UNKNOWN);
		this.trySubscribe(tank.getTankType(), worldObj, xCoord + dir.offsetX * 2 + rot.offsetX * -1, yCoord, zCoord + dir.offsetZ * 2 + rot.offsetZ * -1, ForgeDirection.UNKNOWN);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.tank.readFromNBT(nbt, "diesel");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		this.tank.writeToNBT(nbt, "diesel");
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 1, 2, 3, 4, 5, 6, 7, 8 };
	}

	@Override
	public void setFillForSync(int fill, int index) {
		tank.setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		tank.setTankType(type);
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		return type.name().equals(this.tank.getTankType().name()) ? tank.getMaxFill() : 0;
	}

	@Override
	public int getFluidFill(FluidType type) {
		return type.name().equals(this.tank.getTankType().name()) ? tank.getFill() : 0;
	}

	@Override
	public void setFluidFill(int i, FluidType type) {
		if(type.name().equals(tank.getTankType().name()))
			tank.setFill(i);
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] { tank };
	}
}
