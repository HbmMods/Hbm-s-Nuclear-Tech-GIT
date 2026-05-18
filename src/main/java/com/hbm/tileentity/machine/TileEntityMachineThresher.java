package com.hbm.tileentity.machine;

import java.util.ArrayList;

import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.tileentity.IBufPacketReceiver;
import com.hbm.tileentity.IFluidCopiable;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.fluidmk2.IFluidStandardReceiverMK2;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineThresher extends TileEntityLoadedBase implements IBufPacketReceiver, IFluidStandardReceiverMK2, IFluidCopiable {

	public FluidTank tank;

	public boolean isOn;
	public boolean isSuspended;

	private int turnProgress;
	public float syncAngle;
	public float angle;
	public float prevAngle;

	// 0: waiting, 1: extending, 2: retracting
	private int state = 0;

	public float spin;
	public float lastSpin;

	public TileEntityMachineThresher() {
		this.tank = new FluidTank(Fluids.WOODOIL, 100);
	}

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {

			networkPackNT(100);
			
		} else {

			this.lastSpin = this.spin;

			if(isOn && !isSuspended) {
				this.spin += 15F;

				Vec3 vec = Vec3.createVectorHelper(0.625, 0, 1.625);
				vec.rotateAroundY(-(float) Math.toRadians(angle));

				worldObj.spawnParticle("smoke", xCoord + 0.5 + vec.xCoord, yCoord + 2.0625, zCoord + 0.5 + vec.zCoord, 0, 0, 0);
			}

			if(this.spin >= 360F) {
				this.spin -= 360F;
				this.lastSpin -= 360F;
			}

			this.prevAngle = this.angle;

			if(this.turnProgress > 0) {
				double d0 = MathHelper.wrapAngleTo180_double(this.syncAngle - (double) this.angle);
				this.angle = (float) ((double) this.angle + d0 / (double) this.turnProgress);
				--this.turnProgress;
			} else {
				this.angle = this.syncAngle;
			}
		}
	}

	public static boolean shouldIgnore(World world, int x, int y, int z, Block b, int meta) {

		if((b instanceof IGrowable)) {
			return ((IGrowable) b).func_149851_a(world, x, y, z, world.isRemote);
		}
		return false;
	}

	protected void cutCrop(int x, int y, int z) {

		Block soil = worldObj.getBlock(x, y - 1, z);

		Block b = worldObj.getBlock(x, y, z);
		int meta = worldObj.getBlockMetadata(x, y, z);

		worldObj.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(b) + (meta << 12));

		Block replacementBlock = Blocks.air;
		int replacementMeta = 0;

		if (!worldObj.isRemote && !worldObj.restoringBlockSnapshots) {
			ArrayList<ItemStack> drops = b.getDrops(worldObj, x, y, z, meta, 0);
			boolean replanted = false;

			for (ItemStack drop : drops) {
				if (!replanted && drop.getItem() instanceof IPlantable) {
					IPlantable seed = (IPlantable) drop.getItem();

					if(soil.canSustainPlant(worldObj, x, y - 1, z, ForgeDirection.UP, seed)) {
						replacementBlock = seed.getPlant(worldObj, x, y, z);
						replacementMeta = seed.getPlantMetadata(worldObj, x, y, z);
						replanted = true;
						drop.stackSize -= 1;
					}
				}

				float delta = 0.7F;
				double dx = (double)(worldObj.rand.nextFloat() * delta) + (double)(1.0F - delta) * 0.5D;
				double dy = (double)(worldObj.rand.nextFloat() * delta) + (double)(1.0F - delta) * 0.5D;
				double dz = (double)(worldObj.rand.nextFloat() * delta) + (double)(1.0F - delta) * 0.5D;

				EntityItem entityItem = new EntityItem(worldObj, x + dx, y + dy, z + dz, drop);
				entityItem.delayBeforeCanPickup = 10;
				worldObj.spawnEntityInWorld(entityItem);
			}

			// Apparently, until 1.14 full-grown wheat could sometimes drop no seeds at all
			// This is a quick and dirty workaround for that.
			if (b == Blocks.wheat && !replanted) {
				replacementBlock = b;
				replacementMeta = 0;
				replanted = true;
			}
		}

		worldObj.setBlock(x, y, z, replacementBlock, replacementMeta, 3);
	}

	@Override
	public void serialize(ByteBuf buf) {
		buf.writeBoolean(this.isOn);
		buf.writeBoolean(this.isSuspended);
		buf.writeFloat(this.angle);
		this.tank.serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		this.isOn = buf.readBoolean();
		this.isSuspended = buf.readBoolean();
		this.syncAngle = buf.readFloat();
		this.turnProgress = 3; //use 3-ply for extra smoothness
		this.tank.deserialize(buf);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.isOn = nbt.getBoolean("isOn");
		this.isSuspended = nbt.getBoolean("isSuspended");
		this.angle = nbt.getFloat("angle");
		this.state = nbt.getInteger("state");
		this.tank.readFromNBT(nbt, "t");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("isOn", this.isOn);
		nbt.setBoolean("isSuspended", this.isSuspended);
		nbt.setFloat("angle", this.angle);
		nbt.setInteger("state", this.state);
		tank.writeToNBT(nbt, "t");
	}

	@Override public FluidTank[] getAllTanks() { return new FluidTank[] {tank}; }
	@Override public FluidTank[] getReceivingTanks() { return new FluidTank[] {tank}; }

	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {

		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 10,
					yCoord,
					zCoord - 10,
					xCoord + 11,
					yCoord + 7,
					zCoord + 11
					);
		}

		return bb;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
	
	@Override public FluidTank getTankToPaste() { return tank; }
}
