package com.hbm.tileentity.machine;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
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
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;

public class TileEntityAirPump extends TileEntity implements IFluidStandardReceiver, INBTPacketReceiver {
    private static final int START_CONCENTRATION_VALUE = 15;
    private World customWorld;
    private Set<BlockPos> checkedBlocks;
    private Set<BlockPos> breathableAirBlocks;
    private boolean isSealed;
    private final int MAX_BLOCK_CHECKS = 1000;

    public FluidTank tank;

    public TileEntityAirPump(World world) {
        tank = new FluidTank(Fluids.OXYGEN, 16000);
        checkedBlocks = new HashSet<>();
        breathableAirBlocks = new HashSet<>();
        MinecraftForge.EVENT_BUS.register(this);
        customWorld = world;
    }

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event.world.isRemote) // Only run on the server side
            return;

        BlockPos blockPos = new BlockPos(event.x, event.y, event.z);
        if (isSealed || breathableAirBlocks.contains(blockPos))
            return;

        checkedBlocks.clear();
        breathableAirBlocks.clear();
        isSealed = false;
        checkRoomSeal(blockPos);
    }

    private void checkRoomSeal(BlockPos startBlockPos) {
        Queue<BlockPos> blocksToCheck = new LinkedList<>();
        blocksToCheck.add(startBlockPos);

        while (!blocksToCheck.isEmpty()) {
            if (checkedBlocks.size() >= MAX_BLOCK_CHECKS) {
                isSealed = false;
                return;
            }

            BlockPos pos = blocksToCheck.poll();
            if (!checkedBlocks.contains(pos)) {
                checkedBlocks.add(pos);
                boolean isAirBlock = customWorld.isAirBlock(pos.getX(), pos.getY(), pos.getZ());
                if (isAirBlock) {
                    breathableAirBlocks.add(pos);
                    for (int xOffset = -1; xOffset <= 1; xOffset++) {
                        for (int yOffset = -1; yOffset <= 1; yOffset++) {
                            for (int zOffset = -1; zOffset <= 1; zOffset++) {
                                BlockPos neighborPos = pos.add(xOffset, yOffset, zOffset);
                                if (!checkedBlocks.contains(neighborPos)) {
                                    blocksToCheck.add(neighborPos);
                                }
                            }
                        }
                    }
                }
            }
        }

        isSealed = true;
        placeBlocksInSealedArea();
    }

    private void placeBlocksInSealedArea() {
        for (BlockPos pos : breathableAirBlocks) {
            // Place breathable air block at each position
            customWorld.setBlock(pos.getX(), pos.getY(), pos.getZ(), ModBlocks.asphalt);
        }
    }

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {
            BlockPos pos = new BlockPos(xCoord, yCoord, zCoord);
            if (isSealed || checkedBlocks.contains(pos)) {
                // Handle sealed room logic here
            } else {
                checkRoomSeal(pos);
            }
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
        this.isSealed = nbt.getBoolean("isSealed");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        tank.writeToNBT(nbt, "at");
        nbt.setBoolean("isSealed", isSealed);
    }
    @Override
    public FluidTank[] getAllTanks() {
        return new FluidTank[]{tank};
    }

    @Override
    public FluidTank[] getReceivingTanks() {
        return new FluidTank[]{tank};
    }
}
