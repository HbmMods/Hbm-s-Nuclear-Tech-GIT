package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.inventory.UpgradeManager;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Combustible;
import com.hbm.inventory.fluid.trait.FT_Combustible.FuelGrade;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energy.IEnergyGenerator;
import api.hbm.fluid.IFluidStandardReceiver;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineTurbofan extends TileEntityMachineBase implements ISidedInventory, IEnergyGenerator, IFluidContainer, IFluidAcceptor, IFluidStandardReceiver {

	public long power;
	public static final long maxPower = 500_000;
	public FluidTank tank;
	
	public int afterburner;
	public boolean wasOn;

	public float spin;
	public float lastSpin;
	public int momentum = 0;
	
	private AudioWrapper audio;

	public TileEntityMachineTurbofan() {
		super(3);
		tank = new FluidTank(Fluids.KEROSENE, 24000, 0);
	}

	@Override
	public String getName() {
		return "container.machineTurbofan";
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.power = nbt.getLong("powerTime");
		tank.readFromNBT(nbt, "fuel");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("powerTime", power);
		tank.writeToNBT(nbt, "fuel");
	}

	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}
	
	protected DirPos[] getConPos() {
		
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata());
		ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);
		
		return new DirPos[] {
				new DirPos(this.xCoord + rot.offsetX * 2, this.yCoord, this.zCoord + rot.offsetZ * 2, rot),
				new DirPos(this.xCoord + rot.offsetX * 2 - dir.offsetX, this.yCoord, this.zCoord + rot.offsetZ * 2 - dir.offsetZ, rot),
				new DirPos(this.xCoord - rot.offsetX * 2, this.yCoord, this.zCoord - rot.offsetZ * 2, rot.getOpposite()),
				new DirPos(this.xCoord - rot.offsetX * 2 - dir.offsetX, this.yCoord, this.zCoord - rot.offsetZ * 2 - dir.offsetZ, rot.getOpposite())
		};
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			for(DirPos pos : getConPos()) {
				this.sendPower(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				this.trySubscribe(tank.getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}
			
			tank.loadTank(0, 1, slots);
			
			this.wasOn = false;
			
			UpgradeManager.eval(slots, 2, 2);
			this.afterburner = UpgradeManager.getLevel(UpgradeType.AFTERBURN);
			
			if(slots[2] != null && slots[2].getItem() == ModItems.flame_pony)
				this.afterburner = 100;
			
			long burn = 0;
			int amount = 1 + this.afterburner;
			
			if(tank.getTankType().hasTrait(FT_Combustible.class) && tank.getTankType().getTrait(FT_Combustible.class).getGrade() == FuelGrade.AERO) {
				burn = tank.getTankType().getTrait(FT_Combustible.class).getCombustionEnergy() / 1_000;
			}
			
			int toBurn = Math.min(amount, this.tank.getFill());
			
			if(toBurn > 0) {
				this.wasOn = true;
				this.tank.setFill(this.tank.getFill() - toBurn);
				this.power += burn * toBurn;
				if(this.power > this.maxPower) {
					this.power = this.maxPower;
				}
			}
			
			if(toBurn > 0) {

				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata());
				ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
				
				if(this.afterburner > 0) {
					
					for(int i = 0; i < 2; i++) {
						double speed = 2 + worldObj.rand.nextDouble() * 3;
						double deviation = worldObj.rand.nextGaussian() * 0.2;
						NBTTagCompound data = new NBTTagCompound();
						data.setString("type", "gasfire");
						data.setDouble("mX", -dir.offsetX * speed + deviation);
						data.setDouble("mZ", -dir.offsetZ * speed + deviation);
						data.setFloat("scale", 8F);
						PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, this.xCoord + 0.5F - dir.offsetX * (3 - i), this.yCoord + 1.5F, this.zCoord + 0.5F - dir.offsetZ * (3 - i)), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 150));
					}
					
					/*if(this.afterburner > 90 && worldObj.rand.nextInt(30) == 0) {
						worldObj.newExplosion(null, xCoord + 0.5 + dir.offsetX * 3.5, yCoord + 0.5, zCoord + 0.5 + dir.offsetZ * 3.5, 3F, false, false);
					}*/
					
					if(this.afterburner > 90) {
						NBTTagCompound data = new NBTTagCompound();
						data.setString("type", "gasfire");
						data.setDouble("mY", 0.1 * worldObj.rand.nextDouble());
						data.setFloat("scale", 4F);
						PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data,
								this.xCoord + 0.5F + dir.offsetX * (worldObj.rand.nextDouble() * 4 - 2) + rot.offsetX * (worldObj.rand.nextDouble() * 2 - 1),
								this.yCoord + 1F + worldObj.rand.nextDouble() * 2,
								this.zCoord + 0.5F - dir.offsetZ * (worldObj.rand.nextDouble() * 4 - 2) + rot.offsetZ * (worldObj.rand.nextDouble() * 2 - 1)
								), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 150));
					}
				}
				
				double minX = this.xCoord + 0.5 - dir.offsetX * 3.5 - rot.offsetX * 1.5;
				double maxX = this.xCoord + 0.5 - dir.offsetX * 19.5 + rot.offsetX * 1.5;
				double minZ = this.zCoord + 0.5 - dir.offsetZ * 3.5 - rot.offsetZ * 1.5;
				double maxZ = this.zCoord + 0.5 - dir.offsetZ * 19.5 + rot.offsetZ * 1.5;
				
				List<Entity> list = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(Math.min(minX, maxX), yCoord, Math.min(minZ, maxZ), Math.max(minX, maxX), yCoord + 3, Math.max(minZ, maxZ)));
				
				for(Entity e : list) {
					
					if(this.afterburner > 0) {
						e.setFire(5);
						e.attackEntityFrom(DamageSource.onFire, 5F);
					}
					e.motionX -= dir.offsetX * 0.2;
					e.motionZ -= dir.offsetZ * 0.2;
				}
				
				minX = this.xCoord + 0.5 + dir.offsetX * 3.5 - rot.offsetX * 1.5;
				maxX = this.xCoord + 0.5 + dir.offsetX * 8.5 + rot.offsetX * 1.5;
				minZ = this.zCoord + 0.5 + dir.offsetZ * 3.5 - rot.offsetZ * 1.5;
				maxZ = this.zCoord + 0.5 + dir.offsetZ * 8.5 + rot.offsetZ * 1.5;
				
				list = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(Math.min(minX, maxX), yCoord, Math.min(minZ, maxZ), Math.max(minX, maxX), yCoord + 3, Math.max(minZ, maxZ)));
				
				for(Entity e : list) {
					e.motionX -= dir.offsetX * 0.2;
					e.motionZ -= dir.offsetZ * 0.2;
				}
				
				minX = this.xCoord + 0.5 + dir.offsetX * 3.5 - rot.offsetX * 1.5;
				maxX = this.xCoord + 0.5 + dir.offsetX * 3.75 + rot.offsetX * 1.5;
				minZ = this.zCoord + 0.5 + dir.offsetZ * 3.5 - rot.offsetZ * 1.5;
				maxZ = this.zCoord + 0.5 + dir.offsetZ * 3.75 + rot.offsetZ * 1.5;
				
				list = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(Math.min(minX, maxX), yCoord, Math.min(minZ, maxZ), Math.max(minX, maxX), yCoord + 3, Math.max(minZ, maxZ)));
				
				for(Entity e : list) {
					e.attackEntityFrom(ModDamageSource.turbofan, 1000);
					e.setInWeb();
					
					if(!e.isEntityAlive() && e instanceof EntityLivingBase) {
						NBTTagCompound vdat = new NBTTagCompound();
						vdat.setString("type", "giblets");
						vdat.setInteger("ent", e.getEntityId());
						vdat.setInteger("cDiv", 5);
						PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(vdat, e.posX, e.posY + e.height * 0.5, e.posZ), new TargetPoint(e.dimension, e.posX, e.posY + e.height * 0.5, e.posZ, 150));
						
						worldObj.playSoundEffect(e.posX, e.posY, e.posZ, "mob.zombie.woodbreak", 2.0F, 0.95F + worldObj.rand.nextFloat() * 0.2F);
					}
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			data.setByte("after", (byte) afterburner);
			data.setBoolean("wasOn", wasOn);
			tank.writeToNBT(data, "tank");
			this.networkPack(data, 150);
			
		} else {
			
			this.lastSpin = this.spin;
			
			if(wasOn) {
				if(this.momentum < 100F)
					this.momentum++;
			} else {
				if(this.momentum > 0)
					this.momentum--;
			}
			
			this.spin += momentum / 2;
			
			if(this.spin >= 360) {
				this.spin -= 360F;
				this.lastSpin -= 360F;
			}

			if(momentum > 0) {
				
				if(audio == null) {
					audio = createAudioLoop();
					audio.startSound();
				} else if(!audio.isPlaying()) {
					audio = rebootAudio(audio);
				}

				audio.updateVolume(momentum);
				audio.updatePitch(momentum / 200F + 0.5F + this.afterburner * 0.16F);
				
			} else {
				
				if(audio != null) {
					audio.stopSound();
					audio = null;
				}
			}

			/*
			 * All movement related stuff has to be repeated on the client, but only for the client's player
			 * Otherwise this could lead to desync since the motion is never sent form the server
			 */
			if(tank.getFill() > 0 && !MainRegistry.proxy.me().capabilities.isCreativeMode) {
				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata());
				ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
				
				double minX = this.xCoord + 0.5 - dir.offsetX * 3.5 - rot.offsetX * 1.5;
				double maxX = this.xCoord + 0.5 - dir.offsetX * 19.5 + rot.offsetX * 1.5;
				double minZ = this.zCoord + 0.5 - dir.offsetZ * 3.5 - rot.offsetZ * 1.5;
				double maxZ = this.zCoord + 0.5 - dir.offsetZ * 19.5 + rot.offsetZ * 1.5;
				
				List<Entity> list = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(Math.min(minX, maxX), yCoord, Math.min(minZ, maxZ), Math.max(minX, maxX), yCoord + 3, Math.max(minZ, maxZ)));
				
				for(Entity e : list) {
					if(e == MainRegistry.proxy.me()) {
						e.motionX -= dir.offsetX * 0.2;
						e.motionZ -= dir.offsetZ * 0.2;
					}
				}
				
				minX = this.xCoord + 0.5 + dir.offsetX * 3.5 - rot.offsetX * 1.5;
				maxX = this.xCoord + 0.5 + dir.offsetX * 8.5 + rot.offsetX * 1.5;
				minZ = this.zCoord + 0.5 + dir.offsetZ * 3.5 - rot.offsetZ * 1.5;
				maxZ = this.zCoord + 0.5 + dir.offsetZ * 8.5 + rot.offsetZ * 1.5;
				
				list = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(Math.min(minX, maxX), yCoord, Math.min(minZ, maxZ), Math.max(minX, maxX), yCoord + 3, Math.max(minZ, maxZ)));
				
				for(Entity e : list) {
					if(e == MainRegistry.proxy.me()) {
						e.motionX -= dir.offsetX * 0.2;
						e.motionZ -= dir.offsetZ * 0.2;
					}
				}
				
				minX = this.xCoord + 0.5 + dir.offsetX * 3.5 - rot.offsetX * 1.5;
				maxX = this.xCoord + 0.5 + dir.offsetX * 3.75 + rot.offsetX * 1.5;
				minZ = this.zCoord + 0.5 + dir.offsetZ * 3.5 - rot.offsetZ * 1.5;
				maxZ = this.zCoord + 0.5 + dir.offsetZ * 3.75 + rot.offsetZ * 1.5;
				
				list = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(Math.min(minX, maxX), yCoord, Math.min(minZ, maxZ), Math.max(minX, maxX), yCoord + 3, Math.max(minZ, maxZ)));
				
				for(Entity e : list) {
					if(e == MainRegistry.proxy.me()) {
						e.setInWeb();
					}
				}
			}
		}
	}
	
	public void networkUnpack(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
		this.afterburner = nbt.getByte("after");
		this.wasOn = nbt.getBoolean("wasOn");
		tank.readFromNBT(nbt, "tank");
	}
	
	public AudioWrapper createAudioLoop() {
		return MainRegistry.proxy.getLoopedSound("hbm:block.turbofanOperate", xCoord, yCoord, zCoord, 5.0F, 1.0F);
	}

	@Override
	public void onChunkUnload() {

		if(audio != null) {
			audio.stopSound();
			audio = null;
		}
	}

	@Override
	public void invalidate() {

		super.invalidate();

		if(audio != null) {
			audio.stopSound();
			audio = null;
		}
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
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
		return type == this.tank.getTankType() ? tank.getMaxFill() : 0;
	}

	@Override
	public int getFluidFill(FluidType type) {
		return type == this.tank.getTankType() ? tank.getFill() : 0;
	}

	@Override
	public void setFluidFill(int i, FluidType type) {
		if(type == tank.getTankType())
			tank.setFill(i);
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

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] { tank };
	}
}
