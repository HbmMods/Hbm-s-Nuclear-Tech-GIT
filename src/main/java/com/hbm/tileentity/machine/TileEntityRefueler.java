package com.hbm.tileentity.machine;

import java.util.List;
import java.util.Random;

import com.hbm.handler.ArmorModHandler;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.util.BobMathUtil;

import api.hbm.fluid.IFillableItem;
import api.hbm.fluid.IFluidStandardReceiver;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityRefueler extends TileEntityLoadedBase implements IFluidStandardReceiver {

	public double fillLevel;
	public double prevFillLevel;

	private boolean isOperating = false;
	private int operatingTime;

	public FluidTank tank;

	public TileEntityRefueler() {
		super();
		tank = new FluidTank(Fluids.KEROSENE, 100);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateEntity() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata()).getOpposite();
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		if(!worldObj.isRemote) {
			trySubscribe(tank.getTankType(), worldObj, xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ, dir);

			isOperating = false;

			List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(xCoord + 0.5, yCoord, zCoord + 0.5, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5).expand(0.5, 0.0, 0.5));

			for(EntityPlayer player : players) {
				for(int i = 0; i < 5; i++) {
					
					ItemStack stack = player.getEquipmentInSlot(i);
					if(stack == null) continue;
					
					if(fillFillable(stack)) {
						isOperating = true;
					}

					if(stack.getItem() instanceof ItemArmor && ArmorModHandler.hasMods(stack)) {
						for(ItemStack mod : ArmorModHandler.pryMods(stack)) {
							if(mod == null) continue;

							if(fillFillable(mod)) {
								ArmorModHandler.applyMod(stack, mod);
								isOperating = true;
							}
						}
					}
				}
			}

			if(isOperating) {
				if(operatingTime % 20 == 0)
					worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "random.fizz", 0.2F, 0.5F);

				operatingTime++;
			} else {
				operatingTime = 0;
			}

			networkPackNT(150);
		} else {
			if(isOperating) {
				Random rand = worldObj.rand;

				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "fluidfill");
				data.setInteger("color", tank.getTankType().getColor());
				data.setDouble("posX", xCoord + 0.5 + rand.nextDouble() * 0.0625 + dir.offsetX * 0.5 + rot.offsetX * 0.25);
				data.setDouble("posZ", zCoord + 0.5 + rand.nextDouble() * 0.0625 + dir.offsetZ * 0.5 + rot.offsetZ * 0.25);
				data.setDouble("posY", yCoord + 0.375);
				data.setDouble("mX", -dir.offsetX + rand.nextGaussian() * 0.1);
				data.setDouble("mZ", -dir.offsetZ + rand.nextGaussian() * 0.1);
				data.setDouble("mY", 0D);
				
				MainRegistry.proxy.effectNT(data);
			}

			prevFillLevel = fillLevel;

			double targetFill = (double)tank.getFill() / (double)tank.getMaxFill();
			fillLevel = BobMathUtil.interp(fillLevel, targetFill, targetFill > fillLevel || !isOperating ? 0.1F : 0.01F);
		}


	}

	private boolean fillFillable(ItemStack stack) {
		if(stack.getItem() instanceof IFillableItem) {
			IFillableItem fillable = (IFillableItem) stack.getItem();
			if(fillable.acceptsFluid(tank.getTankType(), stack)) {
				int prevFill = tank.getFill();
				tank.setFill(fillable.tryFill(tank.getTankType(), tank.getFill(), stack));
				return tank.getFill() < prevFill;
			}
		}

		return false;
	}

	@Override
	public void serialize(ByteBuf buf) {
		buf.writeBoolean(isOperating);
		tank.serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		isOperating = buf.readBoolean();
		tank.deserialize(buf);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		tank.readFromNBT(nbt, "t");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		tank.writeToNBT(nbt, "t");
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { tank };
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] { tank };
	}
	
}
