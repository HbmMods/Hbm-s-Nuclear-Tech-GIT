package com.hbm.render.item.block.door;

import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import org.lwjgl.opengl.GL11;

public class ItemRenderWaterDoor extends ItemRenderBase {

    @Override
    public void renderInventory() {
        GL11.glTranslated(0, -4, 0);
        GL11.glScaled(4, 4, 4);
    }

    @Override
    public void renderCommon() {
        bindTexture(ResourceManager.water_door_tex);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        ResourceManager.water_door.renderAll();
        GL11.glShadeModel(GL11.GL_FLAT);
    }
}
