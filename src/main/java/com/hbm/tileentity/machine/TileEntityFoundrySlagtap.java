package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockDynamicSlag.TileEntitySlag;
import com.hbm.handler.threading.PacketThreading;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.util.Compat;

import api.hbm.block.ICrucibleAcceptor;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityFoundrySlagtap extends TileEntityFoundryOutlet implements ICrucibleAcceptor {

	@Override
	public boolean canAcceptPartialFlow(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack) {
		if(filter != null && (filter != stack.material ^ invertFilter)) return false;
		if(isClosed()) return false;
		if(side != ForgeDirection.getOrientation(this.getBlockMetadata()).getOpposite()) return false;

		Vec3 start = Vec3.createVectorHelper(x + 0.5, y - 0.125, z + 0.5);
		Vec3 end = Vec3.createVectorHelper(x + 0.5, y + 0.125 - 15, z + 0.5);

		MovingObjectPosition mop = world.func_147447_a(start, end, true, true, true);

		if(mop == null || mop.typeOfHit != mop.typeOfHit.BLOCK) {
			return false;
		}

		return true;
	}

	@Override
	public MaterialStack flow(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack) {

		if(stack == null || stack.material == null || stack.amount <= 0) {
			return null;
		}

		Vec3 start = Vec3.createVectorHelper(x + 0.5, y - 0.125, z + 0.5);
		Vec3 end = Vec3.createVectorHelper(x + 0.5, y + 0.125 - 15, z + 0.5);

		MovingObjectPosition mop = world.func_147447_a(start, end, true, true, true);

		if(mop == null || mop.typeOfHit != mop.typeOfHit.BLOCK) {
			return null;
		}

		Block hit = world.getBlock(mop.blockX, mop.blockY, mop.blockZ);
		Block above = world.getBlock(mop.blockX, mop.blockY + 1, mop.blockZ);

		boolean didFlow = false;

		if(hit == ModBlocks.slag) {
			TileEntitySlag tile = (TileEntitySlag) Compat.getTileStandard(world, mop.blockX, mop.blockY, mop.blockZ);
			if(tile.mat == stack.material) {
				int transfer = Math.min(tile.maxAmount - tile.amount, stack.amount);
				tile.amount += transfer;
				stack.amount -= transfer;
				didFlow = didFlow || transfer > 0;
				world.markBlockForUpdate(mop.blockX, mop.blockY, mop.blockZ);
				world.scheduleBlockUpdate(mop.blockX, mop.blockY, mop.blockZ, ModBlocks.slag, 1);
			}
		} else if(hit.isReplaceable(world, mop.blockX, mop.blockY, mop.blockZ)) {
			world.setBlock(mop.blockX, mop.blockY, mop.blockZ, ModBlocks.slag);
			TileEntitySlag tile = (TileEntitySlag) Compat.getTileStandard(world, mop.blockX, mop.blockY, mop.blockZ);
			tile.mat = stack.material;
			int transfer = Math.min(tile.maxAmount, stack.amount);
			tile.amount += transfer;
			stack.amount -= transfer;
			didFlow = didFlow || transfer > 0;
			world.markBlockForUpdate(mop.blockX, mop.blockY, mop.blockZ);
			world.scheduleBlockUpdate(mop.blockX, mop.blockY, mop.blockZ, ModBlocks.slag, 1);
		}

		if(stack.amount > 0 && above.isReplaceable(world, mop.blockX, mop.blockY + 1, mop.blockZ)) {
			world.setBlock(mop.blockX, mop.blockY + 1, mop.blockZ, ModBlocks.slag);
			TileEntitySlag tile = (TileEntitySlag) Compat.getTileStandard(world, mop.blockX, mop.blockY + 1, mop.blockZ);
			tile.mat = stack.material;
			int transfer = Math.min(tile.maxAmount, stack.amount);
			tile.amount += transfer;
			stack.amount -= transfer;
			didFlow = didFlow || transfer > 0;
			world.markBlockForUpdate(mop.blockX, mop.blockY + 1, mop.blockZ);
			world.scheduleBlockUpdate(mop.blockX, mop.blockY + 1, mop.blockZ, ModBlocks.slag, 1);
		}

		if(didFlow) {
			ForgeDirection dir = side.getOpposite();
			double hitY = mop.blockY;

			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "foundry");
			data.setInteger("color", stack.material.moltenColor);
			data.setByte("dir", (byte) dir.ordinal());
			data.setFloat("off", 0.375F);
			data.setFloat("base", 0F);
			data.setFloat("len", Math.max(1F, yCoord - (float) (Math.ceil(hitY))));
			PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(data, xCoord + 0.5D - dir.offsetX * 0.125, yCoord + 0.125, zCoord + 0.5D - dir.offsetZ * 0.125), new TargetPoint(worldObj.provider.dimensionId, xCoord + 0.5, yCoord, zCoord + 0.5, 50));
		}

		if(stack.amount <= 0) {
			stack = null;
		}

		return stack;
	}

	@Override public boolean canAcceptPartialPour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack) { return false; }
	@Override public MaterialStack pour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack) { return stack; }
}
