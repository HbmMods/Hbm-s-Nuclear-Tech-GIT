package com.hbm.interfaces;

import net.minecraft.nbt.NBTTagCompound;

public interface ICopiable {

    NBTTagCompound getSettings();

    void pasteSettings(NBTTagCompound nbt, boolean alt);
}
