package com.hbm.tileentity;

import com.hbm.interfaces.IControlReceiver;
import net.minecraft.nbt.NBTTagCompound;

public interface IFilterable extends IControlReceiver {
    void nextMode(int i);

    @Override
    default void receiveControl(NBTTagCompound data) {
        if(data.hasKey("slot")){
            setFilterContents(data);
        }
    }

    void setFilterContents(NBTTagCompound nbt);
}
