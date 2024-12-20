package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Heatable;
import com.hbm.inventory.fluid.trait.FT_Heatable.HeatingStep;
import com.hbm.inventory.fluid.trait.FT_Heatable.HeatingType;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.IBufPacketReceiver;
import com.hbm.tileentity.IFluidCopiable;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineHephaestus extends TileEntityLoadedBase implements IBufPacketReceiver, IFluidStandardTransceiver, IFluidCopiable {

	public FluidTank input;
	public FluidTank output;
	public int bufferedHeat;

	public float rot;
	public float prevRot;

	public TileEntityMachineHephaestus() {
		this.input = new FluidTank(Fluids.OIL, 24_000);
		this.output = new FluidTank(Fluids.HOTOIL, 24_000);
	}

	private int[] heat = new int[10];
	private long fissureScanTime;

	private AudioWrapper audio;

	ByteBuf buf;

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {

			if(this.buf != null)
				this.buf.release();
			this.buf = Unpooled.buffer();

			setupTanks();

			if(worldObj.getTotalWorldTime() % 20 == 0) {
				this.updateConnections();
			}

			int height = (int) (worldObj.getTotalWorldTime() % 10);
			int range = 7;
			int y = yCoord - 1 - height;

			heat[height] = 0;

			if(y >= 0) {
				for(int x = -range; x <= range; x++) {
					for(int z = -range; z <= range; z++) {
						heat[height] += heatFromBlock(xCoord + x, y, zCoord + z);
					}
				}
			}

			input.serialize(buf);

			heatFluid();

			output.serialize(buf);

			if(output.getFill() > 0) {
				for(DirPos pos : getConPos()) {
					this.sendFluid(output, worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				}
			}
			buf.writeInt(this.getTotalHeat());
			networkPackNT(150);

		} else {

			this.prevRot = this.rot;

			if(this.bufferedHeat > 0) {
				this.rot += 0.5F;

				if(worldObj.rand.nextInt(7) == 0) {
					double x = worldObj.rand.nextGaussian() * 2;
					double y = worldObj.rand.nextGaussian() * 3;
					double z = worldObj.rand.nextGaussian() * 2;
					worldObj.spawnParticle("cloud", xCoord + 0.5 + x, yCoord + 6 + y, zCoord + 0.5 + z, 0, 0, 0);
				}

				if(audio == null) {
					audio = MainRegistry.proxy.getLoopedSound("hbm:block.hephaestusRunning", xCoord, yCoord + 5F, zCoord, 0.75F, 10F, 1.0F);
					audio.startSound();
				}
			} else {
				if(audio != null) {
					audio.stopSound();
					audio = null;
				}
			}

			if(this.rot >= 360F) {
				this.prevRot -= 360F;
				this.rot -= 360F;
			}
		}
	}

	protected void heatFluid() {

		FluidType type = input.getTankType();

		if(type.hasTrait(FT_Heatable.class)) {
			FT_Heatable trait = type.getTrait(FT_Heatable.class);
			int heat = this.getTotalHeat();
			HeatingStep step = trait.getFirstStep();

			int inputOps = input.getFill() / step.amountReq;
			int outputOps = (output.getMaxFill() - output.getFill()) / step.amountProduced;
			int heatOps = heat / step.heatReq;
			int ops = Math.min(Math.min(inputOps, outputOps), heatOps);

			input.setFill(input.getFill() - step.amountReq * ops);
			output.setFill(output.getFill() + step.amountProduced * ops);
			worldObj.markTileEntityChunkModified(xCoord, yCoord, zCoord, this);
		}
	}

	protected void setupTanks() {

		FluidType type = input.getTankType();

		if(type.hasTrait(FT_Heatable.class)) {
			FT_Heatable trait = type.getTrait(FT_Heatable.class);

			if(trait.getEfficiency(HeatingType.HEATEXCHANGER) > 0) {
				FluidType outType = trait.getFirstStep().typeProduced;
				output.setTankType(outType);
				return;
			}
		}

		input.setTankType(Fluids.NONE);
		output.setTankType(Fluids.NONE);
	}

	protected int heatFromBlock(int x, int y, int z) {
		Block b = worldObj.getBlock(x, y, z);

		if(b == Blocks.lava || b == Blocks.flowing_lava)	return 5;
		if(b == ModBlocks.volcanic_lava_block)				return 150;

		if(b == ModBlocks.ore_volcano) {
			this.fissureScanTime = worldObj.getTotalWorldTime();
			return 300;
		}

		return 0;
	}

	public int getTotalHeat() {
		boolean fissure = worldObj.getTotalWorldTime() - this.fissureScanTime < 20;
		int heat = 0;

		for(int h : this.heat) {
			heat += h;
		}

		if(fissure) {
			heat *= 3;
		}

		return heat;
	}

	@Override
	public void serialize(ByteBuf buf) {
		buf.writeBytes(this.buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		input.deserialize(buf);
		output.deserialize(buf);

		this.bufferedHeat = buf.readInt();
	}

	private void updateConnections() {

		if(input.getTankType() == Fluids.NONE) return;

		for(DirPos pos : getConPos()) {
			this.trySubscribe(input.getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}

	private DirPos[] getConPos() {

		return new DirPos[] {
				new DirPos(xCoord + 2, yCoord, zCoord, Library.POS_X),
				new DirPos(xCoord - 2, yCoord, zCoord, Library.NEG_X),
				new DirPos(xCoord, yCoord, zCoord + 2, Library.POS_Z),
				new DirPos(xCoord, yCoord, zCoord - 2, Library.NEG_Z),
				new DirPos(xCoord + 2, yCoord + 11, zCoord, Library.POS_X),
				new DirPos(xCoord - 2, yCoord + 11, zCoord, Library.NEG_X),
				new DirPos(xCoord, yCoord + 11, zCoord + 2, Library.POS_Z),
				new DirPos(xCoord, yCoord + 11, zCoord - 2, Library.NEG_Z)
		};
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.input.readFromNBT(nbt, "0");
		this.output.readFromNBT(nbt, "1");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		this.input.writeToNBT(nbt, "0");
		this.output.writeToNBT(nbt, "1");
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] {input, output};
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {output};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {input};
	}

	@Override
	public boolean canConnect(FluidType type, ForgeDirection dir) {
		return dir != ForgeDirection.UNKNOWN && dir != ForgeDirection.UP && dir != ForgeDirection.DOWN;
	}

	@Override
	public void onChunkUnload() {
		super.onChunkUnload();

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

	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {

		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 3,
					yCoord,
					zCoord - 3,
					xCoord + 4,
					yCoord + 12,
					zCoord + 4
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
