package com.hbm.blocks.network;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.tileentity.TilePort.PortDef;
import com.hbm.util.EnumUtil;
import com.hbm.world.gen.nbt.INBTBlockTransformable;

import api.hbm.energymk2.IEnergyReceiverMK2.ConnectionPriority;
import api.hbm.fluidmk2.IFluidStandardTransceiverMK2;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

@Deprecated
public class FluidPump extends BlockContainer implements INBTBlockTransformable {

	public FluidPump(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityFluidPump();
	}

	@Override public int getRenderType() { return -1; }
	@Override public boolean isOpaqueCube() { return false; }
	@Override public boolean renderAsNormalBlock() { return false; }

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {

		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if(i == 0) world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		if(i == 1) world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		if(i == 2) world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		if(i == 3) world.setBlockMetadataWithNotify(x, y, z, 4, 2);
	}

	@Override
	public int transformMeta(int meta, int coordBaseMode) {
		return INBTBlockTransformable.transformMetaDeco(meta, coordBaseMode);
	}

	@Deprecated
	public static class TileEntityFluidPump extends TileEntityLoadedBase implements IFluidStandardTransceiverMK2, IControlReceiver {

		public int bufferSize = 100;
		public FluidTank[] tank;
		public ConnectionPriority priority = ConnectionPriority.NORMAL;
		public boolean redstone = false;

		public TileEntityFluidPump() {
			this.tank = new FluidTank[1];
			this.tank[0] = new FluidTank(Fluids.NONE, bufferSize);
		}

		protected PortDef cachedPort;
		public PortDef getPorts() {
			if(cachedPort == null) {
				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata()).getRotation(ForgeDirection.DOWN);
				cachedPort = PortDef.make(xCoord, yCoord, zCoord, dir, dir.getOpposite());
			}
			return cachedPort;
		}

		@Override
		public void updateEntity() {

			if(!worldObj.isRemote) {

				this.setupFluidOutPorts(getSendingTanks(), getPorts());
				this.setupFluidInPorts(getReceivingTanks(), getPorts());
				this.updatePortFIFO();

				// if the capacity were changed directly, any excess buffered fluid would be destroyed
				// when running a closed loop or handling hard to get fluids, that's quite bad
				if(bufferMismatch()) {
					int nextBuffer = Math.max(this.tank[0].getFill(), this.bufferSize);
					this.tank[0].changeTankSize(nextBuffer);
				}

				this.redstone = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);

				this.networkPackNT(15);
			}
		}
		
		public boolean bufferMismatch() {
			return this.bufferSize != this.tank[0].getMaxFill();
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			tank[0].writeToNBT(nbt, "t");
			nbt.setByte("p", (byte) priority.ordinal());
			nbt.setInteger("buffer", bufferSize);
		}

		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			super.readFromNBT(nbt);
			tank[0].readFromNBT(nbt, "t");
			priority = EnumUtil.grabEnumSafely(ConnectionPriority.class, nbt.getByte("p"));
			bufferSize = nbt.getInteger("buffer");
		}

		@Override
		public void serialize(ByteBuf buf) {
			super.serialize(buf);
			tank[0].serialize(buf);
			buf.writeByte((byte) priority.ordinal());
			buf.writeInt(bufferSize);
		}

		@Override
		public void deserialize(ByteBuf buf) {
			super.deserialize(buf);
			tank[0].deserialize(buf);
			priority = EnumUtil.grabEnumSafely(ConnectionPriority.class, buf.readByte());
			bufferSize = buf.readInt();
		}

		@Override public ConnectionPriority getFluidPriority() { return priority; }
		@Override public FluidTank[] getSendingTanks() { return tank; }
		@Override public FluidTank[] getReceivingTanks() { return tank; }
		@Override public FluidTank[] getAllTanks() { return tank; }

		@Override public long getReceiverSpeed(FluidType type, int pressure) { return redstone|| bufferMismatch() ?  0 : 1_000_000_000; }
		@Override public long getProviderSpeed(FluidType type, int pressure) { return redstone ?  0 : 1_000_000_000; }

		@Override
		public boolean hasPermission(EntityPlayer player) {
			return player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 128;
		}

		@Override
		public void receiveControl(EntityPlayer player, NBTTagCompound data) {
			if(data.hasKey("capacity")) {
				this.bufferSize = MathHelper.clamp_int(data.getInteger("capacity"), 0, 10_000);
			}
			if(data.hasKey("pressure")) {
				this.tank[0].withPressure(MathHelper.clamp_int(data.getByte("pressure"), 0, 5));
			}
			if(data.hasKey("priority")) {
				priority = EnumUtil.grabEnumSafely(ConnectionPriority.class, data.getByte("priority"));
			}

			this.markDirty();
		}
	}
}
