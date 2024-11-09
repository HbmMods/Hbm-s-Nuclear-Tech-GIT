package com.hbm.tileentity.machine;

import java.io.IOException;
import java.util.HashSet;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.*;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;

public abstract class TileEntityMachinePumpBase extends TileEntityLoadedBase implements IFluidStandardTransceiver, IBufPacketReceiver, IConfigurableMachine, IFluidCopiable {

	public static final HashSet<Block> validBlocks = new HashSet();

	static {
		validBlocks.add(Blocks.grass);
		validBlocks.add(Blocks.dirt);
		validBlocks.add(Blocks.sand);
		validBlocks.add(Blocks.mycelium);
		validBlocks.add(ModBlocks.waste_earth);
		validBlocks.add(ModBlocks.dirt_dead);
		validBlocks.add(ModBlocks.dirt_oily);
		validBlocks.add(ModBlocks.sand_dirty);
		validBlocks.add(ModBlocks.sand_dirty_red);
	}

	public FluidTank water;

	public boolean isOn = false;
	public float rotor;
	public float lastRotor;
	public boolean onGround = false;
	public int groundCheckDelay = 0;

	public static int groundHeight = 70;
	public static int groundDepth = 4;
	public static int steamSpeed = 1_000;
	public static int electricSpeed = 10_000;

	@Override
	public String getConfigName() {
		return "waterpump";
	}

	@Override
	public void readIfPresent(JsonObject obj) {
		groundHeight = IConfigurableMachine.grab(obj, "I:groundHeight", groundHeight);
		groundDepth = IConfigurableMachine.grab(obj, "I:groundDepth", groundDepth);
		steamSpeed = IConfigurableMachine.grab(obj, "I:steamSpeed", steamSpeed);
		electricSpeed = IConfigurableMachine.grab(obj, "I:electricSpeed", electricSpeed);
	}

	@Override
	public void writeConfig(JsonWriter writer) throws IOException {
		writer.name("I:groundHeight").value(groundHeight);
		writer.name("I:groundDepth").value(groundDepth);
		writer.name("I:steamSpeed").value(steamSpeed);
		writer.name("I:electricSpeed").value(electricSpeed);
	}

	public void updateEntity() {

		if(!worldObj.isRemote) {

			for(DirPos pos : getConPos()) {
				if(water.getFill() > 0) this.sendFluid(water, worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}

			if(groundCheckDelay > 0) {
				groundCheckDelay--;
			} else {
				onGround = this.checkGround();
			}

			this.isOn = false;
			if(this.canOperate() && yCoord <= groundHeight && onGround) {
				this.isOn = true;
				this.operate();
			}

			networkPackNT(150);

		} else {

			this.lastRotor = this.rotor;
			if(this.isOn) this.rotor += 10F;

			if(this.rotor >= 360F) {
				this.rotor -= 360F;
				this.lastRotor -= 360F;

				MainRegistry.proxy.playSoundClient(xCoord, yCoord, zCoord, "hbm:block.steamEngineOperate", 0.5F, 0.75F);
				MainRegistry.proxy.playSoundClient(xCoord, yCoord, zCoord, "game.neutral.swim.splash", 1F, 0.5F);
			}
		}
	}

	protected boolean checkGround() {

		if(worldObj.provider.hasNoSky) return false;

		int validBlocks = 0;
		int invalidBlocks = 0;

		for(int x = -1; x <= 1; x++) {
			for(int y = -1; y >= -groundDepth; y--) {
				for(int z = -1; z <= 1; z++) {

					Block b = worldObj.getBlock(xCoord + x, yCoord + y, zCoord + z);

					if(y == -1 && !b.isNormalCube()) return false; // first layer has to be full solid

					if(this.validBlocks.contains(b)) validBlocks++;
					else invalidBlocks ++;
				}
			}
		}

		return validBlocks >= invalidBlocks; // valid block count has to be at least 50%
	}

	@Override
	public void serialize(ByteBuf buf) {
		buf.writeBoolean(this.isOn);
		buf.writeBoolean(this.onGround);
		water.serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		this.isOn = buf.readBoolean();
		this.onGround = buf.readBoolean();
		water.deserialize(buf);
	}

	protected abstract boolean canOperate();
	protected abstract void operate();

	protected DirPos[] getConPos() {
		return new DirPos[] {
				new DirPos(xCoord + 2, yCoord, zCoord, Library.POS_X),
				new DirPos(xCoord - 2, yCoord, zCoord, Library.NEG_X),
				new DirPos(xCoord, yCoord, zCoord + 2, Library.POS_Z),
				new DirPos(xCoord, yCoord, zCoord - 2, Library.NEG_Z)
		};
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] {water};
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {water};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[0];
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
					yCoord + 5,
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
	public FluidTank getTankToPaste() {
		return null;
	}
}
