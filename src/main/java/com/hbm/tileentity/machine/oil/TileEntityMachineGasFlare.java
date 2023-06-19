package com.hbm.tileentity.machine.oil;

import java.util.List;

import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.inventory.UpgradeManager;
import com.hbm.inventory.container.ContainerMachineGasFlare;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Flammable;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Gaseous;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Gaseous_ART;
import com.hbm.inventory.gui.GUIMachineGasFlare;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.ParticleUtil;

import api.hbm.energy.IEnergyGenerator;
import api.hbm.fluid.IFluidStandardReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class TileEntityMachineGasFlare extends TileEntityMachineBase implements IEnergyGenerator, IFluidContainer, IFluidAcceptor, IFluidStandardReceiver, IControlReceiver, IGUIProvider {

	public long power;
	public static final long maxPower = 100000;
	public FluidTank tank;
	public boolean isOn = false;
	public boolean doesBurn = false;

	public TileEntityMachineGasFlare() {
		super(6);
		tank = new FluidTank(Fluids.GAS, 64000, 0);
	}

	@Override
	public String getName() {
		return "container.gasFlare";
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.power = nbt.getLong("powerTime");
		tank.readFromNBT(nbt, "gas");
		isOn = nbt.getBoolean("isOn");
		doesBurn = nbt.getBoolean("doesBurn");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("powerTime", power);
		tank.writeToNBT(nbt, "gas");
		nbt.setBoolean("isOn", isOn);
		nbt.setBoolean("doesBurn", doesBurn);
	}

	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return player.getDistanceSq(xCoord, yCoord, zCoord) <= 256;
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("valve")) this.isOn = !this.isOn;
		if(data.hasKey("dial")) this.doesBurn = !this.doesBurn;
		this.worldObj.markTileEntityChunkModified(this.xCoord, this.yCoord, this.zCoord, this);
	}

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {

			this.sendPower(worldObj, xCoord + 2, yCoord, zCoord, Library.POS_X);
			this.sendPower(worldObj, xCoord - 2, yCoord, zCoord, Library.NEG_X);
			this.sendPower(worldObj, xCoord, yCoord, zCoord + 2, Library.POS_Z);
			this.sendPower(worldObj, xCoord, yCoord, zCoord - 2, Library.NEG_Z);

			this.trySubscribe(tank.getTankType(), worldObj, xCoord + 2, yCoord, zCoord, Library.POS_X);
			this.trySubscribe(tank.getTankType(), worldObj, xCoord - 2, yCoord, zCoord, Library.NEG_X);
			this.trySubscribe(tank.getTankType(), worldObj, xCoord, yCoord, zCoord + 2, Library.POS_Z);
			this.trySubscribe(tank.getTankType(), worldObj, xCoord, yCoord, zCoord - 2, Library.NEG_Z);

			tank.setType(3, slots);
			tank.loadTank(1, 2, slots);
			tank.updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			
			int maxVent = 50;
			int maxBurn = 10;
			
			if(isOn && tank.getFill() > 0) {
				
				UpgradeManager.eval(slots, 4, 5);
				int burn = Math.min(UpgradeManager.getLevel(UpgradeType.SPEED), 3);
				int yield = Math.min(UpgradeManager.getLevel(UpgradeType.EFFECT), 3);

				maxVent += maxVent * burn;
				maxBurn += maxBurn * burn;
				
				if(!doesBurn || !(tank.getTankType().hasTrait(FT_Flammable.class))) {
					
					if(tank.getTankType().hasTrait(FT_Gaseous.class) || tank.getTankType().hasTrait(FT_Gaseous_ART.class)) {
						int eject = Math.min(maxVent, tank.getFill());
						tank.setFill(tank.getFill() - eject);
						tank.getTankType().onFluidRelease(this, tank, eject);
						
						if(worldObj.getTotalWorldTime() % 7 == 0)
							this.worldObj.playSoundEffect(this.xCoord, this.yCoord + 11, this.zCoord, "random.fizz", 1.5F, 0.5F);
					}
				} else {
					
					if(tank.getTankType().hasTrait(FT_Flammable.class)) {
						int eject = Math.min(maxBurn, tank.getFill());
						tank.setFill(tank.getFill() - eject);
						
						int penalty = 2;
						if(!tank.getTankType().hasTrait(FT_Gaseous.class) && !tank.getTankType().hasTrait(FT_Gaseous_ART.class))
							penalty = 10;
						
						long powerProd = tank.getTankType().getTrait(FT_Flammable.class).getHeatEnergy() * eject / 1_000; // divided by 1000 per mB
						powerProd /= penalty;
						powerProd += powerProd * yield / 3;
						
						power += powerProd;
						
						if(power > maxPower)
							power = maxPower;
						
						ParticleUtil.spawnGasFlame(worldObj, this.xCoord + 0.5F, this.yCoord + 11.75F, this.zCoord + 0.5F, worldObj.rand.nextGaussian() * 0.15, 0.2, worldObj.rand.nextGaussian() * 0.15);
						
						List<Entity> list = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord + 12, zCoord - 2, xCoord + 2, yCoord + 17, zCoord + 2));
						for(Entity e : list) {
							e.setFire(5);
							e.attackEntityFrom(DamageSource.onFire, 5F);
						}
						
						if(worldObj.getTotalWorldTime() % 3 == 0)
							this.worldObj.playSoundEffect(this.xCoord, this.yCoord + 11, this.zCoord, "hbm:weapon.flamethrowerShoot", 1.5F, 0.75F);

						if(worldObj.getTotalWorldTime() % 20 == 0) PollutionHandler.incrementPollution(worldObj, xCoord, yCoord, zCoord, PollutionType.SOOT, PollutionHandler.SOOT_PER_SECOND * 5);
					}
				}
			}

			power = Library.chargeItemsFromTE(slots, 0, power, maxPower);
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", this.power);
			data.setBoolean("isOn", isOn);
			data.setBoolean("doesBurn", doesBurn);
			this.networkPack(data, 50);

		} else {
			
			if(isOn && tank.getFill() > 0) {
							
				if((!doesBurn || !(tank.getTankType().hasTrait(FT_Flammable.class))) && (tank.getTankType().hasTrait(FT_Gaseous.class) || tank.getTankType().hasTrait(FT_Gaseous_ART.class))) {
						
					NBTTagCompound data = new NBTTagCompound();
					data.setString("type", "tower");
					data.setFloat("lift", 1F);
					data.setFloat("base", 0.25F);
					data.setFloat("max", 3F);
					data.setInteger("life", 150 + worldObj.rand.nextInt(20));
					data.setInteger("color", tank.getTankType().getColor());

					data.setDouble("posX", xCoord + 0.5);
					data.setDouble("posZ", zCoord + 0.5);
					data.setDouble("posY", yCoord + 11);
						
					MainRegistry.proxy.effectNT(data);
					
				}
				
				if(doesBurn && tank.getTankType().hasTrait(FT_Flammable.class) && MainRegistry.proxy.me().getDistanceSq(xCoord, yCoord + 10, zCoord) <= 1024) {
					
					NBTTagCompound data = new NBTTagCompound();
					data.setString("type", "vanillaExt");
					data.setString("mode", "smoke");
					data.setBoolean("noclip", true);
					data.setInteger("overrideAge", 50);

					if(worldObj.getTotalWorldTime() % 2 == 0) {
						data.setDouble("posX", xCoord + 1.5);
						data.setDouble("posZ", zCoord + 1.5);
						data.setDouble("posY", yCoord + 10.75);
					} else {
						data.setDouble("posX", xCoord + 1.125);
						data.setDouble("posZ", zCoord - 0.5);
						data.setDouble("posY", yCoord + 11.75);
					}
					
					MainRegistry.proxy.effectNT(data);
				}
			}
		}

	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
		this.isOn = nbt.getBoolean("isOn");
		this.doesBurn = nbt.getBoolean("doesBurn");
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
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
	public void setPower(long i) {
		this.power = i;
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

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { tank };
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineGasFlare(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineGasFlare(player.inventory, this);
	}
}
