package com.hbm.inventory.gui;

import com.hbm.inventory.SlotLayer;
import com.hbm.packet.GuiLayerPacket;
import com.hbm.packet.PacketDispatcher;

import net.minecraft.inventory.Container;

public abstract class GuiInfoContainerLayered extends GuiInfoContainer {

    private int currentLayer = 0;

    public GuiInfoContainerLayered(Container container) {
        super(container);
    }

    public void setLayer(int layer) {
        currentLayer = layer;
        for (Object o : inventorySlots.inventorySlots) {
            if(!(o instanceof SlotLayer)) continue;
            SlotLayer slot = (SlotLayer)o;

            slot.setLayer(layer);
        }
        
        PacketDispatcher.wrapper.sendToServer(new GuiLayerPacket(layer));
    }

    public int getLayer() {
        return currentLayer;
    }
    
}
