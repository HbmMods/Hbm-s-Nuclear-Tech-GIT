package com.hbm.render.item.block.door;

import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import org.lwjgl.opengl.GL11;

public class ItemRenderSecureAccessDoor extends ItemRenderBase {

    @Override
    public void renderInventory() {
        GL11.glTranslated(0, -4, 0);
        GL11.glScaled(2.4, 2.4, 2.4);
    }

    @Override
    public void renderCommon() {
        bindTexture(ResourceManager.secure_access_door_tex);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        ResourceManager.secure_access_door.renderAll();
        GL11.glShadeModel(GL11.GL_FLAT);
    }
}
