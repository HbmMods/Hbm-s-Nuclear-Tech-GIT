package com.hbm.tileentity.machine;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.gas.BlockGasAir;
import com.hbm.config.GeneralConfig;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.util.fauxpointtwelve.BlockPos;

import api.hbm.energy.IEnergyUser;
import api.hbm.fluid.IFluidStandardReceiver;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;

public class TileEntityAirPump extends TileEntity implements IFluidStandardReceiver, INBTPacketReceiver {
	private static final int START_CONCENTRATION_VALUE = 15;
	private int cooldownTicks = 0;
	 private World customWorld;
	public static final int flucue = 100;

	public FluidTank tank;

	public TileEntityAirPump(World world) {
		tank = new FluidTank(Fluids.OXYGEN, 16000);
        checkedBlocks = new HashSet<>();
        breathableAirBlocks = new HashSet<>();
        MinecraftForge.EVENT_BUS.register(this);
        customWorld = world;
	}
    private Set<BlockPos> checkedBlocks;
    private Set<BlockPos> breathableAirBlocks;

    private final int MAX_BLOCK_CHECKS = 1000;



    public boolean isSealed() {
        return checkedBlocks.size() >= MAX_BLOCK_CHECKS;
    }
    @Override
    public void updateEntity() {
    	if(!worldObj.isRemote) {
    		BlockPos pos = new BlockPos(xCoord, yCoord, zCoord);
            checkRoomSeal(pos);
    	}
	}
    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event.world.isRemote) // Only run on the server side
            return;

        BlockPos blockPos = new BlockPos(event.x, event.y, event.z);
        if (isSealed() || checkedBlocks.contains(blockPos))
            return;

        checkedBlocks.clear();
        breathableAirBlocks.clear();
        checkRoomSeal(blockPos);
    }

    private void checkRoomSeal(BlockPos pos) {
        if (!checkedBlocks.contains(pos) && breathableAirBlocks.size() < MAX_BLOCK_CHECKS) {
            checkedBlocks.add(pos);
            if (customWorld.isAirBlock(pos.getX(), pos.getY(), pos.getZ())) {
                breathableAirBlocks.add(pos);
                placeBlockInSealedArea();
                System.out.println("added");
                for (int xOffset = -1; xOffset <= 1; xOffset++) {
                    for (int yOffset = -1; yOffset <= 1; yOffset++) {
                        for (int zOffset = -1; zOffset <= 1; zOffset++) {
                            BlockPos neighborPos = pos.add(xOffset, yOffset, zOffset);
                            checkRoomSeal(neighborPos);
                        }
                    }
                }
            }
        }
    }
    private void placeBlockInSealedArea() {
        for (BlockPos pos : checkedBlocks) {
            // Place the desired block at each position
            customWorld.setBlock(pos.getX(), pos.getY(), pos.getZ(), ModBlocks.asphalt);
        }
    }
	
	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.tank.readFromNBT(nbt, "at");

	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.tank.readFromNBT(nbt, "at");

	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		tank.writeToNBT(nbt, "at");

	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] {tank};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {tank};
	}
}




