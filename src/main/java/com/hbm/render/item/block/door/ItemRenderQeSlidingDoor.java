package com.hbm.render.item.block.door;

import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import org.lwjgl.opengl.GL11;

public class ItemRenderQeSlidingDoor extends ItemRenderBase {

    @Override
    public void renderInventory() {
        GL11.glTranslated(0, -3.5, 0);
        GL11.glScaled(6, 6, 6);
    }

    @Override
    public void renderCommon() {
        bindTexture(ResourceManager.qe_sliding_door_tex);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        ResourceManager.qe_sliding_door.renderAll();
        GL11.glShadeModel(GL11.GL_FLAT);
    }
}
