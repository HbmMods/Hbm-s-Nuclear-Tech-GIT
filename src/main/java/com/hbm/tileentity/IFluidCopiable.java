package com.hbm.tileentity;

import api.hbm.fluid.IFluidStandardTransceiver;
import com.hbm.interfaces.ICopiable;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.util.BobMathUtil;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.Arrays;

public interface IFluidCopiable extends ICopiable {
    /**
     * @return First type for the normal paste, second type for the alt paste, none if there is no alt paste support
     */
    default int[] getFluidIDToCopy(){
        IFluidStandardTransceiver tile = (IFluidStandardTransceiver) this;
        ArrayList<Integer> types = new ArrayList<>();

        if(tile.getReceivingTanks() != null && !tile.getReceivingTanks()[0].getTankType().hasNoID())
            types.add(tile.getReceivingTanks()[0].getTankType().getID());

        if(tile.getSendingTanks() != null && !tile.getSendingTanks()[0].getTankType().hasNoID())
            types.add(tile.getSendingTanks()[0].getTankType().getID());

        return BobMathUtil.intCollectionToArray(types);
    }

    default FluidTank getTankToPaste(){
        IFluidStandardTransceiver tile = (IFluidStandardTransceiver) this;
        return tile.getReceivingTanks() != null ? tile.getReceivingTanks()[0] : null;
    }

    @Override
    default NBTTagCompound getSettings(){
        NBTTagCompound tag = new NBTTagCompound();
        if(getFluidIDToCopy().length > 0)
            tag.setIntArray("fluidID", getFluidIDToCopy());
        return tag;
    }

    @Override
    default void pasteSettings(NBTTagCompound nbt, boolean alt) {
        if(getTankToPaste() != null) {
            int[] ids = nbt.getIntArray("fluidID");
            if(ids.length > 0) {
                int id = ids[alt ? 1 : 0];
                getTankToPaste().setTankType(Fluids.fromID(id));
            }
        }
    }

}
