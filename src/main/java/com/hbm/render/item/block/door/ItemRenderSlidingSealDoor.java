package com.hbm.render.item.block.door;

import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import org.lwjgl.opengl.GL11;

public class ItemRenderSlidingSealDoor extends ItemRenderBase {
    @Override
    public void renderInventory() {
        GL11.glTranslated(0, -5, 0);
        GL11.glScaled(7, 7, 7);
    }

    @Override
    public void renderCommon() {
        bindTexture(ResourceManager.sliding_seal_door_tex);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        ResourceManager.sliding_seal_door.renderAll();
        GL11.glShadeModel(GL11.GL_FLAT);
    }
}
