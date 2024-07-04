package com.hbm.inventory.gui;

import com.hbm.inventory.SlotLayer;

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
    }

    public int getLayer() {
        return currentLayer;
    }
    
}
