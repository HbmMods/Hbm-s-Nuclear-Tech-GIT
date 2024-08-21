package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.inventory.UpgradeManager;
import com.hbm.inventory.container.ContainerMachineTurbofan;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Combustible;
import com.hbm.inventory.fluid.trait.FT_Combustible.FuelGrade;
import com.hbm.inventory.fluid.trait.FluidTrait;
import com.hbm.inventory.gui.GUIMachineTurbofan;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.IUpgradeInfoProvider;
import com.hbm.tileentity.TileEntityMachinePolluting;
import com.hbm.util.CompatEnergyControl;
import com.hbm.util.I18nUtil;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energymk2.IEnergyProviderMK2;
import api.hbm.fluid.IFluidStandardTransceiver;
import api.hbm.tile.IInfoProviderEC;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineTurbofan extends TileEntityMachinePolluting implements IEnergyProviderMK2, IFluidStandardTransceiver, IGUIProvider, IUpgradeInfoProvider, IInfoProviderEC {

	public long power;
	public static final long maxPower = 1_000_000;
	public FluidTank tank;
	public FluidTank blood;
	
	public int afterburner;
	public boolean wasOn;
	public boolean showBlood = false;
	protected int output;
	protected int consumption;

	public float spin;
	public float lastSpin;
	public int momentum = 0;
	
	private AudioWrapper audio;

	public TileEntityMachineTurbofan() {
		super(5, 150);
		tank = new FluidTank(Fluids.KEROSENE, 24000);
		blood = new FluidTank(Fluids.BLOOD, 24000);
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
		blood.readFromNBT(nbt, "blood");
		this.showBlood = nbt.getBoolean("showBlood");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("powerTime", power);
		tank.writeToNBT(nbt, "fuel");
		blood.writeToNBT(nbt, "blood");
		nbt.setBoolean("showBlood", showBlood);
	}

	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}
	
	protected DirPos[] getConPos() {
		
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10).getRotation(ForgeDirection.UP);
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
			
			this.output = 0;
			this.consumption = 0;
			
			//meta below 12 means that it's an old multiblock configuration
			if(this.getBlockMetadata() < 12) {
				//get old direction
				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata());
				//remove tile from the world to prevent inventory dropping
				worldObj.removeTileEntity(xCoord, yCoord, zCoord);
				//use fillspace to create a new multiblock configuration
				worldObj.setBlock(xCoord, yCoord, zCoord, ModBlocks.machine_turbofan, dir.ordinal() + 10, 3);
				MultiblockHandlerXR.fillSpace(worldObj, xCoord, yCoord, zCoord, ((BlockDummyable) ModBlocks.machine_turbofan).getDimensions(), ModBlocks.machine_turbofan, dir);
				//restore connections
				ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
				((BlockDummyable) ModBlocks.machine_turbofan).makeExtra(worldObj, xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ);
				((BlockDummyable) ModBlocks.machine_turbofan).makeExtra(worldObj, xCoord + dir.offsetX - rot.offsetX, yCoord, zCoord + dir.offsetZ - rot.offsetZ);
				((BlockDummyable) ModBlocks.machine_turbofan).makeExtra(worldObj, xCoord - dir.offsetX, yCoord, zCoord - dir.offsetZ);
				((BlockDummyable) ModBlocks.machine_turbofan).makeExtra(worldObj, xCoord - dir.offsetX - rot.offsetX, yCoord, zCoord - dir.offsetZ - rot.offsetZ);
				//load the tile data to restore the old values
				NBTTagCompound data = new NBTTagCompound();
				this.writeToNBT(data);
				worldObj.getTileEntity(xCoord, yCoord, zCoord).readFromNBT(data);
				return;
			}
			
			tank.setType(4, slots);
			tank.loadTank(0, 1, slots);
			blood.setTankType(Fluids.BLOOD);
			
			this.wasOn = false;
			
			UpgradeManager.eval(slots, 2, 2);
			this.afterburner = UpgradeManager.getLevel(UpgradeType.AFTERBURN);
			
			if(slots[2] != null && slots[2].getItem() == ModItems.flame_pony)
				this.afterburner = 100;
			
			long burnValue = 0;
			int amount = 1 + this.afterburner;
			
			if(tank.getTankType().hasTrait(FT_Combustible.class) && tank.getTankType().getTrait(FT_Combustible.class).getGrade() == FuelGrade.AERO) {
				burnValue = tank.getTankType().getTrait(FT_Combustible.class).getCombustionEnergy() / 1_000;
			}
			
			int amountToBurn = Math.min(amount, this.tank.getFill());
			
			if(amountToBurn > 0) {
				this.wasOn = true;
				this.tank.setFill(this.tank.getFill() - amountToBurn);
				this.output = (int) (burnValue * amountToBurn * (1 + Math.min(this.afterburner / 3D, 4)));
				this.power += this.output;
				this.consumption = amountToBurn;
				
				if(worldObj.getTotalWorldTime() % 20 == 0) super.pollute(tank.getTankType(), FluidTrait.FluidReleaseType.BURN, amountToBurn * 5);;
			}
			
			power = Library.chargeItemsFromTE(slots, 3, power, power);
			
			for(DirPos pos : getConPos()) {
				this.tryProvide(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				this.trySubscribe(tank.getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				if(this.blood.getFill() > 0) this.sendFluid(blood, worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				this.sendSmoke(pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}
			
			if(burnValue > 0 && amountToBurn > 0) {

				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10).getRotation(ForgeDirection.UP);
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
					
					/*if(this.afterburner > 90 && worldObj.rand.nextInt(60) == 0) {
						worldObj.newExplosion(null, xCoord + 0.5 + dir.offsetX * 3.5, yCoord + 0.5, zCoord + 0.5 + dir.offsetZ * 3.5, 3F, false, false);
					}*/
					
					if(this.afterburner > 90 && worldObj.rand.nextInt(30) == 0) {
						worldObj.playSoundEffect(xCoord + 0.5, yCoord + 1.5, zCoord + 0.5, "hbm:block.damage", 3.0F, 0.95F + worldObj.rand.nextFloat() * 0.2F);
					}
					
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
						
						blood.setFill(blood.getFill() + 50); 
						if(blood.getFill() > blood.getMaxFill()) {
							blood.setFill(blood.getMaxFill());
						}
						this.showBlood = true;
					}
				}
			}
			
			if(this.power > this.maxPower) {
				this.power = this.maxPower;
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			data.setByte("after", (byte) afterburner);
			data.setBoolean("wasOn", wasOn);
			data.setBoolean("showBlood", showBlood);
			tank.writeToNBT(data, "tank");
			blood.writeToNBT(data, "blood");
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

				audio.keepAlive();
				audio.updateVolume(getVolume(momentum / 50F));
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
				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10).getRotation(ForgeDirection.UP);
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
		super.networkUnpack(nbt);
		
		this.power = nbt.getLong("power");
		this.afterburner = nbt.getByte("after");
		this.wasOn = nbt.getBoolean("wasOn");
		this.showBlood = nbt.getBoolean("showBlood");
		tank.readFromNBT(nbt, "tank");
		blood.readFromNBT(nbt, "blood");
	}
	
	public AudioWrapper createAudioLoop() {
		return MainRegistry.proxy.getLoopedSound("hbm:block.turbofanOperate", xCoord, yCoord, zCoord, 1.0F, 50F, 1.0F, 20);
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
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] { tank };
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] { blood };
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { tank, blood, smoke, smoke_leaded, smoke_poison };
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineTurbofan(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineTurbofan(player.inventory, this);
	}

	@Override
	public boolean canProvideInfo(UpgradeType type, int level, boolean extendedInfo) {
		return type == UpgradeType.AFTERBURN;
	}

	@Override
	public void provideInfo(UpgradeType type, int level, List<String> info, boolean extendedInfo) {
		info.add(IUpgradeInfoProvider.getStandardLabel(ModBlocks.machine_turbofan));
		if(type == UpgradeType.AFTERBURN) {
			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(this.KEY_EFFICIENCY, "+" + (int)(level * 100 * (1 + Math.min(level / 3D, 4D))) + "%"));
			info.add(EnumChatFormatting.RED + I18nUtil.resolveKey(this.KEY_CONSUMPTION, "+" + (level * 100) + "%"));
		}
	}

	@Override
	public int getMaxLevel(UpgradeType type) {
		if(type == UpgradeType.AFTERBURN) return 3;
		return 0;
	}

	@Override
	public void provideExtraInfo(NBTTagCompound data) {
		data.setBoolean(CompatEnergyControl.B_ACTIVE, this.output > 0);
		data.setDouble(CompatEnergyControl.D_CONSUMPTION_MB, this.consumption);
		data.setDouble(CompatEnergyControl.D_OUTPUT_HE, this.output);
	}
}
