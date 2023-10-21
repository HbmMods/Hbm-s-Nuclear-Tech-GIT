package com.hbm.util;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;

public class FogColorMessageHandler implements IMessageHandler<FogMessage, IMessage> {
    @Override
    public IMessage onMessage(FogMessage message, MessageContext ctx) {
        MainThreadQueue.enqueue(() -> {
            NBTTagCompound data = message.getData();
            float r = data.getFloat("r");
            float g = data.getFloat("g");
            float b = data.getFloat("b");
            
            SkyColorManager.currentSkyColor = Vec3.createVectorHelper(r, g, b);
            
            
        });
        return null;
    }
}

