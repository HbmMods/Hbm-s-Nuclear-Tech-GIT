package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.items.ItemEnums.EnumAshType;
import com.hbm.module.ModuleBurnTime;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachinePolluting;
import com.hbm.util.ItemStackUtil;

import api.hbm.fluid.IFluidStandardSender;
import api.hbm.tile.IHeatSource;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEntityFireboxBase extends TileEntityMachinePolluting implements IFluidStandardSender, IGUIProvider, IHeatSource {

	public int maxBurnTime;
	public int burnTime;
	public int burnHeat;
	public boolean wasOn = false;
	private int playersUsing = 0;
	
	public float doorAngle = 0;
	public float prevDoorAngle = 0;

	public int heatEnergy;


	public TileEntityFireboxBase() {
		super(2, 50);
	}
	
	@Override
	public void openInventory() {
		if(!worldObj.isRemote) this.playersUsing++;
	}
	
	@Override
	public void closeInventory() {
		if(!worldObj.isRemote) this.playersUsing--;
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			boolean canOperate = false;

			for(int i = 2; i < 6; i++) {
				ForgeDirection dir = ForgeDirection.getOrientation(i);
				ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
				
				for(int j = -1; j <= 1; j++) {
					this.sendSmoke(xCoord + dir.offsetX * 2 + rot.offsetX * j, yCoord, zCoord + dir.offsetZ * 2 + rot.offsetZ * j, dir);
				}
			}
			
			wasOn = false;
			
			if(burnTime <= 0) {
				canOperate = breatheAir(0);
				
				for(int i = 0; i < 2; i++) {
					if(slots[i] != null) {
						
						int baseTime = getModule().getBurnTime(slots[i]);
						
						if(baseTime > 0) {
							int fuel = (int) (baseTime * getTimeMult());
							
							TileEntity below = worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
							
							if(below instanceof TileEntityAshpit) {
								TileEntityAshpit ashpit = (TileEntityAshpit) below;
								EnumAshType type = getAshFromFuel(slots[i]);
								if(type == EnumAshType.WOOD) ashpit.ashLevelWood += baseTime;
								if(type == EnumAshType.COAL) ashpit.ashLevelCoal += baseTime;
								if(type == EnumAshType.MISC) ashpit.ashLevelMisc += baseTime;
							}
							
							this.maxBurnTime = this.burnTime = fuel;
							this.burnHeat = getModule().getBurnHeat(getBaseHeat(), slots[i]);
							slots[i].stackSize--;

							if(slots[i].stackSize == 0) {
								slots[i] = slots[i].getItem().getContainerItem(slots[i]);
							}

							this.wasOn = true;
							break;
						}
					}
				} 
			} else {
				if(this.heatEnergy < getMaxHeat()) {
					// firebox consumes 1mB every 5 ticks, heating oven every tick
					canOperate = breatheAir(worldObj.getTotalWorldTime() % (500 / getBaseHeat()) == 0 ? 1 : 0);

					if(canOperate) {
						burnTime--;
						if(worldObj.getTotalWorldTime() % 20 == 0) this.pollute(PollutionType.SOOT, PollutionHandler.SOOT_PER_SECOND * 3);
					}
				} else {
					canOperate = breatheAir(0);
				}

				if(canOperate) {
					this.wasOn = true;
					
					if(worldObj.rand.nextInt(15) == 0 && !this.muffled) {
						worldObj.playSoundEffect(xCoord, yCoord, zCoord, "fire.fire", 1.0F, 0.5F + worldObj.rand.nextFloat() * 0.5F);
					}
				}
			}
			
			if(wasOn) {
				this.heatEnergy = Math.min(this.heatEnergy + this.burnHeat, getMaxHeat());
			} else {
				this.heatEnergy = Math.max(this.heatEnergy - Math.max(this.heatEnergy / 1000, 1), 0);
				if(canOperate) this.burnHeat = 0;
			}
			
			this.networkPackNT(50);
		} else {
			this.prevDoorAngle = this.doorAngle;
			float swingSpeed = (doorAngle / 10F) + 3;
			
			if(this.playersUsing > 0) {
				this.doorAngle += swingSpeed;
			} else {
				this.doorAngle -= swingSpeed;
			}
			
			this.doorAngle = MathHelper.clamp_float(this.doorAngle, 0F, 135F);
			
			if(wasOn && worldObj.getTotalWorldTime() % 5 == 0) {
				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
				double x = xCoord + 0.5 + dir.offsetX;
				double y = yCoord + 0.25;
				double z = zCoord + 0.5 + dir.offsetZ;
				worldObj.spawnParticle("flame", x + worldObj.rand.nextDouble() * 0.5 - 0.25, y + worldObj.rand.nextDouble() * 0.25, z + worldObj.rand.nextDouble() * 0.5 - 0.25, 0, 0, 0);
			}
		}
	}
	
	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeInt(maxBurnTime);
		buf.writeInt(burnTime);
		buf.writeInt(burnHeat);
		buf.writeInt(heatEnergy);
		buf.writeInt(playersUsing);
		buf.writeBoolean(wasOn);
	}
	
	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		maxBurnTime = buf.readInt();
		burnTime = buf.readInt();
		burnHeat = buf.readInt();
		heatEnergy = buf.readInt();
		playersUsing = buf.readInt();
		wasOn = buf.readBoolean();
	}
	
	public static EnumAshType getAshFromFuel(ItemStack stack) {

		List<String> names = ItemStackUtil.getOreDictNames(stack);
		
		for(String name : names) {
			if(name.contains("Coke"))		return EnumAshType.COAL;
			if(name.contains("Coal"))		return EnumAshType.COAL;
			if(name.contains("Lignite"))	return EnumAshType.COAL;
			if(name.startsWith("log"))		return EnumAshType.WOOD;
			if(name.contains("Wood"))		return EnumAshType.WOOD;
			if(name.contains("Sapling"))	return EnumAshType.WOOD;
		}

		return EnumAshType.MISC;
	}

	public abstract ModuleBurnTime getModule();
	public abstract int getBaseHeat();
	public abstract double getTimeMult();
	public abstract int getMaxHeat();

	@Override
	public int[] getAccessibleSlotsFromSide(int meta) {
		return new int[] { 0, 1 };
	}
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return getModule().getBurnTime(itemStack) > 0;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.maxBurnTime = nbt.getInteger("maxBurnTime");
		this.burnTime = nbt.getInteger("burnTime");
		this.burnHeat = nbt.getInteger("burnHeat");
		this.heatEnergy = nbt.getInteger("heatEnergy");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setInteger("maxBurnTime", maxBurnTime);
		nbt.setInteger("burnTime", burnTime);
		nbt.setInteger("burnHeat", burnHeat);
		nbt.setInteger("heatEnergy", heatEnergy);
	}

	@Override
	public int getHeatStored() {
		return heatEnergy;
	}

	@Override
	public void useUpHeat(int heat) {
		this.heatEnergy = Math.max(0, this.heatEnergy - heat);
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 1,
					yCoord,
					zCoord - 1,
					xCoord + 2,
					yCoord + 1,
					zCoord + 2
					);
		}
		
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[0];
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return this.getSmokeTanks();
	}
	
	@Override
	public boolean canConnect(FluidType type, ForgeDirection dir) {
		return dir != ForgeDirection.UNKNOWN && dir != ForgeDirection.DOWN;
	}
}
