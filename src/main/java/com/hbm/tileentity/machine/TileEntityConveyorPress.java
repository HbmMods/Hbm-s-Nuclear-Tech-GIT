package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.entity.item.EntityMovingItem;
import com.hbm.inventory.recipes.PressRecipes;
import com.hbm.items.machine.ItemStamp;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.BufferUtil;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energymk2.IEnergyReceiverMK2;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityConveyorPress extends TileEntityMachineBase implements IEnergyReceiverMK2 {

	public int usage = 100;
	public long power = 0;
	public final static long maxPower = 50000;

	public double speed = 0.125;
	public double press;
	public double renderPress;
	public double lastPress;
	private double syncPress;
	private int turnProgress;
	protected boolean isRetracting = false;
	private int delay;

	public ItemStack syncStack;

	public TileEntityConveyorPress() {
		super(1);
	}

	@Override
	public String getName() {
		return "";
	}

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {

			this.updateConnections();

			if(delay <= 0) {

				if(isRetracting) {

					if(this.canRetract()) {
						this.press -= speed;
						this.power -= this.usage;

						if(press <= 0) {
							press = 0;
							this.isRetracting = false;
							delay = 0;
						}
					}

				} else {

					if(this.canExtend()) {
						this.press += speed;
						this.power -= this.usage;

						if(press >= 1) {
							press = 1;
							this.isRetracting = true;
							delay = 5;
							this.process();
						}
					}
				}

			} else {
				delay--;
			}

			this.networkPackNT(50);
		} else {

			// approach-based interpolation, GO!
			this.lastPress = this.renderPress;

			if(this.turnProgress > 0) {
				this.renderPress = this.renderPress + ((this.syncPress - this.renderPress) / (double) this.turnProgress);
				--this.turnProgress;
			} else {
				this.renderPress = this.syncPress;
			}
		}
	}

	protected void updateConnections() {
		for(DirPos pos : getConPos()) this.trySubscribe(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
	}

	protected DirPos[] getConPos() {
		return new DirPos[] {
				new DirPos(xCoord + 1, yCoord, zCoord, Library.POS_X),
				new DirPos(xCoord - 1, yCoord, zCoord, Library.NEG_X),
				new DirPos(xCoord, yCoord, zCoord + 1, Library.POS_Z),
				new DirPos(xCoord, yCoord, zCoord - 1, Library.NEG_Z),
		};
	}

	public boolean canExtend() {

		if(this.power < usage) return false;
		if(slots[0] == null) return false;

		List<EntityMovingItem> items = worldObj.getEntitiesWithinAABB(EntityMovingItem.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord + 1, zCoord, xCoord + 1, yCoord + 1.5, zCoord + 1));
		if(items.isEmpty()) return false;

		for(EntityMovingItem item : items) {
			ItemStack stack = item.getItemStack();
			if(PressRecipes.getOutput(stack, slots[0]) != null && stack.stackSize == 1) {

				double d0 = 0.35;
				double d1 = 0.65;
				if(item.posX > xCoord + d0 && item.posX < xCoord + d1 && item.posZ > zCoord + d0 && item.posZ < zCoord + d1) {
					item.setPosition(xCoord + 0.5, item.posY, zCoord + 0.5);
				}

				return true;
			}
		}

		return false;
	}

	public void process() {

		List<EntityMovingItem> items = worldObj.getEntitiesWithinAABB(EntityMovingItem.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord + 1, zCoord, xCoord + 1, yCoord + 1.5, zCoord + 1));

		for(EntityMovingItem item : items) {
			ItemStack stack = item.getItemStack();
			ItemStack output = PressRecipes.getOutput(stack, slots[0]);

			if(output != null && stack.stackSize == 1) {
				item.setDead();
				EntityMovingItem out = new EntityMovingItem(worldObj);
				out.setPosition(item.posX, item.posY, item.posZ);
				out.setItemStack(output.copy());
				worldObj.spawnEntityInWorld(out);
			}
		}

		this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.pressOperate", getVolume(1.5F), 1.0F);

		if(slots[0].getMaxDamage() != 0) {
			slots[0].setItemDamage(slots[0].getItemDamage() + 1);
			if(slots[0].getItemDamage() >= slots[0].getMaxDamage()) {
				slots[0] = null;
			}
		}
	}

	public boolean canRetract() {
		return this.power >= usage;
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);

		buf.writeLong(this.power);
		buf.writeDouble(this.press);
		BufferUtil.writeItemStack(buf, slots[0]);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);

		this.power = buf.readLong();
		this.syncPress = buf.readDouble();
		this.syncStack = BufferUtil.readItemStack(buf);

		this.turnProgress = 2;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return stack.getItem() instanceof ItemStamp;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 0 };
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return this.isItemValidForSlot(i, itemStack);
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
	public void setPower(long power) {
		this.power = power;
	}

	@Override
	public boolean canConnect(ForgeDirection dir) {
		return dir != ForgeDirection.DOWN;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.power = nbt.getLong("power");
		this.press = nbt.getDouble("press");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("power", power);
		nbt.setDouble("press", press);
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
					yCoord + 3,
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
}
