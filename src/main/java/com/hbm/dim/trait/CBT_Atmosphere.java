package com.hbm.dim.trait;

import java.util.ArrayList;
import java.util.List;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;


public class CBT_Atmosphere extends CelestialBodyTrait {

    public List<FluidEntry> fluids;
    public float pressure;

    public static class FluidEntry {
        public FluidType fluid;
        public float percentage;

        public FluidEntry(FluidType fluid, float percentage) {
            this.fluid = fluid;
            this.percentage = percentage;
        }
    }

    public CBT_Atmosphere() {
        fluids = new ArrayList<>();
    }

    public CBT_Atmosphere(float pressure, Object... fluidData) {
        this.pressure = pressure;
        fluids = new ArrayList<>();
        for (int i = 0; i < fluidData.length; i += 2) {
            FluidType fluid = (FluidType) fluidData[i];
            float percentage = (float) fluidData[i + 1];
            fluids.add(new FluidEntry(fluid, percentage));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        nbt.setFloat("pressure", pressure);
        NBTTagList fluidList = new NBTTagList();
        for (FluidEntry entry : fluids) {
            NBTTagCompound fluidTag = new NBTTagCompound();
            fluidTag.setInteger("type", entry.fluid.getID());
            fluidTag.setFloat("percentage", entry.percentage);
            fluidList.appendTag(fluidTag);
        }
        nbt.setTag("fluids", fluidList);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        pressure = nbt.getFloat("pressure");
        fluids = new ArrayList<>();
        NBTTagList fluidList = nbt.getTagList("fluids", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < fluidList.tagCount(); i++) {
            NBTTagCompound fluidTag = fluidList.getCompoundTagAt(i);
            FluidType fluid = Fluids.fromID(fluidTag.getInteger("type"));
            float percentage = fluidTag.getFloat("percentage");
            fluids.add(new FluidEntry(fluid, percentage));
        }
    }

    @Override
    public void writeToBytes(ByteBuf buf) {
        buf.writeFloat(pressure);
        buf.writeInt(fluids.size());
        for (FluidEntry entry : fluids) {
            buf.writeInt(entry.fluid.getID());
            buf.writeFloat(entry.percentage);
        }
    }

    @Override
    public void readFromBytes(ByteBuf buf) {
        pressure = buf.readFloat();
        int size = buf.readInt();
        fluids = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            FluidType fluid = Fluids.fromID(buf.readInt());
            float percentage = buf.readFloat();
            fluids.add(new FluidEntry(fluid, percentage));
        }
    }

    public List<Integer> getFluidColors() {
        List<Integer> colors = new ArrayList<>();
        for (FluidEntry entry : fluids) {
            colors.add(entry.fluid.getColor());
        }
        return colors;
    }
}
