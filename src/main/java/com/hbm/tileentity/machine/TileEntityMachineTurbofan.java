package com.hbm.tileentity.machine;

import java.util.List;
import java.util.Random;

import com.hbm.entity.particle.EntitySSmokeFX;
import com.hbm.entity.particle.EntityTSmokeFX;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.interfaces.Spaghetti;
import com.hbm.inventory.FluidTank;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.LoopedSoundPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TETurbofanPacket;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.energy.IEnergyGenerator;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

@Spaghetti("a")
public class TileEntityMachineTurbofan extends TileEntityLoadedBase implements ISidedInventory, IEnergyGenerator, IFluidContainer, IFluidAcceptor {

	private ItemStack slots[];

	public long power;
	public int soundCycle = 0;
	public static final long maxPower = 150000;
	public FluidTank tank;
	Random rand = new Random();
	public int afterburner;
	public boolean isRunning;
	public int spin;

	private static final int[] slots_top = new int[] { 0 };
	private static final int[] slots_bottom = new int[] { 0, 0 };
	private static final int[] slots_side = new int[] { 0 };

	private String customName;

	public TileEntityMachineTurbofan() {
		slots = new ItemStack[3];
		tank = new FluidTank(Fluids.KEROSENE, 64000, 0);
	}

	@Override
	public int getSizeInventory() {
		return slots.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return slots[i];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if (slots[i] != null) {
			ItemStack itemStack = slots[i];
			slots[i] = null;
			return itemStack;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemStack) {
		slots[i] = itemStack;
		if (itemStack != null && itemStack.stackSize > getInventoryStackLimit()) {
			itemStack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.machineTurbofan";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
	}

	public void setCustomName(String name) {
		this.customName = name;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		if (worldObj.getTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		} else {
			return player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64;
		}
	}

	// You scrubs aren't needed for anything (right now)
	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return false;
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (slots[i] != null) {
			if (slots[i].stackSize <= j) {
				ItemStack itemStack = slots[i];
				slots[i] = null;
				return itemStack;
			}
			ItemStack itemStack1 = slots[i].splitStack(j);
			if (slots[i].stackSize == 0) {
				slots[i] = null;
			}

			return itemStack1;
		} else {
			return null;
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("items", 10);

		this.power = nbt.getLong("powerTime");
		tank.readFromNBT(nbt, "fuel");
		slots = new ItemStack[getSizeInventory()];

		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("slot");
			if (b0 >= 0 && b0 < slots.length) {
				slots[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("powerTime", power);
		tank.writeToNBT(nbt, "fuel");
		NBTTagList list = new NBTTagList();

		for (int i = 0; i < slots.length; i++) {
			if (slots[i] != null) {
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("slot", (byte) i);
				slots[i].writeToNBT(nbt1);
				list.appendTag(nbt1);
			}
		}
		nbt.setTag("items", list);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return p_94128_1_ == 0 ? slots_bottom : (p_94128_1_ == 1 ? slots_top : slots_side);
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return this.isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return false;
	}

	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}

	@Spaghetti("HOOOOUUUGH")
	@Override
	public void updateEntity() {
		
		int nrg = 1250;
		int cnsp = 1;
		
		afterburner = 0;
		if(slots[2] != null) {
			if(slots[2].getItem() == ModItems.upgrade_afterburn_1) {
				nrg *= 2;
				cnsp *= 2.5;
				afterburner = 1;
			}
			if(slots[2].getItem() == ModItems.upgrade_afterburn_2) {
				nrg *= 3;
				cnsp *= 5;
				afterburner = 2;
			}
			if(slots[2].getItem() == ModItems.upgrade_afterburn_3) {
				nrg *= 4;
				cnsp *= 7.5;
				afterburner = 3;
			}
		}
		
		if (!worldObj.isRemote) {

			/*this.sendPower(worldObj, this.xCoord + 2, this.yCoord + 1, this.zCoord - 1, Library.POS_X);
			this.sendPower(worldObj, this.xCoord + 2, this.yCoord + 1, this.zCoord + 1, Library.POS_X);
			this.sendPower(worldObj, this.xCoord + 1, this.yCoord + 1, this.zCoord + 2, Library.POS_Z);
			this.sendPower(worldObj, this.xCoord - 1, this.yCoord + 1, this.zCoord + 2, Library.POS_Z);
			this.sendPower(worldObj, this.xCoord - 2, this.yCoord + 1, this.zCoord + 1, Library.NEG_X);
			this.sendPower(worldObj, this.xCoord - 2, this.yCoord + 1, this.zCoord - 1, Library.NEG_X);
			this.sendPower(worldObj, this.xCoord - 1, this.yCoord + 1, this.zCoord - 2, Library.NEG_Z);
			this.sendPower(worldObj, this.xCoord + 1, this.yCoord + 1, this.zCoord - 2, Library.NEG_Z);*/
			
			ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata());
			ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);

			this.sendPower(worldObj, this.xCoord + rot.offsetX * 2, this.yCoord, this.zCoord + rot.offsetZ * 2, rot);
			this.sendPower(worldObj, this.xCoord + rot.offsetX * 2 - dir.offsetX, this.yCoord, this.zCoord + rot.offsetZ * 2 - dir.offsetZ, rot);
			this.sendPower(worldObj, this.xCoord - rot.offsetX * 2, this.yCoord, this.zCoord - rot.offsetZ * 2, rot.getOpposite());
			this.sendPower(worldObj, this.xCoord - rot.offsetX * 2 - dir.offsetX, this.yCoord, this.zCoord - rot.offsetZ * 2 - dir.offsetZ, rot.getOpposite());

			//Tank Management
			tank.loadTank(0, 1, slots);
			tank.updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			
			isRunning = false;
				
			if(tank.getFill() >= cnsp) {
				tank.setFill(tank.getFill() - cnsp);
				power += nrg;

				isRunning = true;
				
				spin += 20;
				spin = spin % 360;
				
				if(power > maxPower)
					power = maxPower;
				
				int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);

				double posX = xCoord + 0.5;
				double posY = yCoord;
				double posZ = zCoord + 0.5;

				if(meta == 2) {
					if(afterburner == 0 && rand.nextInt(3) == 0) {
						EntityTSmokeFX smoke = new EntityTSmokeFX(worldObj);
						smoke.posX = xCoord + 0.5 + (rand.nextGaussian() * 0.5);
						smoke.posY = yCoord + 1.5 + (rand.nextGaussian() * 0.5);
						smoke.posZ = zCoord + 3.25;
						smoke.motionX = rand.nextGaussian() * 0.3;
						smoke.motionY = rand.nextGaussian() * 0.3;
						smoke.motionZ = 2.5 + (rand.nextFloat() * 3.5);
						if(!worldObj.isRemote)
							worldObj.spawnEntityInWorld(smoke);
					}
					
					for(int i = 0; i < afterburner * 5; i++)
						if(afterburner > 0 && rand.nextInt(2) == 0) {
							EntitySSmokeFX smoke = new EntitySSmokeFX(worldObj);
							smoke.posX = xCoord + 0.5 + (rand.nextGaussian() * 0.5);
							smoke.posY = yCoord + 1.5 + (rand.nextGaussian() * 0.5);
							smoke.posZ = zCoord + 3.25;
							smoke.motionX = rand.nextGaussian() * 0.3;
							smoke.motionY = rand.nextGaussian() * 0.3;
							smoke.motionZ = 2.5 + (rand.nextFloat() * 3.5);
							if(!worldObj.isRemote)
								worldObj.spawnEntityInWorld(smoke);
						}
					
					//Exhaust push
					List<Entity> list = (List<Entity>)worldObj.getEntitiesWithinAABBExcludingEntity(null, 
							AxisAlignedBB.getBoundingBox(posX - 1.5, posY, posZ + 3.5, posX + 1.5, posY + 3, posZ + 12));
					
					for(Entity e : list) {
						e.motionZ += 0.5;
						if(afterburner > 0)
							e.setFire(3 * afterburner);
					}
					
					//Intake pull
					list = (List<Entity>)worldObj.getEntitiesWithinAABBExcludingEntity(null, 
							AxisAlignedBB.getBoundingBox(posX - 1.5, posY, posZ - 12, posX + 1.5, posY + 3, posZ - 3.5));
					
					for(Entity e : list) {
						e.motionZ += 0.5;
					}
					
					//Intake kill
					list = (List<Entity>)worldObj.getEntitiesWithinAABBExcludingEntity(null, 
							AxisAlignedBB.getBoundingBox(posX - 1.5, posY, posZ - 4.5, posX + 1.5, posY + 3, posZ - 3.5));
					
					for(Entity e : list) {
						e.attackEntityFrom(ModDamageSource.turbofan, 1000);
					}
				}
				if(meta == 3) {
					if(afterburner == 0 && rand.nextInt(3) == 0) {
						EntityTSmokeFX smoke = new EntityTSmokeFX(worldObj);
						smoke.posX = xCoord + 0.5 + (rand.nextGaussian() * 0.5);
						smoke.posY = yCoord + 1.5 + (rand.nextGaussian() * 0.5);
						smoke.posZ = zCoord - 3.25;
						smoke.motionX = rand.nextGaussian() * 0.3;
						smoke.motionY = rand.nextGaussian() * 0.3;
						smoke.motionZ = -2.5 - (rand.nextFloat() * 3.5);
						if(!worldObj.isRemote)
							worldObj.spawnEntityInWorld(smoke);
					}

					for(int i = 0; i < afterburner * 5; i++)
						if(afterburner > 0 && rand.nextInt(2) == 0) {
							EntitySSmokeFX smoke = new EntitySSmokeFX(worldObj);
							smoke.posX = xCoord + 0.5 + (rand.nextGaussian() * 0.5);
							smoke.posY = yCoord + 1.5 + (rand.nextGaussian() * 0.5);
							smoke.posZ = zCoord - 3.25;
							smoke.motionX = rand.nextGaussian() * 0.3;
							smoke.motionY = rand.nextGaussian() * 0.3;
							smoke.motionZ = -2.5 - (rand.nextFloat() * 3.5);
							if(!worldObj.isRemote)
								worldObj.spawnEntityInWorld(smoke);
						}

					//Exhaust push
					List<Entity> list = (List<Entity>)worldObj.getEntitiesWithinAABBExcludingEntity(null, 
							AxisAlignedBB.getBoundingBox(posX - 1.5, posY, posZ - 12, posX + 1.5, posY + 3, posZ - 3.5));
					
					for(Entity e : list) {
						e.motionZ -= 0.5;
						if(afterburner > 0)
							e.setFire(3 * afterburner);
					}

					//Intake pull
					list = (List<Entity>)worldObj.getEntitiesWithinAABBExcludingEntity(null, 
							AxisAlignedBB.getBoundingBox(posX - 1.5, posY, posZ + 3.5, posX + 1.5, posY + 3, posZ + 12));
					
					for(Entity e : list) {
						e.motionZ -= 0.5;
					}

					//Intake kill
					list = (List<Entity>)worldObj.getEntitiesWithinAABBExcludingEntity(null, 
							AxisAlignedBB.getBoundingBox(posX - 1.5, posY, posZ + 3.5, posX + 1.5, posY + 3, posZ + 4.5));
					
					for(Entity e : list) {
						e.attackEntityFrom(ModDamageSource.turbofan, 1000);
					}
				}
				if(meta == 4) {
					if(afterburner == 0 && rand.nextInt(3) == 0) {
						EntityTSmokeFX smoke = new EntityTSmokeFX(worldObj);
						smoke.posX = xCoord + 3.25;
						smoke.posY = yCoord + 1.5 + (rand.nextGaussian() * 0.5);
						smoke.posZ = zCoord + 0.5 + (rand.nextGaussian() * 0.5);
						smoke.motionX = 2.5 + (rand.nextFloat() * 3.5);
						smoke.motionY = rand.nextGaussian() * 0.3;
						smoke.motionZ = rand.nextGaussian() * 0.3;
						if(!worldObj.isRemote)
							worldObj.spawnEntityInWorld(smoke);
					}

					for(int i = 0; i < afterburner * 5; i++)
						if(afterburner > 0 && rand.nextInt(2) == 0) {
							EntitySSmokeFX smoke = new EntitySSmokeFX(worldObj);
							smoke.posX = xCoord + 3.25;
							smoke.posY = yCoord + 1.5 + (rand.nextGaussian() * 0.5);
							smoke.posZ = zCoord + 0.5 + (rand.nextGaussian() * 0.5);
							smoke.motionX = 2.5 + (rand.nextFloat() * 3.5);
							smoke.motionY = rand.nextGaussian() * 0.3;
							smoke.motionZ = rand.nextGaussian() * 0.3;
							if(!worldObj.isRemote)
								worldObj.spawnEntityInWorld(smoke);
						}
					
					//Exhaust push
					List<Entity> list = (List<Entity>)worldObj.getEntitiesWithinAABBExcludingEntity(null, 
							AxisAlignedBB.getBoundingBox(posX + 3.5, posY, posZ - 1.5, posX + 12, posY + 3, posZ + 1.5));
					
					for(Entity e : list) {
						e.motionX += 0.5;
						if(afterburner > 0)
							e.setFire(3 * afterburner);
					}
					
					//Intake pull
					list = (List<Entity>)worldObj.getEntitiesWithinAABBExcludingEntity(null, 
							AxisAlignedBB.getBoundingBox(posX - 12, posY, posZ - 1.5, posX - 3.5, posY + 3, posZ + 1.5));
					
					for(Entity e : list) {
						e.motionX += 0.5;
					}
					
					//Intake kill
					list = (List<Entity>)worldObj.getEntitiesWithinAABBExcludingEntity(null, 
							AxisAlignedBB.getBoundingBox(posX - 4.5, posY, posZ - 1.5, posX - 3.5, posY + 3, posZ + 1.5));
					
					for(Entity e : list) {
						e.attackEntityFrom(ModDamageSource.turbofan, 1000);
					}
				}
				if(meta == 5) {
					if(afterburner == 0 && rand.nextInt(3) == 0) {
						EntityTSmokeFX smoke = new EntityTSmokeFX(worldObj);
						smoke.posX = xCoord - 3.25;
						smoke.posY = yCoord + 1.5 + (rand.nextGaussian() * 0.5);
						smoke.posZ = zCoord + 0.5 + (rand.nextGaussian() * 0.5);
						smoke.motionX = -2.5 - (rand.nextFloat() * 3.5);
						smoke.motionY = rand.nextGaussian() * 0.3;
						smoke.motionZ = rand.nextGaussian() * 0.3;
						if(!worldObj.isRemote)
							worldObj.spawnEntityInWorld(smoke);
					}

					for(int i = 0; i < afterburner * 5; i++)
						if(afterburner > 0 && rand.nextInt(2) == 0) {
							EntitySSmokeFX smoke = new EntitySSmokeFX(worldObj);
							smoke.posX = xCoord - 3.25;
							smoke.posY = yCoord + 1.5 + (rand.nextGaussian() * 0.5);
							smoke.posZ = zCoord + 0.5 + (rand.nextGaussian() * 0.5);
							smoke.motionX = -2.5 - (rand.nextFloat() * 3.5);
							smoke.motionY = rand.nextGaussian() * 0.3;
							smoke.motionZ = rand.nextGaussian() * 0.3;
							if(!worldObj.isRemote)
								worldObj.spawnEntityInWorld(smoke);
						}
					
					//Exhaust push
					List<Entity> list = (List<Entity>)worldObj.getEntitiesWithinAABBExcludingEntity(null, 
							AxisAlignedBB.getBoundingBox(posX - 12, posY, posZ - 1.5, posX - 3.5, posY + 3, posZ + 1.5));
					
					for(Entity e : list) {
						e.motionX -= 0.5;
						if(afterburner > 0)
							e.setFire(3 * afterburner);
					}
					
					//Intake pull
					list = (List<Entity>)worldObj.getEntitiesWithinAABBExcludingEntity(null, 
							AxisAlignedBB.getBoundingBox(posX + 3.5, posY, posZ - 1.5, posX + 12, posY + 3, posZ + 1.5));
					
					for(Entity e : list) {
						e.motionX -= 0.5;
					}
					
					//Intake kill
					list = (List<Entity>)worldObj.getEntitiesWithinAABBExcludingEntity(null, 
							AxisAlignedBB.getBoundingBox(posX + 3.5, posY, posZ - 1.5, posX + 4.5, posY + 3, posZ + 1.5));
					
					for(Entity e : list) {
						e.attackEntityFrom(ModDamageSource.turbofan, 1000);
					}
				}
			}
		}
		
		if(!worldObj.isRemote) {
			PacketDispatcher.wrapper.sendToAllAround(new TETurbofanPacket(xCoord, yCoord, zCoord, spin, isRunning), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 150));
			PacketDispatcher.wrapper.sendToAllAround(new LoopedSoundPacket(xCoord, yCoord, zCoord), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(xCoord, yCoord, zCoord, power), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
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
}
